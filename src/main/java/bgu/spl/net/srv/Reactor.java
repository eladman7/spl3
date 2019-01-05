package bgu.spl.net.srv;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.srv.bidi.ConnectionHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class Reactor<T> implements Server{

    private final int port;
    private final Supplier<BidiMessagingProtocol<T>> protocolFactory;
    private final Supplier<MessageEncoderDecoder<T>> encoderDecoderFactory;
    private final ActorThreadPool pool;
    private Selector selector;
    private Connections<T> connections;
    private Thread selectorThread;
    private final ConcurrentLinkedQueue<Runnable> selectorTasks = new ConcurrentLinkedQueue<>();
    private Map<Integer, ConnectionHandler<T>> clientsMap;
    private final AtomicInteger indexDispatcher;

    public Reactor(
            int numThreads,
            int port,
            Supplier<BidiMessagingProtocol<T>> protocolFactory,
            Supplier<MessageEncoderDecoder<T>> encoderDecoderFactory) {
        this.pool = new ActorThreadPool(numThreads);
        this.port = port;
        this.protocolFactory = protocolFactory;
        this.encoderDecoderFactory = encoderDecoderFactory;
        this.clientsMap = new ConcurrentHashMap<>();
        this.connections = new ConnectionsImpl<>(clientsMap);
        this.indexDispatcher = new AtomicInteger(0);
    }

    @Override
    public void serve() {
        selectorThread = Thread.currentThread();
        try (Selector selector = Selector.open();
             ServerSocketChannel serverSock = ServerSocketChannel.open()) {

            this.selector = selector; //just to be able to close

            serverSock.bind(new InetSocketAddress(port));
            serverSock.configureBlocking(false);
            serverSock.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server started");

            while (!Thread.currentThread().isInterrupted()) {

                selector.select();
                runSelectionThreadTasks();

                for (SelectionKey key : selector.selectedKeys()) {

                    if (!key.isValid()) {
                        continue;
                    } else if (key.isAcceptable()) {
                        handleAccept(serverSock, selector);
                    } else {
                        handleReadWrite(key);
                    }
                }

                selector.selectedKeys().clear(); //clear the selected keys set so that we can know about new events

            }

        } catch (ClosedSelectorException ex) {
            //do nothing - server was requested to be closed
        } catch (IOException ex) {
            //this is an error
            ex.printStackTrace();
        }

        System.out.println("server closed!!!");
        pool.shutdown();
    }

    /*package*/ void updateInterestedOps(SocketChannel chan, int ops) {
        final SelectionKey key = chan.keyFor(selector);
        if (Thread.currentThread() == selectorThread) {
            key.interestOps(ops);
        } else {
            selectorTasks.add(() -> {
                key.interestOps(ops);
            });
            selector.wakeup();
        }
    }


    private void handleAccept(ServerSocketChannel serverChan, Selector selector) throws IOException {
        SocketChannel clientChan = serverChan.accept();
        clientChan.configureBlocking(false);
        int connectionId = indexDispatcher.getAndIncrement();
        BidiMessagingProtocol<T> protocol = protocolFactory.get();
        protocol.start(connectionId, connections);
        MessageEncoderDecoder<T> codec = encoderDecoderFactory.get();

        final NonBlockingConnectionHandler<T> handler = new NonBlockingConnectionHandler<>(
                codec,
                protocol,
                clientChan,
                this);
        clientsMap.put(connectionId, handler);
        clientChan.register(selector, SelectionKey.OP_READ, handler);
    }

    private void handleReadWrite(SelectionKey key) {
        @SuppressWarnings("unchecked")
        NonBlockingConnectionHandler<T> handler = (NonBlockingConnectionHandler<T>) key.attachment();

        if (key.isReadable()) {
            Runnable task = handler.continueRead();
            if (task != null) {
                pool.submit(handler, task);
            }
        }

        if (key.isValid() && key.isWritable()) {
            handler.continueWrite();
        }
    }

    private void runSelectionThreadTasks() {
        while (!selectorTasks.isEmpty()) {
            selectorTasks.remove().run();
        }
    }

    @Override
    public void close() throws IOException {
        selector.close();
    }

}
