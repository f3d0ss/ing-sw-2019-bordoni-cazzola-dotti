package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.AggregateAction;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.SelectTargetPlayerCommand;
import it.polimi.ingsw.model.command.UseScopeCommand;

import java.util.ArrayList;
import java.util.List;

public class SelectScopeTargetState extends SelectedWeaponState implements TargetingPlayerState {
    private final List<Player> shootedPlayers;
    private Player selectedPlayer;

    public SelectScopeTargetState(AggregateAction selectedAggregateAction, Weapon selectedWeapon, List<Player> shootedPlayers) {
        super(selectedAggregateAction, selectedWeapon);
        this.shootedPlayers = shootedPlayers;
    }

    public Player getSelectedPlayer() {
        return selectedPlayer;
    }

    public List<Player> getShootedPlayers() {
        return shootedPlayers;
    }

    public void addTargetPlayer(Player targetPlayer) {
        if (selectedPlayer != null)
            throw new IllegalStateException();
        selectedPlayer = targetPlayer;
    }

    public void removeTargetPlayer(Player targetPlayer) {
        if (selectedPlayer != targetPlayer)
            throw new IllegalStateException();
        selectedPlayer = null;
    }

    @Override
    public List<Command> getPossibleCommands(Player player) {
        List<Command> commands = new ArrayList<>();
        if (selectedPlayer != null) {
            commands.add(new UseScopeCommand(player, this));
            return commands;
        }
        shootedPlayers.forEach(shootedPlayer -> commands.add(new SelectTargetPlayerCommand(this, shootedPlayer)));
        return commands;
    }

}
