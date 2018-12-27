package bgu.spl.net.impl.rci;

import bgu.spl.net.api.MessageContainer;
import bgu.spl.net.api.Messages.Response;
import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.api.bidi.Connections;

import java.io.Serializable;

// T represents MessageContainer
// D represents Shared DBModels Obj
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
        Response executionResult = cmd.execute(arg);
        Serializable result = null;
        msg.setResult(result);
        connections.send(connectionId, msg);
    }



    @Override
    public boolean shouldTerminate() {
        return false;
    }

}
