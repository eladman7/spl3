package bgu.spl.net.impl.newsfeed;

import bgu.spl.net.impl.rci.Command;
import java.io.Serializable;

public class FetchNewsCommand implements Command<NewsFeed> {

    private String channel;

    public FetchNewsCommand(String channel) {
        this.channel = channel;
    }

    @Override
    public void execute(NewsFeed db) {
//            return null;
//        return db.fetch(channel);
    }

}
