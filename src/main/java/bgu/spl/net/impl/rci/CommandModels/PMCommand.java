package bgu.spl.net.impl.rci.CommandModels;

import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.DBModels.DB;
import bgu.spl.net.impl.rci.DBModels.User;
import bgu.spl.net.impl.rci.ExecutionInfo;


public class PMCommand extends Responder implements Command<ExecutionInfo> {
    private String toUsername;
    private String message;
    private static final short opcode = 6;

    public PMCommand(String toUsername, String message) {
        this.toUsername = toUsername;
        this.message = message;
    }

    @Override
    public void execute(ExecutionInfo execInfo) {
        DB db = execInfo.getDb();
        User to = db.getUser(toUsername);
        User me = db.getUser(execInfo.getConnId());
        if (me.isLoggedIn() && to != null){
            notifyPrivate(execInfo, me.getUsername(), to.getConnectionId(), message);
            db.addPrivateMessage(message, me, to);
            // todo ack here?
        }else {
            error(execInfo, opcode);
        }
    }
}

