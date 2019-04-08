package it.polimi.ingsw;

import java.util.ArrayList;

public class SpawnSquare extends Square {

    final int MAX_WEAPON = 3;
//    private Weapon[] weapons = new Weapon[MAX_WEAPON];
    private ArrayList<PlayerId> spawnPointTrack = new ArrayList<>();

    public ArrayList<PlayerId> getSpawnPointTrack() {
        return spawnPointTrack;
    }

    public void setSpawnPointTrack(ArrayList<PlayerId> spawnPointTrack) {
        this.spawnPointTrack = spawnPointTrack;
    }
}
