package it.polimi.ingsw.model;

import it.polimi.ingsw.model.command.GrabCommand;
import it.polimi.ingsw.model.playerstate.SelectedAggregateActionState;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link SpawnSquare}'s methods
 */
class SpawnSquareTest {

    private SpawnSquare getSpawnSquare() {
        Match match = new Match();
        return match.getBoard().getSpawn(Color.RED);
    }

    @Test
    void getGrabCommands() {
        SpawnSquare spawnSquare = getSpawnSquare();
        List<GrabCommand> grabCommands = spawnSquare.getGrabCommands(new Player(null, null, null), new SelectedAggregateActionState(AggregateActionID.MOVE_GRAB.create()));
        assertEquals(spawnSquare.getWeapons().size(), grabCommands.size());
    }

    @Test
    void getSquareView() {
        SpawnSquare spawnSquare = getSpawnSquare();
        spawnSquare.getSquareView();
    }

    @Test
    void removeWeapon() {
        SpawnSquare spawnSquare = getSpawnSquare();
        Weapon weapon = spawnSquare.getWeapons().get(0);
        spawnSquare.removeWeapon(weapon);
        assertFalse(spawnSquare.getWeapons().contains(weapon));
    }

    @Test
    void addWeapon() {
        SpawnSquare spawnSquare = getSpawnSquare();
        Weapon weapon = new Weapon();
        assertThrows(IllegalStateException.class, () -> spawnSquare.addWeapon(weapon));
    }

    @Test
    void testInit() {
        SpawnSquare spawnSquare = new SpawnSquare(Connection.SAME_ROOM, Connection.DOOR, Connection.MAP_BORDER, Connection.WALL, 1, 1, Color.RED);
        assertTrue(spawnSquare.lackWeapon());
    }

    @Test
    void testTurretSquare() {
        TurretSquare turretSquare = new TurretSquare(Connection.SAME_ROOM, Connection.DOOR, Connection.MAP_BORDER, Connection.WALL, 1, 1);
        turretSquare.getSquareView();
        assertTrue(turretSquare.getGrabCommands(new Player(null, null, null), new SelectedAggregateActionState(AggregateActionID.MOVE_GRAB.create())).isEmpty());
    }
}