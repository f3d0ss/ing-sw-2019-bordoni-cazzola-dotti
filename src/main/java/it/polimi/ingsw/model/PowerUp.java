package it.polimi.ingsw.model;

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

    public Color getColor() {
        return this.color;
    }
}