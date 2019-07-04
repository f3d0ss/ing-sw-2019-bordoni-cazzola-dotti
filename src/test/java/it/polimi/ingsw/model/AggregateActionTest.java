package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link AggregateAction} and {@link AggregateActionID} methods
 */
class AggregateActionTest {

    @Test
    void getMoveNumber() {
        int move = 2;
        boolean grab = true;
        boolean shoot = false;
        boolean reload = false;
        AggregateAction aggregateAction = new AggregateAction(move, grab, shoot, reload);
        assertEquals(move, aggregateAction.getMoveNumber());
    }

    @Test
    void useMovements() {
        int move = 2;
        boolean grab = true;
        boolean shoot = false;
        boolean reload = false;
        AggregateAction aggregateAction = new AggregateAction(move, grab, shoot, reload);
        aggregateAction.useMovements();
        assertTrue(aggregateAction.hasMoved());
        aggregateAction.resetMoves();
        assertFalse(aggregateAction.hasMoved());
    }

    @Test
    void isGrab() {
        int move = 2;
        boolean grab = true;
        boolean shoot = false;
        boolean reload = false;
        AggregateAction aggregateAction = new AggregateAction(move, grab, shoot, reload);
        assertEquals(grab, aggregateAction.isGrab());
    }

    @Test
    void isShoot() {
        int move = 2;
        boolean grab = true;
        boolean shoot = false;
        boolean reload = false;
        AggregateAction aggregateAction = new AggregateAction(move, grab, shoot, reload);
        assertEquals(shoot, aggregateAction.isShoot());
    }

    @Test
    void isReload() {
        int move = 2;
        boolean grab = true;
        boolean shoot = false;
        boolean reload = false;
        AggregateAction aggregateAction = new AggregateAction(move, grab, shoot, reload);
        assertEquals(reload, aggregateAction.isReload());

    }

    @Test
    void testCreateID() {
        for (AggregateActionID aggregateActionID : AggregateActionID.values()) {
            aggregateActionID.create();
        }
    }

    @Test
    void testToStringID() {
        for (AggregateActionID aggregateActionID : AggregateActionID.values()) {
            assertNotNull(aggregateActionID.toString());
        }
    }
}