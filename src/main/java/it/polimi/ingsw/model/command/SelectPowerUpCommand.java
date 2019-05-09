package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Newton;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PowerUp;
import it.polimi.ingsw.model.Teleporter;
import it.polimi.ingsw.model.playerstate.ManageTurnState;
import it.polimi.ingsw.model.playerstate.PlayerState;
import it.polimi.ingsw.model.playerstate.SelectedNewtonState;
import it.polimi.ingsw.model.playerstate.SelectedTeleporterState;

public class SelectPowerUpCommand implements Command{
    private Player player;
    private ManageTurnState currentState;
    private PlayerState nextState;
    private PowerUp powerUp;

    public SelectPowerUpCommand(Player player, Newton powerUp, ManageTurnState currentState) {
        this.player = player;
        this.currentState = currentState;
        nextState = new SelectedNewtonState(powerUp);
        this.powerUp = powerUp;
    }

    public SelectPowerUpCommand(Player player, Teleporter powerUp, ManageTurnState currentState) {
        this.player = player;
        this.currentState = currentState;
        nextState = new SelectedTeleporterState(powerUp);
        this.powerUp = powerUp;
    }

    /**
     * This method select the powerUp to use
     */
    @Override
    public void execute() {
        player.pay(powerUp);
        player.changeState(nextState);
    }

    /**
     * This method restore the previous state
     */
    @Override
    public void undo() {
        player.refund(powerUp);
        player.changeState(currentState);
    }
}
