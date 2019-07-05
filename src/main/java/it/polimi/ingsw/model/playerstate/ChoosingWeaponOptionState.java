package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.AggregateAction;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.command.Command;

import java.util.ArrayList;
import java.util.List;

/**
 * State of the player while choosing a {@link it.polimi.ingsw.model.WeaponMode}
 */
public class ChoosingWeaponOptionState extends SelectedWeaponState {
    /**
     * This constructor create the state of the player while choosing a weapon mode
     *
     * @param selectedAggregateAction This is the aggregate action the player is executing
     * @param selectedWeapon          This is the weapon the player selected
     */
    public ChoosingWeaponOptionState(AggregateAction selectedAggregateAction, Weapon selectedWeapon) {
        super(selectedAggregateAction, selectedWeapon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Command> getPossibleCommands(Player player) {
        return new ArrayList<>(getSelectedWeapon().getSelectWeaponModeCommands(player, this));

    }
}
