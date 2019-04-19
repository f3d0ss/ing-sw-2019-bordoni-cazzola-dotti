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
    private transient List<Player> targetPlayers = new ArrayList<>();
    private transient List<Square> targetSquares = new ArrayList<>();

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
        this.selectedWeaponMode = new WeaponMode(selectedWeaponMode);
    }

    public void deselectWeaponMode() {
        selectedWeaponMode = null;
    }

    private List<WeaponCommand> getPossibleShootCommands(GameBoard gameboard, Player player) {
        //TODO:
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleSelectTargetCommands(GameBoard gameboard, Player shooter) {
        //TODO:
        List<WeaponCommand> possibleCommands = new ArrayList<>();

        //check if mode can move targets before shoot
        if (selectedWeaponMode.isMoveTargetBeforeShoot()) {
            if (selectedWeaponMode.isTargetSquare()) { //select a square first
                if (selectedWeaponMode.isTargetVisibleByShooter()) {//visible by shooter
                    ArrayList<Square> visibleSquares = gameboard.getVisibleSquares(shooter.getPosition(), selectedWeaponMode.getMaxTargetDistance(), selectedWeaponMode.getMinTargetDistance(), false);
                    for (Square square : visibleSquares){
                       if( gameboard.get ){

                       }

                    }
                }
            }
        }

        return possibleCommands;
    }


    public List<WeaponCommand> getPossibleCommands(GameBoard gameboard, Player player) {
        //TODO:
        List<WeaponCommand> possibleCommands = new ArrayList<>();

        if (!hasMaximumTargets())
            possibleCommands.addAll(getPossibleSelectTargetCommands());
        if (hasSufficientTargets())
            possibleCommands.addAll(getPossibleShootCommands());

        return possibleCommands;

    }

    private boolean hasMaximumTargets() {
        //TODO:
        return selectedWeaponMode.getMaxNumberOfTargetPlayers() == targetPlayers.size();
    }

    private boolean hasSufficientTargets() {
        //TODO:
        return selectedWeaponMode.getMinNumberOfTargetPlayers() <= targetPlayers.size();
    }

    /**
     * This method returns the commands to select which weapon mode to use.
     *
     * @param player
     * @param state
     * @return List of the commands
     */
    public List<SelectWeaponModeCommand> getSelectWeaponModeCommands(Player player, ChoosingWeaponOptionState state) {
        List<SelectWeaponModeCommand> selectWeaponModeCommandList = new ArrayList<>();
        for (WeaponMode weaponMode : weaponModes)
            selectWeaponModeCommandList.add(new SelectWeaponModeCommand(player, state, weaponMode));
        return selectWeaponModeCommandList;
    }

    public void addTargetPlayer(Player targetPlayer) {
        if (!targetPlayers.contains(targetPlayer))
            targetPlayers.add(targetPlayer);
    }

    public void addTargetSquare(Square targetSquare) {
        if (!targetSquares.contains(targetSquare))
            targetSquares.add(targetSquare);
    }

    public void removeTargetPlayer(Player targetPlayer) {
        targetPlayers.remove(targetPlayer);
    }

    public void removeTargetSquare(Square targetSquare) {
        targetSquares.remove(targetSquare);
    }

    public void reload() {
        loaded = true;
    }

    public void unload() {
        loaded = false;
    }

    public boolean hasExtraMove() {
        return !extraMoveUsed && extraMove != 0;
    }

    public void useExtraMoves() {
        extraMoveUsed = true;
    }

    public void resetMoves() {
        extraMoveUsed = false;
    }
}
