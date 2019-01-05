package bgu.spl.net.impl.BGSServer.CommandModels;

import bgu.spl.net.impl.BGSServer.Command;
import bgu.spl.net.impl.BGSServer.DBModels.DB;
import bgu.spl.net.impl.BGSServer.DBModels.Post;
import bgu.spl.net.impl.BGSServer.DBModels.PrivateMessage;
import bgu.spl.net.impl.BGSServer.DBModels.User;
import bgu.spl.net.impl.BGSServer.ExecutionInfo;

import java.util.List;
import java.util.stream.Collectors;


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
        synchronized (db.getUsersLock()){
            User user = db.getUser(username);
            if (user != null && password.equals(user.getPassword()) && !user.isLoggedIn()){
                user.setLoggedIn(true);
                user.setConnectionId(execInfo.getConnId());
                ack(execInfo, opcode, null, this);
                sendPendingMessages(execInfo, user);
            }else {
                error(execInfo, opcode);
            }
        }

    }

    private void sendPendingMessages(ExecutionInfo execInfo, User me) {
        DB db = execInfo.getDb();
        List<PrivateMessage> pendingMessages = db.getPrivateMessages().stream().
                filter(x -> x.isPending() && x.getTo().equals(me))
                .collect(Collectors.toList());
        for (PrivateMessage message: pendingMessages){
            notifyPrivate(
                    execInfo, message.getFrom().getUsername(),
                    execInfo.getConnId(), message.getText(), this);
            message.setPending(false);
        }
        for(Post post:me.getPendingPosts()){
            notifyPublic(
                    execInfo, post.getFrom().getUsername(),
                    execInfo.getConnId(), post.getText(), this);
        }
        me.clearPendingPost();


    }
}
