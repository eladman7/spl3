package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.CommandModels.RegisterCommand;

public class RegisterDecoder<D> implements MessageEncoderDecoder<Command<D>> {
    private StringEncoderDecoder stringEncoderDecoder;
    private String username = null;

    public RegisterDecoder() {
        this.stringEncoderDecoder = new StringEncoderDecoder();
    }

    @Override
    public Command<D> decodeNextByte(byte nextByte) {
        if (this.username == null){ // still reading username
            String username = stringEncoderDecoder.decodeNextByte(nextByte);
            if (username != null){
                this.username = username;
            }
        }else { // reading password
            String password = stringEncoderDecoder.decodeNextByte(nextByte);
            if (password != null){ // finish
                String user = this.username;
                this.username = null;
                return new RegisterCommand(user, password);
            }
        }
        return null;
    }

    @Override
    public byte[] encode(Command<D> message) {
        return new byte[0];
    }
}
