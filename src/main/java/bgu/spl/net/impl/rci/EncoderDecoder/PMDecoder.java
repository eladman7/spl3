package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.CommandModels.PMCommand;
import bgu.spl.net.impl.rci.ExecutionInfo;

public class PMDecoder implements MessageEncoderDecoder<Command<ExecutionInfo>> {

    private StringEncoderDecoder stringEncoderDecoder;
    private String toUsername;

    public PMDecoder() {
        this.stringEncoderDecoder = stringEncoderDecoder = new StringEncoderDecoder();
    }

    @Override
    public Command<ExecutionInfo> decodeNextByte(byte nextByte) {
        toUsername = stringEncoderDecoder.decodeNextByte(nextByte);
        if (toUsername != null){
            String message = stringEncoderDecoder.decodeNextByte(nextByte);
            if (message != null){
                return new PMCommand(toUsername, message);
            }
        }
        return null;
    }

    @Override
    public byte[] encode(Command<ExecutionInfo> cmd) {
        return new byte[0];
    }
}


