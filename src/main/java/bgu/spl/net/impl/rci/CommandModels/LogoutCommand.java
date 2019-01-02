package bgu.spl.net.impl.rci.CommandModels;

import bgu.spl.net.api.MessageContainer;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.DBModels.DB;
import bgu.spl.net.impl.rci.DBModels.User;
import bgu.spl.net.impl.rci.ExecutionInfo;


public class LogoutCommand extends Responder implements Command<ExecutionInfo>{
    private static final short opcode = 3;

    @Override
    public void execute(ExecutionInfo execInfo) {
        DB db = execInfo.getDb();
        Connections<MessageContainer> connections = execInfo.getConnections();

        User user = db.getUser(execInfo.getConnId());
        if (user.isLoggedIn()){
            user.setLoggedIn(false);
            user.setConnectionId(-1);
            connections.disconnect(execInfo.getConnId());
            ack(execInfo, opcode, null, this);
        }else {
            error(execInfo, opcode);
        }

    }
}
