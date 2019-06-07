package it.polimi.ingsw.model;

import it.polimi.ingsw.model.command.*;
import it.polimi.ingsw.model.playerstate.ChoosingWeaponOptionState;
import it.polimi.ingsw.model.playerstate.ReadyToShootState;
import it.polimi.ingsw.utils.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


public class WeaponTest {
    List<Weapon> allWeapons;

    private List<Weapon> getAllWeapons() {
        Parser parser = new Parser();
        List<Weapon> weaponList = new ArrayList<>();
        File file = new File("src/resources/weapons/");
        File[] files = file.listFiles();
        for (File f : files) {
            try {
                weaponList.add(parser.deserialize(new FileReader(f.getPath()), Weapon.class));
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
        Player player = new Player(null, null, null);
        weapon.addTargetPlayer(player);
        weapon.removeTargetPlayer(player);
    }

    @Test
    void addRemoveTargetSquare() {
        Square square = new TurretSquare(null, null, null, null, 0, 0);
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
        for (Weapon weapon : allWeapons) {
            if (weapon.hasExtraMove()) {
                weapon.useExtraMoves();
                assertFalse(weapon.hasExtraMove());
            }
        }
    }

    @Test
    void getPossibleCommandsWithoutTargetPlayers() {
        Match match = new Match();
        GameBoard gameBoard = match.getBoard();
        Player player = new Player(match, PlayerId.VIOLET, "Mr");
        player.respawn(Color.BLUE);
        match.addPlayer(player);
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
                assertTrue(targetPlayer == 0);
                assertTrue(move == 0);
                if (!weaponMode.isTargetSquare() && weaponMode.isTargetPlayers()) //these weapons can target empty squares
                    assertTrue(targetSquare == 0);
            }
        }
    }

    @Test
    void getSelectWeaponModeCommands() {
        Match match = new Match();
        GameBoard gameBoard = match.getBoard();
        Player player = new Player(match, PlayerId.VIOLET, "Mr");
        player.respawn(Color.BLUE);
        for (Weapon weapon : allWeapons) {
            ChoosingWeaponOptionState state = new ChoosingWeaponOptionState(new AggregateAction(0, false, true, false), weapon);
            List<SelectWeaponModeCommand> selectWeaponModeCommands = weapon.getSelectWeaponModeCommands(player, state);
            for (SelectWeaponModeCommand selectWeaponModeCommand : selectWeaponModeCommands) {
                selectWeaponModeCommand.execute();
                assertNotNull(weapon.getSelectedWeaponMode());
                selectWeaponModeCommand.undo();
                assertNull(weapon.getSelectedWeaponMode());
            }
        }
    }

    @Test
    void testExtraMoveCommands() {
        Match match = new Match();
        GameBoard gameBoard = match.getBoard();
        Square square = getRandomSquare(gameBoard);
        Player player = new Player(match, PlayerId.VIOLET, "Mr");
        player.respawn(Color.BLUE);
        player.move(square);
        match.addPlayer(player);
        square.addPlayer(player);
        for (Weapon weapon : allWeapons) {
            System.out.println(weapon.getName() + "------------");
            ReadyToShootState state = new ReadyToShootState(new AggregateAction(0, false, true, false), weapon);
            player.changeState(state);
            for (WeaponMode weaponMode : weapon.getWeaponModes()) {
                if (!weaponMode.isMoveShooter())
                    continue;
                weapon.setSelectedWeaponMode(weaponMode);
                System.out.println(weaponMode.getName());
                List<Command> possibleCommands = weapon.getPossibleCommands(gameBoard, player, state);
                System.out.println("number of commands: " + possibleCommands.size());
                for (Command command : possibleCommands) {
                    if (command instanceof MoveCommand) {
                        Square oldPosition = player.getPosition();
                        System.out.println("old pos: " + oldPosition.getRow() + " " + oldPosition.getCol());
                        command.execute();
                        Square newPosition = player.getPosition();
                        System.out.println("new pos: " + newPosition.getRow() + " " + newPosition.getCol());
                        assertTrue(newPosition.getHostedPlayers().contains(player));
                        command.undo();
                        assertEquals(oldPosition, player.getPosition());
                    }
                }
            }
        }
    }

    @Test
    void getPossibleCommands() {
        Match match = new Match();
        GameBoard gameBoard = match.getBoard();
        Square shooterSquare = getRandomSquare(gameBoard);
        Player shooter = new Player(match, PlayerId.VIOLET, PlayerId.VIOLET.playerIdName());
        shooter.respawn(Color.BLUE);
        shooter.move(shooterSquare);
        match.addPlayer(shooter);
        shooterSquare.addPlayer(shooter);

        for (PlayerId playerId : PlayerId.values()) {
            if (!playerId.equals(shooter.getId())) {
                Square square = getRandomSquare(gameBoard);
                Player player = new Player(match, playerId, playerId.playerIdName());
                player.respawn(Color.BLUE);
                player.move(square);
                match.addPlayer(player);
                square.addPlayer(player);
            }
        }

        for (Weapon weapon : allWeapons) {
            System.out.println(weapon.getName() + "------------");
            ReadyToShootState state = new ReadyToShootState(new AggregateAction(0, false, true, false), weapon);
            shooter.changeState(state);
            for (WeaponMode weaponMode : weapon.getWeaponModes()) {
                weapon.setSelectedWeaponMode(weaponMode);
                System.out.print(weaponMode.getName() + " N of Commands: ");
                List<Command> possibleCommands = weapon.getPossibleCommands(gameBoard, shooter, state);
                int shoot = 0, move = 0, targetSquare = 0, targetPlayer = 0;
                List<ShootCommand> shootCommands = new ArrayList<>();
                List<SelectTargetPlayerCommand> selectTargetPlayerCommands = new ArrayList<>();
                List<SelectTargetSquareCommand> selectTargetSquareCommands = new ArrayList<>();
                for (Command command : possibleCommands) {
                    if (command instanceof ShootCommand) {
                        shoot++;
                        shootCommands.add((ShootCommand) command);
                    } else if (command instanceof MoveCommand) {
                        move++;
                    } else if (command instanceof SelectTargetPlayerCommand) {
                        targetPlayer++;
                        selectTargetPlayerCommands.add((SelectTargetPlayerCommand) command);
                    } else if (command instanceof SelectTargetSquareCommand) {
                        targetSquare++;
                        selectTargetSquareCommands.add((SelectTargetSquareCommand) command);
                    }
                }
                System.out.println("Shoot: " + shoot + "| Move: " + move + " |TargetPlayer: " + targetPlayer + " |TargetSquare: " + targetSquare);
                if (!selectTargetPlayerCommands.isEmpty()) {
                    for (SelectTargetPlayerCommand selectTargetPlayerCommand : selectTargetPlayerCommands) {
                        selectTargetPlayerCommand.execute();
                        selectTargetPlayerCommand.undo();
                    }
                }
                if (!selectTargetSquareCommands.isEmpty()) {
                    for (SelectTargetSquareCommand selectTargetSquareCommand : selectTargetSquareCommands) {
                        selectTargetSquareCommand.execute();
                        selectTargetSquareCommand.undo();
                    }
                }
            }
        }
    }

    @Test
    void getPossibleShootCommandsTest() {
        Match match = new Match();
        GameBoard gameBoard = match.getBoard();
        Square shooterSquare = getRandomSquare(gameBoard);
        Player shooter = new Player(match, PlayerId.VIOLET, PlayerId.VIOLET.playerIdName());
        shooter.respawn(Color.BLUE);
        shooter.move(shooterSquare);
        match.addPlayer(shooter);
        shooterSquare.addPlayer(shooter);
        for (PlayerId playerId : PlayerId.values()) {
            if (!playerId.equals(shooter.getId())) {
                Square square = getRandomSquare(gameBoard);
                Player player = new Player(match, playerId, playerId.playerIdName());
                player.respawn(Color.BLUE);
                player.move(square);
                match.addPlayer(player);
                square.addPlayer(player);
            }
        }

        for (Weapon weapon : allWeapons) {
            System.out.println(weapon.getName() + "------------");
            ReadyToShootState state = new ReadyToShootState(new AggregateAction(0, false, true, false), weapon);
            shooter.changeState(state);
            for (WeaponMode weaponMode : weapon.getWeaponModes()) {
                weapon.setSelectedWeaponMode(weaponMode);

                if (!weaponMode.isTargetPlayers() || weaponMode.isTargetSquare()) {
                    weapon.addTargetSquare(match.getCurrentPlayers().get(3).getPosition());
                }

                for (int i = 0; i < weaponMode.getMaxNumberOfTargetPlayers(); i++) {
                    weapon.addTargetPlayer(match.getCurrentPlayers().get(i + 1));
                }

                System.out.println(weaponMode.getName());
                List<Command> possibleCommands = weapon.getPossibleCommands(gameBoard, shooter, state);
                System.out.println("number of commands: " + possibleCommands.size());
                for (Command command : possibleCommands) {
                    if (command instanceof ShootCommand) {
                        match.getCurrentPlayers().stream().map(player -> player.getHealth().size()).forEach(System.out::print);
                        command.execute();
                        match.getCurrentPlayers().stream().map(player -> player.getHealth().size()).forEach(System.out::print);
                        break;
                    }
                }
            }
        }

    }

    private Square getRandomSquare(GameBoard gameBoard) {
        return gameBoard.getSquareList().get(new Random().nextInt(gameBoard.getSquareList().size()));
    }
}

