package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageContainer;
import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.CommandModels.FollowCommand;
import bgu.spl.net.impl.rci.ExecutionInfo;

import java.util.LinkedList;
import java.util.List;


public class FollowDecoder implements MessageEncoderDecoder<MessageContainer> {
    private boolean follow;
    private boolean followFound = false;
    private StringListDecoder stringListDecoder;

    public FollowDecoder() {
        this.stringListDecoder= new StringListDecoder();
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
        List<Byte> encodedMessage = new LinkedList<>();
        return MessageContainerEncoderDecoder.getUnboxingArray(encodedMessage);
    }
}

