package it.polimi.ingsw;

import org.junit.Test;

import static org.junit.Assert.*;

public class MatchTest {

//verify the correct insertion of players

    @Test
    public void testAddPlayer() {
        Match match = new Match();
        Player tmp = new Player();
        for(PlayerId id : PlayerId.values()) {
            tmp.setId(id);
            match.addPlayer(tmp);
            assertEquals(match.getPlayer(id), tmp);
        }
    }

}