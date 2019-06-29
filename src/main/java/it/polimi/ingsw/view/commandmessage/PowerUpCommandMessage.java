package it.polimi.ingsw.view.commandmessage;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.PowerUpID;

public class PowerUpCommandMessage extends CommandMessage {
    private final PowerUpID powerUpID;
    private final Color color;

    public PowerUpCommandMessage(CommandType type, PowerUpID powerUpID, Color color) {
        super(type);
        this.powerUpID = powerUpID;
        this.color = color;
    }

    public PowerUpID getPowerUpID() {
        return powerUpID;
    }

    public Color getColor() {
        return color;
    }
}
