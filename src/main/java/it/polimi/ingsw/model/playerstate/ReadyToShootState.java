package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.WeaponCommand;

import java.util.ArrayList;
import java.util.List;

public class ReadyToShootState extends SelectedWeaponState implements TargetingPlayerState, TargetingSquareState, MovableState {
    public ReadyToShootState(AggregateAction selectedAggregateAction, Weapon selectedWeapon) {
        super(selectedAggregateAction, selectedWeapon);
    }

    @Override
    public List<Command> getPossibleCommands(Player player) {
        return new ArrayList<>(getSelectedWeapon().getPossibleCommands(player.getMatch().getBoard(), player,this));
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
