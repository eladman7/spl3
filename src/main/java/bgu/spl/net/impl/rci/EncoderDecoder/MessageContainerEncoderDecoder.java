package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageContainer;
import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.ExecutionInfo;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class MessageContainerEncoderDecoder implements MessageEncoderDecoder<MessageContainer> {
    private int opcode = -1;
    private Map<Integer, MessageEncoderDecoder<Command<ExecutionInfo>>> codeToDecoder;
    private ShortDecoder shortDecoder;

    public MessageContainerEncoderDecoder() {
        shortDecoder = new ShortDecoder();

        codeToDecoder = new HashMap<>();
        codeToDecoder.put(1, new RegisterDecoder());
        codeToDecoder.put(2, new LoginDecoder());
        codeToDecoder.put(3, new LogoutDecoder());
        codeToDecoder.put(4, new FollowDecoder());
        codeToDecoder.put(5, new PostDecoder());
        codeToDecoder.put(6, new PMDecoder());
        codeToDecoder.put(7, new UserListDecoder());
        codeToDecoder.put(8, new StatsDecoder());
    }

    /**
     * initiate new message container from bytes with command
     *
     * @param nextByte the next byte to consider for the currently decoded message
     * @return a new message container
     */
    @Override
    public MessageContainer decodeNextByte(byte nextByte) {
        if (opcode == -1) { // still in opcode part
            Short code = shortDecoder.decodeNextByte(nextByte);
            if (code != null){
                this.opcode = code;
            }
            return null;
        } else { // we are in the data part

            Command<ExecutionInfo> cmd = decodeCommand(nextByte);

            if (cmd == null) {
                return null;
            } else {
                opcode = -1;
                MessageContainer messageContainer = new MessageContainer();
                messageContainer.setCommand(cmd);
                return messageContainer;
            }
        }

    }

    private Command<ExecutionInfo> decodeCommand(byte nextByte) {
        MessageEncoderDecoder<Command<ExecutionInfo>> decoder = codeToDecoder.get(opcode);
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

        return new byte[0];
    }
}
