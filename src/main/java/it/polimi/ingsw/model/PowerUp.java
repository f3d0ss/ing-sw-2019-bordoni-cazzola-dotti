package it.polimi.ingsw.model;

/**
 * This class represents a PowerUp
 */
public class PowerUp {

    public static final int TARGETING_SCOPE_DAMAGE = 1;
    public static final int TAGBACK_GRENADE_MARKS = 1;

    private final PowerUpID type;
    private final Color color;

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