package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.CommandModels.LoginCommand;
import bgu.spl.net.impl.rci.CommandModels.RegisterCommand;

public class LoginDecoder<D> implements MessageEncoderDecoder<Command<D>> {
    private RegisterDecoder<D> userPassHelper;

    public LoginDecoder() {
        userPassHelper = new RegisterDecoder<>();
    }

    @Override
    public Command<D> decodeNextByte(byte nextByte) {
        RegisterCommand<D> registerCmd = userPassHelper.decodeNextByte(nextByte);
        if (registerCmd == null){ // decoding not finished
            return null;
        }else { // ready
            return new LoginCommand<>(registerCmd.getUsername(), registerCmd.getPassword());
        }
    }

    @Override
    public byte[] encode(Command<D> message) {
        return new byte[0];
    }
}

