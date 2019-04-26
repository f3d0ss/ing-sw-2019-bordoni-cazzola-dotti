package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import org.junit.Test;

import java.util.HashMap;

public class CliTest {

    @Test
    public void displayBoard() {
        Match match = new Match();
        PlayerId client = PlayerId.GREEN;
        Player player = new Player(match, client, "Paolito", match.getBoard().getSpawn(Color.RED));
        Player enemy1 = new Player(match, PlayerId.VIOLET, "FedeCazzola", match.getBoard().getSpawn(Color.RED));
        Player enemy2 = new Player(match, PlayerId.GREY, "FranzDotti", match.getBoard().getSpawn(Color.RED));
        match.addPlayer(player);
        match.getBoard().getSpawn(Color.RED).addPlayer(player);
        match.addPlayer(enemy1);
        match.getBoard().getSpawn(Color.RED).addPlayer(enemy1);
        match.addPlayer(enemy2);
        match.getBoard().getSpawn(Color.RED).addPlayer(enemy2);
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