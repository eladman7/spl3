package bgu.spl.net.api.bidi;

import bgu.spl.net.srv.bidi.ConnectionHandler;

import java.util.Map;

public class ConnectionsImpl<T> implements Connections<T> {
    private Map<Integer, ConnectionHandler<T>> clientsMap;
    private final Object lockMap = new Object();

    public ConnectionsImpl(Map<Integer, ConnectionHandler<T>> clientsMap) {
        this.clientsMap = clientsMap;
    }

    @Override
    public boolean send(int connectionId, T msg) {
        synchronized (lockMap) {
            clientsMap.get(connectionId).send(msg);
        }
        return true;
    }

    @Override
    public void broadcast(T msg) {

    }

    @Override
    public void disconnect(int connectionId) {
        synchronized (clientsMap.get(connectionId)) {
            clientsMap.remove(connectionId);
        }
    }

}
