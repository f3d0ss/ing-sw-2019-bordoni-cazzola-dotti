package it.polimi.ingsw.model;

import it.polimi.ingsw.model.command.*;
import it.polimi.ingsw.model.playerstate.ChoosingWeaponOptionState;
import it.polimi.ingsw.model.playerstate.ReadyToShootState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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

    public List<WeaponMode> getWeaponModes() {
        return weaponModes;
    }

    private List<WeaponCommand> getPossibleShootCommands(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        if (selectedWeaponMode.isMoveTargetBeforeShoot())
            possibleCommands.addAll(getPossibleShootCommandsTargetCanMoveBeforeShoot(shooter, state));
        else if (selectedWeaponMode.isTargetPlayers() && selectedWeaponMode.isTargetSquare())
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
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        if (!targetSquares.isEmpty() && !targetPlayers.isEmpty()) {
            for (Square square : gameboard.getReachableSquare(targetPlayers.get(0).getPosition(), selectedWeaponMode.getMaxTargetMove())) { //generate a ShootCommand for each possible move of the target
                List<EffectCommand> effectCommands = new ArrayList<>();
                if (square.equals(targetSquares.get(0)))//if target player moves on target square
                    effectCommands.add(new EffectCommand(targetPlayers.get(0), selectedWeaponMode.getDamage(0) + selectedWeaponMode.getDamage(1), selectedWeaponMode.getMarks(), square, shooter.getId()));
                else
                    effectCommands.add(new EffectCommand(targetPlayers.get(0), selectedWeaponMode.getDamage(0), selectedWeaponMode.getMarks(), square, shooter.getId()));
                targetSquares.get(0).getHostedPlayers(new ArrayList<>(Arrays.asList(shooter, targetPlayers.get(0)))).forEach(player -> effectCommands.add(new EffectCommand(player, selectedWeaponMode.getDamage(1), selectedWeaponMode.getMarks(), player.getPosition(), shooter.getId()))); //damage players on target square (can be done outside loop)
                possibleCommands.add(new ShootCommand(state, effectCommands, shooter));
            }
        }
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

    private List<WeaponCommand> getPossibleShootCommandsTargetSquareFlameThrower(Player shooter, ReadyToShootState state) {
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        List<EffectCommand> effectCommands = new ArrayList<>();
        if (selectedWeaponMode.getMaxNumberOfTargetPlayers() != 4) {
            for (int i = 0; i < targetPlayers.size(); i++)
                effectCommands.add(new EffectCommand(targetPlayers.get(i), selectedWeaponMode.getDamage(i), selectedWeaponMode.getMarks(), targetPlayers.get(i).getPosition(), shooter.getId()));
        } else {
            for (Player targetPlayer : targetPlayers) {
                if (targetPlayer.getPosition() == targetSquares.get(0))
                    effectCommands.add(new EffectCommand(targetPlayer, selectedWeaponMode.getDamage(0), selectedWeaponMode.getMarks(), targetPlayer.getPosition(), shooter.getId()));
                if (targetPlayer.getPosition() == targetSquares.get(0))
                    effectCommands.add(new EffectCommand(targetPlayer, selectedWeaponMode.getDamage(1), selectedWeaponMode.getMarks(), targetPlayer.getPosition(), shooter.getId()));
            }
        }
        possibleCommands.add(new ShootCommand(state, effectCommands, shooter));
        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleShootCommandsTargetSquareFragmentingWarhead(GameBoard gameBoard, Player shooter, ReadyToShootState state) {
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        for (Square square : gameBoard.getReachableSquare(targetSquares.get(0), selectedWeaponMode.getMaxTargetMove())) {
            List<EffectCommand> effectCommands = new ArrayList<>();
            if (targetPlayers.size() > 1)
                effectCommands = IntStream.range(1, targetPlayers.size()).mapToObj(i -> new EffectCommand(targetPlayers.get(i), selectedWeaponMode.getDamage(i), selectedWeaponMode.getMarks(), targetPlayers.get(i).getPosition(), shooter.getId())).collect(Collectors.toList());
            effectCommands.add(new EffectCommand(targetPlayers.get(0), selectedWeaponMode.getDamage(0) + selectedWeaponMode.getDamage(1), selectedWeaponMode.getMarks(), square, shooter.getId()));
            possibleCommands.add(new ShootCommand(state, effectCommands, shooter));
        }
        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleShootCommandsTargetSquare(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        List<EffectCommand> effectCommands = new ArrayList<>();
        if (selectedWeaponMode.isCardinalDirectionMode()) {
            possibleCommands.addAll(getPossibleShootCommandsTargetSquareFlameThrower(shooter, state));
        } else if (selectedWeaponMode.isMoveTargetAfterShoot()) {
            possibleCommands.addAll(getPossibleShootCommandsTargetSquareFragmentingWarhead(gameboard, shooter, state));
        } else {
            targetPlayers.forEach(player -> effectCommands.add(new EffectCommand(player, selectedWeaponMode.getDamage(0), selectedWeaponMode.getMarks(), player.getPosition(), shooter.getId())));
            possibleCommands.add(new ShootCommand(state, effectCommands, shooter));
        }

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
        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleSelectTargetCommandsMultiTarget(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        //TODO:
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        if (targetPlayers.isEmpty())
            gameboard.getVisibleTargets(shooter, selectedWeaponMode.getMaxTargetDistance(), selectedWeaponMode.getMinTargetDistance()).forEach(player -> possibleCommands.add(new SelectTargetPlayerCommand(state, player)));
        if (targetSquares.isEmpty())
            gameboard.getVisibleSquares(shooter.getPosition(), selectedWeaponMode.getMaxTargetDistance(), selectedWeaponMode.getMinTargetDistance(), false).forEach(square -> possibleCommands.add(new SelectTargetSquareCommand(state, square)));
        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleSelectTargetCommandsTargetRoom(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        //TODO: method that returns a list of the possbile rooms
        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleSelectTargetCommandsTargetPlayers(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        //TODO
        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleSelectTargetCommandsTargetSquareFlameThrower(GameBoard gameBoard, Player shooter, ReadyToShootState state) {
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        List<Player> possibleTargetPlayers = new ArrayList<>();
        if (targetSquares.size() == 1) {
            Square secondTargetSquare = gameBoard.getThirdSquareInTheSameDirection(shooter.getPosition(), targetSquares.get(0));
            if (secondTargetSquare != null) //ask possible 2nd square in the same direction (flameth)
                possibleCommands.add(new SelectTargetSquareCommand(state, secondTargetSquare));
            if (selectedWeaponMode.getMaxNumberOfTargetPlayers() != 4) { //select max 1 player per square
                possibleTargetPlayers.addAll(targetSquares.get(0).getHostedPlayers(shooter));
                possibleTargetPlayers.forEach(player -> possibleCommands.add(new SelectTargetPlayerCommand(state, player)));
            } else
                targetPlayers.addAll(targetSquares.get(0).getHostedPlayers(shooter));
        }
        if (selectedWeaponMode.getMaxNumberOfTargetPlayers() != 4) {
            possibleTargetPlayers.addAll(targetSquares.get(1).getHostedPlayers(shooter));
            possibleTargetPlayers.forEach(player -> possibleCommands.add(new SelectTargetPlayerCommand(state, player)));
        } else
            targetPlayers.addAll(targetSquares.get(1).getHostedPlayers(shooter));
        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleSelectTargetCommandsTargetSquareFragmentingWarhead(Player shooter, ReadyToShootState state) {
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        List<Player> possibleTargetPlayers = new ArrayList<>();
        if (targetPlayers.isEmpty()) { //commands to target 1st player
            targetSquares.stream().map(square -> square.getHostedPlayers(shooter)).forEach(possibleTargetPlayers::addAll);
            possibleTargetPlayers.forEach(player -> possibleCommands.add(new SelectTargetPlayerCommand(state, player)));
        } else { //add other players on the same square
            List<Player> playersToExclude = new ArrayList<>();
            playersToExclude.add(shooter);
            playersToExclude.add(targetPlayers.get(0));
            targetPlayers.addAll(targetPlayers.get(0).getPosition().getHostedPlayers(playersToExclude));
        }
        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleSelectTargetCommandsTargetSquare(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        if (targetSquares.isEmpty()) { //selectSquare(s) first
            List<Square> possibleTargetSquares = new ArrayList<>();
            if (selectedWeaponMode.isCardinalDirectionMode())
                possibleTargetSquares.addAll(gameboard.getVisibleSquares(shooter.getPosition(), selectedWeaponMode.getMinTargetDistance(), selectedWeaponMode.getMinTargetDistance(), true));
            possibleTargetSquares.addAll(gameboard.getVisibleSquares(shooter.getPosition(), selectedWeaponMode.getMaxTargetDistance(), selectedWeaponMode.getMinTargetDistance(), true));
            possibleTargetSquares.forEach(square -> possibleCommands.add(new SelectTargetSquareCommand(state, square)));
        } else {
            if (selectedWeaponMode.isCardinalDirectionMode()) {
                possibleCommands.addAll(getPossibleSelectTargetCommandsTargetSquareFlameThrower(gameboard, shooter, state));
            } else if (selectedWeaponMode.getName().contains("fragmenting warhead")) {//rocketlaunch
                possibleCommands.addAll(getPossibleSelectTargetCommandsTargetSquareFragmentingWarhead(shooter, state));
            } else //Other modes target all players
                targetSquares.stream().map(square -> square.getHostedPlayers(shooter)).forEach(targetPlayers::addAll);
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
        return shooter.getAccessibleSquare(extraMove).stream().map(square -> new MoveCommand(shooter, square, state)).collect(Collectors.toList());
    }

    private boolean hasMaximumTargets() {
        return selectedWeaponMode.getMaxNumberOfTargetPlayers() == targetPlayers.size();
    }

    private boolean hasSufficientTargets() {
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
        return weaponModes.stream().map(weaponMode -> new SelectWeaponModeCommand(player, state, weaponMode)).collect(Collectors.toList());
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
        return false;
    }
}
