package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.AggregateAction;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Square;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.DoneCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * State when the player is shooting and selecting targets
 */
public class ReadyToShootState extends SelectedWeaponState implements TargetingPlayerState, TargetingSquareState, MovableState {
    /**
     * This constructor create the state of the player
     *
     * @param selectedAggregateAction This is the aggregate action the player is executing
     * @param selectedWeapon          This is the weapon selected in the action
     */
    public ReadyToShootState(AggregateAction selectedAggregateAction, Weapon selectedWeapon) {
        super(selectedAggregateAction, selectedWeapon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Command> getPossibleCommands(Player player) {
        List<Command> commands = new ArrayList<>();
        if (!getSelectedWeapon().isLoaded()) commands.add(new DoneCommand(player, this));
        commands.addAll(getSelectedWeapon().getPossibleCommands(player.getMatch().getBoard(), player, this));
        return commands;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTargetPlayer(Player targetPlayer) {
        getSelectedWeapon().addTargetPlayer(targetPlayer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeTargetPlayer(Player targetPlayer) {
        getSelectedWeapon().removeTargetPlayer(targetPlayer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTargetSquare(Square targetSquare) {
        getSelectedWeapon().addTargetSquare(targetSquare);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeTargetSquare(Square targetSquare) {
        getSelectedWeapon().removeTargetSquare(targetSquare);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void useMoves() {
        getSelectedWeapon().useExtraMoves();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetMoves() {
        getSelectedWeapon().resetMoves();
    }
}
