package bgu.spl.net.impl.rci.CommandModels;

import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.DBModels.DB;
import bgu.spl.net.impl.rci.ExecutionInfo;


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
        DB db = execInfo.getDb();

        synchronized (db.getUsersLock()) {
            if (db.getUser(username) == null) {
                db.addUser(username, password);
                ack(execInfo, opcode, null, this);
            } else {
                error(execInfo, opcode);
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
