package it.polimi.ingsw.network;

import it.polimi.ingsw.view.SquareView;

public class SquareViewTransfer extends Message {

    private SquareView attachment;

    public SquareViewTransfer(SquareView sw) {
        super(Protocol.UPDATE_SQUARE, "", null, null);
        attachment = sw;
    }

    public SquareView getAttachment() {
        return attachment;
    }
}
