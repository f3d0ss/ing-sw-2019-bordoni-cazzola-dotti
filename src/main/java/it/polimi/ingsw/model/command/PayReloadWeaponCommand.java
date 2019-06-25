package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.ManageTurnState;
import it.polimi.ingsw.model.playerstate.PendingPaymentReloadWeaponState;
import it.polimi.ingsw.view.commandmessage.CommandMessage;
import it.polimi.ingsw.view.commandmessage.CommandType;
import it.polimi.ingsw.view.commandmessage.SimpleCommandMessage;

/**
 * This command actualize the payment for the reload of a weapon
 */
public class PayReloadWeaponCommand implements Command {
    private Player player;
    private PendingPaymentReloadWeaponState currentState;

    /**
     * This constructor create a command for pay the reloading
     *
     * @param player       is the player who reload
     * @param currentState is the current state
     */
    public PayReloadWeaponCommand(Player player, PendingPaymentReloadWeaponState currentState) {
        this.player = player;
        this.currentState = currentState;
    }

    /**
     * This method actualize the payment and reload the weapon
     */
    @Override
    public void execute() {
        currentState.getSelectedReloadingWeapon().reload();
        player.changeState(new ManageTurnState());
    }

    /**
     * This method refund the player
     */
    @Override
    public void undo() {
        currentState.getSelectedReloadingWeapon().unload();
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
        return new SimpleCommandMessage(CommandType.PAY);
    }
}
