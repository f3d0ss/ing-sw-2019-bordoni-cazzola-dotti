package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.AggregateAction;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.DoneCommand;
import it.polimi.ingsw.model.command.SelectScopeCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * State when te player uses a Scope powerup
 */
public class ScopeState extends SelectedWeaponState {
    private final List<Player> shootedPlayer;

    public ScopeState(AggregateAction selectedAggregateAction, Weapon selectedWeapon, List<Player> shootedPlayer) {
        super(selectedAggregateAction, selectedWeapon);
        this.shootedPlayer = shootedPlayer;
    }


    /**
     * Gets a list of the players that have just been shot by the players
     *
     * @return list of the shoot players
     */
    public List<Player> getShootedPlayer() {
        return shootedPlayer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Command> getPossibleCommands(Player player) {
        List<Command> commands = new ArrayList<>();
        player.getTargetingScopes().forEach(scope -> commands.add(new SelectScopeCommand(player, this, scope)));
        commands.add(new DoneCommand(player, this));
        return commands;
    }
}
