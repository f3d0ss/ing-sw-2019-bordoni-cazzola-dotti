package it.polimi.ingsw.model.View;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerId;
import org.junit.Test;

import static org.junit.Assert.*;

public class CliTest {

    @Test
    public void displayBoard() {
        Cli cli = new Cli();
        Match match = new Match();
        for(PlayerId id : PlayerId.values()) {
            Player player = new Player(match, id, null, match.getBoard().getSquare(0, 0));
            match.addPlayer(player);
            match.getBoard().getSquare(0, 0).addPlayer(player);
        }
        cli.setMatch(match);
        cli.displayBoard();
    }
}