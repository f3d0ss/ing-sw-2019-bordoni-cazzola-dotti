package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.SelectedAggregateActionState;

public abstract class GrabCommand implements Command {
    protected Player player;
    protected SelectedAggregateActionState currentState;

    public GrabCommand(Player player, SelectedAggregateActionState currentState) {
        this.player = player;
        this.currentState = currentState;
    }
}
