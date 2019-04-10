package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class MatchTest {

//verify the correct insertion of players

    @Test
    public void testAddPlayer() {
        Match match = new Match();
        Player tmp = new Player(match, PlayerId.BLUE, "Paolino");
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
        for (int k = 0; k < 8; k++) {
            assertEquals(match.decreaseDeathsCounter(), true);
        }
        assertEquals(match.decreaseDeathsCounter(), false);
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
}