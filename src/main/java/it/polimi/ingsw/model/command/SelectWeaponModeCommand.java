package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.WeaponMode;
import it.polimi.ingsw.model.playerstate.ChoosingWeaponOptionState;
import it.polimi.ingsw.model.playerstate.PendingPaymentWeaponOptionState;

/**
 * This command represent the action of select a weapon mode
 */
public class SelectWeaponModeCommand implements Command {
    protected Player player;
    protected ChoosingWeaponOptionState currentState;
    private WeaponMode weaponMode;

    /**
     * This constructor create a command for select a weapon mode
     * @param player is the player who select the weapon mode
     * @param currentState is the current state
     * @param weaponMode is the weapon mode selected
     */
    public SelectWeaponModeCommand(Player player, ChoosingWeaponOptionState currentState, WeaponMode weaponMode) {
        this.player = player;
        this.currentState = currentState;
        this.weaponMode = weaponMode;
    }

    /**
     * This method execute the command
     */
    @Override
    public void execute() {
        currentState.getSelectedWeapon().setSelectedWeaponMode(weaponMode);
        player.changeState(new PendingPaymentWeaponOptionState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon(), weaponMode.getCost()));

    }

    /**
     * This method undo the command
     */
    @Override
    public void undo() {
        currentState.getSelectedWeapon().deselectWeaponMode(weaponMode);
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
