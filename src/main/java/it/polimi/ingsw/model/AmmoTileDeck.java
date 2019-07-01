package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Deck of multiple {@link AmmoTile}
 */
public class AmmoTileDeck implements Deck {
    private ArrayList<AmmoTile> ammoTiles = new ArrayList<>();

    @Override
    public void shuffle() {
        Collections.shuffle(ammoTiles);
    }

    /**
     * Gets and remove the first ammotile from the deck
     *
     * @return First ammotile of the deck
     */
    public AmmoTile drawAmmoTile() {
        return ammoTiles.isEmpty() ? null : ammoTiles.remove(0);
    }

    /**
     * Adds an ammotile to the deck
     *
     * @param ammoTile ammotile to add
     */
    public void add(AmmoTile ammoTile) {
        ammoTiles.add(ammoTile);
    }

}
