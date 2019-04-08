package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.PowerUp;

public interface PendingPaymentState {
    public void addPendingAmmo(Color color);
    public void addPendingCard(PowerUp powerUp);

}
