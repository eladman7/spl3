package bgu.spl.net.impl.BGSServer.DBModels;

public class Post {
    private String text;
    private User from;

    public String getText() {
        return text;
    }

    public Post(String text, User from) {
        this.text = text;
        this.from = from;
    }

    public User getFrom() {
        return from;
    }

}
