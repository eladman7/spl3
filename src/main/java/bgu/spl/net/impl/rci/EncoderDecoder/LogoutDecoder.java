package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.CommandModels.LogoutCommand;
import bgu.spl.net.impl.rci.ExecutionInfo;

public class LogoutDecoder implements MessageEncoderDecoder<Command<ExecutionInfo>> {
    @Override
    public LogoutCommand decodeNextByte(byte nextByte) {
        return new LogoutCommand();
    }

    @Override
    public byte[] encode(Command<ExecutionInfo> message) {
        return new byte[0];
    }
}

