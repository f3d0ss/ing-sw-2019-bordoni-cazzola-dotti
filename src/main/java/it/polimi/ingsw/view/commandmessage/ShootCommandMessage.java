package it.polimi.ingsw.view.commandmessage;


import it.polimi.ingsw.view.ConcreteView;

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

    public String printCommand(){
        String shootString = "";
        for (int j = 0; j < effectCommandMessageList.size(); j++) {
            EffectCommandMessage effectCommandMessage = effectCommandMessageList.get(j);
            shootString = new StringBuilder().append(shootString).append(effectCommandMessage.getPlayer().playerIdName()).append(" ").append(effectCommandMessage.getDamage()).append(" danno").toString();
            if (effectCommandMessage.getMarks() > 0)
                shootString = new StringBuilder().append(shootString).append(" + ").append(effectCommandMessage.getMarks()).append(" marchi").toString();
            if (effectCommandMessage.getCol() != null)
                shootString = new StringBuilder().append(shootString).append("+ spostalo in ").append(ConcreteView.getHorizontalCoordinateName(effectCommandMessage.getRow())).append(ConcreteView.getVerticalCoordinateName(effectCommandMessage.getCol())).toString();
            if (effectCommandMessageList.size() > j + 1)
                shootString = new StringBuilder().append(shootString).append("\n").toString();
        }
        return shootString;
    }
}
