package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
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
    private int minNumberOfTargetPlayers;

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
/*
    public static void main(String[] args) {
        GsonBuilder g = new GsonBuilder();
        g.setPrettyPrinting();
        g.serializeNulls();
        Gson gson = g.create();
        List<Weapon> weaponList = new ArrayList<>();
        File file = new File("src/resources/weapons/");
        File[] files = file.listFiles();
        for (File f : files) {
            System.out.println(f.getPath());

            try {
                weaponList.add(gson.fromJson(new FileReader(f.getPath()), Weapon.class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        for (Weapon w : weaponList)
            for (WeaponMode wm : w.weaponModes)
                if (wm.isTargetRoom())
                    System.out.println(wm.name + wm.getDescription() + w.name);
    }
*/
    public WeaponMode(WeaponMode other) {
        this.name = other.name;
        this.description = other.description;
        this.cost = other.cost;
        this.maxNumberOfTargetPlayers = other.maxNumberOfTargetPlayers;
        this.targetPlayers = other.targetPlayers;
        this.targetSquare = other.targetSquare;
        this.targetRoom = other.targetRoom;
        this.eachTargetInTheSameRoom = other.eachTargetInTheSameRoom;
        this.eachTargetOnTheSameSquare = other.eachTargetOnTheSameSquare;
        this.eachTargetOnDifferentSquares = other.eachTargetOnDifferentSquares;
        this.damageEveryone = other.damageEveryone;
        this.targetVisibleByOtherTarget = other.targetVisibleByOtherTarget;
        this.targetVisibleByShooter = other.targetVisibleByShooter;
        this.cardinalDirectionMode = other.cardinalDirectionMode;
        this.maxTargetDistance = other.maxTargetDistance;
        this.minTargetDistance = other.minTargetDistance;
        this.marks = other.marks;
        this.damage = other.damage;
        this.additionalDamageAvailable = other.additionalDamageAvailable;
        this.maxAdditionalDamagePerPlayer = other.maxAdditionalDamagePerPlayer;
        this.moveTargetBeforeShoot = other.moveTargetBeforeShoot;
        this.moveTargetAfterShoot = other.moveTargetAfterShoot;
        this.moveShooter = other.moveShooter;
        this.maxTargetMove = other.maxTargetMove;
        this.maxShooterMove = other.maxShooterMove;
    }

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

    public int getMinNumberOfTargetPlayers() {
        return minNumberOfTargetPlayers;
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

    public int getDamage(int index) {
        return damage.get(index);
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
/*//TODO:
MOVE
//add something to manage powerglove move effect (go to target square after)
//cyberblade shoot + move + shoot

SHOOT
//add something to manage flamethrower 2 squares in cardinal direction diff damage
//grenade launcher target player and a square
*/
