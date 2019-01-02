package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageContainer;
import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.rci.CommandModels.RegisterCommand;

public class RegisterDecoder implements MessageEncoderDecoder<MessageContainer> {
    private StringEncoderDecoder stringEncoderDecoder;
    private String username = null;

    public RegisterDecoder() {
        this.stringEncoderDecoder = new StringEncoderDecoder();
    }

    @Override
    public MessageContainer decodeNextByte(byte nextByte) {
        if (this.username == null) { // still reading username
            String username = stringEncoderDecoder.decodeNextByte(nextByte);
            if (username != null) {
                this.username = username;
            }
        } else { // reading password
            String password = stringEncoderDecoder.decodeNextByte(nextByte);
            if (password != null) { // finish
                String user = this.username;
                this.username = null;
                RegisterCommand registerCommand = new RegisterCommand(user, password);
                MessageContainer messageContainer = new MessageContainer();
                messageContainer.setCommand(registerCommand);
            }
        }
        return null;
    }

    @Override
    public byte[] encode(MessageContainer cmd) {
        return new byte[0];
    }
}
