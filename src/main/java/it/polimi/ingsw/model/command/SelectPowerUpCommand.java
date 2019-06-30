package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PowerUp;
import it.polimi.ingsw.model.PowerUpID;
import it.polimi.ingsw.model.playerstate.ManageTurnState;
import it.polimi.ingsw.model.playerstate.SelectedNewtonState;
import it.polimi.ingsw.model.playerstate.SelectedTeleporterState;
import it.polimi.ingsw.view.commandmessage.CommandMessage;
import it.polimi.ingsw.view.commandmessage.CommandType;
import it.polimi.ingsw.view.commandmessage.PowerUpCommandMessage;

/**
 * This command represent the action of select a power up
 */
public class SelectPowerUpCommand implements Command {
    private final Player player;
    private final ManageTurnState currentState;
    private final PowerUp powerUp;

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
            player.changeState(new SelectedTeleporterState());
        else if (powerUp.getType() == PowerUpID.NEWTON)
            player.changeState(new SelectedNewtonState());
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

    @Override
    public CommandMessage createCommandMessage() {
        return new PowerUpCommandMessage(CommandType.SELECT_POWER_UP, powerUp.getType(), powerUp.getColor());
    }
}
