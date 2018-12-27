package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.CommandModels.UserListCommand;
import bgu.spl.net.impl.rci.ExecutionInfo;

public class UserListDecoder implements MessageEncoderDecoder<Command<ExecutionInfo>> {
    @Override
    public Command<ExecutionInfo> decodeNextByte(byte nextByte) {
        return new UserListCommand();
    }

    @Override
    public byte[] encode(Command<ExecutionInfo> cmd) {
        return new byte[0];
    }
}
