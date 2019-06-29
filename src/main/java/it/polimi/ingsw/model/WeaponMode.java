package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

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
    private boolean moveTargetBeforeShoot;//vortex,tractor beam
    private boolean moveTargetAfterShoot;//same direction (Hammer)
    private boolean moveShooter;
    private int maxTargetMove;
    private int maxShooterMove;

    //extra param
    private boolean multiAction;
    private boolean spinner;
    private boolean fragmentingWarHeadMod;
    private boolean moveOnTarget;
    private boolean hellionMod;

    /*public static void main(String[] args) {
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
                wm.postDeserialization();
                if (wm.hellionMod || wm.fragmentingWarHeadMod) {
                    System.out.println(c + " " + w.getName() + " " + wm.name + " " + " " + wm.description + " " + wm.getMaxTargetDistance() + " " + wm.getMinTargetDistance() + " maxtargets" + wm.maxNumberOfTargetPlayers + " min" + wm.getMinNumberOfTargetPlayers());
                    c++;
                }
            }

        for (Weapon weapon : weaponList) {
            if (weapon.getName().contains("ZX"))
                System.out.println(gson.toJson(weapon));
        }
    }*/

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
        /*multiAction = getName().contains("slice and dice");
        spinner = getName().contains("tsunami");
        fragmentingWarHeadMod = getName().contains("fragmenting warhead");
        moveOnTarget = getDescription().contains("oose 1 target on any square exactly 1 move away. Move onto that square and give the target 1 damage and 2 mark")
                || getDescription().contains("ve onto that square. You may deal 2 damage to 1 target there. If you want,");
        hellionMod = getDescription().contains(" see at least 1 move away. Then give 2 mark to that target and everyo")
                || getDescription().contains("see at least 1 move away. Then give 1 mark to that target and everyone else on");*/

    }
}