package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.AggregateAction;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.SpawnSquare;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.SelectDiscardedWeaponCommand;

import java.util.ArrayList;
import java.util.List;

public class DiscardingWeaponState extends SelectedWeaponState {
    private final SpawnSquare spawn;

    public DiscardingWeaponState(AggregateAction selectedAggregateAction, Weapon selectedWeapon, SpawnSquare spawn) {
        super(selectedAggregateAction, selectedWeapon);
        this.spawn = spawn;
    }

    public SpawnSquare getSpawn() {
        return spawn;
    }

    @Override
    public List<Command> getPossibleCommands(Player player) {
        List<Command> commands = new ArrayList<>();
        player.getWeapons().forEach((weapon -> commands.add(new SelectDiscardedWeaponCommand(player, this, weapon))));
        return commands;
    }
}
