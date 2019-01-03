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
            notifyPrivate(execInfo, me.getUsername(), to.getConnectionId(), message, this);
            db.addPrivateMessage(message, me, to);
            ack(execInfo, opcode, null, this);
        }else {
            System.out.println("error: me.isLoggedIn()="+me.isLoggedIn()+" (to != null)="+(to != null));
            error(execInfo, opcode);
        }
    }
}

