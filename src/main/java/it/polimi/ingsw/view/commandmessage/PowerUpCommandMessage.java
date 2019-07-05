package it.polimi.ingsw.view.commandmessage;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.PowerUpID;

/**
 * This class wraps an instance of {@link PowerUpID} during transfer from server to client.
 */
public class PowerUpCommandMessage extends CommandMessage {
    private final PowerUpID powerUpID;
    private final Color color;

    public PowerUpCommandMessage(CommandType type, PowerUpID powerUpID, Color color) {
        super(type);
        this.powerUpID = powerUpID;
        this.color = color;
    }

    /**
     * Gets the PowerUpID contained in attachment to the message.
     *
     * @return the instance of PowerUpID in attachment
     */
    public PowerUpID getPowerUpID() {
        return powerUpID;
    }

    /**
     * Gets the Color of the powerUp contained in attachment to the message.
     *
     * @return the Color of the PowerUp in attachment
     */
    public Color getColor() {
        return color;
    }
}
