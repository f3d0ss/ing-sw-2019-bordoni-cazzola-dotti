package it.polimi.ingsw.view.commandmessage;

import it.polimi.ingsw.model.Color;

/**
 * This class wraps an instance of {@link ColorCommandMessage} during transfer from server to client.
 */
public class ColorCommandMessage extends CommandMessage {
    private final Color color;

    public ColorCommandMessage(CommandType type, Color color) {
        super(type);
        this.color = color;
    }

    /**
     * Gets the color contained in attachment to the message.
     *
     * @return the color in attachment
     */
    public Color getColor() {
        return color;
    }
}
