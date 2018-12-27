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
    public RegisterCommand<D> decodeNextByte(byte nextByte) {
        if (this.username == null){ // still reading username
            String username = stringEncoderDecoder.decodeNextByte(nextByte);
            if (username == null){ // still reading username
                return null;
            }else { // got first 0 byte
                this.username = username;
                return null;
            }
        }else { // reading password
            String password = stringEncoderDecoder.decodeNextByte(nextByte);
            if (password == null){ // still reading pass
                return null;
            }else { // got second 0 byte, finish
                this.username = null;
                return new RegisterCommand<>(username, password);
            }
        }
    }

    @Override
    public byte[] encode(Command<D> message) {
        return new byte[0];
    }
}
