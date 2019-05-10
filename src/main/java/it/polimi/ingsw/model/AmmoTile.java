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

    @Override
    public String toString() {
        String string = "";
        if (powerUp > 0)
            string = "P";
        for (Map.Entry<Color, Integer> entry : ammo.entrySet())
            for (int i = 0; i < entry.getValue(); i++)
                string = string + entry.getKey().colorName().substring(0, 1);
        return string;
    }
}
