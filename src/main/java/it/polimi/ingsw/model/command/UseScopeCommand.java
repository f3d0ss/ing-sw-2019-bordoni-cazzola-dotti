package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PowerUp;
import it.polimi.ingsw.model.TargetingScope;
import it.polimi.ingsw.model.playerstate.ScopeState;
import it.polimi.ingsw.model.playerstate.SelectScopeTargetState;

public class UseScopeCommand implements Command {
    private Player player;
    private SelectScopeTargetState currentState;

    public UseScopeCommand(Player player, SelectScopeTargetState currentState) {
        this.player = player;
        this.currentState = currentState;
    }

    @Override
    public void execute() {
        currentState.getSelectedPlayer().addDamage(TargetingScope.DAMAGE, player.getId());
        player.changeState(new ScopeState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon(), currentState.getShootedPlayers()));
    }

    @Override
    public void undo() {
        //TODO: decide if permit undo after damage
    }
}
