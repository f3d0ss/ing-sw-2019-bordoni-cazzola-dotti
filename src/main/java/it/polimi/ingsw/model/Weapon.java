package it.polimi.ingsw.model;

import it.polimi.ingsw.model.command.*;
import it.polimi.ingsw.model.playerstate.ChoosingWeaponOptionState;
import it.polimi.ingsw.model.playerstate.ReadyToShootState;

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

    private List<WeaponCommand> getPossibleShootCommands(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        //TODO:
        List<WeaponCommand> possibleCommands = new ArrayList<>();

        //check if mode can move targets before shoot
        if (selectedWeaponMode.isMoveTargetBeforeShoot())
            possibleCommands.addAll(getPossibleShootCommandsTargetCanMoveBeforeShoot(shooter, state));


        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleShootCommandsTargetCanMoveBeforeShoot(Player shooter, ReadyToShootState state) {
        //TODO:
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        List<EffectCommand> effectCommands = new ArrayList<>();
        if (selectedWeaponMode.isTargetSquare()) {
            for (int i = 0; i < targetPlayers.size(); i++) {
                if (selectedWeaponMode.getDamage().size() == 1)
                    effectCommands.add(new EffectCommand(targetPlayers.get(i), selectedWeaponMode.getDamage(0), selectedWeaponMode.getMarks(), targetSquares.get(0), shooter.getId()));
                if (selectedWeaponMode.getDamage().size() > 1)
                    effectCommands.add(new EffectCommand(targetPlayers.get(i), selectedWeaponMode.getDamage(i), selectedWeaponMode.getMarks(), targetSquares.get(0), shooter.getId()));
            }
        }
        possibleCommands.add(new ShootCommand(state, effectCommands, shooter));
        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleSelectTargetCommandsTargetCanMoveBeforeShoot(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        //TODO:
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        if (selectedWeaponMode.isMoveTargetBeforeShoot()) { //check if mode can move targets before shoot
            if (selectedWeaponMode.isTargetSquare()) {
                if (targetSquares.isEmpty()) { //select a square first
                    List<Square> possibleTargetSquares = new ArrayList<>();
                    if (selectedWeaponMode.isTargetVisibleByShooter()) //target square must be visible and with players that can be moved to it
                        possibleTargetSquares.addAll(gameboard.getReachableSquaresWithOtherPlayers(gameboard.getVisibleSquares(shooter.getPosition(), selectedWeaponMode.getMaxTargetDistance(), selectedWeaponMode.getMinTargetDistance(), false), selectedWeaponMode.getMaxTargetMove(), shooter));
                    for (Square possibleTarget : possibleTargetSquares)
                        possibleCommands.add(new SelectTargetSquareCommand(state, possibleTarget));
                } else { //pick target players if not already selected
                    List<Player> otherPlayersOnReachableSquares = gameboard.getOtherPlayersOnReachableSquares(targetSquares.get(0), selectedWeaponMode.getMaxTargetMove(), shooter);
                    for (Player possibleTargetPlayer : otherPlayersOnReachableSquares)
                        if (!targetPlayers.contains(possibleTargetPlayer))
                            possibleCommands.add(new SelectTargetPlayerCommand(state, possibleTargetPlayer));
                }
            }
        }
        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleSelectTargetCommands(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        //TODO:
        List<WeaponCommand> possibleCommands = new ArrayList<>();

        //check if mode can move targets before shoot
        if (selectedWeaponMode.isMoveTargetBeforeShoot())
            possibleCommands.addAll(getPossibleSelectTargetCommandsTargetCanMoveBeforeShoot(gameboard, shooter, state));

        return possibleCommands;
    }

    /**
     * This method returns the possible commands to select targets or to shoot them
     *
     * @param gameboard
     * @param shooter
     * @param state
     * @return
     */
    public List<WeaponCommand> getPossibleCommands(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        //TODO:
        List<WeaponCommand> possibleCommands = new ArrayList<>();

        if (!hasMaximumTargets())
            possibleCommands.addAll(getPossibleSelectTargetCommands(gameboard, shooter, state));
        if (hasSufficientTargets())
            possibleCommands.addAll(getPossibleShootCommands(gameboard, shooter, state));

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

    public void deselectWeaponMode(WeaponMode weaponMode) {
        if (selectedWeaponMode.equals(weaponMode))
            selectedWeaponMode = null;
    }
}
