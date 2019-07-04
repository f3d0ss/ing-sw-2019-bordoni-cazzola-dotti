package it.polimi.ingsw.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testColor() {
        for (Color color : Color.values()) {
            assertEquals(color.colorName().substring(0, 1), color.colorInitial() + "");
            assertEquals(color.colorID().substring(0, 1).toUpperCase(), color.colorInitial() + "");
        }
    }

    @Test
    void setPlayerOnDutyTest() {
        Match match = new Match();
        match.setPlayerOnDuty(PlayerId.VIOLET);
        assertTrue(match.getKillshotTrack().isEmpty());
    }

    /**
     * Tests if {@link Match#sendModelAfterReconnection(PlayerId)} throws NullPointer
     */
    @Test
    void noUpdateTest() {
        Match match = new Match();
        match.updateAllModel();
        assertThrows(NullPointerException.class, () -> match.sendModelAfterReconnection(PlayerId.BLUE));
    }

}