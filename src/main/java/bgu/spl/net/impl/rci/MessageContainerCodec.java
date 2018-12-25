package bgu.spl.net.impl.rci;

import bgu.spl.net.api.MessageContainer;
import bgu.spl.net.api.MessageEncoderDecoder;

import java.io.Serializable;

public class MessageContainerCodec<D> implements MessageEncoderDecoder<MessageContainer> {
    /**
     * initiate new message container from bytes with command
     * @param nextByte the next byte to consider for the currently decoded
     * message
     * @return
     */
    @Override
    public MessageContainer decodeNextByte(byte nextByte) {

        // logic decides which commnd
        Command<D> cmd = new Command<D>() {
            @Override
            public Serializable execute(D arg) {
                return null;
            }
        };

        MessageContainer<D> messageContainer = new MessageContainer<>();
        messageContainer.setCommand(cmd);
        return messageContainer;
    }

    /**
     * encode relevant client data to write in response
     * consider: ack/error according to result
     * add additional data
     * @param message the message to encode
     * @return
     */
    @Override
    public byte[] encode(MessageContainer message) {

        return new byte[0];
    }
}
