package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;

public class WeaponDeck implements Deck {
    private ArrayList<Weapon> weapons;

    public WeaponDeck(ArrayList<Weapon> weapons) {
        this.weapons = weapons;
    }

    @Override
    public void shuffle() {
        Collections.shuffle(weapons);
    }

    public Weapon drawWeapon(){
        return weapons.remove(0);
    }
}
