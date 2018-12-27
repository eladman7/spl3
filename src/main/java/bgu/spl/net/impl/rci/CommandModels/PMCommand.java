package bgu.spl.net.impl.rci.CommandModels;

import bgu.spl.net.impl.rci.Command;

import java.io.Serializable;

public class PMCommand<D> implements Command<D> {
    private String toUsername;
    private String message;

    public PMCommand(String toUsername, String message) {
        this.toUsername = toUsername;
        this.message = message;
    }

    @Override
    public Serializable execute(D arg) {
        return null;
    }
}

