package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.cli.Cli;
import org.junit.Test;

import java.util.HashMap;

public class CliTest {

    @Test
    public void displayBoard() {
        Match match = new Match();
        PlayerId client = PlayerId.GREEN;
        Player player = new Player(match, client, "Paolito");
        Player enemy1 = new Player(match, PlayerId.VIOLET, "FedeCazzola");
        Player enemy2 = new Player(match, PlayerId.GREY, "FranzDotti");
        match.addPlayer(player);
        player.respawn(Color.RED);
        match.addPlayer(enemy1);
        enemy1.respawn(Color.RED);
        match.addPlayer(enemy2);
        enemy2.respawn(Color.RED);
        player.addAmmoTile(new AmmoTile(0, new HashMap<Color, Integer>() {{
            put(Color.BLUE, 1);
            put(Color.RED, 4);
            put(Color.YELLOW, 1);
        }}));
        enemy1.addAmmoTile(new AmmoTile(0, new HashMap<Color, Integer>() {{
            put(Color.BLUE, 1);
            put(Color.RED, 0);
            put(Color.YELLOW, 2);
        }}));
        player.addDamage(3, PlayerId.VIOLET);
        player.addDamage(4, PlayerId.YELLOW);
        player.addMarks(2, PlayerId.BLUE);
        player.addWeapon(new Weapon());
        enemy1.addWeapon(new Weapon());
        match.restoreCards();
        Cli cli = new Cli(match.getBoard().getHeight(), match.getBoard().getWidth(), match, client);
        cli.update("Test");
    }
}