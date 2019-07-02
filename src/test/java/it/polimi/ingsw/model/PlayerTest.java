package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class PlayerTest {

    private final static int MAX_AMMO = 3;
    private final static int MAX_POWERUP = 3;
    private final static int MAX_MARK = 3;
    private final static int MAX_DAMAGE = 12;
    final int ROWS = 3;
    final int COLUMNS = 4;

    //verify the correct insertion of ammos and powerups

    @Test
    void testAddAmmoTile() {
        Map<Color, Integer> ammo = new HashMap<>();
        int colorcubes;
        int addingammo = 5;
//        Map<Color,Integer> resources;
        Player player = new Player(new Match(), null, null);
        for (Color c : Color.values()) {
            ammo.put(c, 1);
            AmmoTile tile = new AmmoTile(0, ammo);
            for (int i = 1; i < addingammo; i++) {
                player.addAmmoTile(tile);
                colorcubes = player.getAmmo().get(c);
                if ((i + Player.INITIAL_AMMO_NUMBER) <= MAX_AMMO)
                    assertEquals(i + Player.INITIAL_AMMO_NUMBER, colorcubes);
                else
                    assertEquals(MAX_AMMO, colorcubes);
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
    void testAddDamage() {
        Player player = new Player(new Match(), null, null);
        assertFalse(player.isDead());
        for (int i = 1; i < MAX_DAMAGE - 1; i++) {
            player.addDamage(1, PlayerId.GREEN);
            assertFalse(player.isDead());
        }
        player.addDamage(1, PlayerId.GREEN);
        assertTrue(player.isDead());
    }

    //verify the correct insertion of marks and their changing in damages

    @Test
    void testAddMarks() {

        int marks;

        for (marks = 1; marks <= MAX_MARK; marks++) {
            Player player = new Player(new Match(), null, null);
            player.addMarks(marks, PlayerId.VIOLET);
            assertFalse(player.isDead());
            for (int i = 1; i < MAX_DAMAGE - 1 - marks; i++) {
                player.addDamage(1, PlayerId.VIOLET);
                assertFalse(player.isDead());
            }
            player.addDamage(1, PlayerId.GREEN);
            assertTrue(player.isDead());
        }

        Player player = new Player(new Match(), null, null);
        player.addMarks(marks, PlayerId.VIOLET);
        assertFalse(player.isDead());
        for (int i = 1; i < MAX_DAMAGE - 1 - MAX_MARK; i++) {
            player.addDamage(1, PlayerId.VIOLET);
            assertFalse(player.isDead());
        }
        player.addDamage(1, PlayerId.GREEN);
        assertTrue(player.isDead());
    }

    //verify the correct movements from all position in game board

    @Test
    void testMove() {
//        Match match = new Match();
//        Square square;
//        CardinalDirection dir;
//        Player player;
//        for(int i = 0; i < ROWS; i++) {
//            for (int j = 0; j < COLUMNS; j++) {
//                square = match.getBoard().getSquare(i, j);
//                if (square != null) {
//                    player = new Player(match, null, null, square);
//                    assertEquals(player.getPosition(), square);
//                    for (CardinalDirection c : CardinalDirection.values()) {
//                        dir = c;
//                        if (square.getConnection(dir) != Connection.MAP_BORDER && square.getConnection(dir) != Connection.WALL) {
//                            player.move(dir);
//                            switch (dir) {
//                                case NORTH:
//                                    assertEquals(player.getPosition(), match.getBoard().getSquare(i - 1, j));
//                                    break;
//                                case EAST:
//                                    assertEquals(player.getPosition(), match.getBoard().getSquare(i, j + 1));
//                                    break;
//                                case SOUTH:
//                                    assertEquals(player.getPosition(), match.getBoard().getSquare(i + 1, j));
//                                    break;
//                                case WEST:
//                                    assertEquals(player.getPosition(), match.getBoard().getSquare(i, j - 1));
//                                    break;
//                            }
//                            player.move(dir.getOpposite());
//                            assertEquals(player.getPosition(), square);
//                        }
//                    }
//                }
//            }
//        }
    }

    @Test
    void testGetSpawnCommands() {
        Match match = new Match();
        Player player = new Player(match, null, null);
        int initalSize = player.getPowerUps().size();
        int nOfCommands = player.getSpawnCommands().size();
        int size = player.getPowerUps().size();
        //To spawn a player must draw 2 power ups
        assertEquals(initalSize + 2, size);
        assertEquals(size, nOfCommands);
    }

    @Test
    void testGetReSpawnCommands() {
        Match match = new Match();
        Player player = new Player(match, null, null);
        int initalSize = player.getPowerUps().size();
        int nOfCommands = player.getRespawnCommands().size();
        int size = player.getPowerUps().size();
        //To Respawn a player must draw 1 power ups
        assertEquals(initalSize + 1, size);
        assertEquals(size, nOfCommands);
    }

    @Test
    void testAggregateActions() {
        Match match = new Match();
        Player player = new Player(match, null, null);
        if (!match.isLastTurn())
            assertEquals(3, player.getPossibleAggregateAction().size());
        int availableAggregateActionCounter = player.getAvailableAggregateActionCounter();
        player.deselectAggregateAction();
        assertEquals(availableAggregateActionCounter + 1, player.getAvailableAggregateActionCounter());
        player.selectAggregateAction();
        assertEquals(availableAggregateActionCounter, player.getAvailableAggregateActionCounter());
    }

    @Test
    void testDeaths() {
        Match match = new Match();
        Player player = new Player(match, null, null);
        int times = 3;
        for (int i = 0; i < times; i++)
            player.addDeaths();
        assertEquals(times, player.getDeaths());
    }

    @Test
    void testPoints() {
        int points = 4;
        Match match = new Match();
        Player player = new Player(match, null, null);
        player.addPoints(points);
        assertEquals(points, player.getPoints());
    }

    @Test
    void testFlipBoard() {
        Match match = new Match();
        Player player = new Player(match, null, null);
        assertFalse(player.isFlippedBoard());
        player.flipBoard();
        assertTrue(player.isFlippedBoard());
    }

    @Test
    void testAmmoCubes() {
        Match match = new Match();
        Player player = new Player(match, null, null);
        // has 1 ammo for each color (MAX 3 per color)
        int yellow = 2;
        int blue = 1;
        int red = 2;
        player.refund(Color.YELLOW, yellow);
        player.refund(Color.BLUE, blue);
        player.refund(Color.RED, red);
        Map<Color, Integer> ammo = player.getAmmo();
        assertEquals(blue + 1, ammo.get(Color.BLUE));
        assertEquals(red + 1, ammo.get(Color.RED));
        assertEquals(yellow + 1, ammo.get(Color.YELLOW));

        player.pay(Color.YELLOW, yellow);
        assertEquals(1, ammo.get(Color.YELLOW));
    }

    @Test
    void testPowerUp() {
        Match match = new Match();
        Player player = new Player(match, null, null);
        PowerUp powerUp = match.drawPowerUpCard();
        player.refund(powerUp);
        assertEquals(1, player.getPowerUps().size());
        if (powerUp.getType().equals(PowerUpID.NEWTON))
            assertEquals(1, player.getNewtons().size());
        if (powerUp.getType().equals(PowerUpID.TARGETING_SCOPE))
            assertEquals(1, player.getTargetingScopes().size());
        if (powerUp.getType().equals(PowerUpID.TAGBACK_GRENADE))
            assertEquals(1, player.getTagbackGrenades().size());
        if (powerUp.getType().equals(PowerUpID.TELEPORTER))
            assertEquals(1, player.getTeleports().size());

        player.pay(powerUp);
        assertEquals(0, player.getPowerUps().size());
    }

    @Test
    void testWeapons() {
        Match match = new Match();
        Player player = new Player(match, null, null);
        int nOfWeapons = 4;
        for (int i = 0; i < nOfWeapons; i++) {
            if (i >= Player.MAX_WEAPONS)
                assertThrows(IllegalStateException.class, () -> player.addWeapon(new Weapon()));
            else
                player.addWeapon(new Weapon());
        }
        if (nOfWeapons > Player.MAX_WEAPONS)
            nOfWeapons = Player.MAX_WEAPONS;
        assertEquals(nOfWeapons, player.getWeapons().size());
    }
}