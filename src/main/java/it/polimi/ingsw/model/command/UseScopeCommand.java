package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.ScopeState;

public class UseScopeCommand implements Command {
    private Player player;
    private ScopeState currentState;

    public UseScopeCommand(Player player, ScopeState currentState) {
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
