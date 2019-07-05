package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.PowerUpID;

/**
 * This class represent an {@link it.polimi.ingsw.model.PowerUp} on view side.
 */
public class PowerUpView {
    private final PowerUpID type;
    private final Color color;

    public PowerUpView(PowerUpID type, Color color) {
        this.type = type;
        this.color = color;
    }

    /**
     * Gets the name of the powerup.
     *
     * @return a string containing the name of the powerup
     */
    public String getName() {
        return type.powerUpName();
    }

    /**
     * Gets the type of the powerup.
     *
     * @return the type of the powerup
     */
    public PowerUpID getType() {
        return type;
    }

    /**
     * Gets the color of the powerup.
     *
     * @return the color of the powerup
     */
    public Color getColor() {
        return color;
    }
}
