package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TargetingScope;
import it.polimi.ingsw.model.exception.IllegalUndoException;
import it.polimi.ingsw.model.playerstate.ScopeState;
import it.polimi.ingsw.model.playerstate.SelectScopeTargetState;

public class UseScopeCommand implements Command {
    private Player player;
    private SelectScopeTargetState currentState;

    public UseScopeCommand(Player player, SelectScopeTargetState currentState) {
        this.player = player;
        this.currentState = currentState;
    }

    /**
     * This method execute the effect of the scope
     */
    @Override
    public void execute() {
        currentState.getSelectedPlayer().addDamage(TargetingScope.DAMAGE, player.getId());
        player.changeState(new ScopeState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon(), currentState.getShootedPlayers()));
    }

    /**
     * This method throw an exception because after a Scope you can't go back
     */
    @Override
    public void undo() {
        throw new IllegalUndoException();
    }
}
