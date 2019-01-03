package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageContainer;
import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.rci.CommandModels.LoginCommand;
import bgu.spl.net.impl.rci.CommandModels.RegisterCommand;

public class LoginDecoder implements MessageEncoderDecoder<MessageContainer> {
    private RegisterDecoder userPassHelper;

    public LoginDecoder() {
        userPassHelper = new RegisterDecoder();
    }

    @Override
    public MessageContainer decodeNextByte(byte nextByte) {
        MessageContainer result = new MessageContainer();

        MessageContainer messageContainer = userPassHelper.decodeNextByte(nextByte);
        if (messageContainer != null) {
            RegisterCommand registerCmd = (RegisterCommand) messageContainer.getCommand();
            result.setCommand(new LoginCommand(registerCmd.getUsername(), registerCmd.getPassword()));
            return result;
        }

        return null;
    }

    @Override
    public byte[] encode(MessageContainer message) {
        return new byte[0];
    }
}

