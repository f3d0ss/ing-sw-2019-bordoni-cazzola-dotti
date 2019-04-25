package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class AmmoTileDeck implements Deck {
    private ArrayList<AmmoTile> ammoTiles;

    public AmmoTileDeck() {
        ammoTiles = new ArrayList<AmmoTile>();
    }

    @Override
    public void shuffle() {
        Collections.shuffle(ammoTiles);
    }

    public AmmoTile drawAmmoTile() {
        return ammoTiles.isEmpty() ? null : ammoTiles.remove(0);
    }

    public void add(AmmoTile ammoTile) {
        ammoTiles.add(ammoTile);
    }

    public void initializeDeck() {
        ammoTiles = new ArrayList<AmmoTile>();
        AmmoTile adding;
        //set tiles with one poweup
        for (Color first : Color.values())
            for (Color second : Color.values()) {
                adding = new AmmoTile(1, first != second ?
                        new HashMap<Color, Integer>() {{
                            put(first, 1);
                            put(second, 1);
                        }} :
                        new HashMap<Color, Integer>() {{
                            put(first, 2);
                        }});
                ammoTiles.add(adding);
                //tiles with poweups are replicated twice
                ammoTiles.add(adding);
                //set tiles with three ammos
                for (Color third : Color.values()) {
                    if (first == second && second != third) {
                        adding = new AmmoTile(0,
                                new HashMap<Color, Integer>() {{
                                    put(first, 2);
                                    put(third, 1);
                                }});
                        ammoTiles.add(adding);
                    } else if (first == third && second != third) {
                        adding = new AmmoTile(0,
                                new HashMap<Color, Integer>() {{
                                    put(first, 2);
                                    put(second, 1);
                                }});
                        ammoTiles.add(adding);
                    } else if (second == third && first != second) {
                        adding = new AmmoTile(0,
                                new HashMap<Color, Integer>() {{
                                    put(first, 1);
                                    put(second, 2);
                                }});
                        ammoTiles.add(adding);
                    }
                }
            }
        this.shuffle();
    }
}
