package it.polimi.ingsw.network;

import it.polimi.ingsw.view.SquareView;

/**
 * This class wraps an instance of {@link SquareView} during transfer from server to client.
 */
public class SquareViewTransfer extends Message {

    private final SquareView attachment;

    public SquareViewTransfer(SquareView sw) {
        super(Protocol.UPDATE_SQUARE, "", null);
        attachment = sw;
    }

    /**
     * Gets the instance of SquareView in attachment to the message.
     *
     * @return the instance of SquareView in attachment to the message
     */
    public SquareView getAttachment() {
        return attachment;
    }
}
