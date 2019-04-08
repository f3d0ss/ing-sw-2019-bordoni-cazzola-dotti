package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.SelectedAggregateActionState;

public class SelectShootActionCommand implements Command {
    private Player player;
    private SelectedAggregateActionState currentState;

    public SelectShootActionCommand(Player player, SelectedAggregateActionState currentState) {
        this.player = player;
        this.currentState = currentState;
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
