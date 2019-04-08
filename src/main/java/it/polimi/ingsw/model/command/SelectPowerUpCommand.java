package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PowerUp;
import it.polimi.ingsw.model.playerstate.ManageTurnState;

public class SelectPowerUpCommand implements Command{
    private Player player;
    private PowerUp powerUp;
    private ManageTurnState currentState;

    public SelectPowerUpCommand(Player player, PowerUp powerUp, ManageTurnState currentState) {
        this.player = player;
        this.powerUp = powerUp;
        this.currentState = currentState;
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
