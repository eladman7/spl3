package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.impl.rci.DBModels.DB;
import bgu.spl.net.impl.rci.EncoderDecoder.MessageContainerEncoderDecoder;
import bgu.spl.net.impl.rci.RemoteCommandInvocationProtocol;
import bgu.spl.net.srv.Server;

public class ReactorMain {

    public static void main(String[] args) {
        DB db = new DB();

        String port = args[1];

        Server.reactor(
                Runtime.getRuntime().availableProcessors(),
                Integer.parseInt(port),
                () ->  new RemoteCommandInvocationProtocol(db), //protocol factory
                MessageContainerEncoderDecoder::new //message encoder decoder factory
        ).serve();

    }
}

