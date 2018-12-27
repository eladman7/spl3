package bgu.spl.net.impl.rci.CommandModels;

import bgu.spl.net.api.MessageContainer;
import bgu.spl.net.api.Messages.Ack;
import bgu.spl.net.api.Messages.Error;
import bgu.spl.net.api.Messages.Response;
import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.DBModels.DB;


public class RegisterCommand<D> implements Command<DB> {
    private static final short opcode = 1;
    private String username;
    private String password;

    public RegisterCommand(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Response execute(DB db) {
        synchronized (db.getUsersLock()){
            if (db.getUser(username) == null){
                db.addUser(username, password);
                return new Ack(opcode, null);
            }else {
                return new Error(opcode);
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
