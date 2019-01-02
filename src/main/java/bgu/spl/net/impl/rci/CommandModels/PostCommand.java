package bgu.spl.net.impl.rci.CommandModels;


import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.DBModels.DB;
import bgu.spl.net.impl.rci.DBModels.User;
import bgu.spl.net.impl.rci.ExecutionInfo;

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
        if (me.isLoggedIn()){
            db.addPost(postMessage, me);
            Set<Integer> toNotifyConnIds = new HashSet<>();

            Queue<User> followers = me.getMyFollowers();
            updateFollowers(toNotifyConnIds, followers);
            updateTagged(db, toNotifyConnIds);

            for(int connId: toNotifyConnIds){
                notifyPublic(execInfo, me.getUsername(), connId, postMessage, this);
            }
            // todo ack here?
        }else {
            error(execInfo, opcode);
        }


    }

    private void updateTagged(DB db, Set<Integer> toNotifyConnIds) {
        for (User user: db.getUsers()){
            if (postMessage.contains("@" + user.getUsername())){
                toNotifyConnIds.add(user.getConnectionId());
            }
        }
    }

    private void updateFollowers(Set<Integer> toNotifyConnIds, Queue<User> followers) {
        for (User user: followers){
            toNotifyConnIds.add(user.getConnectionId());
        }
    }
}

