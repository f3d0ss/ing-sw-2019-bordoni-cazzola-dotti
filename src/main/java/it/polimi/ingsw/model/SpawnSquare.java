package it.polimi.ingsw.model;

import it.polimi.ingsw.model.command.SelectBuyingWeaponCommand;
import it.polimi.ingsw.model.command.GrabCommand;
import it.polimi.ingsw.model.playerstate.SelectedAggregateActionState;

import java.util.ArrayList;
import java.util.List;

public class SpawnSquare extends Square {

    static final int MAX_WEAPON = 3;
    private ArrayList<Weapon> weapons;
    private ArrayList<PlayerId> spawnPointTrack;

    public SpawnSquare(Connection northConnection, Connection eastConnection, Connection southConnection, Connection westConnection, int row, int col, Color color) {
        super(northConnection, eastConnection, southConnection, westConnection, row, col, color);
        weapons = new ArrayList<>();
        spawnPointTrack = new ArrayList<>();
    }

    public List<PlayerId> getSpawnPointTrack() {
        return spawnPointTrack;
    }

    @Override
    public List<GrabCommand> getGrabCommands(Player player, SelectedAggregateActionState state) {
        ArrayList<GrabCommand> commands = new ArrayList<>();
        weapons.forEach(weapon -> commands.add(new SelectBuyingWeaponCommand(player, state, weapon, this)));
        return commands;
    }

    public void removeWeapon(Weapon weapon) {
        weapons.remove(weapon);
    }

    public void addWeapon(Weapon weapon) {
        if (weapons.size() >= MAX_WEAPON)
            throw new IllegalStateException();
        weapons.add(weapon);
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public boolean lackWeapon() {
        return weapons.size() < MAX_WEAPON;
    }
}
