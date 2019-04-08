package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.SelectedPowerUpState;

public class UseTeleportCommand implements Command{
    private Player player;
    private SelectedPowerUpState currentState;

    public UseTeleportCommand(Player player, SelectedPowerUpState currentState) {
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
