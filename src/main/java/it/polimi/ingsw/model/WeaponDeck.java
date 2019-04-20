package it.polimi.ingsw.model;

import java.util.Collections;
import java.util.List;

public class WeaponDeck implements Deck {
    private List<Weapon> weapons;

    public WeaponDeck(List<Weapon> weapons) {
        this.weapons = weapons;
    }

    @Override
    public void shuffle() {
        Collections.shuffle(weapons);
    }

    public Weapon drawWeapon() {
        return weapons.isEmpty() ? null : weapons.remove(0);
    }
}
