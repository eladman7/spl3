package bgu.spl.net.impl.rci.CommandModels;

import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.DBModels.DB;
import bgu.spl.net.impl.rci.DBModels.User;
import bgu.spl.net.impl.rci.ExecutionInfo;


public class UserListCommand extends Responder implements Command<ExecutionInfo> {
    private static final short opcode = 7;

    @Override
    public void execute(ExecutionInfo execInfo) {
        DB db = execInfo.getDb();
        User me = db.getUser(execInfo.getConnId());
        if (me != null && me.isLoggedIn()) {
            synchronized (db.getUsersLock()){ // in case a new user joined in the middle
                ack(execInfo, opcode, db.getUsers().stream().map(User::getUsername).toArray(), this);
            }

        } else {
            error(execInfo, opcode);
        }
    }
}

