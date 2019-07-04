package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.ManageTurnState;
import it.polimi.ingsw.model.playerstate.PendingPaymentWeaponState;
import it.polimi.ingsw.view.commandmessage.CommandMessage;
import it.polimi.ingsw.view.commandmessage.CommandType;
import it.polimi.ingsw.view.commandmessage.SimpleCommandMessage;

/**
 * This command represents the action of buy a weapon
 */
public class PayWeaponCommand implements Command {
    /**
     * This is the player is the player doing the command
     */
    private final Player player;
    /**
     * This is the current state of the player
     */
    private final PendingPaymentWeaponState currentState;

    /**
     * This constructor create a command for buy a weapon
     *
     * @param player       is the player who buy the weapon
     * @param currentState is the current state
     */
    public PayWeaponCommand(Player player, PendingPaymentWeaponState currentState) {
        this.player = player;
        this.currentState = currentState;
    }

    /**
     * This method actualize the payment and give the weapon to the player
     */
    @Override
    public void execute() {
        player.addWeapon(currentState.getSelectedWeapon());
        player.changeState(new ManageTurnState());
    }

    /**
     * This method refund the player and remove the weapon from the player
     */
    @Override
    public void undo() {
        player.removeWeapon(currentState.getSelectedWeapon());
        player.changeState(currentState);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUndoable() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandMessage createCommandMessage() {
        return new SimpleCommandMessage(CommandType.PAY);
    }
}
