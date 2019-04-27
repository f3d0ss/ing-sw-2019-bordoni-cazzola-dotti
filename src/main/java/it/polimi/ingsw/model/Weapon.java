package it.polimi.ingsw.model;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.command.*;
import it.polimi.ingsw.model.playerstate.ChoosingWeaponOptionState;
import it.polimi.ingsw.model.playerstate.ReadyToShootState;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class represents a weapon
 */
public class Weapon {
    @Expose
    private String name;
    @Expose
    private String description;
    @Expose
    private Map<Color, Integer> reloadingCost;
    @Expose
    private Map<Color, Integer> buyCost;
    @Expose
    private List<WeaponMode> weaponModes;
    private int extraMove = 0;
    private boolean extraMoveUsed = false;
    private boolean loaded = true;
    private WeaponMode selectedWeaponMode = null;
    private List<Player> targetPlayers = new ArrayList<>();
    private List<Square> targetSquares = new ArrayList<>();

    public Map<Color, Integer> getWeaponBuyCost() {
        return buyCost == null ? new HashMap<>() : buyCost;
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
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        if (targetPlayers.isEmpty())
            gameboard.getVisibleTargets(shooter, selectedWeaponMode.getMaxTargetDistance(), selectedWeaponMode.getMinTargetDistance()).forEach(player -> possibleCommands.add(new SelectTargetPlayerCommand(state, player)));
        if (targetSquares.isEmpty())
            gameboard.getVisibleSquares(shooter.getPosition(), selectedWeaponMode.getMaxTargetDistance(), selectedWeaponMode.getMinTargetDistance(), false).forEach(square -> possibleCommands.add(new SelectTargetSquareCommand(state, square)));
        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleSelectTargetCommandsTargetRoom(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        //Furnace basic mode
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        if (targetSquares.isEmpty()) //get squares at distance 1 accessible through doors.
            gameboard.getSquareInOtherVisibleRooms(shooter.getPosition()).forEach(square -> possibleCommands.add(new SelectTargetSquareCommand(state, square)));
        else if (targetPlayers.isEmpty())
            gameboard.getRoomSquares(targetSquares.get(0)).forEach(square -> targetPlayers.addAll(square.getHostedPlayers(shooter)));
        return possibleCommands;
    }


    private List<WeaponCommand> getPossibleSelectTargetCommandsTargetPlayersVisibleStandard(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        List<Player> visibleTargets = gameboard.getVisibleTargets(shooter, selectedWeaponMode.getMaxTargetDistance(), selectedWeaponMode.getMinTargetDistance());
        targetPlayers.forEach(targetPlayer -> visibleTargets.stream().filter(newTarget -> targetPlayer.getId().equals(newTarget.getId())).forEachOrdered(visibleTargets::remove));
        if (selectedWeaponMode.isEachTargetOnDifferentSquares()) //shockwave basiceffect
            targetPlayers.forEach(targetPlayer -> visibleTargets.stream().filter(newTarget -> targetPlayer.getPosition().equals(newTarget.getPosition())).forEachOrdered(visibleTargets::remove));
        return visibleTargets.stream().map(player -> new SelectTargetPlayerCommand(state, player)).collect(Collectors.toList());
    }

    private List<WeaponCommand> getPossibleSelectTargetCommandsTargetPlayersVisibleByOtherTarget(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        List<Player> visibleTargets = gameboard.getVisibleTargets(targetPlayers.get(targetPlayers.size() - 1), selectedWeaponMode.getMaxTargetDistance(), selectedWeaponMode.getMinTargetDistance());
        for (Player visibleTarget : visibleTargets) {
            for (Player targetPlayer : targetPlayers) {
                if (visibleTarget.getId().equals(shooter.getId()) || visibleTarget.getId().equals(targetPlayer.getId())) {
                    visibleTargets.remove(visibleTarget);
                }
            }
        }
        visibleTargets.forEach(player -> possibleCommands.add(new SelectTargetPlayerCommand(state, player)));
        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleSelectTargetCommandsTargetPlayersVisible(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        if (targetPlayers.isEmpty()) {
            List<Player> visibleTargets = gameboard.getVisibleTargets(shooter, selectedWeaponMode.getMaxTargetDistance(), selectedWeaponMode.getMinTargetDistance());
            if (selectedWeaponMode.getName().contains("tsunami")) //directly pick targets
                targetPlayers.addAll(visibleTargets);
            visibleTargets.forEach(player -> possibleCommands.add(new SelectTargetPlayerCommand(state, player)));
        } else {
            if (name.equals("Hellion") && targetPlayers.size() == 1) //add other players on the same square (shooter cant be on this square)
                targetPlayers.addAll(targetPlayers.get(0).getPosition().getHostedPlayers(targetPlayers.get(0)));
            else if (selectedWeaponMode.isTargetVisibleByOtherTarget()) { //THOR
                possibleCommands.addAll(getPossibleSelectTargetCommandsTargetPlayersVisibleByOtherTarget(gameboard, shooter, state));
            } else if (!selectedWeaponMode.getName().contains("tsunami")) { //otherweapons
                possibleCommands.addAll(getPossibleSelectTargetCommandsTargetPlayersVisibleStandard(gameboard, shooter, state));
            }
        }
        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleSelectTargetCommandsTargetPlayersCardinalDirection(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        if (selectedWeaponMode.isTargetVisibleByShooter()) {//PowerGlove rocketfistmode
            if (targetPlayers.isEmpty())
                gameboard.getVisibleTargets(shooter, selectedWeaponMode.getMaxTargetDistance(), selectedWeaponMode.getMinTargetDistance()).forEach(player -> possibleCommands.add(new SelectTargetPlayerCommand(state, player)));
            else {
                Square squareInTheSameDirection = gameboard.getThirdSquareInTheSameDirection(shooter.getPosition(), targetPlayers.get(0).getPosition(), false);
                if (squareInTheSameDirection != null)
                    squareInTheSameDirection.getHostedPlayers().forEach(player -> possibleCommands.add(new SelectTargetPlayerCommand(state, player)));
            }
        } else { //RAILGUN
            if (targetPlayers.isEmpty())
                gameboard.getPlayersOnCardinalDirectionSquares(shooter, selectedWeaponMode.getMaxTargetDistance(), selectedWeaponMode.getMinTargetDistance(), true).forEach(player -> possibleCommands.add(new SelectTargetPlayerCommand(state, player)));
            else  //target another player in the same direction
                gameboard.getPlayersInTheSameDirection(shooter, targetPlayers, selectedWeaponMode.getMaxTargetDistance(), selectedWeaponMode.getMinTargetDistance(), true).forEach(player -> possibleCommands.add(new SelectTargetPlayerCommand(state, player)));
        }
        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleSelectTargetCommandsTargetPlayersNotVisible(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        //HeatSeeker, maybe create a method to get all players / non visible in gameboard
        List<Player> allOtherPlayers = gameboard.getOtherPlayersOnReachableSquares(shooter.getPosition(), selectedWeaponMode.getMaxTargetDistance(), shooter);
        allOtherPlayers.removeAll(gameboard.getVisibleTargets(shooter, Integer.MAX_VALUE, 0));
        return allOtherPlayers.stream().map(player -> new SelectTargetPlayerCommand(state, player)).collect(Collectors.toList());
    }

    private List<WeaponCommand> getPossibleSelectTargetCommandsTargetPlayers(GameBoard gameboard, Player shooter, ReadyToShootState state) {
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        //TODO slice n dice (cyberblade) or just consider in getShootCmds??
        if (selectedWeaponMode.isTargetVisibleByShooter() && !selectedWeaponMode.isCardinalDirectionMode())
            possibleCommands.addAll(getPossibleSelectTargetCommandsTargetPlayersVisible(gameboard, shooter, state));
        else if (selectedWeaponMode.isCardinalDirectionMode())
            possibleCommands.addAll(getPossibleSelectTargetCommandsTargetPlayersCardinalDirection(gameboard, shooter, state));
        else if (!selectedWeaponMode.isTargetVisibleByShooter())
            possibleCommands.addAll(getPossibleSelectTargetCommandsTargetPlayersNotVisible(gameboard, shooter, state));
        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleSelectTargetCommandsTargetSquareFlameThrower(GameBoard gameBoard, Player shooter, ReadyToShootState state) {
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        List<Player> possibleTargetPlayers = new ArrayList<>();
        if (targetSquares.size() == 1) {
            Square secondTargetSquare = gameBoard.getThirdSquareInTheSameDirection(shooter.getPosition(), targetSquares.get(0), false);
            if (secondTargetSquare != null) //ask possible 2nd square in the same direction (flameth)
                possibleCommands.add(new SelectTargetSquareCommand(state, secondTargetSquare));
            if (targetPlayers.isEmpty()) {
                if (selectedWeaponMode.getMaxNumberOfTargetPlayers() != 4) { //select max 1 player per square
                    possibleTargetPlayers.addAll(targetSquares.get(0).getHostedPlayers(shooter));
                    possibleTargetPlayers.forEach(player -> possibleCommands.add(new SelectTargetPlayerCommand(state, player)));
                } else
                    targetPlayers.addAll(targetSquares.get(0).getHostedPlayers(shooter));
            }
        } else {
            if (selectedWeaponMode.getMaxNumberOfTargetPlayers() != 4 && targetPlayers.size() == 1) {
                possibleTargetPlayers.addAll(targetSquares.get(1).getHostedPlayers(shooter));
                possibleTargetPlayers.forEach(player -> possibleCommands.add(new SelectTargetPlayerCommand(state, player)));
            } else if (!targetPlayers.containsAll(targetSquares.get(1).getHostedPlayers(shooter)))
                targetPlayers.addAll(targetSquares.get(1).getHostedPlayers(shooter));
        }
        return possibleCommands;
    }

    private List<WeaponCommand> getPossibleSelectTargetCommandsTargetSquareFragmentingWarhead(Player shooter, ReadyToShootState state) {
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        List<Player> possibleTargetPlayers = new ArrayList<>();
        //add other players on the same square
        if (targetPlayers.isEmpty()) { //commands to target 1st player
            targetSquares.stream().map(square -> square.getHostedPlayers(shooter)).forEach(possibleTargetPlayers::addAll);
            possibleTargetPlayers.forEach(player -> possibleCommands.add(new SelectTargetPlayerCommand(state, player)));
        } else if (targetPlayers.size() == 1) //add other players on the square (fragmenting warhead effect)
            targetPlayers.addAll(targetPlayers.get(0).getPosition().getHostedPlayers(new ArrayList<>(Arrays.asList(shooter, targetPlayers.get(0)))));
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
            } else if (targetPlayers.isEmpty()) //Other modes target all players
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
        targetPlayers.add(targetPlayer);
    }

    public void addTargetSquare(Square targetSquare) {
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
        extraMove = selectedWeaponMode.getMaxShooterMove();
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
