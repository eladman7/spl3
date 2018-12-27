package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.CommandModels.StatsCommand;
import bgu.spl.net.impl.rci.ExecutionInfo;

public class StatsDecoder implements MessageEncoderDecoder<Command<ExecutionInfo>> {

    private StringEncoderDecoder stringEncoderDecoder;

    public StatsDecoder() {
        stringEncoderDecoder = new StringEncoderDecoder();
    }

    @Override
    public Command<ExecutionInfo> decodeNextByte(byte nextByte) {
        String username = stringEncoderDecoder.decodeNextByte(nextByte);
        if (username != null){
            return new StatsCommand(username);
        }
        return null;
    }

    @Override
    public byte[] encode(Command<ExecutionInfo> cmd) {
        return new byte[0];
    }
}

