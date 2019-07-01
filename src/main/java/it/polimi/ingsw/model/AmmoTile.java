package it.polimi.ingsw.model;

import java.util.Map;

/**
 * This class represents a single ammo tile
 */
public class AmmoTile {

    private final int powerUp;
    private final Map<Color, Integer> ammo;

    public AmmoTile(int powerUp, Map<Color, Integer> ammo) {
        this.powerUp = powerUp;
        this.ammo = ammo;
    }

    /**
     * Gets ho many power up are contained
     *
     * @return number of power up
     */
    public int getPowerUp() {
        return powerUp;
    }

    /**
     * Gets Ammo contained
     *
     * @return Ammo contained
     */
    public Map<Color, Integer> getAmmo() {
        return ammo;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        if (powerUp > 0)
            string = new StringBuilder("P");
        for (Map.Entry<Color, Integer> entry : ammo.entrySet())
            for (int i = 0; i < entry.getValue(); i++)
                string.append(entry.getKey().colorName(), 0, 1);
        return string.toString();
    }
}
