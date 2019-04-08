package it.polimi.ingsw.model;

/**
 * This abstract class represents a PowerUp
 */
public abstract class PowerUp {

    private Color color;

    public PowerUp(Color color) {
        this.color = color;
    }

    public abstract List<Command> getPossibleCommands(Gameboard gameboard, Player player);
}
