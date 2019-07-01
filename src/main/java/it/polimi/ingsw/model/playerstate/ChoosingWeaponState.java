package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.AggregateAction;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.SelectReloadingWeaponCommand;
import it.polimi.ingsw.model.command.SelectWeaponCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * State of the player while choosing which {@link it.polimi.ingsw.model.Weapon} to use
 */
public class ChoosingWeaponState extends AfterSelectedAggregateActionState {
    public ChoosingWeaponState(AggregateAction selectedAggregateAction) {
        super(selectedAggregateAction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Command> getPossibleCommands(Player player) {
        List<Command> commands = new ArrayList<>();

        player.getWeapons().forEach(weapon -> {
            if (weapon.isLoaded())
                commands.add(new SelectWeaponCommand(player, weapon, this));
            else if (getSelectedAggregateAction().isReload())
                commands.add(new SelectReloadingWeaponCommand(player, weapon, this));
        });
        return commands;
    }
}
