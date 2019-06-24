package it.polimi.ingsw.model;

import it.polimi.ingsw.model.command.GrabCommand;
import it.polimi.ingsw.model.command.SelectBuyingWeaponCommand;
import it.polimi.ingsw.model.playerstate.SelectedAggregateActionState;
import it.polimi.ingsw.view.SpawnSquareView;
import it.polimi.ingsw.view.WeaponView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpawnSquare extends Square {

    public static final int MAX_WEAPON = 3;
    private List<Weapon> weapons;

    public SpawnSquare(Connection northConnection, Connection eastConnection, Connection southConnection, Connection westConnection, int row, int col, Color color) {
        super(northConnection, eastConnection, southConnection, westConnection, row, col, color, "spawn");
        weapons = new ArrayList<>();
    }

    @Override
    protected void update() {
        match.getAllVirtualViews().forEach(viewInterface -> viewInterface.update(new SpawnSquareView(getRow(), getCol(), getConnections(), getColor(), weapons.stream().map(weapon -> new WeaponView(weapon.getName(), weapon.isLoaded())).collect(Collectors.toList()), getHostedPlayers().stream().map(Player::getId).collect(Collectors.toList()))));
    }

    @Override
    public List<GrabCommand> getGrabCommands(Player player, SelectedAggregateActionState state) {
        ArrayList<GrabCommand> commands = new ArrayList<>();
        weapons.forEach(weapon -> commands.add(new SelectBuyingWeaponCommand(player, state, weapon, this)));
        return commands;
    }

    public void removeWeapon(Weapon weapon) {
        weapons.remove(weapon);
        update();
    }

    public void addWeapon(Weapon weapon) {
        if (weapons.size() >= MAX_WEAPON)
            throw new IllegalStateException();
        weapons.add(weapon);
        update();
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public boolean lackWeapon() {
        return weapons.size() < MAX_WEAPON;
    }
}
