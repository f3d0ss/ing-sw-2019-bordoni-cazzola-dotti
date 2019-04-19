package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerId;
import org.junit.Test;

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