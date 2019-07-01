package it.polimi.ingsw.model;

import java.util.Collections;
import java.util.List;

/**
 * Contains a Deck of multiple {@link PowerUp}
 */
public class PowerUpDeck implements Deck {
    private List<PowerUp> powerUps;

    PowerUpDeck(List<PowerUp> powerUps) {
        this.powerUps = powerUps;
    }

    @Override
    public void shuffle() {
        Collections.shuffle(powerUps);
    }

    /**
     * Gets and remove the first powerup from the deck
     *
     * @return First powerup of the deck
     */
    PowerUp drawPowerUp() {
        return powerUps.isEmpty() ? null : powerUps.remove(0);
    }

    /**
     * Adds a powerup to the deck
     *
     * @param powerUp powerup to add
     */
    public void add(PowerUp powerUp) {
        powerUps.add(powerUp);
    }

    /**
     * Removes a powerup from the deck
     *
     * @param powerUp power up to remove
     */
    public void remove(PowerUp powerUp) {
        powerUps.remove(powerUp);
    }
}
