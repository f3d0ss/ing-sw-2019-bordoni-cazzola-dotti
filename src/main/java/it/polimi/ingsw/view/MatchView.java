package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;

import java.util.List;
import java.util.Map;

public class MatchView {

    private final List<PlayerId> killshotTrack;
    private final int deathsCounter;
    private final Map<Color,List<WeaponView>> weaponsOnSpawn;

    public MatchView(List<PlayerId> killshotTrack, int deathsCounter, Map<Color, List<WeaponView>> weaponsOnSpawn) {
        this.killshotTrack = killshotTrack;
        this.deathsCounter = deathsCounter;
        this.weaponsOnSpawn = weaponsOnSpawn;
    }

    public void updateSpawn(Color color, List<WeaponView> weapons){
        weaponsOnSpawn.put(color, weapons);
    }

    public Map<Color, List<WeaponView>> getWeaponsOnSpawn() {
        return weaponsOnSpawn;
    }

    public int getDeathsCounter() {
        return deathsCounter;
    }

    public List<PlayerId> getKillshotTrack() {
        return killshotTrack;
    }
}
