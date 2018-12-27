package bgu.spl.net.impl.rci;

import bgu.spl.net.api.MessageContainer;
import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.rci.DBModels.DB;


public class RemoteCommandInvocationProtocol implements BidiMessagingProtocol<MessageContainer> {
    private int connectionId;
    private Connections<MessageContainer> connections;
    private DB db;

    public RemoteCommandInvocationProtocol(DB db) {
        this.db = db;
    }

    @Override
    public void start(int connectionId, Connections<MessageContainer> connections) {
        this.connectionId = connectionId;
        this.connections = connections;
    }

    /**
     * execute command from message container
     * send message container with result to connetions
     * @param msg - message container
     */
    @Override
    public void process(MessageContainer msg) {
        Command<ExecutionInfo> cmd = msg.getCommand();
        ExecutionInfo execInfo = new ExecutionInfo(connections, connectionId, db);
        cmd.execute(execInfo);
    }



    @Override
    public boolean shouldTerminate() {
        return false;
    }

}
