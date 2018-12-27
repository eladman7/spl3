package bgu.spl.net.impl.rci.EncoderDecoder;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.CommandModels.FollowCommand;
import bgu.spl.net.impl.rci.ExecutionInfo;


public class FollowDecoder implements MessageEncoderDecoder<Command<ExecutionInfo>> {
    private boolean follow;
    private boolean followFound = false;
    private StringListDecoder stringListDecoder;

    public FollowDecoder() {
        this.stringListDecoder= new StringListDecoder();
    }

    @Override
    public Command<ExecutionInfo> decodeNextByte(byte nextByte) {
        if (!followFound){
            this.follow = (nextByte == 0);
            followFound = true;
        }else {
            String[] users = stringListDecoder.decodeNextByte(nextByte);
            if (users != null){
                followFound = false;
                return new FollowCommand(users, follow);
            }
        }
        return null;
    }


    @Override
    public byte[] encode(Command<ExecutionInfo> message) {
        return new byte[0];
    }
}

