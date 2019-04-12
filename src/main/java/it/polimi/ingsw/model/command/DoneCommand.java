package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.PlayerState;

public class DoneCommand implements Command {
    private PlayerState currentState;
    private Player player;

    public DoneCommand(Player player, PlayerState currentState) {
        this.currentState = currentState;
        this.player = player;
    }
    /*create one constructor for each possible done in chart*/

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
