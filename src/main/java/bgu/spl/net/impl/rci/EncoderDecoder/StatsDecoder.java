package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.CommandModels.StatsCommand;

public class StatsDecoder<D> implements MessageEncoderDecoder<Command<D>> {

    private StringEncoderDecoder stringEncoderDecoder;

    public StatsDecoder() {
        stringEncoderDecoder = new StringEncoderDecoder();
    }

    @Override
    public Command<D> decodeNextByte(byte nextByte) {
        String username = stringEncoderDecoder.decodeNextByte(nextByte);
        if (username != null){
            return new StatsCommand<>(username);
        }
        return null;
    }

    @Override
    public byte[] encode(Command<D> message) {
        return new byte[0];
    }
}

