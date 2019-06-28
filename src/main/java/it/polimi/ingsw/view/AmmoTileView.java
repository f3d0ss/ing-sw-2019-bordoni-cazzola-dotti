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

    public boolean isEmpty(){
        return (powerUp == 0) && (ammo == null);
    }


    @Override
    public String toString() {
        StringBuilder id = new StringBuilder();
        id.append("P".repeat(Math.max(0, powerUp)));
        if (ammo != null){
            for (int i = 0; i < ammo.getOrDefault(Color.BLUE, 0); i++) {
                id.append(Color.BLUE.colorInitial());
            }
            for (int i = 0; i < ammo.getOrDefault(Color.RED, 0); i++) {
                id.append(Color.RED.colorInitial());
            }
            for (int i = 0; i < ammo.getOrDefault(Color.YELLOW, 0); i++) {
                id.append(Color.YELLOW.colorInitial());
            }
        }
        return String.valueOf(id);
    }
}
