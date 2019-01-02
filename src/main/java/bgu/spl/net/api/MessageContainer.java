package bgu.spl.net.api;

import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.ExecutionInfo;

public class MessageContainer{
    public enum Type {
        ACK, ERROR, NOTIFICATION;
    }

    private boolean pm;
    private Command<ExecutionInfo> command;
    private Type type;
    private String error;
    private Object additionalData;
    private short originOpcode;
    private String fromUsername;
    private String content;

    public boolean isPm() {
        return pm;
    }

    public void setPm(boolean pm) {
        this.pm = pm;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageContainer() {
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public short getOriginOpcode() {
        return originOpcode;
    }

    public void setOriginOpcode(short originOpcode) {
        this.originOpcode = originOpcode;
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

    public Command<ExecutionInfo> getCommand() {
        return command;
    }

    public void setCommand(Command<ExecutionInfo> command) {
        this.command = command;
    }
}
