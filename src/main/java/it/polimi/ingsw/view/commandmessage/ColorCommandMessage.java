package it.polimi.ingsw.view.commandmessage;

import it.polimi.ingsw.model.Color;

public class ColorCommandMessage extends CommandMessage {
    private Color color;

    public ColorCommandMessage(CommandType type, Color color) {
        super(type);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
