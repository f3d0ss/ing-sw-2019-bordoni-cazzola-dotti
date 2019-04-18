package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.WeaponMode;
import it.polimi.ingsw.model.playerstate.ChoosingWeaponOptionState;
import it.polimi.ingsw.model.playerstate.PendingPaymentWeaponOptionState;

public class SelectWeaponModeCommand implements Command {
    protected Player player;
    protected ChoosingWeaponOptionState currentState;
    private WeaponMode weaponMode;

    public SelectWeaponModeCommand(Player player, ChoosingWeaponOptionState currentState, WeaponMode weaponMode) {
        this.player = player;
        this.currentState = currentState;
        this.weaponMode = weaponMode;
    }

    @Override
    public void execute() {
        currentState.getSelectedWeapon().setSelectedWeaponMode(weaponMode);
        player.changeState(new PendingPaymentWeaponOptionState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon(), weaponMode.getCost()));

    }

    @Override
    public void undo() {

    }
}
