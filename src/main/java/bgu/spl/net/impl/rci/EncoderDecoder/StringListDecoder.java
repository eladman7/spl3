package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageEncoderDecoder;

public class StringListDecoder implements MessageEncoderDecoder<String[]> {
    private String[] strings = null;
    private int size = -1;
    private int nextStringIndex = 0;
    private ShortDecoder shortDecoder;
    private StringEncoderDecoder stringEncoderDecoder;

    public StringListDecoder() {
        this.shortDecoder = new ShortDecoder();
        this.stringEncoderDecoder = new StringEncoderDecoder();
    }

    @Override
    public String[] decodeNextByte(byte nextByte) {
        if (size == -1) { // still in size part
            Short code = shortDecoder.decodeNextByte(nextByte);
            if (code != null){
                size = code;
                strings = new String[size];
            }
        } else{ // list part
            if (nextStringIndex == size){ // finished
                String[] result = duplicate(strings);
                strings = null;
                size = -1;
                nextStringIndex = 0;
                return result;
            }
            String next = stringEncoderDecoder.decodeNextByte(nextByte);
            if (next != null){
                strings[nextStringIndex] = next;
                nextStringIndex++;
            }

        }
        return null;

    }
    private String[] duplicate(String[] src){
        String[] dest = new String[src.length];
        System.arraycopy( src, 0, dest, 0, src.length );
        return dest;
    }

    @Override
    public byte[] encode(String[] message) {
        return null;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
