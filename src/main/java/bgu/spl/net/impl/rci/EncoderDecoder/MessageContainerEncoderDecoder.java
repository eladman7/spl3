package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageContainer;
import bgu.spl.net.api.MessageEncoderDecoder;

import java.util.*;

public class MessageContainerEncoderDecoder implements MessageEncoderDecoder<MessageContainer> {
    private int opcode = -1;
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
        System.out.println("inside MessageContainerEncoderDecoder.decodeNextByte()");
        if (opcode == -1) { // still in opcode part
            Short code = shortDecoder.decodeNextByte(nextByte);
            if (code != null) {
                this.opcode = code;
            }
        } else { // we are in the data part

            MessageContainer messageContainer = decodeCommand(nextByte);
            if (messageContainer != null) {
                opcode = -1;
                return messageContainer;
            }
        }
        return null;
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
     * @return
     */
    @Override
    public byte[] encode(MessageContainer message) {
        List<Byte> encodedBytes = new LinkedList<>();
        ShortDecoder shortDecoder = new ShortDecoder();
        Byte oneInByte = (byte) 1;
        Byte zeroInByte = (byte) 0;
        // handle notification
        if (message.getType() == MessageContainer.Type.NOTIFICATION) {
            encodedBytes.addAll(Arrays.asList(getBoxingArray(shortDecoder.encode((short) 12))));
            if (message.isPm()) {
                encodedBytes.add(zeroInByte);
            } else {
                encodedBytes.add(oneInByte);
            }
            StringEncoderDecoder encoderDecoder = new StringEncoderDecoder();
            encodedBytes.addAll(Arrays.asList(getBoxingArray(encoderDecoder.encode(message.getFromUsername()))));
            encodedBytes.add(zeroInByte);
            encodedBytes.addAll(Arrays.asList(getBoxingArray(encoderDecoder.encode(message.getFromUsername()))));
            encodedBytes.add(zeroInByte);
        } else {
            if (message.getType() == MessageContainer.Type.ACK) {
                encodedBytes.addAll(Arrays.asList(getBoxingArray(shortDecoder.encode((short) 10))));
            } else if (message.getType() == MessageContainer.Type.ERROR) {
                encodedBytes.addAll(Arrays.asList(getBoxingArray(shortDecoder.encode((short) 11))));
            }
            encodedBytes.addAll(Arrays.asList(getBoxingArray(shortDecoder.encode(message.getOriginOpcode()))));
            // encode specific command data
            encodedBytes.addAll(Arrays.asList(getBoxingArray(codeToDecoder.get(message.getOriginOpcode())
                    .encode(message))));
        }
        return getUnboxingArray(encodedBytes);
    }

    public static byte[] getUnboxingArray(List<Byte> bytes) {
        byte[] finalArr = new byte[bytes.size()];
        int j = 0;
        for (Byte b : bytes)
            finalArr[j++] = b;
        return finalArr;
    }

    public static Byte[] getBoxingArray(byte[] bytesArr) {
        Byte[] bytes = new Byte[bytesArr.length];
        int i = 0;
        for (byte b : bytesArr)
            bytes[i++] = b;
        return bytes;
    }
}
