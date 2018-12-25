package bgu.spl.net.api.bidi;

import bgu.spl.net.srv.bidi.ConnectionHandler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConnectionsImp<T> implements Connections<T> {
    private ConcurrentMap<Integer, ConnectionHandler<T>> clientsMap;

    public ConnectionsImp() {
        this.clientsMap = new ConcurrentHashMap<>();
    }

    @Override
    public boolean send(int connectionId, T msg) {
        return false;
    }

    @Override
    public void broadcast(T msg) {

    }

    @Override
    public void disconnect(int connectionId) {

    }

    public void addConnection(int connectionId, ConnectionHandler<T> connectionHandler) {
        clientsMap.put(connectionId, connectionHandler);
    }

}
