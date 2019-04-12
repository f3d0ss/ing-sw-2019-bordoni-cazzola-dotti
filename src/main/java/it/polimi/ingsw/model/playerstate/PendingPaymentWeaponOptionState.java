package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.command.Command;


import java.util.List;
import java.util.Map;

public class PendingPaymentWeaponOptionState extends SelectedWeaponState implements PendingPaymentState {
    private Map<Color, Integer> modeCost;
    public PendingPaymentWeaponOptionState(AggregateAction selectedAggregateAction, Weapon selectedWeapon, Map<Color,Integer> firstOptionalModeCost) {
        super(selectedAggregateAction, selectedWeapon);
        this.modeCost = firstOptionalModeCost;
    }

    @Override
    public void addPendingAmmo(Color color) {

    }

    @Override
    public void addPendingCard(PowerUp powerUp) {

    }

    @Override
    public List<Command> getPossibleCommands(Player player) {
        return null;
    }
}
