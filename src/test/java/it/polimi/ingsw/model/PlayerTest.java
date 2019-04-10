package it.polimi.ingsw.model;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class PlayerTest {

    private final static int MAX_AMMO = 3;
    private final static int MAX_POWERUP = 3;
    private final static int MAX_MARK = 3;
    private final static int MAX_DAMAGE = 12;
    //verify the correct insertion of ammos and powerups

    @Test
    public void testAddAmmoTile() {
        Map<Color, Integer> ammo = new HashMap<>();
        int colorcubes;
        int addingammo = 5;
//        Map<Color,Integer> resources;
        Player player = new Player(null, null, null, null);
        for (Color c : Color.values()) {
            ammo.put(c, 1);
            AmmoTile tile = new AmmoTile(0, ammo);
            for (int i = 1; i < addingammo; i++) {
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
//        int addingpowerup = 5;
//        for (int i=1; i<addingpoewrup; i++){
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
        Player player = new Player(null, null, null,null);
        assertEquals(player.isDead(), false);
        for (int i=1; i<MAX_DAMAGE-1; i++) {
            player.addDamage(1, PlayerId.RED);
            assertEquals(player.isDead(), false);
        }
        player.addDamage(1, PlayerId.RED);
        assertEquals(player.isDead(), true);
    }

    //verify the correct insertion of marks and their changing in damages

    @Test
    public void testAddMarks() {

        int marks;

        for(marks=1; marks<=MAX_MARK; marks++) {
            Player player = new Player(null, null, null,null);
            player.addMarks(marks, PlayerId.VIOLET);
            assertEquals(player.isDead(), false);
            for (int i = 1; i < MAX_DAMAGE - 1 - marks; i++) {
                player.addDamage(1, PlayerId.VIOLET);
                assertEquals(player.isDead(), false);
            }
            player.addDamage(1, PlayerId.RED);
            assertEquals(player.isDead(), true);
        }

        Player player = new Player(null, null, null, null);
        player.addMarks(marks, PlayerId.VIOLET);
        assertEquals(player.isDead(), false);
        for (int i = 1; i < MAX_DAMAGE -1 - MAX_MARK; i++) {
            player.addDamage(1, PlayerId.VIOLET);
            assertEquals(player.isDead(), false);
        }
        player.addDamage(1, PlayerId.RED);
        assertEquals(player.isDead(), true);
    }

    //verify the correct movements

    @Test
    public void testMove() {
        Match match = new Match();
        GameBoard board = match.getBoard();
        Player player = new Player(match, null, null, board.getSquare(0, 0));
        player.move(CardinalDirection.SOUTH);
        assertEquals(player.getPosition(), board.getSquare(1, 0));
        player.move(CardinalDirection.EAST);
        assertEquals(player.getPosition(), board.getSquare(1, 1));
        player.move(CardinalDirection.SOUTH);
        assertEquals(player.getPosition(), board.getSquare(2, 1));
        player.move(CardinalDirection.EAST);
        assertEquals(player.getPosition(), board.getSquare(2, 2));
        player.move(CardinalDirection.EAST);
        assertEquals(player.getPosition(), board.getSquare(2, 3));
        player.move(CardinalDirection.NORTH);
        assertEquals(player.getPosition(), board.getSquare(1, 3));
        player.move(CardinalDirection.WEST);
        assertEquals(player.getPosition(), board.getSquare(1, 2));
        player.move(CardinalDirection.NORTH);
        assertEquals(player.getPosition(), board.getSquare(0, 2));
        player.move(CardinalDirection.WEST);
        assertEquals(player.getPosition(), board.getSquare(0, 1));
        player.move(CardinalDirection.WEST);
        assertEquals(player.getPosition(), board.getSquare(0, 0));
    }
}