package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains tests for {@link Player}'s methods
 */
class PlayerTest {

    private final static int MAX_AMMO = 3;
    private final static int MAX_MARK = 3;
    private final static int MAX_DAMAGE = 12;
    private final static int SKULLS = 8;

    /**
     * verifies the correct insertion of ammos and powerups
     */
    @Test
    void testAddAmmoTile() {
        Map<Color, Integer> ammo = new HashMap<>();
        int colorcubes;
        int addingammo = 5;
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
    }

    /**
     * verifies the correct insertion of damages
     */
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

    /**
     * verifies the correct insertion of marks and their changing in damages
     */
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

    /**
     * Tests spawncommands logic
     */
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

    /**
     * Tests Respawn commands logic
     */
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

    /**
     * Tests available aggregate actions
     */
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

    /**
     * Tests add deaths
     */
    @Test
    void testDeaths() {
        Match match = new Match();
        Player player = new Player(match, null, null);
        int times = 3;
        for (int i = 0; i < times; i++)
            player.addDeaths();
        assertEquals(times, player.getDeaths());
    }

    /**
     * Tests points addition
     */
    @Test
    void testPoints() {
        int points = 4;
        Match match = new Match();
        Player player = new Player(match, null, null);
        player.addPoints(points);
        assertEquals(points, player.getPoints());
    }

    /**
     * Tests board flip
     */
    @Test
    void testFlipBoard() {
        Match match = new Match();
        Player player = new Player(match, null, null);
        assertFalse(player.isFlippedBoard());
        player.flipBoard();
        assertTrue(player.isFlippedBoard());
    }

    /**
     * Tests if player ammo cubes are added and removed correctly
     */
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

    /**
     * Tests if player power ups are added and removed correctly
     */
    @Test
    void testPowerUp() {
        Match match = new Match();
        Player player = new Player(match, null, null);
        PowerUp powerUp = match.drawPowerUpCard();
        player.refund(powerUp);
        assertEquals(1, player.getPowerUps().size());
        if (powerUp.getType().equals(PowerUpID.NEWTON))
            assertEquals(1, player.getNewtons().size());
        else assertEquals(0, player.getNewtons().size());
        if (powerUp.getType().equals(PowerUpID.TARGETING_SCOPE))
            assertEquals(1, player.getTargetingScopes().size());
        else assertEquals(0, player.getTargetingScopes().size());
        if (powerUp.getType().equals(PowerUpID.TAGBACK_GRENADE))
            assertEquals(1, player.getTagbackGrenades().size());
        else assertEquals(0, player.getTagbackGrenades().size());
        if (powerUp.getType().equals(PowerUpID.TELEPORTER))
            assertEquals(1, player.getTeleports().size());
        else assertEquals(0, player.getTeleports().size());

        player.pay(powerUp);
        assertEquals(0, player.getPowerUps().size());
    }

    /**
     * Tests if player weapons are added and removed correctly
     */
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

    /**
     * Tests init of a player at the start of his turn
     */
    @Test
    void testInit() {
        Match match = new Match();
        Player player = new Player(match, null, null);
        player.initialize();
        short standardCounter = 2;
        short finalfrenzyAfter1stPlayer = 1;
        assertEquals(standardCounter, player.getAvailableAggregateActionCounter());
        //8 deaths triggers final frenzy mode
        for (int i = 0; i < SKULLS; i++)
            player.addDeaths();
        match.firstPlayerPlayedLastTurn();
        player.initialize();
        //now it should return
        assertEquals(finalfrenzyAfter1stPlayer, player.getAvailableAggregateActionCounter());
    }

    /**
     * Tests if damage in the same action does not remove marks
     */
    @Test
    void testSameActionDamage() {
        Match match = new Match();
        Player p1 = new Player(match, PlayerId.VIOLET, null);
        Player p2 = new Player(match, PlayerId.BLUE, null);
        match.addPlayer(p1);
        match.addPlayer(p2);
        int marks = 2;
        p1.addMarks(marks, p2.getId());
        int damage = 3;
        p1.addDamageSameAction(damage, p2.getId());
        assertEquals(marks, p1.getMarks().get(p2.getId()));
        assertEquals(damage, p1.getHealth().size());
    }

    /**
     * Tests if the last shooter is added correctly
     */
    @Test
    void testLastShooter() {
        Match match = new Match();
        Player p1 = new Player(match, PlayerId.VIOLET, null);
        Player p2 = new Player(match, PlayerId.BLUE, null);
        match.addPlayer(p1);
        match.addPlayer(p2);
        int damage = 3;
        p1.addDamageSameAction(damage, p2.getId());
        assertEquals(p2, p1.getLastShooter());
    }

    /**
     * Test getters of player's name
     */
    @Test
    void testNickName() {
        Player player = new Player(null, PlayerId.BLUE, PlayerId.BLUE.playerIdName());
        assertEquals(PlayerId.BLUE.playerIdName(), player.toString());
        assertEquals(PlayerId.BLUE.playerIdName(), player.getNickname());
    }

    @Test
    void testPlayerID() {
        for (PlayerId playerId : PlayerId.values()) {
            assertNotNull(playerId.playerIdName());
            assertNotNull(playerId.playerId());
        }
    }
}