package bgu.spl.net.impl.rci.CommandModels;

import bgu.spl.net.api.Messages.Response;
import bgu.spl.net.impl.rci.Command;

public class FollowCommand<D> implements Command<D> {

    private String[] users;
    private boolean follow;

    public FollowCommand(String[] users, boolean follow) {
        this.users = users;
        this.follow = follow;
    }

    @Override
    public Response execute(D db) {
        return null;
    }
}

