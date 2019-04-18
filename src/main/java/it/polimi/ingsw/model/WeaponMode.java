package it.polimi.ingsw.model;

import java.util.List;
import java.util.Map;

/**
 * This class represents a weapon's mode
 */
public class WeaponMode {
    //general parameters
    //flamethrower,grenadelauncher?
    private String name;
    private String description;
    private Map<Color, Integer> cost;
    private int maxNumberOfTargetPlayers;

    //shooting parameters
    private boolean targetPlayers;
    private boolean targetSquare;
    private boolean targetRoom;
    private boolean eachTargetInTheSameRoom;
    private boolean eachTargetOnTheSameSquare;
    private boolean eachTargetOnDifferentSquares;//shockwave
    private boolean damageEveryone; //The weapon must damage every target possible, the player can't decide not to shoot one target.
    private boolean targetVisibleByOtherTarget;//thor
    private boolean targetVisibleByShooter;
    private boolean cardinalDirectionMode;
    private int maxTargetDistance;
    private int minTargetDistance;
    private int marks;
    private List<Integer> damage;
    private int additionalDamageAvailable;
    private int maxAdditionalDamagePerPlayer;

    //move parameters
    private boolean moveTargetBeforeShoot;//vortex,tractor beam
    private boolean moveTargetAfterShoot;//same direction (Hammer)
    private boolean moveShooter;
    private int maxTargetMove;
    private int maxShooterMove;

    /*public static void main(String[] args) {
        GsonBuilder g = new GsonBuilder();
        g.setPrettyPrinting();
        g.serializeNulls();
        Gson gson = g.create();
        Weapon lock = null;
        try {
            lock = gson.fromJson(new FileReader("src/resources/weapons/Flamethrower.json"), Weapon.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(lock.getReloadingCost().toString() +
                lock.getWeaponBuyCost() +
                lock.isLoaded());
        System.out.println(gson.toJson(lock));
    }*/

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Map<Color, Integer> getCost() {
        return cost;
    }

    public int getMaxNumberOfTargetPlayers() {
        return maxNumberOfTargetPlayers;
    }

    public boolean isTargetPlayers() {
        return targetPlayers;
    }

    public boolean isTargetSquare() {
        return targetSquare;
    }

    public boolean isTargetRoom() {
        return targetRoom;
    }

    public boolean isEachTargetInTheSameRoom() {
        return eachTargetInTheSameRoom;
    }

    public boolean isEachTargetOnTheSameSquare() {
        return eachTargetOnTheSameSquare;
    }

    public boolean isEachTargetOnDifferentSquares() {
        return eachTargetOnDifferentSquares;
    }

    public boolean isDamageEveryone() {
        return damageEveryone;
    }

    public boolean isTargetVisibleByOtherTarget() {
        return targetVisibleByOtherTarget;
    }

    public boolean isTargetVisibleByShooter() {
        return targetVisibleByShooter;
    }

    public boolean isCardinalDirectionMode() {
        return cardinalDirectionMode;
    }

    public int getMaxTargetDistance() {
        return maxTargetDistance;
    }

    public int getMinTargetDistance() {
        return minTargetDistance;
    }

    public int getMarks() {
        return marks;
    }

    public List<Integer> getDamage() {
        return damage;
    }

    public int getAdditionalDamageAvailable() {
        return additionalDamageAvailable;
    }

    public int getMaxAdditionalDamagePerPlayer() {
        return maxAdditionalDamagePerPlayer;
    }

    public boolean isMoveTargetBeforeShoot() {
        return moveTargetBeforeShoot;
    }

    public boolean isMoveTargetAfterShoot() {
        return moveTargetAfterShoot;
    }

    public boolean isMoveShooter() {
        return moveShooter;
    }

    public int getMaxTargetMove() {
        return maxTargetMove;
    }

    public int getMaxShooterMove() {
        return maxShooterMove;
    }
}
