package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageContainer;
import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.rci.CommandModels.FollowCommand;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class FollowDecoder implements MessageEncoderDecoder<MessageContainer> {
    private Boolean follow = null;
    private StringListDecoder stringListDecoder;
    private ShortDecoder shortDecoder;

    public FollowDecoder() {
        this.stringListDecoder = new StringListDecoder();
        this.shortDecoder = new ShortDecoder();
    }

    @Override
    public MessageContainer decodeNextByte(byte nextByte) {
        if (follow == null){
            this.follow = (nextByte == (byte) 0);
        }else {
            String[] users = stringListDecoder.decodeNextByte(nextByte);
            if (users != null){
                MessageContainer messageContainer = new MessageContainer();
                messageContainer.setCommand(new FollowCommand(users, follow));
                follow = null;
                return messageContainer;
            }
        }
        return null;
    }


    @Override
    public byte[] encode(MessageContainer message) {
        Object[] _toFollowNames = (Object[]) message.getAdditionalData();
        String[] toFollowNames = Arrays.asList(_toFollowNames).toArray(new String[_toFollowNames.length]);
        byte[] encodedNames = stringListDecoder.encode(toFollowNames);
        byte[] encodedLength = shortDecoder.encode((short) encodedNames.length);

        List<Byte> encodedMessage = new LinkedList<>();
        MessageContainerEncoderDecoder.addBytesToList(encodedMessage, encodedLength);
        MessageContainerEncoderDecoder.addBytesToList(encodedMessage, encodedNames);

        return MessageContainerEncoderDecoder.getUnboxingArray(encodedMessage);
    }
}

