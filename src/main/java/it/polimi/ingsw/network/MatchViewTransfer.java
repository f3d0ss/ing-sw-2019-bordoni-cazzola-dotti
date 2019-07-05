package it.polimi.ingsw.network;

import it.polimi.ingsw.view.MatchView;

/**
 * This class wraps an instance of {@link MatchView} during transfer from server to client.
 */
public class MatchViewTransfer extends Message {

    private final MatchView attachment;

    public MatchViewTransfer(MatchView mw) {
        super(Protocol.UPDATE_MATCH, "", null);
        attachment = mw;
    }

    /**
     * Gets the instance of MatchView in attachment to the message.
     *
     * @return the instance of MatchView in attachment to the message
     */
    public MatchView getAttachment() {
        return attachment;
    }
}
