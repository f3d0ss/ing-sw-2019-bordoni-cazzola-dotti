package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PowerUp;
import it.polimi.ingsw.model.PowerUpID;
import it.polimi.ingsw.model.playerstate.ManageTurnState;
import it.polimi.ingsw.model.playerstate.SelectedNewtonState;
import it.polimi.ingsw.model.playerstate.SelectedTeleporterState;

/**
 * This command represent the action of select a power up
 */
public class SelectPowerUpCommand implements Command {
    private Player player;
    private ManageTurnState currentState;
    private PowerUp powerUp;

    /**
     * This constructor create a command for select a power up
     *
     * @param player       is the player who select the power up
     * @param powerUp      is the power up selected
     * @param currentState is the current state
     */
    public SelectPowerUpCommand(Player player, PowerUp powerUp, ManageTurnState currentState) {
        this.player = player;
        this.currentState = currentState;
        this.powerUp = powerUp;
    }

    /**
     * This method select the powerUp to use
     */
    @Override
    public void execute() {
        player.pay(powerUp);
        if (powerUp.getType() == PowerUpID.TELEPORTER)
            player.changeState(new SelectedTeleporterState(powerUp));
        else if (powerUp.getType() == PowerUpID.NEWTON)
            player.changeState(new SelectedNewtonState(powerUp));
    }

    /**
     * This method restore the previous state
     */
    @Override
    public void undo() {
        player.refund(powerUp);
        player.changeState(currentState);
    }

    /**
     * @return true if the command is undoable
     */
    @Override
    public boolean isUndoable() {
        return true;
    }
}
