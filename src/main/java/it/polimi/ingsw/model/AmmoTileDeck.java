package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;

public class AmmoTileDeck implements Deck {
    private ArrayList<AmmoTile> ammoTiles;

    public AmmoTileDeck(ArrayList<AmmoTile> ammoTiles) {
        this.ammoTiles = ammoTiles;
    }

    @Override
    public void shuffle() {
        Collections.shuffle(ammoTiles);
    }

    public AmmoTile drawAmmoTile(){
        return ammoTiles.remove(0);
    }
}
