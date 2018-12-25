package bgu.spl.net.api;

import bgu.spl.net.impl.rci.Command;

import java.io.Serializable;

public class MessageContainer<D> implements Serializable {
    private Command<D> command;
    private Serializable result;
    private boolean ack;
    private String error;
    private Object additionalData;

    public boolean isAck() {
        return ack;
    }

    public void setAck(boolean ack) {
        this.ack = ack;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(Object additionalData) {
        this.additionalData = additionalData;
    }

    public Serializable getResult() {
        return result;
    }

    public void setResult(Serializable result) {
        this.result = result;
    }

    public Command<D> getCommand() {
        return command;
    }

    public void setCommand(Command<D> command) {
        this.command = command;
    }
}
