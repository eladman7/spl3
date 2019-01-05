package bgu.spl.net.impl.BGSServer.Protocol;

public interface Command<T>{
    void execute(T arg);
}
