package bgu.spl.net.impl.rci;

import bgu.spl.net.api.ResponseContainer;
import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.api.bidi.Connections;

import java.io.Serializable;

public class RemoteCommandInvocationProtocol<T,D> implements BidiMessagingProtocol<T> {
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

    @Override
    public void process(ResponseContainer<T> msg) {
        Serializable executionResult = ((Command<D>) msg).execute(arg);

        connections.send(connectionId, (T)executionResult);
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }

}
