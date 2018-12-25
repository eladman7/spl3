package bgu.spl.net.impl.rci.CommandModels;

import bgu.spl.net.impl.rci.Command;

import java.io.Serializable;

public class RegisterCommand <D> implements Command<D> {

    @Override
    public Serializable execute(D arg) {
        return null;
    }
}
