package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Square;
import it.polimi.ingsw.model.playerstate.ManageTurnState;
import it.polimi.ingsw.model.playerstate.SelectedPowerUpState;
import it.polimi.ingsw.model.playerstate.TargetingPlayerState;

import java.util.List;

public class UseNewtonCommand implements Command {
    private Player player;
    private TargetingPlayerState currentState;
    private Player targetPlayer;
    private Square targetSquare;
    private Square oldSquare;

    public UseNewtonCommand(Player player, TargetingPlayerState currentState, Square targetSquare, Player targetPlayer) {
        this.player = player;
        this.currentState = currentState;
        this.targetPlayer = targetPlayer;
        this.targetSquare = targetSquare;
    }

    @Override
    public void execute() {
        oldSquare = targetPlayer.getPosition();
        oldSquare.removePlayer(targetPlayer);
        targetPlayer.move(targetSquare);
        targetPlayer.getPosition().addPlayer(targetPlayer);
        player.changeState(new ManageTurnState());

    }

    @Override
    public void undo() {
        targetPlayer.getPosition().removePlayer(targetPlayer);
        targetPlayer.move(oldSquare);
        targetPlayer.getPosition().addPlayer(targetPlayer);
        player.changeState(currentState);
    }
}
