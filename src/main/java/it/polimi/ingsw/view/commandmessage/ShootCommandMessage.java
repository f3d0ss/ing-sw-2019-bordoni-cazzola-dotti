package it.polimi.ingsw.view.commandmessage;


import it.polimi.ingsw.view.ConcreteView;

import java.util.List;

/**
 * This class wraps an list of of {@link EffectCommandMessage} during transfer from server to client.
 */
public class ShootCommandMessage extends CommandMessage {

    private final List<EffectCommandMessage> effectCommandMessageList;

    public ShootCommandMessage(CommandType type, List<EffectCommandMessage> effectCommandMessageList) {
        super(type);
        this.effectCommandMessageList = effectCommandMessageList;
    }

    /**
     * Gets the list of EffectCommandMessage contained in attachment to the message.
     *
     * @return the list of EffectCommandMessage in attachment
     */
    public List<EffectCommandMessage> getEffectCommandMessageList() {
        return effectCommandMessageList;
    }


    /**
     * Composes a string with all effects contained in the list.
     *
     * @return the string with all effects
     */
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
