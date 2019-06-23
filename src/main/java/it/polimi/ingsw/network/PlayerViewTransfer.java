package it.polimi.ingsw.network;

import it.polimi.ingsw.view.PlayerView;

public class PlayerViewTransfer extends Message {

    private PlayerView attachment;

    public PlayerViewTransfer(PlayerView pw) {
        super(Protocol.UPDATE_PLAYER, "", null);
        attachment = pw;
    }

    public PlayerView getAttachment() {
        return attachment;
    }
}
