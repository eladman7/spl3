package bgu.spl.net.impl.BGSServer.EncoderDecoder;

import bgu.spl.net.api.MessageContainer;
import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.BGSServer.CommandModels.StatsCommand;

import java.util.LinkedList;
import java.util.List;

public class StatsDecoder implements MessageEncoderDecoder<MessageContainer> {

    private StringEncoderDecoder stringEncoderDecoder;
    private ShortDecoder shortDecoder;

    public StatsDecoder() {
        stringEncoderDecoder = new StringEncoderDecoder();
        shortDecoder = new ShortDecoder();
    }

    @Override
    public MessageContainer decodeNextByte(byte nextByte) {
        String username = stringEncoderDecoder.decodeNextByte(nextByte);
        if (username != null) {
            MessageContainer messageContainer = new MessageContainer();
            messageContainer.setCommand(new StatsCommand(username));
            return messageContainer;
        }
        return null;
    }

    @Override
    public byte[] encode(MessageContainer message) {
        short[] data = (short[]) message.getAdditionalData();
        byte[] encodedNumPosts = shortDecoder.encode(data[0]);
        byte[] encodedNumFollowers = shortDecoder.encode(data[1]);
        byte[] encodedNumFollowing = shortDecoder.encode(data[2]);


        List<Byte> encodedMessage = new LinkedList<>();
        MessageContainerEncoderDecoder.addBytesToList(encodedMessage, encodedNumPosts);
        MessageContainerEncoderDecoder.addBytesToList(encodedMessage, encodedNumFollowers);
        MessageContainerEncoderDecoder.addBytesToList(encodedMessage, encodedNumFollowing);

        return MessageContainerEncoderDecoder.getUnboxingArray(encodedMessage);
    }
}

