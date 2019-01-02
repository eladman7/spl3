package bgu.spl.net.impl.rci.CommandModels;

import bgu.spl.net.api.MessageContainer;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.rci.ExecutionInfo;

public class Responder {
    protected void ack(ExecutionInfo execInfo, short opcode, Object additionalData, Command<ExecutionInfo> command) {
        Connections<MessageContainer> connections = execInfo.getConnections();
        MessageContainer messageContainer = new MessageContainer();
        messageContainer.setType(MessageContainer.Type.ACK);
        messageContainer.setOriginOpcode(opcode);
        messageContainer.setAdditionalData(additionalData);
        messageContainer.setCommand(command);
        connections.send(execInfo.getConnId(), messageContainer);
    }

    protected void error(ExecutionInfo execInfo, short opcode) {
        Connections<MessageContainer> connections = execInfo.getConnections();
        MessageContainer messageContainer = new MessageContainer();
        messageContainer.setType(MessageContainer.Type.ERROR);
        messageContainer.setOriginOpcode(opcode);
        connections.send(execInfo.getConnId(), messageContainer);
    }

    protected void notifyPublic(ExecutionInfo execInfo, String fromUsername, int toUserConnId, String content, Command<ExecutionInfo> command) {
        _notify(execInfo, fromUsername, toUserConnId, content, false, command);
    }

    protected void notifyPrivate(ExecutionInfo execInfo, String fromUsername, int toUserConnId, String content, Command<ExecutionInfo> command) {
        _notify(execInfo, fromUsername, toUserConnId, content, true, command);
    }

    private void _notify(ExecutionInfo execInfo, String fromUsername, int toUserConnId, String content, boolean pm, Command<ExecutionInfo> command) {
        Connections<MessageContainer> connections = execInfo.getConnections();
        MessageContainer messageContainer = new MessageContainer();
        messageContainer.setType(MessageContainer.Type.NOTIFICATION);
        messageContainer.setFromUsername(fromUsername);
        messageContainer.setContent(content);
        messageContainer.setPm(pm);
        connections.send(toUserConnId, messageContainer);
    }
}
