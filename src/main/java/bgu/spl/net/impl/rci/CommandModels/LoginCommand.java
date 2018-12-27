package bgu.spl.net.impl.rci.CommandModels;

import bgu.spl.net.api.Messages.Ack;
import bgu.spl.net.api.Messages.Error;
import bgu.spl.net.api.Messages.Response;
import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.DBModels.DB;
import bgu.spl.net.impl.rci.DBModels.User;


public class LoginCommand<D> implements Command<D> {
    private static final short opcode = 2;
    private String username;
    private String password;

    public LoginCommand(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Response execute(D _db) {
        DB db = (DB) _db;
        User user = db.getUser(username);
        if (user != null && password.equals(user.getPassword()) && !user.isLoggedIn()){
            user.setLoggedIn(true);
            // todo add user to connections somehow
            return new Ack(opcode, null);
        }else {
            return new Error(opcode);
        }
    }
}
