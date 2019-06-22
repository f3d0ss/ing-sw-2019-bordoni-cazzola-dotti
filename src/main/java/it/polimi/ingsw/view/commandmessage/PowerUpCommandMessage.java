package it.polimi.ingsw.view.commandmessage;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.PowerUpID;

public class PowerUpCommandMessage extends CommandMessage {
    private PowerUpID powerUpID;
    private Color color;
    private String jsonType = getClass().getSimpleName();

    public PowerUpCommandMessage(CommandType type, PowerUpID powerUpID, Color color) {
        super(type);
        this.powerUpID = powerUpID;
        this.color = color;
    }
}
