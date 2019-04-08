package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.playerstate.SelectedAggregateActionState;

public class SelectBuyingWeaponCommand implements Command {

    private Player player;
    private Weapon buyingWeapon;
    private SelectedAggregateActionState currentState;

    public SelectBuyingWeaponCommand(Player player, Weapon buyingWeapon, SelectedAggregateActionState currentState) {
        this.player = player;
        this.buyingWeapon = buyingWeapon;
        this.currentState = currentState;
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
