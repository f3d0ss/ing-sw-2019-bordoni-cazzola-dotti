package it.polimi.ingsw.model;

/**
 * This abstract class represents a PowerUp
 */
public class PowerUp {

    public static final int TRGETING_SCOPE_DAMAGE = 1;
    public static final int TAGBACK_GRENADE_MARKS = 1;

    private PowerUpID type;
    private Color color;

    public PowerUp(PowerUpID type, Color color) {
        this.color = color;
        this.type = type;
    }

    public Color getColor() {
        return this.color;
    }

    public PowerUpID getType() {
        return this.type;
    }
}