package bgu.spl.net.impl.BGSServer.CommandModels;

import bgu.spl.net.impl.BGSServer.Protocol.Command;
import bgu.spl.net.impl.BGSServer.DBModels.DB;
import bgu.spl.net.impl.BGSServer.DBModels.User;
import bgu.spl.net.impl.BGSServer.Protocol.ExecutionInfo;


public class RegisterCommand extends Responder implements Command<ExecutionInfo> {
    private static final short opcode = 1;
    private String username;
    private String password;

    public RegisterCommand(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void execute(ExecutionInfo execInfo) {
        System.out.println("inside RegisterCommand.execute()");
        DB db = execInfo.getDb();

        User me = db.getUser(execInfo.getConnId());
        if (me != null && me.isLoggedIn()){
            error(execInfo, opcode);
        } else {
            synchronized (db.getUsersLock()) { // in case 2 clients sends the same thing together
                if (db.getUser(username) == null) {
                    db.addUser(username, password);
                    ack(execInfo, opcode, null, this);
                } else {
                    error(execInfo, opcode);
                }
            }
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
