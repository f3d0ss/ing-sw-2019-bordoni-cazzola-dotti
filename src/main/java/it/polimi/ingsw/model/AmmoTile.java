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
        String string = new String();
        if (powerUp > 0)
            string = "P";
        for (Color color : ammo.keySet())
            for (int i = 0; i < ammo.get(color); i++)
                string = string + color.colorShortName();
        return string;
    }
}
