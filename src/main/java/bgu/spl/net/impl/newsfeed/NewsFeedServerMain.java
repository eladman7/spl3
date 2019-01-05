//package bgu.spl.net.impl.newsfeed;
//
//import bgu.spl.net.impl.rci.DBModels.DB;
//import bgu.spl.net.impl.rci.EncoderDecoder.MessageContainerEncoderDecoder;
//import bgu.spl.net.impl.rci.RemoteCommandInvocationProtocol;
//import bgu.spl.net.srv.Server;
//
//public class NewsFeedServerMain {
//
//    public static void main(String[] args) {
//        NewsFeed feed = new NewsFeed(); //one shared object
//        DB db = new DB();
//
//// you can use any server...
////        Server.threadPerClient(
////                7777, //port
////                () -> new RemoteCommandInvocationProtocol<>(feed), //protocol factory
////                ObjectEncoderDecoder::new //message encoder decoder factory
////        ).serve();
//
//        Server.reactor(
//                Runtime.getRuntime().availableProcessors(),
//                7777, //port
//                () ->  new RemoteCommandInvocationProtocol(db), //protocol factory
//                MessageContainerEncoderDecoder::new //message encoder decoder factory
//        ).serve();
//
//    }
//}
