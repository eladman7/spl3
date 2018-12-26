package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.rci.Command;

public class UserListDecoder<D> implements MessageEncoderDecoder<Command<D>> {
    @Override
    public Command<D> decodeNextByte(byte nextByte) {
        return null;
    }

    @Override
    public byte[] encode(Command<D> message) {
        return new byte[0];
    }
}

