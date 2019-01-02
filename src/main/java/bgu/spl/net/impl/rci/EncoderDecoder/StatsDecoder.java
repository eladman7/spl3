package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageContainer;
import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.CommandModels.StatsCommand;
import bgu.spl.net.impl.rci.ExecutionInfo;

public class StatsDecoder implements MessageEncoderDecoder<MessageContainer> {

    private StringEncoderDecoder stringEncoderDecoder;

    public StatsDecoder() {
        stringEncoderDecoder = new StringEncoderDecoder();
    }

    @Override
    public MessageContainer decodeNextByte(byte nextByte) {
        MessageContainer messageContainer = new MessageContainer();
        String username = stringEncoderDecoder.decodeNextByte(nextByte);
        if (username != null) {
            messageContainer.setCommand(new StatsCommand(username));
            return messageContainer;
        }
        return null;
    }

    @Override
    public byte[] encode(MessageContainer cmd) {
        return new byte[0];
    }
}

