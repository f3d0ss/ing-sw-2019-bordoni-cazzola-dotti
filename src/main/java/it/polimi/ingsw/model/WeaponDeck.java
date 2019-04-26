package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeaponDeck implements Deck {
    private List<Weapon> weapons;

    public WeaponDeck() {
        //TODO: hardcoded weapon creation just for tests
        weapons = new ArrayList<>();
        for (int i = 0; i < 21; i++)
            weapons.add(new Weapon());
    }

    @Override
    public void shuffle() {
        Collections.shuffle(weapons);
    }

    public Weapon drawWeapon() {
        return weapons.isEmpty() ? null : weapons.remove(0);
    }
}
