package it.polimi.ingsw.model;

import it.polimi.ingsw.model.command.Command;

import java.util.List;

/**
 * This abstract class represents a PowerUp
 */
public abstract class PowerUp {

    private Color color;

    public PowerUp(Color color) {
        this.color = color;
    }

    public abstract List<Command> getPossibleCommands(GameBoard gameboard, Player player);
}
