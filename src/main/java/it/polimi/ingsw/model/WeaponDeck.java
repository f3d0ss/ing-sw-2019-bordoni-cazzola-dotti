package it.polimi.ingsw.model;

import java.util.Collections;
import java.util.List;

/**
 * Deck of multiple {@link Weapon}
 */
public class WeaponDeck implements Deck {
    private List<Weapon> weapons;

    WeaponDeck(List<Weapon> weaponList) {
        weapons = weaponList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shuffle() {
        Collections.shuffle(weapons);
    }

    /**
     * Gets and remove the first weapon from the deck
     *
     * @return First weapon of the deck
     */
    public Weapon drawWeapon() {
        return weapons.isEmpty() ? null : weapons.remove(0);
    }

    /**
     * Adds a weapon
     *
     * @param weapon weapon to add
     */
    public void add(Weapon weapon) {
        weapons.add(weapon);
    }

    /**
     * @return true if deck is empty
     */
    public boolean isEmpty() {
        return weapons.isEmpty();
    }
}
