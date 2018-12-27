package bgu.spl.net.impl.rci.CommandModels;

import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.ExecutionInfo;


public class StatsCommand implements Command<ExecutionInfo> {
    private String username;

    public StatsCommand(String username) {
        this.username = username;
    }

    @Override
    public void execute(ExecutionInfo execInfo) {

    }
}

