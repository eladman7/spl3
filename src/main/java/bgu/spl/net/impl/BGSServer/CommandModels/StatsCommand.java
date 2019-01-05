package bgu.spl.net.impl.BGSServer.CommandModels;

import bgu.spl.net.impl.BGSServer.Protocol.Command;
import bgu.spl.net.impl.BGSServer.DBModels.DB;
import bgu.spl.net.impl.BGSServer.DBModels.Post;
import bgu.spl.net.impl.BGSServer.DBModels.User;
import bgu.spl.net.impl.BGSServer.Protocol.ExecutionInfo;

import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;


public class StatsCommand extends Responder implements Command<ExecutionInfo> {
    private String username;
    private static final short opcode = 8;

    public StatsCommand(String username) {
        this.username = username;
    }

    @Override
    public void execute(ExecutionInfo execInfo) {
        DB db = execInfo.getDb();
        User me = db.getUser(execInfo.getConnId());
        User dataUser = db.getUser(username);

        if (me != null && me.isLoggedIn() && dataUser != null) {
            List<Post> userPosts = db.getPosts().stream().
                    filter(x-> x.getFrom().equals(dataUser)).
                    collect(Collectors.toList());

            Queue<User> followers = dataUser.getMyFollowers();
            Queue<User> following = dataUser.getImFollowing();

            short[] data = new short[3];
            data[0] = (short) userPosts.size();
            data[1] = (short) followers.size();
            data[2] = (short) following.size();

            ack(execInfo, opcode, data, this);
        } else {
            error(execInfo, opcode);
        }
    }
}

