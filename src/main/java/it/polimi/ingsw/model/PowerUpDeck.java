package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;

public class PowerUpDeck implements Deck {
    private ArrayList<PowerUp> powerUps;

    public PowerUpDeck(ArrayList<PowerUp> powerUps) {
        this.powerUps = powerUps;
    }

    @Override
    public void shuffle() {
        Collections.shuffle(powerUps);
    }

    public PowerUp drawPowerUp(){
        return powerUps.remove(0);
    }
}
