package bgu.spl.net.impl.rci;

import bgu.spl.net.api.MessageContainer;
import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.api.bidi.Connections;

import java.io.Serializable;

// T represents MessageContainer
// D represents Shared DB Obj
public class RemoteCommandInvocationProtocol<T extends MessageContainer,D> implements BidiMessagingProtocol<T> {
    private int connectionId;
    private Connections<T> connections;
    private D arg;

    public RemoteCommandInvocationProtocol(D arg) {
        this.arg = arg;
    }

    @Override
    public void start(int connectionId, Connections<T> connections) {
        this.connectionId = connectionId;
        this.connections = connections;
    }

    /**
     * execute command from message container
     * send message container with result to connetions
     * @param msg - message container
     */
    @Override
    public void process(T msg) {
        Command<D> cmd = msg.getCommand();
        Serializable executionResult = cmd.execute(arg);
        msg.setResult(executionResult);
        connections.send(connectionId, msg);
    }



    @Override
    public boolean shouldTerminate() {
        return false;
    }

}
