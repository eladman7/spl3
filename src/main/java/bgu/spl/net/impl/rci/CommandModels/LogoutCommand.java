package bgu.spl.net.impl.rci.CommandModels;

import bgu.spl.net.api.Messages.Ack;
import bgu.spl.net.api.Messages.Error;
import bgu.spl.net.api.Messages.Response;
import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.DBModels.DB;
import bgu.spl.net.impl.rci.DBModels.User;


public class LogoutCommand<D> implements Command<D> {
    private static final short opcode = 3;

    @Override
    public Response execute(D _db) {
        DB db = (DB) _db;
        // todo figure how to get the username here
        String username = "";
        User user = db.getUser(username);
        if (user.isLoggedIn()){
            user.setLoggedIn(false);
            // todo do somthing with connections as well
            return new Ack(opcode, null);
        }else {
            return new Error(opcode);
        }

    }
}
