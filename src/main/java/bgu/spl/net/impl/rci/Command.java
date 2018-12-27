package bgu.spl.net.impl.rci;

public interface Command<T>{
    void execute(T arg);
}
