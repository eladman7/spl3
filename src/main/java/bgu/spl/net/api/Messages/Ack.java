package bgu.spl.net.api.Messages;

public class Ack extends Response {
    private Object additionalData;

    public Ack(short originOpcode, Object additionalData) {
        super(originOpcode);
        this.additionalData = additionalData;
    }
}
