package it.polimi.ingsw.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This class represents a weapon's mode
 */
public class WeaponMode {
    //general parameters
    private String name;
    private String description;
    private Map<Color, Integer> cost;
    private int maxNumberOfTargetPlayers;
    private int minNumberOfTargetPlayers;

    //shooting parameters
    private boolean targetPlayers;
    private boolean targetSquare;
    private boolean targetRoom;
    private boolean eachTargetOnDifferentSquares;
    private boolean targetVisibleByOtherTarget;
    private boolean targetVisibleByShooter;
    private boolean cardinalDirectionMode;
    private int maxTargetDistance;
    private int minTargetDistance;
    private int marks;
    private List<Integer> damage;
    private int additionalDamageAvailable;
    private int maxAdditionalDamagePerPlayer;

    //move parameters
    private boolean moveTargetBeforeShoot;
    private boolean moveTargetAfterShoot;
    private boolean moveShooter;
    private int maxTargetMove;
    private int maxShooterMove;

    //extra param
    private boolean multiAction;
    private boolean spinner;
    private boolean fragmentingWarHeadMod;
    private boolean moveOnTarget;
    private boolean hellionMod;

    public String getName() {
        return name;
    }

    String getDescription() {
        return description;
    }

    public Map<Color, Integer> getCost() {
        return Objects.requireNonNullElseGet(cost, LinkedHashMap::new);
    }

    int getMaxNumberOfTargetPlayers() {
        return maxNumberOfTargetPlayers;
    }

    int getMinNumberOfTargetPlayers() {
        return minNumberOfTargetPlayers;
    }

    boolean isTargetPlayers() {
        return targetPlayers;
    }

    boolean isTargetSquare() {
        return targetSquare;
    }

    boolean isTargetRoom() {
        return targetRoom;
    }

    boolean isEachTargetOnDifferentSquares() {
        return eachTargetOnDifferentSquares;
    }

    boolean isTargetVisibleByOtherTarget() {
        return targetVisibleByOtherTarget;
    }

    boolean isTargetVisibleByShooter() {
        return targetVisibleByShooter;
    }

    public boolean isCardinalDirectionMode() {
        return cardinalDirectionMode;
    }

    int getMaxTargetDistance() {
        return maxTargetDistance;
    }

    int getMinTargetDistance() {
        return minTargetDistance;
    }

    int getMarks() {
        return marks;
    }

    List<Integer> getDamage() {
        return damage;
    }

    int getDamage(int index) {
        return damage.get(index);
    }

    int getAdditionalDamageAvailable() {
        return additionalDamageAvailable;
    }

    int getMaxAdditionalDamagePerPlayer() {
        return maxAdditionalDamagePerPlayer;
    }

    boolean isMoveTargetBeforeShoot() {
        return moveTargetBeforeShoot;
    }

    boolean isMoveTargetAfterShoot() {
        return moveTargetAfterShoot;
    }

    boolean isMoveShooter() {
        return moveShooter;
    }

    int getMaxTargetMove() {
        return maxTargetMove;
    }

    int getMaxShooterMove() {
        return maxShooterMove;
    }

    public boolean isMultiAction() {
        return multiAction;
    }

    public boolean isSpinner() {
        return spinner;
    }

    public boolean isFragmentingWarHeadMod() {
        return fragmentingWarHeadMod;
    }

    public boolean isMoveOnTarget() {
        return moveOnTarget;
    }

    public boolean isHellionMod() {
        return hellionMod;
    }

    void postDeserialization() {
        final int maxSteps = GameBoard.ROWS * GameBoard.COLUMNS - 1;
        if (maxShooterMove > maxSteps)
            maxShooterMove = maxSteps;
        if (maxTargetDistance > maxSteps)
            maxTargetDistance = maxSteps;
        if (maxTargetMove > maxSteps)
            maxTargetDistance = maxSteps;
    }
}