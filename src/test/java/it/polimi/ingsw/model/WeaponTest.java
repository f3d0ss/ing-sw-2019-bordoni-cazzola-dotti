package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

public class WeaponTest {
    List<Weapon> allWeapons;

    private List<Weapon> getAllWeapons() {
        GsonBuilder g = new GsonBuilder();
        g.setPrettyPrinting();
        g.serializeNulls();
        Gson gson = g.create();
        List<Weapon> weaponList = new ArrayList<>();
        File file = new File("src/resources/weapons/");
        File[] files = file.listFiles();
        for (File f : files) {
            try {
                weaponList.add(gson.fromJson(new FileReader(f.getPath()), Weapon.class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return weaponList;
    }

    Weapon getRandomWeapon() {
        return allWeapons.get(new Random().nextInt(allWeapons.size()));
    }

    @BeforeEach
    void setUp() {
        allWeapons = getAllWeapons();
    }

    @Test
    void getWeaponCosts() {
        for (Weapon weapon : allWeapons) {
            Map<Color, Integer> weaponBuyCost = weapon.getWeaponBuyCost();
            Map<Color, Integer> reloadingCost = weapon.getReloadingCost();
            assertTrue(reloadingCost.size() > 0);
            if (weaponBuyCost.size() > 0)
                assertTrue(reloadingCost.size() >= weaponBuyCost.size());
        }
    }

    @Test
    void deselectWeaponMode() {
        Weapon weapon = getRandomWeapon();
        weapon.deselectWeaponMode();
        assertTrue(weapon.getSelectedWeaponMode() == null);
    }

    @Test
    void getWeaponModes() {
        Weapon weapon = getRandomWeapon();
        assertFalse(weapon.getWeaponModes().isEmpty());
    }

    @Test
    void addRemoveTargetPlayer() {
        Weapon weapon = getRandomWeapon();
        Player player = new Player(null, null, null);
        weapon.addTargetPlayer(player);
        weapon.removeTargetPlayer(player);
    }

    @Test
    void addRemoveTargetSquare() {
        Square square = new TurretSquare(null, null, null, null, 0, 0, null);
        Weapon weapon = getRandomWeapon();
        weapon.addTargetSquare(square);
        weapon.removeTargetSquare(square);
    }

    @Test
    void reload() {
        Weapon weapon = getRandomWeapon();
        weapon.reload();
        assertTrue(weapon.isLoaded());
    }

    @Test
    void unload() {
        Weapon weapon = getRandomWeapon();
        weapon.unload();
        assertFalse(weapon.isLoaded());
    }

    @Test
    void extraMoves() {
        Weapon weapon = getRandomWeapon();
        if (weapon.hasExtraMove()) {
            weapon.useExtraMoves();
            assertFalse(weapon.hasExtraMove());
        }
    }

    @Test
    void getPossibleCommands() {
    }

    @Test
    void getSelectWeaponModeCommands() {
    }
}
