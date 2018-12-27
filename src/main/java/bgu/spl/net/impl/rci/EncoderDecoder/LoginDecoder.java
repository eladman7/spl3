package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageContainer;
import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.CommandModels.LoginCommand;
import bgu.spl.net.impl.rci.CommandModels.RegisterCommand;
import bgu.spl.net.impl.rci.ExecutionInfo;

public class LoginDecoder implements MessageEncoderDecoder<Command<ExecutionInfo>> {
    private RegisterDecoder userPassHelper;

    public LoginDecoder() {
        userPassHelper = new RegisterDecoder();
    }

    @Override
    public Command<ExecutionInfo> decodeNextByte(byte nextByte) {
        Command cmd = userPassHelper.decodeNextByte(nextByte);
        RegisterCommand registerCmd = (RegisterCommand) cmd;
        if (registerCmd != null){
            return new LoginCommand(registerCmd.getUsername(), registerCmd.getPassword());
        }
        return null;
    }

    @Override
    public byte[] encode(Command<ExecutionInfo> message) {
        return new byte[0];
    }
}

