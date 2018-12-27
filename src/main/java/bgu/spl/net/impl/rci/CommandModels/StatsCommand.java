package bgu.spl.net.impl.rci.CommandModels;

import bgu.spl.net.impl.rci.Command;

import java.io.Serializable;

public class StatsCommand<D> implements Command<D> {
    private String username;

    public StatsCommand(String username) {
        this.username = username;
    }

    @Override
    public Serializable execute(D arg) {
        return null;
    }
}

