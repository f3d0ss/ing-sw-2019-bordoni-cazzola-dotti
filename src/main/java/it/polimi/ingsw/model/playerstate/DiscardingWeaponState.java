package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.AggregateAction;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.DoneCommand;
import it.polimi.ingsw.model.command.SelectDiscardedWeaponCommand;

import java.util.ArrayList;
import java.util.List;

public class DiscardingWeaponState extends SelectedWeaponState {
    public DiscardingWeaponState(AggregateAction selectedAggregateAction, Weapon selectedWeapon) {
        super(selectedAggregateAction, selectedWeapon);
    }

    @Override
    public List<Command> getPossibleCommands(Player player) {
        List<Command> commands = new ArrayList<>();
        player.getWeapons().forEach((weapon -> commands.add(new SelectDiscardedWeaponCommand(player, this, weapon))));
        return commands;
    }
}
