package bgu.spl.net.impl.BGSServer.DBModels;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DB {
    private Queue<User> users;
    private Queue<Post> posts;
    private Queue<PrivateMessage> privateMessages;

    private final Object usersLock;

    public DB() {
        users = new ConcurrentLinkedQueue<>();
        posts = new ConcurrentLinkedQueue<>();
        privateMessages = new ConcurrentLinkedQueue<>();
        usersLock = new Object();
    }

    public Object getUsersLock() {
        return usersLock;
    }

    public Queue<User> getUsers() {
        return users;
    }

    public User getUser(String username){
        for (User user: users){
            if (user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public User getUser(int connectionId){
        for (User user: users){
            if (user.getConnectionId() == connectionId){
                return user;
            }
        }
        return null;
    }

    public Post addPost(String content, User from){
        Post post = new Post(content, from);
        posts.add(post);
        return post;
    }

    public Queue<Post> getPosts() {
        return posts;
    }

    public void addPrivateMessage(String content, User from, User to){
        privateMessages.add(new PrivateMessage(content, from, to, false));
    }

    public void addPendingMessage(String content, User from, User to){
        privateMessages.add(new PrivateMessage(content, from, to, true));
    }

    public Queue<PrivateMessage> getPrivateMessages() {
        return privateMessages;
    }

    public void addUser(String username, String password) {
        users.add(new User(username, password));
    }
}
