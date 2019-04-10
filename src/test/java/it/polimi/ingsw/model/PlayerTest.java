package it.polimi.ingsw.model;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class PlayerTest {

    private final static int MAX_AMMO = 3;
    private final static int MAX_POWERUP = 3;
    private final static int MAX_DAMAGE = 12;
    //verify the correct insertion of ammos and powerups

    @Test
    public void testAddAmmoTile() {
        Map<Color, Integer> ammo = new HashMap<>();
        int colorcubes;
//        Map<Color,Integer> resources;
        Player player = new Player(null, null, null);
        for (Color c : Color.values()) {
            ammo.put(c, 1);
            AmmoTile tile = new AmmoTile(0, ammo);
            for (int i = 1; i < 5; i++) {
                player.addAmmoTile(tile);
                colorcubes = player.getAmmo().get(c);
                if (i < MAX_AMMO)
                    assertEquals(colorcubes, i);
                else
                    assertEquals(colorcubes, MAX_AMMO);
            }
        }

//        //bisogna istanziare i powerup prima
//        AmmoTile tile = new AmmoTile(1, ammo);
//        for (int i=1; i<5; i++){
//            player.addAmmoTile(tile);
//            if (i < MAX_POWERUP)
//                assertEquals(player.getPowerUps().size(), i);
//            else
//                assertEquals(player.getPowerUps().size(), MAX_POWERUP);
//        }
    }

    //verify the correct insertion of damages

    @Test
    public void testAddDamage() {
        Player player = new Player(null, null, null);
        assertEquals(player.isDead(), false);
        for (int i=1; i<MAX_DAMAGE-1; i++) {
            player.addDamage(1, PlayerId.RED);
            assertEquals(player.isDead(), false);
        }
        player.addDamage(1, PlayerId.RED);
        assertEquals(player.isDead(), true);
    }
}