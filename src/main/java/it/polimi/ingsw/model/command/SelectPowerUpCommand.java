package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PowerUp;
import it.polimi.ingsw.model.playerstate.ManageTurnState;
import it.polimi.ingsw.model.playerstate.SelectedPowerUpState;

public class SelectPowerUpCommand implements Command{
    private Player player;
    private PowerUp powerUp;
    private ManageTurnState currentState;

    public SelectPowerUpCommand(Player player, PowerUp powerUp, ManageTurnState currentState) {
        this.player = player;
        this.powerUp = powerUp;
        this.currentState = currentState;
    }

    /**
     * This method select the powerUp to use
     */
    @Override
    public void execute() {
        player.changeState(new SelectedPowerUpState(powerUp));
    }

    /**
     * This method restore the previous state
     */
    @Override
    public void undo() {
        player.changeState(currentState);
    }
}
