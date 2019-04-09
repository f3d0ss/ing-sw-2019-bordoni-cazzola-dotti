package it.polimi.ingsw.model;

import java.util.Map;

/**
 * This class represents a single ammo tile
 */
public class AmmoTile {

    private int powerUp;
    private Map<Color, Integer> ammo;

    public AmmoTile(int powerUp, Map<Color, Integer> ammo) {
        this.powerUp = powerUp;
        this.ammo = ammo;
    }

    public int getPowerUp() {
        return powerUp;
    }

    public Map<Color, Integer> getAmmo() {
        return ammo;
    }
}
