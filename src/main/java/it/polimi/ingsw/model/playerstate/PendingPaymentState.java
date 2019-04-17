package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.PowerUp;

import java.util.List;
import java.util.Map;

public interface PendingPaymentState {
    void addPendingAmmo(Color color);
    void addPendingCard(PowerUp powerUp);
    void removePendingAmmo(Color color);
    void removePendingCard(PowerUp powerUp);

    Map<Color,Integer> getPendingAmmoPayment();

    List<PowerUp> getPendingCardPayment();
}
