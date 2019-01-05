package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.api.MessageContainer;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.BGSServer.DBModels.DB;

public class ExecutionInfo {
    private Connections<MessageContainer> connections;
    private int connId;
    private DB db;
    private boolean logoutSuccessful;

    public boolean isLogout() {
        return logoutSuccessful;
    }

    public void setLogout(boolean newState) {
        this.logoutSuccessful = newState;
    }


    public ExecutionInfo(Connections<MessageContainer> connections, int connId, DB db) {
        this.connections = connections;
        this.connId = connId;
        this.db = db;
        this.logoutSuccessful = false;
    }

    public Connections<MessageContainer> getConnections() {
        return connections;
    }


    public int getConnId() {
        return connId;
    }

    public DB getDb() {
        return db;
    }

    public void setDb(DB db) {
        this.db = db;
    }
}
