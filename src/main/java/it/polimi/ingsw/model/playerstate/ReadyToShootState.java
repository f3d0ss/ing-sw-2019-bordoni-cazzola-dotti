package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.AggregateAction;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Square;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.DoneCommand;

import java.util.ArrayList;
import java.util.List;

public class ReadyToShootState extends SelectedWeaponState implements TargetingPlayerState, TargetingSquareState, MovableState {
    public ReadyToShootState(AggregateAction selectedAggregateAction, Weapon selectedWeapon) {
        super(selectedAggregateAction, selectedWeapon);
    }

    @Override
    public List<Command> getPossibleCommands(Player player) {
        List<Command> commands = new ArrayList<>();
        if(!getSelectedWeapon().isLoaded()) commands.add(new DoneCommand(player, this));
        commands.addAll(getSelectedWeapon().getPossibleCommands(player.getMatch().getBoard(), player, this));
        return commands;
    }

    public void addTargetPlayer(Player targetPlayer) {
        getSelectedWeapon().addTargetPlayer(targetPlayer);
    }

    public void removeTargetPlayer(Player targetPlayer) {
        getSelectedWeapon().removeTargetPlayer(targetPlayer);
    }

    public void addTargetSquare(Square targetSquare) {
        getSelectedWeapon().addTargetSquare(targetSquare);
    }

    public void removeTargetSquare(Square targetSquare) {
        getSelectedWeapon().removeTargetSquare(targetSquare);
    }

    @Override
    public void useMoves() {
        getSelectedWeapon().useExtraMoves();
    }

    @Override
    public void resetMoves() {
        getSelectedWeapon().resetMoves();
    }
}
