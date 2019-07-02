package it.polimi.ingsw.model;

/**
 * This class represents a PowerUp
 */
public class PowerUp {

    /**
     * Damage of a Scope
     */
    public static final int TARGETING_SCOPE_DAMAGE = 1;
    /**
     * Damage of a tagback grenade
     */
    public static final int TAGBACK_GRENADE_MARKS = 1;

    private final PowerUpID type;
    private final Color color;

    public PowerUp(PowerUpID type, Color color) {
        this.color = color;
        this.type = type;
    }

    /**
     * Gets type
     *
     * @return value of type
     */
    public PowerUpID getType() {
        return type;
    }

    /**
     * Gets color
     *
     * @return value of color
     */
    public Color getColor() {
        return color;
    }
}