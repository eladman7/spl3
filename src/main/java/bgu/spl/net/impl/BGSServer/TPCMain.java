package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.impl.BGSServer.DBModels.DB;
import bgu.spl.net.impl.BGSServer.EncoderDecoder.MessageContainerEncoderDecoder;
import bgu.spl.net.srv.Server;

public class TPCMain {

    public static void main(String[] args) {
        DB db = new DB();

        int port = Integer.parseInt(args[0]);

        Server.threadPerClient(
                port, //port
                () ->  new RemoteCommandInvocationProtocol(db), //protocol factory
                MessageContainerEncoderDecoder::new //message encoder decoder factory
        ).serve();
    }
}
