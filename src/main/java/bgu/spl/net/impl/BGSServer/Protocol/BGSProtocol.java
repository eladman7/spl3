package bgu.spl.net.impl.BGSServer.Protocol;

import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.BGSServer.DBModels.DB;


public class BGSProtocol implements BidiMessagingProtocol<MessageContainer> {
    private int connectionId;
    private Connections<MessageContainer> connections;
    private DB db;
    private boolean shouldTerminate;

    public BGSProtocol(DB db) {
        this.db = db;
    }

    @Override
    public void start(int connectionId, Connections<MessageContainer> connections) {
        this.connectionId = connectionId;
        this.connections = connections;
        this.shouldTerminate = false;
    }

    /**
     * execute command from message container
     * send message container with result to connections
     * @param msg - message container
     */
    @Override
    public void process(MessageContainer msg) {
        Command<ExecutionInfo> cmd = msg.getCommand();
        ExecutionInfo execInfo = new ExecutionInfo(connections, connectionId, db);
        cmd.execute(execInfo);
        if (execInfo.isLogout()){
            shouldTerminate = true;
        }
    }


    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }

}
