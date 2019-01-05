package bgu.spl.net.impl.BGSServer.CommandModels;

import bgu.spl.net.impl.BGSServer.Protocol.MessageContainer;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.BGSServer.Protocol.Command;
import bgu.spl.net.impl.BGSServer.DBModels.DB;
import bgu.spl.net.impl.BGSServer.DBModels.User;
import bgu.spl.net.impl.BGSServer.Protocol.ExecutionInfo;


public class LogoutCommand extends Responder implements Command<ExecutionInfo>{
    private static final short opcode = 3;

    @Override
    public void execute(ExecutionInfo execInfo) {
        DB db = execInfo.getDb();
        Connections<MessageContainer> connections = execInfo.getConnections();

        User user = db.getUser(execInfo.getConnId());
        if (user != null && user.isLoggedIn()){
            synchronized (user.getUserLock()) { // in case the user has just received a message/post,
                                                // he will get it and then logout
                user.setLoggedIn(false);
                user.setConnectionId(-1);
                ack(execInfo, opcode, null, this);
                connections.disconnect(execInfo.getConnId());
                execInfo.setLogout(true);
            }

        }else {
            error(execInfo, opcode);
        }

    }
}
