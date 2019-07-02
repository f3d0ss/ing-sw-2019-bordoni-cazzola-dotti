package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.playerstate.ManageTurnState;
import it.polimi.ingsw.model.playerstate.SelectedTeleporterState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandsTest {

    @Test
    void testDoneCommandAndIdleState() {
        Player player = new Player(null, null, null);
        ManageTurnState manageTurnState = new ManageTurnState();
        player.changeState(manageTurnState);
        new DoneCommand(player, manageTurnState).execute(); //the player now must be in Idle State
        assertNull(player.getPossibleCommands()); //In idle state getCommands must return null
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

}