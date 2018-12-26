package bgu.spl.net.impl.rci.DBModels;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class User {
    private final String username;
    private final String password;
    private final AtomicBoolean connected;
    private Queue<User> following;
    private Queue<User> followers;
    private Queue<Post> posted;
    private Queue<PrivateMessage> sent;
    private Queue<PrivateMessage> received;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
        connected = new AtomicBoolean(false);
        following = new ConcurrentLinkedQueue<>();
        followers = new ConcurrentLinkedQueue<>();
        posted = new ConcurrentLinkedQueue<>();
        sent = new ConcurrentLinkedQueue<>();
        received = new ConcurrentLinkedQueue<>();
    }

    public Queue<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Queue<User> followers) {
        this.followers = followers;
    }

    public boolean isConnected() {
        return connected.get();
    }

    public void setConnected(boolean connected) {
        this.connected.set(connected);
    }

    public Queue<User> getFollowing() {
        return following;
    }

    public void setFollowing(Queue<User> following) {
        this.following = following;
    }

    public Queue<Post> getPosted() {
        return posted;
    }

    public void setPosted(Queue<Post> posted) {
        this.posted = posted;
    }

    public Queue<PrivateMessage> getSent() {
        return sent;
    }

    public void setSent(Queue<PrivateMessage> sent) {
        this.sent = sent;
    }

    public Queue<PrivateMessage> getReceived() {
        return received;
    }

    public void setReceived(Queue<PrivateMessage> received) {
        this.received = received;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
