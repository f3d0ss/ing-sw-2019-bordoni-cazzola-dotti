package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class MatchTest {

    private final static int SKULLS = 8;

//verify the correct insertion of players

    @Test
    public void testAddPlayer() {
        Match match = new Match();
        Player tmp = new Player(match, PlayerId.BLUE, "Paolino", null);
        for (PlayerId id : PlayerId.values()) {
            tmp.setId(id);
            match.addPlayer(tmp);
            assertEquals(match.getPlayer(id), tmp);
        }
    }

//verify the correct deaths count

    @Test
    public void testDecreaseDeaths() {
        Match match = new Match();
        int attempts = 10;
        for (int k = 0; k < attempts; k++)
            assertEquals(match.decreaseDeathsCounter(), k < SKULLS);
    }

//verify the correct count of killshots

    @Test
    public void testAddKillshot() {
        Match match = new Match();
        int total = 1;
        for (PlayerId id : PlayerId.values()) {
            for (int i = 0; i < total; i++)
                match.addKillshot(id);
            assertEquals(match.getPlayerKillshots(id), total);
            total++;
        }
    }

    //verify the correct drawing of PowerUp

    /*to be verified once powerups are added

    @Test
    public void testDrawPowerUpCard() {
        Match match = new Match();
        assertEquals(match.drawPowerUpCard(),null);
    }*/
}