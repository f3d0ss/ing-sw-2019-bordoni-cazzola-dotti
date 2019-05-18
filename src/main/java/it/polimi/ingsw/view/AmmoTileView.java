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
}
