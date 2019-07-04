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

/**
 * Test for {@link Weapon}'s methods
 */
class WeaponTest {
    private List<Weapon> allWeapons;

    private List<Weapon> getAllWeapons() {
        Parser parser = new Parser();
        List<Weapon> weaponList = new ArrayList<>();
        File file = new File("src/resources/weapons/");
        File[] files = file.listFiles();
        for (File f : files) {
            try {
                weaponList.add(parser.deserialize(new FileReader(f.getPath())));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return weaponList;
    }

    @BeforeEach
    void setUp() {
        allWeapons = getAllWeapons();
    }

    private Weapon getWeaponByName(String name) {
        for (Weapon weapon : allWeapons) {
            if (weapon.getName().contains(name))
                return weapon;
        }
        return null;
    }

    Weapon getRandomWeapon() {
        return allWeapons.get(new Random().nextInt(allWeapons.size()));
    }

    private Square getRandomSquare(GameBoard gameBoard) {
        return gameBoard.getSquareList().get(new Random().nextInt(gameBoard.getSquareList().size()));
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
        assertNull(weapon.getSelectedWeaponMode());
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
    void setExtraMoveTest() {
        for (Weapon weapon : getAllWeapons())
            for (WeaponMode weaponMode : weapon.getWeaponModes()) {
                weapon.setSelectedWeaponMode(weaponMode);
                weapon.useExtraMoves();
                assertFalse(weapon.hasExtraMove());
                weapon.resetMoves();
                assertTrue(weapon.hasExtraMove());
            }
    }

    /**
     * Test if without targets the weapon does not create commands
     */
    @Test
    void getPossibleCommandsWithoutTargetPlayers() {
        Match match = new Match();
        GameBoard gameBoard = match.getBoard();
        Player player = new Player(match, PlayerId.VIOLET, "Mr");
        player.respawn(Color.BLUE);
        match.addPlayer(player);
        for (Weapon weapon : allWeapons) {
            ReadyToShootState state = new ReadyToShootState(new AggregateAction(0, false, true, false), weapon);
            player.changeState(state);
            for (WeaponMode weaponMode : weapon.getWeaponModes()) {
                weapon.setSelectedWeaponMode(weaponMode);
                List<Command> possibleCommands = weapon.getPossibleCommands(gameBoard, player, state);
                int shoot = 0, move = 0, targetSquare = 0, targetPlayer = 0;
                for (Command command : possibleCommands) {
                    if (command instanceof ShootCommand) shoot++;
                    else if (command instanceof MoveCommand) move++;
                    else if (command instanceof SelectTargetPlayerCommand) targetPlayer++;
                    else if (command instanceof SelectTargetSquareCommand) targetSquare++;
                }
                assertEquals(0, targetPlayer);
                assertEquals(0, move);
                if (!weaponMode.isTargetSquare() && weaponMode.isTargetPlayers()) //these weapons can target empty squares
                    assertEquals(0, targetSquare);
            }
        }
    }

    /**
     * Tests if commands to select a weapon mode are generated correctly
     */
    @Test
    void getSelectWeaponModeCommands() {
        Match match = new Match();
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

    /**
     * Tests correct generation of move commands
     */
    @Test
    void testExtraMoveCommands() {
        Match match = new Match();
        GameBoard gameBoard = match.getBoard();
        Square square = getRandomSquare(gameBoard);
        Player player = new Player(match, PlayerId.VIOLET, "Mr");
        player.respawn(Color.BLUE);
        player.move(square);
        match.addPlayer(player);
        for (Weapon weapon : allWeapons) {
            ReadyToShootState state = new ReadyToShootState(new AggregateAction(0, false, true, false), weapon);
            player.changeState(state);
            for (WeaponMode weaponMode : weapon.getWeaponModes()) {
                if (!weaponMode.isMoveShooter())
                    continue;
                weapon.setSelectedWeaponMode(weaponMode);
                List<Command> possibleCommands = weapon.getPossibleCommands(gameBoard, player, state);
                for (Command command : possibleCommands) {
                    if (command instanceof MoveCommand) {
                        Square oldPosition = player.getPosition();
                        command.execute();
                        Square newPosition = player.getPosition();
                        assertTrue(newPosition.getHostedPlayers().contains(player));
                        command.undo();
                        assertEquals(oldPosition, player.getPosition());
                    }
                }
            }
        }
    }

    /**
     * Test creation of commands for targets and shooter on the same square
     */
    @Test
    void getPossibleCommandsTargetsOnSameSquare() {
        Match match = new Match();
        GameBoard gameBoard = match.getBoard();
        Square shooterSquare = gameBoard.getSquare(1, 1);
        Player shooter = new Player(match, PlayerId.VIOLET, PlayerId.VIOLET.playerIdName());
        shooter.respawn(Color.BLUE);
        shooter.move(shooterSquare);
        match.addPlayer(shooter);

        Player player = new Player(match, PlayerId.GREEN, PlayerId.GREEN.playerIdName());
        player.respawn(Color.BLUE);
        player.move(shooterSquare);
        match.addPlayer(player);

        Player player1 = new Player(match, PlayerId.GREY, PlayerId.GREY.playerIdName());
        player1.respawn(Color.BLUE);
        player1.move(shooterSquare);
        match.addPlayer(player1);

        for (Weapon weapon : allWeapons) {
            ReadyToShootState state = new ReadyToShootState(new AggregateAction(0, false, true, false), weapon);
            shooter.changeState(state);
            for (WeaponMode weaponMode : weapon.getWeaponModes()) {
                weapon.setSelectedWeaponMode(weaponMode);
                List<Command> possibleCommands = weapon.getPossibleCommands(gameBoard, shooter, state);
                int shoot = 0, move = 0, targetSquare = 0, targetPlayer = 0;
                for (Command command : possibleCommands) {
                    if (command instanceof ShootCommand) {
                        shoot++;
                    } else if (command instanceof MoveCommand) {
                        move++;
                    } else if (command instanceof SelectTargetPlayerCommand) {
                        targetPlayer++;
                    } else if (command instanceof SelectTargetSquareCommand) {
                        targetSquare++;
                    }
                }
                assertEquals(0, shoot);
                assertTrue(targetPlayer < 3);
                if (weaponMode.getMinTargetDistance() > 0)
                    assertEquals(0, targetPlayer);
                if (weaponMode.getMaxTargetDistance() == 0)
                    assertTrue(targetPlayer + targetSquare > 0);
                if (weaponMode.isEachTargetOnDifferentSquares())
                    assertTrue(targetPlayer <= 1);
            }
        }
    }

    /**
     * Test creation of commands for targets in the same room
     */
    @Test
    void getPossibleCommandsTargetsInTheSameRoom() {
        Match match = new Match();
        GameBoard gameBoard = match.getBoard();

        Square shooterSquare = gameBoard.getSquare(1, 0);
        Player shooter = new Player(match, PlayerId.VIOLET, PlayerId.VIOLET.playerIdName());
        shooter.respawn(Color.BLUE);
        shooter.move(shooterSquare);
        match.addPlayer(shooter);

        Player player = new Player(match, PlayerId.GREEN, PlayerId.GREEN.playerIdName());
        player.respawn(Color.BLUE);
        player.move(shooterSquare);
        match.addPlayer(player);

        Square square1 = gameBoard.getSquare(1, 1);
        Player player1 = new Player(match, PlayerId.GREY, PlayerId.GREY.playerIdName());
        player1.respawn(Color.BLUE);
        player1.move(square1);
        match.addPlayer(player1);

        Player player2 = new Player(match, PlayerId.BLUE, PlayerId.BLUE.playerIdName());
        player2.respawn(Color.BLUE);
        player2.move(square1);
        match.addPlayer(player2);

        Square square3 = gameBoard.getSquare(1, 2);
        Player player3 = new Player(match, PlayerId.GREY, PlayerId.GREY.playerIdName());
        player3.respawn(Color.BLUE);
        player3.move(square3);
        match.addPlayer(player3);

        for (Weapon weapon : allWeapons) {
            ReadyToShootState state = new ReadyToShootState(new AggregateAction(0, false, true, false), weapon);
            shooter.changeState(state);
            for (WeaponMode weaponMode : weapon.getWeaponModes()) {
                weapon.setSelectedWeaponMode(weaponMode);
                List<Command> possibleCommands = weapon.getPossibleCommands(gameBoard, shooter, state);
                int shoot = 0, move = 0, targetSquare = 0, targetPlayer = 0;
                for (Command command : possibleCommands) {
                    if (command instanceof ShootCommand) {
                        shoot++;
                    } else if (command instanceof MoveCommand) {
                        move++;
                    } else if (command instanceof SelectTargetPlayerCommand) {
                        targetPlayer++;
                    } else if (command instanceof SelectTargetSquareCommand) {
                        targetSquare++;
                    }
                }
                if (!weaponMode.isSpinner()) //spinner autoselects targets
                    assertEquals(0, shoot);
                assertTrue(targetPlayer <= match.getCurrentPlayers().size());
                if (weaponMode.isTargetVisibleByShooter() && weaponMode.getMinTargetDistance() <= 2 && !weaponMode.isTargetRoom() && !weaponMode.isSpinner())
                    assertTrue(targetPlayer + targetSquare > 0);
            }
        }
    }


    /**
     * Tests creation of shoot commands
     */
    @Test
    void getPossibleShootCommandsTest() {
        Match match = new Match();
        GameBoard gameBoard = match.getBoard();

        Square shooterSquare = gameBoard.getSquare(1, 0);
        Player shooter = new Player(match, PlayerId.VIOLET, PlayerId.VIOLET.playerIdName());
        shooter.respawn(Color.BLUE);
        shooter.move(shooterSquare);
        match.addPlayer(shooter);

        Player player = new Player(match, PlayerId.GREEN, PlayerId.GREEN.playerIdName());
        player.respawn(Color.BLUE);
        player.move(shooterSquare);
        match.addPlayer(player);

        Square square1 = gameBoard.getSquare(1, 1);
        Player player1 = new Player(match, PlayerId.GREY, PlayerId.GREY.playerIdName());
        player1.respawn(Color.BLUE);
        player1.move(square1);
        match.addPlayer(player1);

        Player player2 = new Player(match, PlayerId.BLUE, PlayerId.BLUE.playerIdName());
        player2.respawn(Color.BLUE);
        player2.move(square1);
        match.addPlayer(player2);

        Square square3 = gameBoard.getSquare(1, 2);
        Player player3 = new Player(match, PlayerId.GREY, PlayerId.GREY.playerIdName());
        player3.respawn(Color.BLUE);
        player3.move(square3);
        match.addPlayer(player3);

        for (Weapon weapon : allWeapons) {
            ReadyToShootState state = new ReadyToShootState(new AggregateAction(0, false, true, false), weapon);
            shooter.changeState(state);
            for (WeaponMode weaponMode : weapon.getWeaponModes()) {
                //weaponmodes that need also to select target Squares or can't target player in this situation are excluded
                if (weaponMode.isTargetPlayers() && !weaponMode.isTargetSquare() && weaponMode.isTargetVisibleByShooter() && weaponMode.getMinTargetDistance() <= 2
                        && !weaponMode.isTargetRoom() && !weaponMode.isSpinner()) {
                    weapon.setSelectedWeaponMode(weaponMode);
                    List<Command> possibleCommands = weapon.getPossibleCommands(gameBoard, shooter, state);
                    for (Command command : possibleCommands) {
                        if (command instanceof SelectTargetPlayerCommand) {
                            command.execute(); //first targetPlayersCommand is executed
                            possibleCommands = shooter.getPossibleCommands();
                            break;
                        }
                    }
                    int shoot = 0;
                    for (Command command : possibleCommands) {
                        if (command instanceof ShootCommand) {
                            shoot++;
                        }
                    }
                    //these weaponmodes must generate at least 1 shoot command
                    if (weaponMode.getMinNumberOfTargetPlayers() == 1) {
                        assertTrue(shoot > 0);
                        //weaponmodes that move target should have more shootcommands (the move of the target is included in the effect of the shootcommand)
                        if (weaponMode.isMoveTargetAfterShoot())
                            assertTrue(shoot > 1);
                    } else if (weaponMode.getMinNumberOfTargetPlayers() > 1) {//must select more targets to be able to shoot
                        assertEquals(0, shoot);
                    }
                }
            }
        }
    }

    /**
     * Tests barbecue mod
     */
    @Test
    void flamethrowerSecondModeTest() {
        Match match = new Match();
        GameBoard gameBoard = match.getBoard();
        Square shooterSquare = gameBoard.getSquare(2, 1);
        Player shooter = new Player(match, PlayerId.VIOLET, PlayerId.VIOLET.playerIdName());
        shooter.respawn(Color.BLUE);
        shooter.move(shooterSquare);
        match.addPlayer(shooter);

        Square square = gameBoard.getSquare(2, 2);
        Player player = new Player(match, PlayerId.GREEN, PlayerId.GREEN.playerIdName());
        player.respawn(Color.BLUE);
        player.move(square);
        match.addPlayer(player);

        Square square1 = gameBoard.getSquare(2, 2);
        Player player1 = new Player(match, PlayerId.GREY, PlayerId.GREY.playerIdName());
        player1.respawn(Color.BLUE);
        player1.move(square1);
        match.addPlayer(player1);

        Square square2 = gameBoard.getSquare(2, 3);
        Player player2 = new Player(match, PlayerId.BLUE, PlayerId.BLUE.playerIdName());
        player2.respawn(Color.BLUE);
        player2.move(square2);
        match.addPlayer(player2);

        Weapon flamethrowher = getWeaponByName("Flame");
        ReadyToShootState state = new ReadyToShootState(new AggregateAction(0, false, true, false), flamethrowher);
        shooter.changeState(state);
        flamethrowher.setSelectedWeaponMode(flamethrowher.getWeaponModes().get(1));
        List<Command> possibleCommands = shooter.getPossibleCommands();
        possibleCommands.get(0).execute();
        possibleCommands = shooter.getPossibleCommands();
        possibleCommands.get(0).execute();
        possibleCommands = shooter.getPossibleCommands();
        possibleCommands.get(0).execute();
        assertEquals(2, player.getHealth().size());
        assertEquals(2, player1.getHealth().size());
        assertEquals(1, player2.getHealth().size());
    }
}