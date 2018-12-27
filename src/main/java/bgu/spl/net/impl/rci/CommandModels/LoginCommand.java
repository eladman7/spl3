package bgu.spl.net.impl.rci.CommandModels;

import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.DBModels.DB;
import bgu.spl.net.impl.rci.DBModels.User;
import bgu.spl.net.impl.rci.ExecutionInfo;


public class LoginCommand extends Responder implements Command<ExecutionInfo> {
    private static final short opcode = 2;
    private String username;
    private String password;

    public LoginCommand(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void execute(ExecutionInfo execInfo) {
        DB db = execInfo.getDb();
        User user = db.getUser(username);
        if (user != null && password.equals(user.getPassword()) && !user.isLoggedIn()){
            user.setLoggedIn(true);
            user.setConnectionId(execInfo.getConnId());
            ack(execInfo, opcode, null);
        }else {
            error(execInfo, opcode);
        }
    }
}
