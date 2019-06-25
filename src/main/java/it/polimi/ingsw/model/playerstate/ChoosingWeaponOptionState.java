package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.AggregateAction;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.command.Command;

import java.util.ArrayList;
import java.util.List;

public class ChoosingWeaponOptionState extends SelectedWeaponState {

    public ChoosingWeaponOptionState(AggregateAction selectedAggregateAction, Weapon selectedWeapon) {
        super(selectedAggregateAction, selectedWeapon);
    }

    @Override
    public List<Command> getPossibleCommands(Player player) {
        return new ArrayList<>(getSelectedWeapon().getSelectWeaponModeCommands(player, this));

    }
}
