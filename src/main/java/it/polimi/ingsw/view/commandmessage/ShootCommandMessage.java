package it.polimi.ingsw.view.commandmessage;


import java.util.List;

public class ShootCommandMessage extends CommandMessage {

    private final List<EffectCommandMessage> effectCommandMessageList;

    public ShootCommandMessage(CommandType type, List<EffectCommandMessage> effectCommandMessageList) {
        super(type);
        this.effectCommandMessageList = effectCommandMessageList;
    }

    public List<EffectCommandMessage> getEffectCommandMessageList() {
        return effectCommandMessageList;
    }
}
