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
    public byte[] encode(String message) {
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        byte[] bytes1 = new byte[bytes.length + 1];
        for (int i = 0; i < bytes.length; i++) {
            bytes1[i] = bytes[i];
        }
        bytes1[bytes.length] = (byte) 0;
        return bytes1;
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
