package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.CommandModels.UserListCommand;

public class UserListDecoder<D> implements MessageEncoderDecoder<Command<D>> {
    @Override
    public Command<D> decodeNextByte(byte nextByte) {
        return new UserListCommand<>();
    }

    @Override
    public byte[] encode(Command<D> message) {
        return new byte[0];
    }
}

