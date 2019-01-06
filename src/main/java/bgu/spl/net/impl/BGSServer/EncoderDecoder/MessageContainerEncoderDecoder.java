package bgu.spl.net.impl.BGSServer.EncoderDecoder;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.BGSServer.Protocol.MessageContainer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class MessageContainerEncoderDecoder implements MessageEncoderDecoder<MessageContainer> {
    private Short opcode = null;
    private Map<Short, MessageEncoderDecoder<MessageContainer>> codeToDecoder;
    private ShortDecoder shortDecoder;

    public MessageContainerEncoderDecoder() {
        shortDecoder = new ShortDecoder();

        codeToDecoder = new HashMap<>();
        codeToDecoder.put((short) 1, new RegisterDecoder());
        codeToDecoder.put((short) 2, new LoginDecoder());
        codeToDecoder.put((short) 3, new LogoutDecoder());
        codeToDecoder.put((short) 4, new FollowDecoder());
        codeToDecoder.put((short) 5, new PostDecoder());
        codeToDecoder.put((short) 6, new PMDecoder());
        codeToDecoder.put((short) 7, new UserListDecoder());
        codeToDecoder.put((short) 8, new StatsDecoder());
    }

    /**
     * initiate new message container from bytes with command
     *
     * @param nextByte the next byte to consider for the currently decoded message
     * @return a new message container
     */
    @Override
    public MessageContainer decodeNextByte(byte nextByte) {
        if (opcode == null) { // still in opcode part
            opcode = shortDecoder.decodeNextByte(nextByte);

            if (opcode != null && (opcode == 3 || opcode == 7) ){ // nothing else to read
                MessageEncoderDecoder<MessageContainer> decoder = codeToDecoder.get(opcode);
                return finish(decoder.decodeNextByte((byte) 0));
            }

        } else { // we are in the data part

            MessageContainer messageContainer = decodeCommand(nextByte);
            if (messageContainer != null) {
                return finish(messageContainer);
            }
        }
        return null;
    }

    private MessageContainer finish(MessageContainer messageContainer) {
        opcode = null;
        return messageContainer;
    }

    private MessageContainer decodeCommand(byte nextByte) {
        MessageEncoderDecoder<MessageContainer> decoder = codeToDecoder.get((short) opcode);
        if (decoder == null) {
            throw new IllegalArgumentException("forbidden opcode " + opcode);
        } else {
            return decoder.decodeNextByte(nextByte);
        }
    }


    /**
     * encode relevant client data to write in response
     * consider: ack/error according to result
     * add additional data
     *
     * @param message the message to encode
     * @return encoded message in bytes
     */
    @Override
    public byte[] encode(MessageContainer message) {
        List<Byte> encodedBytes = new LinkedList<>();
        ShortDecoder shortDecoder = new ShortDecoder();

        if (message.getType() == MessageContainer.Type.NOTIFICATION) {
            writeNotification(message, encodedBytes, shortDecoder);

        } else if (message.getType() == MessageContainer.Type.ACK) {
            writeAck(message, encodedBytes, shortDecoder);

        } else if (message.getType() == MessageContainer.Type.ERROR) {
            writeError(message, encodedBytes, shortDecoder);
        }

        return getUnboxingArray(encodedBytes);
    }

    private void writeError(MessageContainer message, List<Byte> encodedBytes, ShortDecoder shortDecoder) {
        byte[] encodedOpcode = shortDecoder.encode((short) 11);
        addBytesToList(encodedBytes, encodedOpcode);
        byte[] encodedOrigin = shortDecoder.encode(message.getOriginOpcode());
        addBytesToList(encodedBytes, encodedOrigin);
    }

    private void writeAck(MessageContainer message, List<Byte> encodedBytes, ShortDecoder shortDecoder) {
        byte[] ackCodeBytes = shortDecoder.encode((short) 10);
        addBytesToList(encodedBytes, ackCodeBytes);
        byte[] originBytes = shortDecoder.encode(message.getOriginOpcode());
        addBytesToList(encodedBytes, originBytes);
        if (message.getAdditionalData() != null){
            MessageEncoderDecoder<MessageContainer> encoder = codeToDecoder.get(message.getOriginOpcode());
            byte[] encodedAdditionalData = encoder.encode(message);
            if (encodedAdditionalData.length > 0){
                addBytesToList(encodedBytes, encodedAdditionalData);
            }
        }
    }

    private void writeNotification(MessageContainer message, List<Byte> encodedBytes, ShortDecoder shortDecoder) {
        byte[] encodedOpcode = shortDecoder.encode((short) 9);
        addBytesToList(encodedBytes, encodedOpcode);
        if (message.isPm()) {
            encodedBytes.add((byte) 0);
        } else {
            encodedBytes.add((byte) 1);
        }
        StringEncoderDecoder stringEncoder = new StringEncoderDecoder();
        byte[] userBytes = stringEncoder.encode(message.getFromUsername());
        addBytesToList(encodedBytes, userBytes);
        byte[] contentBytes = stringEncoder.encode(message.getContent());
        addBytesToList(encodedBytes, contentBytes);
    }

    public static byte[] getUnboxingArray(List<Byte> bytes) {
        byte[] finalArr = new byte[bytes.size()];
        int j = 0;
        for (Byte b : bytes)
            finalArr[j++] = b;
        return finalArr;
    }

    public static void addBytesToList(List<Byte> byteList, byte[] byteArr) {
        for (byte b : byteArr) {
            byteList.add(b);
        }
    }
}
