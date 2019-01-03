package bgu.spl.net.impl.rci.CommandModels;

import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.DBModels.DB;
import bgu.spl.net.impl.rci.DBModels.User;
import bgu.spl.net.impl.rci.ExecutionInfo;

import java.util.List;
import java.util.stream.Collectors;

public class UserListCommand extends Responder implements Command<ExecutionInfo> {
    private static final short opcode = 7;

    @Override
    public void execute(ExecutionInfo execInfo) {
        DB db = execInfo.getDb();
        User me = db.getUser(execInfo.getConnId());
        if (me.isLoggedIn()) {
            List<String> userNames = db.getUsers().stream()
                    .map(User::getUsername)
                    .collect(Collectors.toList());
            ack(execInfo, opcode, userNames.toArray(), this);
        } else {
            error(execInfo, opcode);
        }
    }
}

