package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.AggregateAction;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.SpawnSquare;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.SelectDiscardedWeaponCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * State of the player while discarding a weapon
 */
public class DiscardingWeaponState extends SelectedWeaponState {
    private final SpawnSquare spawn;

    /**
     * This constructor create the state of the player while discarding a weapon
     *
     * @param selectedAggregateAction This is the aggregate action the player is executing
     * @param selectedWeapon          This is the weapon selected for the buy
     * @param spawn                   This is the spawn where the player is buying a weapon
     */
    public DiscardingWeaponState(AggregateAction selectedAggregateAction, Weapon selectedWeapon, SpawnSquare spawn) {
        super(selectedAggregateAction, selectedWeapon);
        this.spawn = spawn;
    }

    /**
     * Gets spawn square
     *
     * @return spawn square
     */
    public SpawnSquare getSpawn() {
        return spawn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Command> getPossibleCommands(Player player) {
        List<Command> commands = new ArrayList<>();
        player.getWeapons().forEach((weapon -> commands.add(new SelectDiscardedWeaponCommand(player, this, weapon))));
        return commands;
    }
}
