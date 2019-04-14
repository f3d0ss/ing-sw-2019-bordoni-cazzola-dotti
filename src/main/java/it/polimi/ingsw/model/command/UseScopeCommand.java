package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PowerUp;
import it.polimi.ingsw.model.TargetingScope;
import it.polimi.ingsw.model.playerstate.ScopeState;

public class UseScopeCommand implements Command {
    private Player player;
    private ScopeState currentState;
    private TargetingScope scope;

    public UseScopeCommand(Player player, ScopeState currentState, TargetingScope scope) {
        this.player = player;
        this.currentState = currentState;
        this.scope = scope;
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
