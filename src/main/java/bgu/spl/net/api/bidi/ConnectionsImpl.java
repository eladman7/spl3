package bgu.spl.net.api.bidi;

import bgu.spl.net.srv.bidi.ConnectionHandler;
import java.util.Map;

public class ConnectionsImpl<T> implements Connections<T> {
    private Map<Integer, ConnectionHandler<T>> clientsMap;

    public ConnectionsImpl(Map<Integer, ConnectionHandler<T>> clientsMap) {
        this.clientsMap = clientsMap;
    }

    @Override
    public boolean send(int connectionId, T msg) {
        // no need for sync because handler.send is thread safe
        if (clientsMap.containsKey(connectionId)) {
            clientsMap.get(connectionId).send(msg);
            return true;
        } else return false;
    }

    @Override
    public void broadcast(T msg) {
        // no need for sync because handler.send is thread safe
        clientsMap.values().forEach(handler -> handler.send(msg));
    }

    @Override
    public void disconnect(int connectionId) {
        // no need for sync because clientsMap is concurrent
        clientsMap.remove(connectionId);
    }

}
