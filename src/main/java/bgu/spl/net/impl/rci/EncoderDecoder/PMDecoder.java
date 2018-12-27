package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.CommandModels.PMCommand;

public class PMDecoder<D> implements MessageEncoderDecoder<Command<D>> {

    private StringEncoderDecoder stringEncoderDecoder;
    private String toUsername;

    public PMDecoder(StringEncoderDecoder stringEncoderDecoder) {
        this.stringEncoderDecoder = stringEncoderDecoder;
    }

    @Override
    public Command<D> decodeNextByte(byte nextByte) {
        toUsername = stringEncoderDecoder.decodeNextByte(nextByte);
        if (toUsername != null){
            String message = stringEncoderDecoder.decodeNextByte(nextByte);
            if (message != null){
                return new PMCommand<>(toUsername, message);
            }
        }
        return null;
    }

    @Override
    public byte[] encode(Command<D> message) {
        return new byte[0];
    }
}


