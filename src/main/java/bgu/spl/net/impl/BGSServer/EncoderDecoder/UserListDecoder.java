package bgu.spl.net.impl.BGSServer.EncoderDecoder;

import bgu.spl.net.impl.BGSServer.Protocol.MessageContainer;
import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.BGSServer.CommandModels.UserListCommand;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UserListDecoder implements MessageEncoderDecoder<MessageContainer> {
    private ShortDecoder shortEncoder;
    private StringListDecoder stringListEncoder;

    public UserListDecoder() {
        shortEncoder = new ShortDecoder();
        stringListEncoder = new StringListDecoder();
    }

    @Override
    public MessageContainer decodeNextByte(byte nextByte) {
        MessageContainer messageContainer = new MessageContainer();
        messageContainer.setCommand(new UserListCommand());
        return messageContainer;
    }

    @Override
    public byte[] encode(MessageContainer message) {
        List<Byte> encodedBytes = new LinkedList<>();
        Object[] _userNames = (Object[]) message.getAdditionalData();


        String[] userNames = Arrays.asList(_userNames).toArray(new String[_userNames.length]);
        byte[] encodedSize = shortEncoder.encode((short) userNames.length);
        byte[] encodedNames = stringListEncoder.encode(userNames);

        MessageContainerEncoderDecoder.addBytesToList(encodedBytes, encodedSize);
        MessageContainerEncoderDecoder.addBytesToList(encodedBytes, encodedNames);

        return MessageContainerEncoderDecoder.getUnboxingArray(encodedBytes);
    }

}
