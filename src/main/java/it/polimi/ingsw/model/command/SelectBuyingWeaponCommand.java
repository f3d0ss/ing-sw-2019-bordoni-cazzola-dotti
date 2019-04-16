package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.playerstate.DiscardingWeaponState;
import it.polimi.ingsw.model.playerstate.PendingPaymentWeaponState;
import it.polimi.ingsw.model.playerstate.SelectedAggregateActionState;

public class SelectBuyingWeaponCommand extends GrabCommand {
    private Weapon weapon;
    private SelectedAggregateActionState currentState;

    public SelectBuyingWeaponCommand(Player player, SelectedAggregateActionState currentState, Weapon weapon) {
        super(player);
        this.weapon = weapon;
        this.currentState = currentState;
    }

    @Override
    public void execute() {
        if(player.getWeapons().size() == Player.MAX_WEAPONS)
            player.changeState(new DiscardingWeaponState(currentState.getSelectedAggregateAction(), weapon));
        else
            player.changeState(new PendingPaymentWeaponState(currentState.getSelectedAggregateAction(), weapon));
    }

    @Override
    public void undo() {
        player.changeState(currentState);
    }
}
