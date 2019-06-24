package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Color;

import java.util.Map;

public class AmmoTileView {
    private final int powerUp;
    private final Map<Color, Integer> ammo;

    public AmmoTileView(int powerUp, Map<Color, Integer> ammo) {
        this.powerUp = powerUp;
        this.ammo = ammo;
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
