package it.polimi.ingsw.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for {@link Match}'s methods
 */
class MatchTest {

    private final static int SKULLS = 8;

    /**
     * Verifies the correct insertion of players
     */
    @Test
    void testAddPlayer() {
        Match match = new Match();
        Player tmp;
        for (PlayerId id : PlayerId.values()) {
            tmp = new Player(match, id, "Paolino");
            match.addPlayer(tmp);
            assertEquals(match.getPlayer(id), tmp);
        }
    }

    /**
     * Verifies the correct deaths count
     */
    @Test
    void testDecreaseDeaths() {
        Match match = new Match();
        int attempts = 10;
        for (int k = 0; k < attempts; k++)
            assertEquals(match.decreaseDeathsCounter(), k < SKULLS);
    }

    /**
     * Verifies the correct count of killshots
     */
    @Test
    void testAddKillshot() {
        Match match = new Match();
        int total = 1;
        for (PlayerId id : PlayerId.values()) {
            for (int i = 0; i < total; i++)
                match.addKillshot(id);
            assertEquals(match.getPlayerKillshots(id), total);
            total++;
        }
    }

    /**
     * verifies the correct drawing of PowerUp
     */
    @Test
    void testDrawPowerUpCard() {
        Match match = new Match();
        assertNotNull(match.drawPowerUpCard());
    }


}