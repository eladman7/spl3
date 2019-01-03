package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageContainer;
import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.rci.CommandModels.PostCommand;

public class PostDecoder implements MessageEncoderDecoder<MessageContainer> {

    private StringEncoderDecoder stringEncoderDecoder;

    public PostDecoder() {
        this.stringEncoderDecoder = new StringEncoderDecoder();
    }

    @Override
    public MessageContainer decodeNextByte(byte nextByte) {
        MessageContainer messageContainer = new MessageContainer();
        String postMessage = stringEncoderDecoder.decodeNextByte(nextByte);
        if (postMessage != null){
            messageContainer.setCommand(new PostCommand(postMessage));
            return messageContainer;
        }
        return null;
    }

    @Override
    public byte[] encode(MessageContainer cmd) {
        return new byte[0];
    }
}

