package bgu.spl.net.impl.rci;

import bgu.spl.net.api.MessageContainer;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.rci.DBModels.DB;

public class ExecutionInfo {
    private Connections<MessageContainer> connections;
    private int connId;
    private DB db;
    private MessageContainer resultMessage;

    public MessageContainer getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(MessageContainer resultMessage) {
        this.resultMessage = resultMessage;
    }

    public ExecutionInfo(Connections<MessageContainer> connections, int connId, DB db) {
        this.connections = connections;
        this.connId = connId;
        this.db = db;
    }

    public Connections<MessageContainer> getConnections() {
        return connections;
    }

    public void setConnections(Connections<MessageContainer> connections) {
        this.connections = connections;
    }

    public int getConnId() {
        return connId;
    }

    public void setConnId(int connId) {
        this.connId = connId;
    }

    public DB getDb() {
        return db;
    }

    public void setDb(DB db) {
        this.db = db;
    }
}
