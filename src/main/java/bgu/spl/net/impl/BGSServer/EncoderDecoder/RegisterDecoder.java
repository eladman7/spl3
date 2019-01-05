package bgu.spl.net.impl.BGSServer.EncoderDecoder;

import bgu.spl.net.api.MessageContainer;
import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.BGSServer.CommandModels.RegisterCommand;

public class RegisterDecoder implements MessageEncoderDecoder<MessageContainer> {
    private StringEncoderDecoder stringEncoderDecoder;
    private String username = null;

    public RegisterDecoder() {
        this.stringEncoderDecoder = new StringEncoderDecoder();
    }

    @Override
    public MessageContainer decodeNextByte(byte nextByte) {
        if (username == null) { // still reading username
            username = stringEncoderDecoder.decodeNextByte(nextByte);

        } else { // reading password
            String password = stringEncoderDecoder.decodeNextByte(nextByte);
            if (password != null) { // finish
                RegisterCommand registerCommand = new RegisterCommand(username, password);
                MessageContainer messageContainer = new MessageContainer();
                messageContainer.setCommand(registerCommand);
                this.username = null;
                return messageContainer;
            }
        }
        return null;
    }

    @Override
    public byte[] encode(MessageContainer cmd) {
        return new byte[0];
    }
}
