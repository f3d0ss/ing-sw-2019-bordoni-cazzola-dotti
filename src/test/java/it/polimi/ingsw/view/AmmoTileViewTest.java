package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Color;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AmmoTileViewTest {

    @Test
    void toString1() {
        Map<Color, Integer> ammo = new EnumMap<>(Color.class);
        ammo.put(Color.RED, 2);
        AmmoTileView ammoTileView = new AmmoTileView(1, ammo);
        assertEquals("PRR", ammoTileView.toString());

        ammo = new EnumMap<>(Color.class);
        ammo.put(Color.RED, 1);
        ammo.put(Color.BLUE, 1);
        ammoTileView = new AmmoTileView(1, ammo);
        assertEquals("PBR", ammoTileView.toString());

        ammo = new EnumMap<>(Color.class);
        ammo.put(Color.BLUE, 1);
        ammo.put(Color.YELLOW, 1);
        ammo.put(Color.RED, 1);
        ammoTileView = new AmmoTileView(0, ammo);
        assertEquals("BRY", ammoTileView.toString());

        ammo = new EnumMap<>(Color.class);
        ammo.put(Color.YELLOW, 2);
        ammo.put(Color.BLUE, 1);
        ammoTileView = new AmmoTileView(0, ammo);
        assertEquals("BYY", ammoTileView.toString());
    }
}