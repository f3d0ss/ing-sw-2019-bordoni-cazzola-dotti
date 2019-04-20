package it.polimi.ingsw.model;

import java.util.Collections;
import java.util.List;

public class AmmoTileDeck implements Deck {
    private List<AmmoTile> ammoTiles;

    public AmmoTileDeck(List<AmmoTile> ammoTiles) {
        this.ammoTiles = ammoTiles;
    }

    @Override
    public void shuffle() {
        Collections.shuffle(ammoTiles);
    }

    public AmmoTile drawAmmoTile() {
        return ammoTiles.isEmpty() ? null : ammoTiles.remove(0);
    }

    public void add(AmmoTile ammoTile){
        ammoTiles.add(ammoTile);
    }
}
