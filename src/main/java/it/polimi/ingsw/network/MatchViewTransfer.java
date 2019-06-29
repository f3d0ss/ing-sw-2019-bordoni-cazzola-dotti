package it.polimi.ingsw.network;

import it.polimi.ingsw.view.MatchView;

public class MatchViewTransfer extends Message {

    private final MatchView attachment;

    public MatchViewTransfer(MatchView mw) {
        super(Protocol.UPDATE_MATCH, "", null);
        attachment = mw;
    }

    public MatchView getAttachment() {
        return attachment;
    }
}
