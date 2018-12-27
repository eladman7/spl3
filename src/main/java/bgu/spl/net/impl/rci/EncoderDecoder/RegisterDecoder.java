package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageContainer;
import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.CommandModels.RegisterCommand;
import bgu.spl.net.impl.rci.ExecutionInfo;

public class RegisterDecoder implements MessageEncoderDecoder<Command<ExecutionInfo>> {
    private StringEncoderDecoder stringEncoderDecoder;
    private String username = null;

    public RegisterDecoder() {
        this.stringEncoderDecoder = new StringEncoderDecoder();
    }

    @Override
    public Command<ExecutionInfo> decodeNextByte(byte nextByte) {
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
    public byte[] encode(Command<ExecutionInfo> cmd) {
        return new byte[0];
    }
}
