package it.polimi.ingsw.model;

import java.util.Collections;
import java.util.List;

public class WeaponDeck implements Deck {
    private List<Weapon> weapons;

    WeaponDeck(List<Weapon> weaponList) {
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

    public boolean isEmpty() {
        return weapons.isEmpty();
    }
}
