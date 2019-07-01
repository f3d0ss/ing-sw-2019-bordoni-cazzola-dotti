package it.polimi.ingsw.model;

import it.polimi.ingsw.model.command.GrabCommand;
import it.polimi.ingsw.model.command.SelectBuyingWeaponCommand;
import it.polimi.ingsw.model.playerstate.SelectedAggregateActionState;
import it.polimi.ingsw.view.SpawnSquareView;
import it.polimi.ingsw.view.SquareView;
import it.polimi.ingsw.view.WeaponView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a spawn location
 */
public class SpawnSquare extends Square {
    /**
     * max nuber of weapons per spawn
     */
    public static final int MAX_WEAPON = 3;
    private List<Weapon> weapons;

    public SpawnSquare(Connection northConnection, Connection eastConnection, Connection southConnection, Connection westConnection, int row, int col, Color color) {
        super(northConnection, eastConnection, southConnection, westConnection, row, col, color, "spawn");
        weapons = new ArrayList<>();
    }

    @Override
    protected void update() {
        match.getAllVirtualViews().forEach(viewInterface -> viewInterface.update(getSquareView()));
    }

    @Override
    public List<GrabCommand> getGrabCommands(Player player, SelectedAggregateActionState state) {
        ArrayList<GrabCommand> commands = new ArrayList<>();
        weapons.forEach(weapon -> commands.add(new SelectBuyingWeaponCommand(player, state, weapon, this)));
        return commands;
    }

    @Override
    protected SquareView getSquareView() {
        return new SpawnSquareView(getRow(), getCol(), getConnections(), getColor(), weapons.stream().map(weapon -> new WeaponView(weapon.getName(), weapon.isLoaded())).collect(Collectors.toList()), getHostedPlayers().stream().map(Player::getId).collect(Collectors.toList()));
    }

    /**
     * Removes the weapon from the spawn's shop
     *
     * @param weapon weapon to remove
     */
    public void removeWeapon(Weapon weapon) {
        weapons.remove(weapon);
        update();
    }

    /**
     * Adds the weapon to the spawn's shop
     *
     * @param weapon weapon to add
     */
    public void addWeapon(Weapon weapon) {
        if (weapons.size() >= MAX_WEAPON)
            throw new IllegalStateException();
        weapons.add(weapon);
        update();
    }

    /**
     * @return all weapons on the spawn's shop
     */
    public List<Weapon> getWeapons() {
        return weapons;
    }

    /**
     * @return true if the spawn's shop is not full
     */
    public boolean lackWeapon() {
        return weapons.size() < MAX_WEAPON;
    }
}
