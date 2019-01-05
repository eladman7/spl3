package bgu.spl.net.impl.BGSServer.DBModels;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DB {
    private Queue<User> users;
    private Queue<Post> posts;
    private Queue<PrivateMessage> privateMessages;

    private final Object usersLock;
    private final Object postsLock;
    private final Object privateMessagesLock;

    public DB() {
        users = new ConcurrentLinkedQueue<>();
        posts = new ConcurrentLinkedQueue<>();
        privateMessages = new ConcurrentLinkedQueue<>();
        usersLock = new Object();
        postsLock = new Object();
        privateMessagesLock = new Object();
    }

    public Object getUsersLock() {
        return usersLock;
    }

    public Object getPostsLock() {
        return postsLock;
    }

    public Object getPrivateMessagesLock() {
        return privateMessagesLock;
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

    public void setUsers(Queue<User> users) {
        this.users = users;
    }

    public Post addPost(String content, User from){
        Post post = new Post(content, from);
        posts.add(post);
        return post;
    }

    public Queue<Post> getPosts() {
        return posts;
    }

    public void setPosts(Queue<Post> posts) {
        this.posts = posts;
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

    public void setPrivateMessages(Queue<PrivateMessage> privateMessages) {
        this.privateMessages = privateMessages;
    }

    public void addUser(String username, String password) {
        users.add(new User(username, password));
    }
}
