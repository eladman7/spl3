package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageEncoderDecoder;

import java.nio.ByteBuffer;

public class ShortDecoder implements MessageEncoderDecoder<Short> {
    private final ByteBuffer codeBuffer = ByteBuffer.allocate(2);
    // todo elad why is it like this?

    public ShortDecoder() {
        // todo elad and not like this?
        // codeBuffer = ByteBuffer.allocate(2);
    }

    @Override
    public Short decodeNextByte(byte nextByte) {
        codeBuffer.put(nextByte);
        if (!codeBuffer.hasRemaining()) {// just finished code part
            codeBuffer.rewind();
            short code = codeBuffer.getShort(); // todo debug on this point see that it works
            codeBuffer.clear();
            return code;
        }
        return null;
    }

    @Override
    public byte[] encode(Short message) {
        return ByteBuffer.allocate(4).putInt(message).array();
    }
}
