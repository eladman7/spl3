package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageContainer;
import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.rci.CommandModels.FollowCommand;

import java.util.LinkedList;
import java.util.List;


public class FollowDecoder implements MessageEncoderDecoder<MessageContainer> {
    private boolean follow;
    private boolean followFound = false;
    private StringListDecoder stringListDecoder;
    private ShortDecoder shortDecoder;

    public FollowDecoder() {
        this.stringListDecoder = new StringListDecoder();
        this.shortDecoder = new ShortDecoder();
    }

    @Override
    public MessageContainer decodeNextByte(byte nextByte) {
        MessageContainer messageContainer = new MessageContainer();
        if (!followFound){
            this.follow = (nextByte == 0);
            followFound = true;
        }else {
            String[] users = stringListDecoder.decodeNextByte(nextByte);
            if (users != null){
                followFound = false;
                messageContainer.setCommand(new FollowCommand(users, follow));
                return messageContainer;
            }
        }
        return null;
    }


    @Override
    public byte[] encode(MessageContainer message) {
        List<String> toFollowNames = (List<String>) message.getAdditionalData();
        byte[] encodedNames = stringListDecoder.encode((String[]) toFollowNames.toArray());
        byte[] encodedLength = shortDecoder.encode((short) encodedNames.length);

        List<Byte> encodedMessage = new LinkedList<>();
        MessageContainerEncoderDecoder.addBytesToList(encodedMessage, encodedLength);
        MessageContainerEncoderDecoder.addBytesToList(encodedMessage, encodedNames);

        return MessageContainerEncoderDecoder.getUnboxingArray(encodedMessage);
    }
}

