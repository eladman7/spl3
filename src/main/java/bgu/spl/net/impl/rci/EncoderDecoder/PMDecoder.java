package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageContainer;
import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.rci.CommandModels.PMCommand;

public class PMDecoder implements MessageEncoderDecoder<MessageContainer> {

    private StringEncoderDecoder stringEncoderDecoder;
    private String toUsername;

    public PMDecoder() {
        this.stringEncoderDecoder = stringEncoderDecoder = new StringEncoderDecoder();
    }

    @Override
    public MessageContainer decodeNextByte(byte nextByte) {
        if (toUsername == null) {
            toUsername = stringEncoderDecoder.decodeNextByte(nextByte);
        }else {
            String message = stringEncoderDecoder.decodeNextByte(nextByte);
            if (message != null) {
                MessageContainer messageContainer = new MessageContainer();
                messageContainer.setCommand(new PMCommand(toUsername, message));
                toUsername = null;
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


