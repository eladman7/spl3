package bgu.spl.net.impl.BGSServer.CommandModels;


import bgu.spl.net.impl.BGSServer.Command;
import bgu.spl.net.impl.BGSServer.DBModels.DB;
import bgu.spl.net.impl.BGSServer.DBModels.Post;
import bgu.spl.net.impl.BGSServer.DBModels.User;
import bgu.spl.net.impl.BGSServer.ExecutionInfo;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;


public class PostCommand extends Responder implements Command<ExecutionInfo> {
    private String postMessage;
    private static final short opcode = 5;

    public PostCommand(String postMessage) {
        this.postMessage = postMessage;
    }

    @Override
    public void execute(ExecutionInfo execInfo) {
        DB db = execInfo.getDb();
        User me = db.getUser(execInfo.getConnId());
        if (me != null && me.isLoggedIn()){
            Post newPost = db.addPost(postMessage, me);
            Set<String> toNotifyUserNames = new HashSet<>();

            updateFollowers(toNotifyUserNames, me.getMyFollowers());
            updateTagged(db, toNotifyUserNames);

            for(String username: toNotifyUserNames){
                User user = db.getUser(username);
                synchronized (user.getUserLock()){ // let a tagged user get a message before he is logged out
                    if (user.isLoggedIn()){
                        notifyPublic(execInfo, me.getUsername(), user.getConnectionId(), postMessage, this);
                    }else {
                        user.addPendingPost(newPost);
                    }
                }

            }
            ack(execInfo, opcode, null, this);
        }else {
            error(execInfo, opcode);
        }


    }

    private void updateTagged(DB db, Set<String> toNotifyUserNames) {
        for (User user: db.getUsers()){
            if (postMessage.contains("@" + user.getUsername())){
                toNotifyUserNames.add(user.getUsername());
            }
        }
    }

    private void updateFollowers(Set<String> toNotifyUserNames, Queue<User> followers) {
        for (User user: followers){
            toNotifyUserNames.add(user.getUsername());
        }
    }
}

