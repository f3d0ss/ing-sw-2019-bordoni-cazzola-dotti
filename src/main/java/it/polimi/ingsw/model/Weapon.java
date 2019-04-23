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
    public String name;
    private String description;
    private Map<Color, Integer> reloadingCost;
    private Map<Color, Integer> buyCost;
    public List<WeaponMode> weaponModes;
    private transient int extraMove = 0;
    private transient boolean extraMoveUsed = false;
    private transient boolean loaded = true;
    private transient WeaponMode selectedWeaponMode = null;
    private transient List<Player> targetPlayers = new ArrayList<>();
    private transient List<Square> targetSquares = new ArrayList<>();
    private transient boolean damageToDo = false;

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
        this.selectedWeaponMode = new WeaponMode(selectedWeaponMode); //remove copy constructor?
        if (selectedWeaponMode.isMoveShooter()) { //set extraMove
            extraMove = selectedWeaponMode.getMaxShooterMove();
            extraMoveUsed = false;
        }
    }

    public void deselectWeaponMode() {
        selectedWeaponMode = null;
    }

    private List<WeaponCommand> getPossibleShootCommands(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        if (selectedWeaponMode.isMoveTargetBeforeShoot())
            possibleCommands.addAll(getPossibleShootCommandsTargetCanMoveBeforeShoot(shooter, state));
        else if (selectedWeaponMode.isTargetPlayers() && getSelectedWeaponMode().isTargetSquare())
            possibleCommands.addAll(getPossibleShootCommandsMultiTarget(gameboard, shooter, state));
        else if (selectedWeaponMode.isTargetSquare())
            possibleCommands.addAll(getPossibleShootCommandsTargetSquare(gameboard, shooter, state));
        else if (selectedWeaponMode.isTargetPlayers())
            possibleCommands.addAll(getPossibleShootCommandsTargetPlayers(gameboard, shooter, state));
        else if (selectedWeaponMode.isTargetRoom())
            possibleCommands.addAll(getPossibleShootCommandsTargetRoom(gameboard, shooter, state));
        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleShootCommandsMultiTarget(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        //TODO:
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleShootCommandsTargetRoom(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        //TODO:
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleShootCommandsTargetPlayers(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        //TODO:
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleShootCommandsTargetSquare(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        //TODO:
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleShootCommandsTargetCanMoveBeforeShoot(Player shooter, ReadyToShootState state) {
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

    private List<WeaponCommand> getPossibleSelectTargetCommands(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        if (selectedWeaponMode.isMoveTargetBeforeShoot())
            possibleCommands.addAll(getPossibleSelectTargetCommandsTargetCanMoveBeforeShoot(gameboard, shooter, state));
        else if (selectedWeaponMode.isTargetPlayers() && selectedWeaponMode.isTargetSquare())
            possibleCommands.addAll(getPossibleSelectTargetCommandsMultiTarget(gameboard, shooter, state));
        else if (selectedWeaponMode.isTargetSquare())
            possibleCommands.addAll(getPossibleSelectTargetCommandsTargetSquare(gameboard, shooter, state));
        else if (selectedWeaponMode.isTargetPlayers())
            possibleCommands.addAll(getPossibleSelectTargetCommandsTargetPlayers(gameboard, shooter, state));
        else if (selectedWeaponMode.isTargetRoom())
            possibleCommands.addAll(getPossibleSelectTargetCommandsTargetRoom(gameboard, shooter, state));
        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleSelectTargetCommandsTargetCanMoveBeforeShoot(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        if (selectedWeaponMode.isMoveTargetBeforeShoot()) { //check if mode can move targets before shoot
            if (selectedWeaponMode.isTargetSquare()) {
                if (targetSquares.isEmpty()) { //select a square (first call)
                    List<Square> possibleTargetSquares = new ArrayList<>();
                    if (selectedWeaponMode.isTargetVisibleByShooter()) //target square must be visible and with players that can be moved to it
                        possibleTargetSquares.addAll(gameboard.getReachableSquaresWithOtherPlayers(gameboard.getVisibleSquares(shooter.getPosition(), selectedWeaponMode.getMaxTargetDistance(), selectedWeaponMode.getMinTargetDistance(), false), selectedWeaponMode.getMaxTargetMove(), shooter));
                    for (Square possibleTarget : possibleTargetSquares)
                        possibleCommands.add(new SelectTargetSquareCommand(state, possibleTarget));
                } else { //pick target players if not already selected (second call)
                    List<Player> otherPlayersOnReachableSquares = gameboard.getOtherPlayersOnReachableSquares(targetSquares.get(0), selectedWeaponMode.getMaxTargetMove(), shooter);
                    for (Player possibleTargetPlayer : otherPlayersOnReachableSquares)
                        if (!targetPlayers.contains(possibleTargetPlayer))
                            possibleCommands.add(new SelectTargetPlayerCommand(state, possibleTargetPlayer));
                }
            }
        }
        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleSelectTargetCommandsMultiTarget(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        //TODO:
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleSelectTargetCommandsTargetRoom(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        //TODO: method that returns a list of the possbile rooms (a square for each room)
        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleSelectTargetCommandsTargetPlayers(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        //TODO
        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleSelectTargetCommandsTargetSquare(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        //TODO Flamethrower 2 squares (optional), rocketlauncher select 1st targetPlayer, others select all player on square
        if (targetSquares.isEmpty()) { //selectSquare(s) first
            List<Square> possibleTargetSquares = new ArrayList<>();
            possibleTargetSquares.addAll(gameboard.getVisibleSquares(shooter.getPosition(), selectedWeaponMode.getMaxTargetDistance(), selectedWeaponMode.getMinTargetDistance(), true));
            possibleTargetSquares.forEach(square -> possibleCommands.add(new SelectTargetSquareCommand(state, square)));
        } else {
            List<Player> possibleTargetPlayers = new ArrayList<>();
            targetSquares.stream().map(square -> square.getHostedPlayers(shooter)).forEach(possibleTargetPlayers::addAll);
            possibleTargetPlayers.forEach(player -> possibleCommands.add(new SelectTargetPlayerCommand(state, player)));
        }
        return possibleCommands;
    }

    /**
     * This method returns the possible commands
     *
     * @param gameboard
     * @param shooter
     * @param state
     * @return
     */
    public List<Command> getPossibleCommands(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        List<Command> possibleCommands = new ArrayList<>();
        if (hasExtraMove())
            possibleCommands.addAll(getPossibleExtraMoveCommands(shooter, state));
        if (!hasMaximumTargets())
            possibleCommands.addAll(getPossibleSelectTargetCommands(gameboard, shooter, state));
        if (hasSufficientTargets())
            possibleCommands.addAll(getPossibleShootCommands(gameboard, shooter, state));
        return possibleCommands;

    }

    private List<MoveCommand> getPossibleExtraMoveCommands(Player shooter, ReadyToShootState state) {
        List<MoveCommand> possibleCommands = new ArrayList<>();
        shooter.getAccessibleSquare(extraMove).forEach(square -> possibleCommands.add(new MoveCommand(shooter, square, state)));
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
        //if (targetPlayers.contains(targetPlayer) || hasMaximumTargets() )
        //  throw new IllegalArgumentException();
        //else
        targetPlayers.add(targetPlayer);
    }

    public void addTargetSquare(Square targetSquare) {
        // if (targetSquares.contains(targetSquare) || selectedWeaponMode.isCardinalDirectionMode() && !targetSquares.isEmpty() && (targetSquare.getCol() != targetSquares.get(0).getCol() || targetSquare.getRow() != targetSquares.get(0).getRow()))
        //   throw new IllegalArgumentException();
        //else
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
        selectedWeaponMode.getMaxShooterMove();
    }

    public void deselectWeaponMode(WeaponMode weaponMode) {
        if (selectedWeaponMode.equals(weaponMode))
            selectedWeaponMode = null;
    }

    public boolean hasDamageToDo() {
//        TODO: return if player can shoot again
        return damageToDo;
    }
}
