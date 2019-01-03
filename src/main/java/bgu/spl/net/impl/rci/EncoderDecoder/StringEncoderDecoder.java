package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageEncoderDecoder;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class StringEncoderDecoder implements MessageEncoderDecoder<String> {

    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;

    @Override
    public String decodeNextByte(byte nextByte) {
        //notice that the top 128 ascii characters have the same representation as their utf-8 counterparts
        //this allow us to do the following comparison
        if (nextByte == 0) {
            return popString();
        }

        pushByte(nextByte);
        return null; //not a full String yet
    }

    @Override
    public byte[] encode(String s) {
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);

        byte[] bytesWithFinishingZero = new byte[bytes.length + 1];
        System.arraycopy(bytes, 0, bytesWithFinishingZero, 0, bytes.length);
        bytesWithFinishingZero[bytes.length] = (byte) 0;
        return bytesWithFinishingZero;
    }

    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }

        bytes[len++] = nextByte;
    }

    private String popString() {
        String result = new String(bytes, 0, len, StandardCharsets.UTF_8);
        len = 0;
        return result;
    }
}
