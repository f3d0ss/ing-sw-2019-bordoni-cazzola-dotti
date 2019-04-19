package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.AggregateAction;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TargetingScope;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.DoneCommand;
import it.polimi.ingsw.model.command.SelectScopeCommand;
import it.polimi.ingsw.model.command.UseScopeCommand;

import java.util.ArrayList;
import java.util.List;

public class ScopeState extends SelectedWeaponState {
    private List<Player> shootedPlayer;

    public ScopeState(AggregateAction selectedAggregateAction, Weapon selectedWeapon, List<Player> shootedPlayer) {
        super(selectedAggregateAction, selectedWeapon);
        this.shootedPlayer = shootedPlayer;
    }

    public List<Player> getShootedPlayer() {
        return shootedPlayer;
    }

    @Override
    public List<Command> getPossibleCommands(Player player) {
        List<Command> commands = new ArrayList<>();
        player.getPowerUps().forEach(powerUp -> {
            if (powerUp.isScope())
                commands.add(new SelectScopeCommand(player, this, (TargetingScope) powerUp));
        });
        commands.add(new DoneCommand(player, this));
        return commands;
    }
}
