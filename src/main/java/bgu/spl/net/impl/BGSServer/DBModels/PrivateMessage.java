package bgu.spl.net.impl.BGSServer.DBModels;

public class PrivateMessage {
    private String text;
    private User from;
    private User to;
    private boolean isPending;

    public boolean isPending() {
        return isPending;
    }

    public void setPending(boolean pending) {
        isPending = pending;
    }

    public PrivateMessage(String text, User from, User to, boolean isPending) {
        this.text = text;
        this.from = from;
        this.to = to;
        this.isPending = isPending;
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
