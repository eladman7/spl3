package bgu.spl.net.impl.rci;

import java.io.Serializable;

public interface Command<D> extends Serializable {
    // T here is DBModels
    Serializable execute(D arg);
}
