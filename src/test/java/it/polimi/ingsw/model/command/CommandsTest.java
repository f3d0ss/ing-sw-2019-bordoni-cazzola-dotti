package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.playerstate.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests some Commands
 */
class CommandsTest {

    @Test
    void testDoneCommandAndIdleState() {
        Player player = new Player(null, null, null);
        ManageTurnState manageTurnState = new ManageTurnState();
        player.changeState(manageTurnState);
        DoneCommand doneCommand = new DoneCommand(player, manageTurnState); //the player now must be in Idle State
        doneCommand.execute();
        assertNull(player.getPossibleCommands()); //In idle state getCommands must return null
        doneCommand.createCommandMessage();
        if (doneCommand.isUndoable())
            doneCommand.undo();
    }

    @Test
    void testEffectCommandThrows() {
        Player player = new Player(null, PlayerId.VIOLET, null);
        //A player can't damage or mark himself
        assertThrows(UnsupportedOperationException.class, () -> new EffectCommand(player, 1, 0, null, player.getId()));
        assertThrows(UnsupportedOperationException.class, () -> new EffectCommand(player, 0, 1, null, player.getId()));
    }

    @Test
    void testEffectCommand() {
        Match match = new Match();
        Square square1 = match.getBoard().getSquareList().get(0);
        Player player = new Player(match, PlayerId.VIOLET, null);
        match.addPlayer(player);
        player.respawn(Color.YELLOW);
        Player player1 = new Player(match, PlayerId.GREY, null);
        match.addPlayer(player1);
        int damage = 2;
        int marks = 1;
        EffectCommand effectCommand = new EffectCommand(player, damage, marks, square1, player1.getId());
        effectCommand.execute();
        //effect command must damage, mark and move the player
        assertEquals(player1.getId(), player.getHealth().get(0));
        assertEquals(damage, player.getHealth().size());
        assertEquals(marks, player.getMarks().get(player1.getId()));
        assertEquals(marks, player.getMarks().size());
        assertEquals(square1, player.getPosition());

        assertEquals(player1, player.getLastShooter());
    }

    @Test
    void testGrabTile() {
        Match match = new Match();
        Player player = new Player(match, PlayerId.VIOLET, null);
        match.addPlayer(player);
        //player created with 0 power ups and 1 ammo cube per color
        TurretSquare square = match.getBoard().getTurrets().get(0);
        AmmoTile ammoTile = square.getAmmoTile();
        GrabTileCommand grabTileCommand = new GrabTileCommand(player, ammoTile, square);
        grabTileCommand.execute();
        //test ammo add with maps
        //test power up add
        assertEquals(ammoTile.getPowerUp(), player.getPowerUps().size());
        //square is now empty
        assertNull(square.getAmmoTile());

        //grab command is undoable
        assertThrows(IllegalUndoException.class, grabTileCommand::undo);
    }

    @Test
    void testTeleporterCommand() {
        Match match = new Match();
        Square square = match.getBoard().getSquareList().get(0);
        Player player = new Player(match, PlayerId.VIOLET, null);
        match.addPlayer(player);
        player.respawn(Color.YELLOW);
        UseTeleportCommand useTeleportCommand = new UseTeleportCommand(player, new SelectedTeleporterState(), square);
        useTeleportCommand.execute();

        assertEquals(square, player.getPosition());

        //command is undoable
        if (useTeleportCommand.isUndoable())
            useTeleportCommand.undo();

        assertEquals(match.getBoard().getSpawn(Color.YELLOW), player.getPosition());
    }

    @Test
    void testEffect() {
        Match match = new Match();
        Player player = new Player(match, PlayerId.BLUE, PlayerId.BLUE.playerIdName());
        player.respawn(Color.YELLOW);
        EffectCommand effectCommand = new EffectCommand(player, 1, 1, player.getPosition(), PlayerId.GREY);
        effectCommand.createCommandMessage();
        effectCommand.execute();
        assertEquals(player, effectCommand.getPlayer());
    }

    @Test
    void testMove() {
        Match match = new Match();
        GameBoard gameBoard = match.getBoard();
        Player player = new Player(match, PlayerId.BLUE, PlayerId.BLUE.playerIdName());
        match.addPlayer(player);
        player.respawn(Color.RED);
        Square square1 = gameBoard.getSquareList().get(0);
        player.move(square1);
        Square square = gameBoard.getSquareList().get(1);
        MoveCommand moveCommand = new MoveCommand(player, square, new SelectedAggregateActionState(AggregateActionID.MOVE_MOVE_GRAB.create()));
        MoveCommand moveCommand1 = new MoveCommand(player, square1, new ReadyToShootState(AggregateActionID.SHOOT.create(), new Weapon()));
        moveCommand1.createCommandMessage();
        moveCommand.execute();
        assertEquals(square, player.getPosition());
        if (moveCommand.isUndoable())
            moveCommand.undo();
        assertEquals(square1, player.getPosition());
        moveCommand1.execute();
        assertEquals(square1, player.getPosition());
    }

    @Test
    void testPayReloadBeforeShot() {
        Match match = new Match();
        Player player = new Player(match, PlayerId.BLUE, PlayerId.BLUE.playerIdName());
        player.respawn(Color.YELLOW);
        Weapon weapon = new Weapon();
        weapon.unload();
        PayReloadBeforeShotCommand payReloadBeforeShotCommand = new PayReloadBeforeShotCommand(player, new PendingPaymentReloadBeforeShotState(
                AggregateActionID.SHOOT.create(), weapon));
        payReloadBeforeShotCommand.execute();
        assertTrue(weapon.isLoaded());
        payReloadBeforeShotCommand.createCommandMessage();
        if (payReloadBeforeShotCommand.isUndoable())
            payReloadBeforeShotCommand.undo();
        assertFalse(weapon.isLoaded());
    }

    @Test
    void testPayReloadBeforeWeaponCommand() {
        Match match = new Match();
        Player player = new Player(match, PlayerId.BLUE, PlayerId.BLUE.playerIdName());
        player.respawn(Color.YELLOW);
        Weapon weapon = new Weapon();
        weapon.unload();
        PayReloadWeaponCommand payReloadBeforeShotCommand = new PayReloadWeaponCommand(player, new PendingPaymentReloadWeaponState(weapon));
        payReloadBeforeShotCommand.execute();
        assertTrue(weapon.isLoaded());
        payReloadBeforeShotCommand.createCommandMessage();
        if (payReloadBeforeShotCommand.isUndoable())
            payReloadBeforeShotCommand.undo();
        assertFalse(weapon.isLoaded());
    }

    @Test
    void testPayScope() {
        Match match = new Match();
        Player player = new Player(match, PlayerId.BLUE, PlayerId.BLUE.playerIdName());
        player.respawn(Color.YELLOW);
        Weapon weapon = new Weapon();
        Player player1 = new Player(match, PlayerId.GREY, null);
        List<Player> list = new ArrayList<>();
        list.add(player1);
        PayScopeCommand payScopeCommand = new PayScopeCommand(player, new PendingPaymentScopeState(AggregateActionID.SHOOT.create(), weapon, list));
        payScopeCommand.execute();
        payScopeCommand.createCommandMessage();
        if (payScopeCommand.isUndoable())
            payScopeCommand.undo();
    }
}