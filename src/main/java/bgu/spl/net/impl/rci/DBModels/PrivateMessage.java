package bgu.spl.net.impl.rci.DBModels;

public class PrivateMessage {
    private String text;
    private User from;
    private User to;

    public PrivateMessage(String text, User from, User to) {
        this.text = text;
        this.from = from;
        this.to = to;
    }

    public String getText() {
        return text;
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

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }
}
