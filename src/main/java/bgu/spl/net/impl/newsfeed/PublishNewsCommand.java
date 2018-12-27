package bgu.spl.net.impl.newsfeed;

import bgu.spl.net.api.Messages.Response;
import bgu.spl.net.impl.rci.Command;
import java.io.Serializable;

public class PublishNewsCommand implements Command<NewsFeed> {
 
    private String channel;
    private String news;
 
    public PublishNewsCommand(String channel, String news) {
        this.channel = channel;
        this.news = news;
    }
 
    @Override
    public Response execute(NewsFeed db) {
        db.publish(channel, news);
        return null;
    }
 
}