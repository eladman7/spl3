package bgu.spl.net.impl.BGSServer.CommandModels;

import bgu.spl.net.impl.BGSServer.Command;
import bgu.spl.net.impl.BGSServer.DBModels.DB;
import bgu.spl.net.impl.BGSServer.DBModels.User;
import bgu.spl.net.impl.BGSServer.ExecutionInfo;


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
        if (me != null && me.isLoggedIn() && to != null){

            synchronized (db.getPostsLock()){ // in case the user leaves in the middle of this
                if (to.isLoggedIn()){
                    notifyPrivate(execInfo, me.getUsername(), to.getConnectionId(), message, this);
                    db.addPrivateMessage(message, me, to);
                }
                else{
                    db.addPendingMessage(message, me, to); // will be handled later
                }
            }

            ack(execInfo, opcode, null, this);
        }else {
//            System.out.println("error: me.isLoggedIn()="+me.isLoggedIn()+" (to != null)="+(to != null));
            error(execInfo, opcode);
        }
    }
}

