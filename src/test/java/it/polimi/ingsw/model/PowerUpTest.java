package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for {@link PowerUp} and {@link PowerUpID} methods
 */
class PowerUpTest {

    @Test
    void getTypeAndColor() {
        for (PowerUpID powerUpID : PowerUpID.values()) {
            for (Color color : Color.values()) {
                PowerUp powerUp = new PowerUp(powerUpID, color);
                assertEquals(color, powerUp.getColor());
                assertEquals(powerUpID, powerUp.getType());
            }
        }
    }

    @Test
    void testIdMethods() {
        for (PowerUpID powerUpID : PowerUpID.values()) {
            assertNotNull(powerUpID.powerUpName());
            assertNotNull(powerUpID.powerUpID());
        }
    }

}