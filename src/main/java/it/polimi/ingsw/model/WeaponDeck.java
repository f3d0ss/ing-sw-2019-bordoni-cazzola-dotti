package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeaponDeck implements Deck {
    private List<Weapon> weapons = new ArrayList<>();

    /*public WeaponDeck() {
        //TODO: hardcoded weapon creation just for tests
        weapons = new ArrayList<>();
        for (int i = 0; i < 21; i++)
            weapons.add(new Weapon());
    }*/

    public WeaponDeck(List<Weapon> weaponList) {
        weapons = weaponList;
    }

    @Override
    public void shuffle() {
        Collections.shuffle(weapons);
    }

    public Weapon drawWeapon() {
        return weapons.isEmpty() ? null : weapons.remove(0);
    }

    public void add(Weapon weapon) {
        weapons.add(weapon);
    }

    public void remove(Weapon weapon) {
        weapons.remove(weapon);
    }
}
