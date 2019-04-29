package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class AmmoTileDeckTest {

    @Test
    public void initializeDeck() {
        AmmoTileDeck deck = new AmmoTileDeck();
        AmmoTileDeck deckShuffled = new AmmoTileDeck();
        AmmoTile tile;
        deck.initializeDeck();
        tile = deck.drawAmmoTile();
        while(tile != null) {
            System.out.printf(tile.toString() + " ");
            deckShuffled.add(tile);
            tile = deck.drawAmmoTile();
        }
    }
}