package bgu.spl.net.impl.BGSServer.CommandModels;

import bgu.spl.net.api.MessageContainer;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.BGSServer.Command;
import bgu.spl.net.impl.BGSServer.DBModels.DB;
import bgu.spl.net.impl.BGSServer.DBModels.User;
import bgu.spl.net.impl.BGSServer.ExecutionInfo;


public class LogoutCommand extends Responder implements Command<ExecutionInfo>{
    private static final short opcode = 3;

    @Override
    public void execute(ExecutionInfo execInfo) {
        DB db = execInfo.getDb();
        Connections<MessageContainer> connections = execInfo.getConnections();

        User user = db.getUser(execInfo.getConnId());
        if (user != null && user.isLoggedIn()){
            user.setLoggedIn(false);
            user.setConnectionId(-1);
            synchronized (db.getPostsLock()) { // in case we are writing a message/post to him at the moment
                ack(execInfo, opcode, null, this);
                connections.disconnect(execInfo.getConnId());
            }

        }else {
            error(execInfo, opcode);
        }

    }
}
