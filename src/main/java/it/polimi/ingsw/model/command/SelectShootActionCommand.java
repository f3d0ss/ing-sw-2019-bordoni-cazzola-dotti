package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.ChoosingWeaponState;
import it.polimi.ingsw.model.playerstate.SelectedAggregateActionState;
import it.polimi.ingsw.view.commandmessage.CommandMessage;
import it.polimi.ingsw.view.commandmessage.CommandType;
import it.polimi.ingsw.view.commandmessage.SimpleCommandMessage;

/**
 * This command represent the action of select a shoot action
 */
public class SelectShootActionCommand implements Command {
    /**
     * This is the player is the player doing the command
     */
    private final Player player;
    /**
     * This is the current state of the player
     */
    private final SelectedAggregateActionState currentState;

    /**
     * This constructor create a command for select a shoot action
     *
     * @param player       is the player who select the shoot action
     * @param currentState is the current state
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUndoable() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandMessage createCommandMessage() {
        return new SimpleCommandMessage(CommandType.SELECT_SHOOT_ACTION);
    }
}
