package bgu.spl.net.impl.rci.CommandModels;

import bgu.spl.net.impl.rci.Command;

import java.io.Serializable;

public class UserListCommand<D> implements Command<D> {

    @Override
    public Serializable execute(D db) {
        return null;
    }
}

