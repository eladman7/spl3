package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.impl.BGSServer.DBModels.DB;
import bgu.spl.net.impl.BGSServer.EncoderDecoder.MessageContainerEncoderDecoder;
import bgu.spl.net.impl.BGSServer.Protocol.BGSProtocol;
import bgu.spl.net.srv.Server;

public class ReactorMain {

    public static void main(String[] args) {
        DB db = new DB();

        int port = Integer.parseInt(args[0]);
        int numThreads = Integer.parseInt(args[1]);

        Server.reactor(
                numThreads,
                port,
                () ->  new BGSProtocol(db), //protocol factory
                MessageContainerEncoderDecoder::new //message encoder decoder factory
        ).serve();

    }
}

