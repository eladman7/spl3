package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageEncoderDecoder;

import java.nio.ByteBuffer;

public class ShortDecoder implements MessageEncoderDecoder<Short> {
    private final ByteBuffer codeBuffer = ByteBuffer.allocate(2);

    public ShortDecoder() {}

    @Override
    public Short decodeNextByte(byte nextByte) {
        codeBuffer.put(nextByte);
        if (!codeBuffer.hasRemaining()) {// just finished code part
            codeBuffer.rewind();
            short code = codeBuffer.getShort();
            codeBuffer.clear();
            return code;
        }
        return null;
    }

    @Override
    public byte[] encode(Short num) {
        return ByteBuffer.allocate(2).putShort(num).array();
    }
}
