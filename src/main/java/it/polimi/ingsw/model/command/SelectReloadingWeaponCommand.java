package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.playerstate.*;

public class SelectReloadingWeaponCommand implements Command{
    private Player player;
    private Weapon weapon;
    private PlayerState currentState;
    private PlayerState nextState;

    public SelectReloadingWeaponCommand(Player player, Weapon weapon, ChoosingWeaponState currentState) {
        this.player = player;
        this.weapon = weapon;
        this.currentState = currentState;
        nextState = new PendingPaymentReloadBeforeShotState(currentState.getSelectedAggregateAction(),weapon);
    }

    public SelectReloadingWeaponCommand(Player player, Weapon weapon, ManageTurnState currentState) {
        this.player = player;
        this.weapon = weapon;
        this.currentState = currentState;
        nextState = new PendingPaymentReloadWeaponState(weapon);
    }

    @Override
    public void execute() {
        player.changeState(nextState);
    }

    @Override
    public void undo() {
        player.changeState(currentState);
    }
}
