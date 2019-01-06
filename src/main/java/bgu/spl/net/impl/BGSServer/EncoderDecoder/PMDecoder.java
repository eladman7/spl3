package bgu.spl.net.impl.BGSServer.EncoderDecoder;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.BGSServer.CommandModels.PMCommand;
import bgu.spl.net.impl.BGSServer.Protocol.MessageContainer;

public class PMDecoder implements MessageEncoderDecoder<MessageContainer> {

    private StringEncoderDecoder stringEncoderDecoder;
    private String toUsername;

    public PMDecoder() {
        this.stringEncoderDecoder = new StringEncoderDecoder();
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


