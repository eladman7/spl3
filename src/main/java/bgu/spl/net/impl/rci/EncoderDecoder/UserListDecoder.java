package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageContainer;
import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.CommandModels.UserListCommand;
import bgu.spl.net.impl.rci.ExecutionInfo;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UserListDecoder implements MessageEncoderDecoder<MessageContainer> {


    @Override
    public MessageContainer decodeNextByte(byte nextByte) {
        MessageContainer messageContainer = new MessageContainer();
        messageContainer.setCommand(new UserListCommand());
        return messageContainer;
    }

    @Override
    public byte[] encode(MessageContainer message) {
        List<Byte> userListCommandBytes = new LinkedList<>();
        List<String> userNames = (List<String>) message.getAdditionalData();
        // add num of users param
        userListCommandBytes.addAll(Arrays.asList(MessageContainerEncoderDecoder.getBoxingArray(
                ByteBuffer.allocate(4).putInt(userNames.size()).array())));
        userNames.forEach(userName -> userListCommandBytes.addAll(Arrays.asList(
                MessageContainerEncoderDecoder.getBoxingArray(userName.getBytes()))));
        return MessageContainerEncoderDecoder.getUnboxingArray(userListCommandBytes);
    }

}
