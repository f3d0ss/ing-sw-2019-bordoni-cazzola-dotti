package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

public class WeaponMode {
    //general parameters
    //flamethrower,grenadelauncher?
    private String name;
    private String description;
    private Map<Color, Integer> cost;
    //private boolean needOtherEffect;
    private int maxNumberOfTargetPlayers;

    //shooting parameters
    private boolean targetPlayers;
    private boolean targetSquare;
    private boolean targetRoom;
    private boolean eachTargetInTheSameRoom;
    private boolean eachTargetOnTheSameSquare;
    private boolean eachTargetOnDifferentSquares;//shockwave
    private boolean damageEveryone; //must damage every player that can be damaged.
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
}
