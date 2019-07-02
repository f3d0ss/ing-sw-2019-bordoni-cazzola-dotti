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

    /**
     * Gets name
     *
     * @return value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets description
     *
     * @return value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets cost
     *
     * @return value of cost
     */
    public Map<Color, Integer> getCost() {
        return Objects.requireNonNullElseGet(cost, LinkedHashMap::new);
    }

    /**
     * Gets maxNumberOfTargetPlayers
     *
     * @return value of maxNumberOfTargetPlayers
     */
    public int getMaxNumberOfTargetPlayers() {
        return maxNumberOfTargetPlayers;
    }

    /**
     * Gets minNumberOfTargetPlayers
     *
     * @return value of minNumberOfTargetPlayers
     */
    public int getMinNumberOfTargetPlayers() {
        return minNumberOfTargetPlayers;
    }

    /**
     * Gets targetPlayers
     *
     * @return value of targetPlayers
     */
    public boolean isTargetPlayers() {
        return targetPlayers;
    }

    /**
     * Gets targetSquare
     *
     * @return value of targetSquare
     */
    public boolean isTargetSquare() {
        return targetSquare;
    }

    /**
     * Gets targetRoom
     *
     * @return value of targetRoom
     */
    public boolean isTargetRoom() {
        return targetRoom;
    }

    /**
     * Gets eachTargetOnDifferentSquares
     *
     * @return value of eachTargetOnDifferentSquares
     */
    public boolean isEachTargetOnDifferentSquares() {
        return eachTargetOnDifferentSquares;
    }

    /**
     * Gets targetVisibleByOtherTarget
     *
     * @return value of targetVisibleByOtherTarget
     */
    public boolean isTargetVisibleByOtherTarget() {
        return targetVisibleByOtherTarget;
    }

    /**
     * Gets targetVisibleByShooter
     *
     * @return value of targetVisibleByShooter
     */
    public boolean isTargetVisibleByShooter() {
        return targetVisibleByShooter;
    }

    /**
     * Gets cardinalDirectionMode
     *
     * @return value of cardinalDirectionMode
     */
    public boolean isCardinalDirectionMode() {
        return cardinalDirectionMode;
    }

    /**
     * Gets maxTargetDistance
     *
     * @return value of maxTargetDistance
     */
    public int getMaxTargetDistance() {
        return maxTargetDistance;
    }

    /**
     * Gets minTargetDistance
     *
     * @return value of minTargetDistance
     */
    public int getMinTargetDistance() {
        return minTargetDistance;
    }

    /**
     * Gets marks
     *
     * @return value of marks
     */
    public int getMarks() {
        return marks;
    }

    /**
     * Gets damage
     *
     * @return value of damage
     */
    public List<Integer> getDamage() {
        return damage;
    }

    /**
     * Gets damage for a specific player or square
     *
     * @param index index
     * @return value of the damage
     */
    int getDamage(int index) {
        return damage.get(index);
    }

    /**
     * Gets additionalDamageAvailable
     *
     * @return value of additionalDamageAvailable
     */
    public int getAdditionalDamageAvailable() {
        return additionalDamageAvailable;
    }

    /**
     * Gets maxAdditionalDamagePerPlayer
     *
     * @return value of maxAdditionalDamagePerPlayer
     */
    public int getMaxAdditionalDamagePerPlayer() {
        return maxAdditionalDamagePerPlayer;
    }

    /**
     * Gets moveTargetBeforeShoot
     *
     * @return value of moveTargetBeforeShoot
     */
    public boolean isMoveTargetBeforeShoot() {
        return moveTargetBeforeShoot;
    }

    /**
     * Gets moveTargetAfterShoot
     *
     * @return value of moveTargetAfterShoot
     */
    public boolean isMoveTargetAfterShoot() {
        return moveTargetAfterShoot;
    }

    /**
     * Gets moveShooter
     *
     * @return value of moveShooter
     */
    public boolean isMoveShooter() {
        return moveShooter;
    }

    /**
     * Gets maxTargetMove
     *
     * @return value of maxTargetMove
     */
    public int getMaxTargetMove() {
        return maxTargetMove;
    }

    /**
     * Gets maxShooterMove
     *
     * @return value of maxShooterMove
     */
    public int getMaxShooterMove() {
        return maxShooterMove;
    }

    /**
     * Gets multiAction
     *
     * @return value of multiAction
     */
    public boolean isMultiAction() {
        return multiAction;
    }

    /**
     * Gets spinner
     *
     * @return value of spinner
     */
    public boolean isSpinner() {
        return spinner;
    }

    /**
     * Gets fragmentingWarHeadMod
     *
     * @return value of fragmentingWarHeadMod
     */
    public boolean isFragmentingWarHeadMod() {
        return fragmentingWarHeadMod;
    }

    /**
     * Gets moveOnTarget
     *
     * @return value of moveOnTarget
     */
    public boolean isMoveOnTarget() {
        return moveOnTarget;
    }

    /**
     * Gets hellionMod
     *
     * @return value of hellionMod
     */
    public boolean isHellionMod() {
        return hellionMod;
    }

    /**
     * This method should be called after loading weapons from json files to decrease some values from 2^31-1
     */
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