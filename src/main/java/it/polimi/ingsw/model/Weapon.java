package it.polimi.ingsw.model;

import it.polimi.ingsw.model.command.SelectWeaponModeCommand;
import it.polimi.ingsw.model.command.WeaponCommand;
import it.polimi.ingsw.model.playerstate.ChoosingWeaponOptionState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class represents a weapon
 */
public class Weapon {
    private String name;
    private String description;
    private Map<Color, Integer> reloadingCost;
    private Map<Color, Integer> buyCost;
    private List<WeaponMode> weaponModes;
    private transient int extraMove = 0;
    private transient boolean extraMoveUsed = false;
    private transient boolean loaded = true;
    private transient WeaponMode selectedWeaponMode = null;

    public Map<Color, Integer> getWeaponBuyCost() {
        return buyCost;
    }

    public Map<Color, Integer> getReloadingCost() {
        return reloadingCost;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public int getExtraMove() {
        return extraMove;
    }

    public WeaponMode getSelectedWeaponMode() {
        return selectedWeaponMode;
    }

    public void setSelectedWeaponMode(WeaponMode selectedWeaponMode) {
        this.selectedWeaponMode = selectedWeaponMode;
    }

    public List<WeaponCommand> getPossibleCommands(GameBoard gameboard, Player player) {
        //TODO:
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        return possibleCommands;
    }

    /**
     * This method returns the commands to select which weapon mode to use.
     *
     * @param player
     * @param state
     * @return List of the commands
     */
    public List<SelectWeaponModeCommand> getSelectOptionCommands(Player player, ChoosingWeaponOptionState state) {
        //TODO:
        List<SelectWeaponModeCommand> selectWeaponModeCommandList = new ArrayList<>();
        for (WeaponMode weaponMode : weaponModes)
            selectWeaponModeCommandList.add(new SelectWeaponModeCommand(player, state, weaponMode));
        return selectWeaponModeCommandList;
    }

    public void addTargetPlayer(Player targetPlayer) {
        //TODO:
    }

    public void addTargetSquare(Square targetSquare) {
        //TODO:
    }

    public void removeTargetPlayer(Player targetPlayer) {
        //TODO:
    }

    public void removeTargetSquare(Square targetSquare) {
        //TODO:
    }

    public void reload() {
        //TODO:
    }

    public void unload() {
        //TODO:
    }

    public boolean hasExtraMove() {
        return !extraMoveUsed && extraMove != 0;
    }

    public void useExtraMoves() {
        //TODO:
    }

    public void resetMoves() {
        //TODO:
    }

    public void deselectWeaponMode(WeaponMode weaponMode) {
        //TODO:
    }
}
