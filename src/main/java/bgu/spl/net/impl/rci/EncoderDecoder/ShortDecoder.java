package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageEncoderDecoder;

import java.nio.ByteBuffer;

public class ShortDecoder implements MessageEncoderDecoder<Integer> {
    private final ByteBuffer codeBuffer = ByteBuffer.allocate(2);
    // todo elad why is it like this?

    public ShortDecoder() {
        // todo elad and not like this?
        // codeBuffer = ByteBuffer.allocate(2);
    }

    @Override
    public Integer decodeNextByte(byte nextByte) {
            codeBuffer.put(nextByte);

            if (!codeBuffer.hasRemaining()) {// just finished code part
                int code = codeBuffer.getInt(); // todo debug on this point see that it works
                codeBuffer.flip();
                return code;
            }else {
                return null;
            }
    }

    @Override
    public byte[] encode(Integer message) {
        return new byte[0];
    }
}