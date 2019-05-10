package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class AmmoTileDeck implements Deck {
    private ArrayList<AmmoTile> ammoTiles;

    public AmmoTileDeck() {
        ammoTiles = new ArrayList<>();
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
        ammoTiles = new ArrayList<>();
        Map<Color, Integer> cubes = new EnumMap(Color.class);
        //set tiles with one poweup
        for (Color first : Color.values())
            for (Color second : Color.values()) {
                if(first != second) {
                    cubes.put(first, 1);
                    cubes.put(second, 1);
                } else {
                    cubes.put(first, 2);
                }
                ammoTiles.add(new AmmoTile(1, cubes));
                //tiles with poweups are replicated twice
                ammoTiles.add(new AmmoTile(1, cubes));
                //now set tiles with three ammos
                for (Color third : Color.values()) {
                    if (first == second && second != third) {
                        cubes.put(first, 2);
                        cubes.put(third, 1);
                        ammoTiles.add(new AmmoTile(0, cubes));
                    } else if (first == third && second != third) {
                        cubes.put(first, 2);
                        cubes.put(second, 1);
                        ammoTiles.add(new AmmoTile(0, cubes));
                    } else if (second == third && first != second) {
                        cubes.put(first, 1);
                        cubes.put(second, 2);
                        ammoTiles.add(new AmmoTile(0, cubes));
                    }
                }
            }
        this.shuffle();
    }
}
