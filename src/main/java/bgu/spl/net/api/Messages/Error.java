package bgu.spl.net.api.Messages;

public class Error extends Response{
    public Error(short originOpcode) {
        super(originOpcode);
    }
}
