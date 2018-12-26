package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageContainer;
import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.rci.Command;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class EncoderDecoderMessageContainer<D> implements MessageEncoderDecoder<MessageContainer> {
    private RegisterDecoder<D> registerDecoder;
    private LoginDecoder<D> loginDecoder;
    private LogoutDecoder<D> logoutDecoder;
    private FollowDecoder<D> followDecoder;
    private PostDecoder<D> postDecoder;
    private PMDecoder<D> pmDecoder;
    private StatsDecoder<D> statsDecoder;
    private UserListDecoder<D> userListDecoder;
    private final ByteBuffer opCodeBuffer = ByteBuffer.allocate(2);
    private int opcode;
    private boolean inDataPart = false;
    private Map<Integer, MessageEncoderDecoder<Command<D>>> codeToDecoder;

    public EncoderDecoderMessageContainer() {
        codeToDecoder = new HashMap<>();
        codeToDecoder.put(1, new RegisterDecoder<>());
        codeToDecoder.put(2, new LoginDecoder<>());
        codeToDecoder.put(3, new LogoutDecoder<>());
        codeToDecoder.put(4, new FollowDecoder<>());
        codeToDecoder.put(5, new PostDecoder<>());
        codeToDecoder.put(6, new PMDecoder<>());
        codeToDecoder.put(8, new StatsDecoder<>());
        codeToDecoder.put(7, new UserListDecoder<>());
    }

    /**
     * initiate new message container from bytes with command
     * @param nextByte the next byte to consider for the currently decoded message
     * @return a new message container
     */
    @Override
    public MessageContainer decodeNextByte(byte nextByte) {
        if (!inDataPart){ // starting a new message
            if (opCodeBuffer.hasRemaining()){ // still in opcode part
                opCodeBuffer.put(nextByte);
            }else{ // just finished opcode
                opCodeBuffer.flip();
                opcode = opCodeBuffer.getInt(); // todo debug on this point see that it works
            }
            return null;
        }else{ // we are in the data part

            Command<D> cmd = decodeCommand(nextByte);

            if (cmd == null){
                return null;
            }else{
                inDataPart = false;
                opcode = 0;
                opCodeBuffer.clear();
                MessageContainer<D> messageContainer = new MessageContainer<>();
                messageContainer.setCommand(cmd);
                return messageContainer;
            }
        }

    }

    private Command<D> decodeCommand(byte nextByte) {
        MessageEncoderDecoder<Command<D>> decoder = codeToDecoder.get(opcode);
        if (decoder == null){
            throw new IllegalArgumentException("forbidden opcode " + opcode);
        }else {
            return decoder.decodeNextByte(nextByte);
        }
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
