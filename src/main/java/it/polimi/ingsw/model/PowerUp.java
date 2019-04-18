package it.polimi.ingsw.model;

import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.playerstate.SelectedPowerUpState;
import it.polimi.ingsw.model.playerstate.TargetingPlayerState;

import java.util.List;

/**
 * This abstract class represents a PowerUp
 */
public abstract class PowerUp {

    private String name;
    private Color color;

    public PowerUp(String name, Color color) {
        this.color = color;
        this.name = name;
    }

    public Color getColor(){
        return this.color;
    };

    public abstract List<Command> getPossibleCommands(GameBoard gameboard, Player player, TargetingPlayerState currentState);

    public abstract boolean isScope();

    public abstract boolean isTagBackGrenade();

    public  void addTargetPlayer(Player targetPlayer){
        throw new IllegalStateException();
    }

    public  void removeTargetPlayer(Player targetPlayer){
        throw new IllegalStateException();
    }

    public  void addTargetSquare(Square targetSquare){
        throw new IllegalStateException();
    }

    public  void removeTargetSquare(Square targetSquare){
        throw new IllegalStateException();
    }
}
