package it.polimi.ingsw.model;

import java.util.Collections;
import java.util.List;

public class PowerUpDeck implements Deck {
    private List<PowerUp> powerUps;

    public PowerUpDeck(List<PowerUp> powerUps) {
        this.powerUps = powerUps;
    }

    @Override
    public void shuffle() {
        Collections.shuffle(powerUps);
    }

    public PowerUp drawPowerUp() {
        return powerUps.isEmpty() ? null : powerUps.remove(0);
    }

    public void add(PowerUp powerUp) {
        powerUps.add(powerUp);
    }

    public void remove(PowerUp powerUp) {
        powerUps.remove(powerUp);
    }
}
