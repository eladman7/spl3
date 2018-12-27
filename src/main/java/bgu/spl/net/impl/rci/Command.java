package bgu.spl.net.impl.rci;

import bgu.spl.net.api.Messages.Response;

public interface Command<D>{
    // T here is DBModels
    Response execute(D db);
}
