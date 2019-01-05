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

    public void setText(String text) {
        this.text = text;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }
}
