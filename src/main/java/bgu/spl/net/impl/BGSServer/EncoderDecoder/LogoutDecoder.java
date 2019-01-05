package bgu.spl.net.impl.BGSServer.EncoderDecoder;

import bgu.spl.net.impl.BGSServer.Protocol.MessageContainer;
import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.BGSServer.CommandModels.LogoutCommand;

public class LogoutDecoder implements MessageEncoderDecoder<MessageContainer> {
    @Override
    public MessageContainer decodeNextByte(byte nextByte) {
        MessageContainer messageContainer = new MessageContainer();
        messageContainer.setCommand(new LogoutCommand());
        return messageContainer;
    }

    @Override
    public byte[] encode(MessageContainer message) {
        return new byte[0];
    }

}

