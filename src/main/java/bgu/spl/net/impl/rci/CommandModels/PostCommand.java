package bgu.spl.net.impl.rci.CommandModels;

import bgu.spl.net.impl.rci.Command;

import java.io.Serializable;

public class PostCommand<D> implements Command<D> {
    private String postMessage;

    public PostCommand(String postMessage) {
        this.postMessage = postMessage;
    }

    @Override
    public Serializable execute(D arg) {
        return null;
    }
}

