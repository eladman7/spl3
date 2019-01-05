package bgu.spl.net.impl.BGSServer.DBModels;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class User {
    private final String username;
    private final String password;
    private final AtomicBoolean loggedIn;
    private Queue<User> following;
    private Queue<User> followers;
    private Queue<Post> pendingPosts;
    private Queue<Post> posted;
    private Queue<PrivateMessage> sent;
    private Queue<PrivateMessage> received;
    private int connectionId;

    public boolean isFollowing(String username){
        for (User user: following){
            if (user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public int getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }

    public Queue<Post> getPendingPosts() {
        return pendingPosts;
    }

    public void addPendingPost(Post post) {
        this.pendingPosts.add(post);
    }

    public void clearPendingPost() {
        this.pendingPosts.clear();
    }

    public void removePendingPost(Post post) {
        this.pendingPosts.remove(post);
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        loggedIn = new AtomicBoolean(false);
        following = new ConcurrentLinkedQueue<>();
        followers = new ConcurrentLinkedQueue<>();
        posted = new ConcurrentLinkedQueue<>();
        sent = new ConcurrentLinkedQueue<>();
        received = new ConcurrentLinkedQueue<>();
        pendingPosts = new ConcurrentLinkedQueue<>();
    }

    public Queue<User> getMyFollowers() {
        return followers;
    }

    public void setMyFollowers(Queue<User> followers) {
        this.followers = followers;
    }

    public void addFollower(User follower) {
        this.followers.add(follower);
    }

    public void addFollowing(User toFollow) {
        this.following.add(toFollow);
    }

    public void removeFollower(User follower) {
        this.followers.remove(follower);
    }

    public void removeFollowing(User toUnFollow) {
        this.following.remove(toUnFollow);
    }

    public boolean isLoggedIn() {
        return loggedIn.get();
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn.set(loggedIn);
    }

    public Queue<User> getImFollowing() {
        return following;
    }

//    public void setImFollowing(Queue<User> following) {
//        this.following = following;
//    }

//    public Queue<Post> getPosted() {
//        return posted;
//    }

//    public void setPosted(Queue<Post> posted) {
//        this.posted = posted;
//    }

//    public Queue<PrivateMessage> getSent() {
//        return sent;
//    }

//    public void setSent(Queue<PrivateMessage> sent) {
//        this.sent = sent;
//    }

//    public Queue<PrivateMessage> getReceived() {
//        return received;
//    }

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
