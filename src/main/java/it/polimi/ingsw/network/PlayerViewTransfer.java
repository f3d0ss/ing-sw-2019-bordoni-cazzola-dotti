package it.polimi.ingsw.network;

import it.polimi.ingsw.view.PlayerView;

/**
 * This class wraps an instance of {@link PlayerView} during transfer from server to client.
 */
public class PlayerViewTransfer extends Message {

    private final PlayerView attachment;

    public PlayerViewTransfer(PlayerView pw) {
        super(Protocol.UPDATE_PLAYER, "", null);
        attachment = pw;
    }

    /**
     * Gets the instance of PlayerView in attachment to the message.
     *
     * @return the instance of PlayerView in attachment to the message
     */
    public PlayerView getAttachment() {
        return attachment;
    }
}
