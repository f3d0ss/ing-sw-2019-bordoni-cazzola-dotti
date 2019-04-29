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
    private String name;
    private String description;
    private Map<Color, Integer> cost;
    private int maxNumberOfTargetPlayers;
    private int minNumberOfTargetPlayers;

    //shooting parameters
    private boolean targetPlayers;
    private boolean targetSquare;
    private boolean targetRoom;
    private boolean eachTargetInTheSameRoom; //(RM?)
    private boolean eachTargetOnTheSameSquare; //(RM?)
    private boolean eachTargetOnDifferentSquares;//shockwave
    private boolean damageEveryone; //The weapon must damage every target possible, the player can't decide not to shoot one target.(RM?)
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

   /* public static void main(String[] args) {
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
        int c = 1;
        for (Weapon w : weaponList)
            for (WeaponMode wm : w.getWeaponModes()) {
                if (!wm.isMoveTargetBeforeShoot() && !wm.isTargetSquare() && wm.isTargetPlayers() && wm.isTargetVisibleByShooter() && !wm.isCardinalDirectionMode()) {
                    if (wm.moveTargetAfterShoot)
                        System.out.println(c + " " + w.getName() + " " + wm.name + " " + " " + wm.description + " " + wm.getMaxTargetDistance() + " " + wm.getMinTargetDistance() + " maxtargets" + wm.maxNumberOfTargetPlayers + " min" + wm.getMinNumberOfTargetPlayers());
                    c++;
                }
            }
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