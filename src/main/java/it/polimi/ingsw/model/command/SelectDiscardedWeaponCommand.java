package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.playerstate.DiscardingWeaponState;
import it.polimi.ingsw.model.playerstate.PendingPaymentWeaponState;
import it.polimi.ingsw.view.commandmessage.CommandMessage;
import it.polimi.ingsw.view.commandmessage.CommandType;
import it.polimi.ingsw.view.commandmessage.WeaponCommandMessage;

/**
 * This command represent the action of discard a weapon used only when a player has already 3 weapons
 */
public class SelectDiscardedWeaponCommand implements Command {

    private Player player;
    private DiscardingWeaponState currentState;
    private Weapon weaponToDiscard;
    private boolean wasLoaded;

    /**
     * This constructor create a command for discard a weapon
     *
     * @param player          is the player who discard the weapon
     * @param currentState    is the current state
     * @param weaponToDiscard is the weapon to discard
     */
    public SelectDiscardedWeaponCommand(Player player, DiscardingWeaponState currentState, Weapon weaponToDiscard) {
        this.player = player;
        this.currentState = currentState;
        this.weaponToDiscard = weaponToDiscard;
    }

    /**
     * This method discard a weapon and put it in the spawn
     */
    @Override
    public void execute() {
        wasLoaded = weaponToDiscard.isLoaded();
        weaponToDiscard.reload();
        player.removeWeapon(weaponToDiscard);
        currentState.getSpawn().addWeapon(weaponToDiscard);
        player.changeState(new PendingPaymentWeaponState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon()));
    }

    /**
     * This method restore the weapon discarded
     */
    @Override
    public void undo() {
        if (!wasLoaded)
            weaponToDiscard.unload();
        player.addWeapon(weaponToDiscard);
        currentState.getSpawn().removeWeapon(weaponToDiscard);
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
        return new WeaponCommandMessage(CommandType.SELECT_DISCARD_WEAPON, weaponToDiscard.getName());
    }
}
