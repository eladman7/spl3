package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.CommandModels.PostCommand;
import bgu.spl.net.impl.rci.ExecutionInfo;

public class PostDecoder implements MessageEncoderDecoder<Command<ExecutionInfo>> {

    private StringEncoderDecoder stringEncoderDecoder;

    public PostDecoder() {
        this.stringEncoderDecoder = new StringEncoderDecoder();
    }

    @Override
    public Command<ExecutionInfo> decodeNextByte(byte nextByte) {
        String postMessage = stringEncoderDecoder.decodeNextByte(nextByte);
        if (postMessage != null){
            return new PostCommand(postMessage);
        }
        return null;
    }

    @Override
    public byte[] encode(Command<ExecutionInfo> cmd) {
        return new byte[0];
    }
}

