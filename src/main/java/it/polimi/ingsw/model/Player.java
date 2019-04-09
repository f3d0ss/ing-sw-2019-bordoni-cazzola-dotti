package it.polimi.ingsw.model;

import it.polimi.ingsw.model.playerstate.PendingPaymentWeaponOptionState;

import java.util.ArrayList;
import java.util.Map;

public class Player {

    private PlayerId id;
    private ArrayList<PlayerId> health;
    private int deaths;
    private ArrayList<PlayerId> marks;
    private int points;
    private String Nickname;
//    private ArrayList<Weapon> weapons;
//    private ArrayList<PowerUp> powerUps;
    private Map<Color, Integer> ammo;
    private boolean disconnetted;
    private int availableAggregateActionCounter;
//    private PlayerState playerState;

    public PlayerId getId() {
        return id;
    }

    public void setId(PlayerId id) {
        this.id = id;
    }

    public void changeState(PendingPaymentWeaponOptionState pendingPaymentWeaponOptionState) {
    }

    public void move(CardinalDirection direction) {
    }
}
