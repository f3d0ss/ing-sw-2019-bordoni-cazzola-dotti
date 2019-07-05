package it.polimi.ingsw.view.commandmessage;

import it.polimi.ingsw.model.PlayerId;

/**
 * This class wraps an instance of {@link PlayerId} during transfer from server to client.
 */
public class PlayerCommandMessage extends CommandMessage {
    private final PlayerId playerId;

    public PlayerCommandMessage(CommandType type, PlayerId playerId) {
        super(type);
        this.playerId = playerId;
    }

    /**
     * Gets the PlayerId contained in attachment to the message.
     *
     * @return the instance of PlayerId in attachment
     */
    public PlayerId getPlayerId() {
        return playerId;
    }
}
