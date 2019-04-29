package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.model.command.*;
import it.polimi.ingsw.model.playerstate.ReadyToShootState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

public class WeaponTest {
    List<Weapon> allWeapons;

    private List<Weapon> getAllWeapons() {
        GsonBuilder g = new GsonBuilder();
        g.setPrettyPrinting();
        g.serializeNulls();
        Gson gson = g.create();
        List<Weapon> weaponList = new ArrayList<>();
        File file = new File("src/resources/weapons/");
        File[] files = file.listFiles();
        for (File f : files) {
            try {
                weaponList.add(gson.fromJson(new FileReader(f.getPath()), Weapon.class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return weaponList;
    }

    Weapon getRandomWeapon() {
        return allWeapons.get(new Random().nextInt(allWeapons.size()));
    }

    @BeforeEach
    void setUp() {
        allWeapons = getAllWeapons();
    }

    @Test
    void getWeaponCosts() {
        for (Weapon weapon : allWeapons) {
            Map<Color, Integer> weaponBuyCost = weapon.getWeaponBuyCost();
            Map<Color, Integer> reloadingCost = weapon.getReloadingCost();
            assertTrue(reloadingCost.size() > 0);
            if (weaponBuyCost.size() > 0)
                assertTrue(reloadingCost.size() >= weaponBuyCost.size());
        }
    }

    @Test
    void deselectWeaponMode() {
        Weapon weapon = getRandomWeapon();
        weapon.deselectWeaponMode();
        assertTrue(weapon.getSelectedWeaponMode() == null);
    }

    @Test
    void getWeaponModes() {
        Weapon weapon = getRandomWeapon();
        assertFalse(weapon.getWeaponModes().isEmpty());
    }

    @Test
    void addRemoveTargetPlayer() {
        Weapon weapon = getRandomWeapon();
        Player player = new Player(null, null, null, null);
        weapon.addTargetPlayer(player);
        weapon.removeTargetPlayer(player);
    }

    @Test
    void addRemoveTargetSquare() {
        Square square = new TurretSquare(null, null, null, null, 0, 0, null);
        Weapon weapon = getRandomWeapon();
        weapon.addTargetSquare(square);
        weapon.removeTargetSquare(square);
    }

    @Test
    void reload() {
        Weapon weapon = getRandomWeapon();
        weapon.reload();
        assertTrue(weapon.isLoaded());
    }

    @Test
    void unload() {
        Weapon weapon = getRandomWeapon();
        weapon.unload();
        assertFalse(weapon.isLoaded());
    }

    @Test
    void extraMoves() {
        Weapon weapon = getRandomWeapon();
        if (weapon.hasExtraMove()) {
            weapon.useExtraMoves();
            assertFalse(weapon.hasExtraMove());
        }
    }

    @Test
    void getPossibleCommandsWithoutTargetPlayers() {
        Match match = new Match();
        GameBoard gameBoard = match.getBoard();
        Player player = new Player(match, PlayerId.VIOLET, "Mr", gameBoard.getSpawn(Color.BLUE));
        match.addPlayer(player);
        match.getBoard().getSpawn(Color.BLUE).addPlayer(player);
        for (Weapon weapon : allWeapons) {
            System.out.println(weapon.getName() + "------------");
            ReadyToShootState state = new ReadyToShootState(new AggregateAction(0, false, true, false), weapon);
            player.changeState(state);
            for (WeaponMode weaponMode : weapon.getWeaponModes()) {
                weapon.setSelectedWeaponMode(weaponMode);
                System.out.print(weaponMode.getName() + " N of Commands: ");
                List<Command> possibleCommands = weapon.getPossibleCommands(gameBoard, player, state);
                int shoot = 0, move = 0, targetSquare = 0, targetPlayer = 0;
                for (Command command : possibleCommands) {
                    if (command instanceof ShootCommand) shoot++;
                    else if (command instanceof MoveCommand) move++;
                    else if (command instanceof SelectTargetPlayerCommand) targetPlayer++;
                    else if (command instanceof SelectTargetSquareCommand) targetSquare++;
                }
                System.out.println("Shoot: " + shoot + "| Move: " + move + " |TargetPlayer: " + targetPlayer + " |TargetSquare: " + targetSquare);
                assertTrue(targetPlayer == 0); //these weapons should generate Move Commands
                if (weaponMode.isMoveShooter())
                    assertTrue(move > 0);
                else assertTrue(move == 0);
                if (!weaponMode.isTargetSquare() && weaponMode.isTargetPlayers()) //these weapons can target empty squares
                    assertTrue(targetSquare == 0);
            }
        }
    }

    @Test
    void getSelectWeaponModeCommands() {
    }
}
