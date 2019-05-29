package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.ChoosingWeaponState;
import it.polimi.ingsw.model.playerstate.SelectedAggregateActionState;

public class SelectShootActionCommand implements Command {
    private Player player;
    private SelectedAggregateActionState currentState;

    public SelectShootActionCommand(Player player, SelectedAggregateActionState currentState) {
        this.player = player;
        this.currentState = currentState;
    }

    /**
     * This method change the player state to ChoosingWeaponState
     */
    @Override
    public void execute() {
        player.changeState(new ChoosingWeaponState(currentState.getSelectedAggregateAction()));
    }

    /**
     * This method restore the previous state
     */
    @Override
    public void undo() {
        player.changeState(currentState);
    }

    @Override
    public boolean isUndoable() {
        return true;
    }
}
