package bgu.spl.net.impl.rci.CommandModels;

import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.DBModels.DB;
import bgu.spl.net.impl.rci.DBModels.User;
import bgu.spl.net.impl.rci.ExecutionInfo;

import java.util.ArrayList;
import java.util.List;

public class FollowCommand extends Responder implements Command<ExecutionInfo> {
    private String[] usernamesToFollow;
    private boolean follow;
    private static final short opcode = 4;

    public FollowCommand(String[] users, boolean follow) {
        this.usernamesToFollow = users;
        this.follow = follow;
    }

    @Override
    public void execute(ExecutionInfo execInfo) {
        DB db = execInfo.getDb();
        User me = db.getUser(execInfo.getConnId());
        if (me.isLoggedIn()){
            if (follow) {
                executeFollow(execInfo);
            }else {
                executeUnFollow(execInfo);
            }
        }else {
            error(execInfo, opcode);
        }
    }

    private void executeUnFollow(ExecutionInfo execInfo) {
        // im logged in
        DB db = execInfo.getDb();
        User me = db.getUser(execInfo.getConnId());
        String[] namesToUnFollow = usernamesToFollow;
        List<User> newToUnFollow = new ArrayList<>();

        for (String username: namesToUnFollow){
            User userToUnFollow = db.getUser(username);
            if (userToUnFollow != null && me.isFollowing(username)){
                newToUnFollow.add(userToUnFollow);
            }
        }

        if (newToUnFollow.size() > 0){
            List<String> toUnFollowNames = usersToNames(newToUnFollow);
            ack(execInfo, opcode, toUnFollowNames, this);
        }else {
            error(execInfo, opcode);
        }


    }

    private void executeFollow(ExecutionInfo execInfo) {
        // im logged in
        DB db = execInfo.getDb();
        User me = db.getUser(execInfo.getConnId());
        List<User> newToFollow = new ArrayList<>();

        for (String username: usernamesToFollow){
            User userToFollow = db.getUser(username);
            if (userToFollow != null && !me.isFollowing(username)){
                newToFollow.add(userToFollow);
            }
        }

        if (newToFollow.size() > 0){
            List<String> toFollowNames = usersToNames(newToFollow);
            ack(execInfo, opcode, toFollowNames, this);
        }else {
            error(execInfo, opcode);
        }
    }

    private List<String> usersToNames(List<User> users) {
        List<String> names = new ArrayList<>();
        for (User user: users){
            names.add(user.getUsername());
        }
        return names;
    }

}

