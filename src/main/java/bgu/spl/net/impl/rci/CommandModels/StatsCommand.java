package bgu.spl.net.impl.rci.CommandModels;

import bgu.spl.net.api.Messages.Response;
import bgu.spl.net.impl.rci.Command;

import java.io.Serializable;

public class StatsCommand<D> implements Command<D> {
    private String username;

    public StatsCommand(String username) {
        this.username = username;
    }

    @Override
    public Response execute(D db) {
        return null;
    }
}

