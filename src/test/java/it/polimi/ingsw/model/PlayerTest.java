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
    final int ROWS = 3;
    final int COLUMNS = 4;

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

        //bisogna istanziare i powerup prima
        /*
        AmmoTile tile = new AmmoTile(1, ammo);
        int addingpowerup = 5;
        for (int i=1; i<addingpowerup; i++){
            player.addAmmoTile(tile);
            if (i < MAX_POWERUP)
               assertEquals(player.getPowerUps().size(), i);
            else
                assertEquals(player.getPowerUps().size(), MAX_POWERUP);
        }
        */
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
        CardinalDirection dir;
        for(int i=0; i<ROWS; i++)
            for(int j=0; j<COLUMNS; j++)
                if(board.getSquare(i,j)!=null) {
                    Player player = new Player(match, null, null, board.getSquare(i, j));
                    for (CardinalDirection c : CardinalDirection.values()) {
                        dir = c;
                        if (board.getSquare(i, j).getConnection(dir) != Connection.MAP_BORDER && board.getSquare(i, j).getConnection(dir) != Connection.WALL) {
                            player.move(dir);
                        /*    switch (dir) {
                                //case NORTH: assertEquals(player.getPosition(), board.getSquare(i - 1, j));
                                case EAST: assertEquals(player.getPosition(), board.getSquare(i, j + 1));
                                case SOUTH: assertEquals(player.getPosition(), board.getSquare(i + 1, j));
                                //case WEST: assertEquals(player.getPosition(), board.getSquare(i, j-1));
                            }*/
                            player.move(dir.getOpposite());
                            assertEquals(player.getPosition(), board.getSquare(i, j));
                        }
                    }
                }
    }
}