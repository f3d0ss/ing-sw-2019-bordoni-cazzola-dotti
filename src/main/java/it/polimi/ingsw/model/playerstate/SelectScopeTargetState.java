package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.AggregateAction;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.SelectTargetPlayerCommand;
import it.polimi.ingsw.model.command.UseScopeCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * State when the player is selecting targets to use a Scope power up
 */
public class SelectScopeTargetState extends SelectedWeaponState implements TargetingPlayerState {
    /**
     * This is the list of the players hit by the last shot and selectable by the scope
     */
    private final List<Player> shootedPlayers;
    /**
     * This is the player selected as target of the Scope
     */
    private Player selectedPlayer = null;

    /**
     * This constructor create the state of the player when he is selecting targets to use a Scope power up
     *
     * @param selectedAggregateAction This is the aggregate action the player is executing
     * @param selectedWeapon          This is the weapon selected in the action
     * @param shootedPlayers          This is the list of the players hit by the last shot and selectable by the scope
     */
    public SelectScopeTargetState(AggregateAction selectedAggregateAction, Weapon selectedWeapon, List<Player> shootedPlayers) {
        super(selectedAggregateAction, selectedWeapon);
        this.shootedPlayers = shootedPlayers;
    }

    /**
     * Gets the selected player
     *
     * @return selected player
     */
    public Player getSelectedPlayer() {
        return selectedPlayer;
    }

    /**
     * Gets a list of the players that have just been shot by the players
     *
     * @return list of the shoot players
     */
    public List<Player> getShootedPlayers() {
        return shootedPlayers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTargetPlayer(Player targetPlayer) {
        if (selectedPlayer != null)
            throw new IllegalStateException();
        selectedPlayer = targetPlayer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeTargetPlayer(Player targetPlayer) {
        if (selectedPlayer != targetPlayer)
            throw new IllegalStateException();
        selectedPlayer = null;
    }

    /**
     * {@inheritDoc}
     */
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
