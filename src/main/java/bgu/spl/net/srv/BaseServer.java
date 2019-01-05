package bgu.spl.net.srv;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.srv.bidi.ConnectionHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public abstract class BaseServer<T> implements Server {

    private final int port;
    private final Supplier<BidiMessagingProtocol<T>> protocolFactory;
    private final Supplier<MessageEncoderDecoder<T>> encdecFactory;
    private ServerSocket sock;
    private final AtomicInteger indexDispatcher;
    private Map<Integer, ConnectionHandler<T>> clientsMap;
    private Connections<T> connections;

    public BaseServer(
            int port,
            Supplier<BidiMessagingProtocol<T>> protocolFactory,
            Supplier<MessageEncoderDecoder<T>> encdecFactory) {
        this.clientsMap = new ConcurrentHashMap<>();
        this.connections = new ConnectionsImpl<>(clientsMap);
        this.port = port;
        this.protocolFactory = protocolFactory;
        this.encdecFactory = encdecFactory;
        this.indexDispatcher = new AtomicInteger(0);
        this.sock = null;
    }

    @Override
    public void serve() {

        try (ServerSocket serverSock = new ServerSocket(port)) {
            System.out.println("Server started");

            this.sock = serverSock; //just to be able to close

            while (!Thread.currentThread().isInterrupted()) {

                Socket clientSock = serverSock.accept();

                BidiMessagingProtocol<T> protocol = protocolFactory.get();
                int connectionId = indexDispatcher.getAndIncrement();
                protocol.start(connectionId, connections);

                MessageEncoderDecoder<T> encoderDecoder = encdecFactory.get();

                BlockingConnectionHandler<T> handler = new BlockingConnectionHandler<>(
                        clientSock, encoderDecoder, protocol);

                clientsMap.put(connectionId, handler);
                execute(handler);
            }
        } catch (IOException ex) {
        }

        System.out.println("server closed!!!");
    }

    @Override
    public void close() throws IOException {
        if (sock != null)
            sock.close();
    }

    protected abstract void execute(BlockingConnectionHandler<T> handler);

}
