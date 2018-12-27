package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.CommandModels.PostCommand;

public class PostDecoder<D> implements MessageEncoderDecoder<Command<D>> {

    private StringEncoderDecoder stringEncoderDecoder;

    public PostDecoder() {
        this.stringEncoderDecoder = new StringEncoderDecoder();
    }

    @Override
    public Command<D> decodeNextByte(byte nextByte) {
        String postMessage = stringEncoderDecoder.decodeNextByte(nextByte);
        if (postMessage != null){
            return new PostCommand<>(postMessage);
        }
        return null;
    }

    @Override
    public byte[] encode(Command<D> message) {
        return new byte[0];
    }
}

