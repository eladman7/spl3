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
            if (clientsMap.containsKey(connectionId)) {
                clientsMap.get(connectionId).send(msg);
                return true;
            } else return false;
        }
    }

    @Override
    public void broadcast(T msg) {
        synchronized (lockMap) {
            clientsMap.values().forEach(value -> value.send(msg));
        }
    }

    @Override
    public void disconnect(int connectionId) {
        synchronized (lockMap) {
            clientsMap.remove(connectionId);
        }
    }

}
