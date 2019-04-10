package it.polimi.ingsw.model;

import it.polimi.ingsw.model.command.BuyCommand;
import it.polimi.ingsw.model.command.GrabCommand;

import java.util.ArrayList;
import java.util.List;

public class SpawnSquare extends Square {

    static final int MAX_WEAPON = 3;
    private Weapon[] weapons = new Weapon[MAX_WEAPON];
    private ArrayList<PlayerId> spawnPointTrack;

    public SpawnSquare(Connection northConnection, Connection eastConnection, Connection southConnection, Connection westConnection, int row, int col, ArrayList<Player> hostedPlayers, Weapon[] weapons, ArrayList<PlayerId> spawnPointTrack) {
        super(northConnection, eastConnection, southConnection, westConnection, row, col, hostedPlayers);
        this.weapons = weapons;
        this.spawnPointTrack = spawnPointTrack;
    }

    public ArrayList<PlayerId> getSpawnPointTrack() {
        return spawnPointTrack;
    }

    @Override
    public List<GrabCommand> getGrabCommands(Player player) {
        ArrayList<GrabCommand> temp = new ArrayList<>();
        for (int i = 0; i < MAX_WEAPON; i++)
            if (weapons[i] != null)
                temp.add(new BuyCommand(player, weapons[i]));
        return temp;

    }
}
