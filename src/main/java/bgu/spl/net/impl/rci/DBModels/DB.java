package bgu.spl.net.impl.rci.DBModels;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DB {
    private Queue<User> users;
    private Queue<Post> posts;
    private Queue<PrivateMessage> privateMessages;

    public DB() {
        users = new ConcurrentLinkedQueue<>();
        posts = new ConcurrentLinkedQueue<>();
        privateMessages = new ConcurrentLinkedQueue<>();

    }

    public Queue<User> getUsers() {
        return users;
    }

    public void setUsers(Queue<User> users) {
        this.users = users;
    }

    public Queue<Post> getPosts() {
        return posts;
    }

    public void setPosts(Queue<Post> posts) {
        this.posts = posts;
    }

    public Queue<PrivateMessage> getPrivateMessages() {
        return privateMessages;
    }

    public void setPrivateMessages(Queue<PrivateMessage> privateMessages) {
        this.privateMessages = privateMessages;
    }
}
