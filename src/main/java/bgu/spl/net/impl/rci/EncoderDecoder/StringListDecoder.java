package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageEncoderDecoder;

import java.util.LinkedList;
import java.util.List;

public class StringListDecoder implements MessageEncoderDecoder<String[]> {
    private String[] strings = null;
    private Short size = null;
    private int nextStringIndex = 0;
    private ShortDecoder shortDecoder;
    private StringEncoderDecoder stringEncoderDecoder;

    public StringListDecoder() {
        this.shortDecoder = new ShortDecoder();
        this.stringEncoderDecoder = new StringEncoderDecoder();
    }

    @Override
    public String[] decodeNextByte(byte nextByte) {
        if (size == null) { // still in size part
            Short listSize = shortDecoder.decodeNextByte(nextByte);
            if (listSize != null) {
                size = listSize;
                strings = new String[listSize];
            }
        } else { // list part

            String next = stringEncoderDecoder.decodeNextByte(nextByte);
            if (next != null) {
                strings[nextStringIndex] = next;
                nextStringIndex++;
            }

            if (nextStringIndex == size) { // finished
                String[] result = duplicate(strings);
                strings = null;
                size = null;
                nextStringIndex = 0;
                return result;
            }

        }
        return null;

    }

    private String[] duplicate(String[] src) {
        String[] dest = new String[src.length];
        System.arraycopy(src, 0, dest, 0, src.length);
        return dest;
    }

    @Override
    public byte[] encode(String[] strings) {
        List<Byte> encodedMessage = new LinkedList<>();
        for (String s : strings) {
            byte[] encodedString = this.stringEncoderDecoder.encode(s);
            MessageContainerEncoderDecoder.addBytesToList(encodedMessage, encodedString);
        }

        return MessageContainerEncoderDecoder.getUnboxingArray(encodedMessage);
    }


}
