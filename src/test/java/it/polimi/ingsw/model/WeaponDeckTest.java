package it.polimi.ingsw.model;

import org.junit.Test;

public class WeaponDeckTest {

    @Test
    public void drawWeapon() {
        WeaponDeck deck = new WeaponDeck();
        for (int i = 0; i < 21; i++)
            System.out.println(deck.drawWeapon());
        System.out.println(deck.drawWeapon());
    }
}