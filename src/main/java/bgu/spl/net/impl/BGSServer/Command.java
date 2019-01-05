package bgu.spl.net.impl.BGSServer;

public interface Command<T>{
    void execute(T arg);
}
