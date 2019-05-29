package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PowerUp;
import it.polimi.ingsw.model.exception.IllegalUndoException;
import it.polimi.ingsw.model.playerstate.ScopeState;
import it.polimi.ingsw.model.playerstate.SelectScopeTargetState;

/**
 * This command represent the action of use a scope
 */
public class UseScopeCommand implements Command {
    private Player player;
    private SelectScopeTargetState currentState;

    /**
     * This constructor create a command for use a scope
     * @param player is the player who use the scope
     * @param currentState is the current state
     */
    public UseScopeCommand(Player player, SelectScopeTargetState currentState) {
        this.player = player;
        this.currentState = currentState;
    }

    /**
     * This method execute the effect of the scope
     */
    @Override
    public void execute() {
        currentState.getSelectedPlayer().addDamage(PowerUp.TRGETING_SCOPE_DAMAGE, player.getId());
        player.changeState(new ScopeState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon(), currentState.getShootedPlayers()));
    }

    /**
     * This method throw an exception because after a Scope you can't go back
     */
    @Override
    public void undo() {
        throw new IllegalUndoException();
    }

    /**
     * @return true if the command is undoable
     */
    @Override
    public boolean isUndoable() {
        return false;
    }
}
