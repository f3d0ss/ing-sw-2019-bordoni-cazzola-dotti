package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.PlayerId;

import java.util.List;
import java.util.Map;

public class PlayerView {
    private final PlayerId id;
    private final List<PlayerId> health;
    private final int deaths;
    private final Map<PlayerId, Integer> marks;
    private final String nickname;
    private final List<WeaponView> weapons;
    private final List<PowerUpView> powerUps;
    private final Map<Color, Integer> ammo;
    private final int availableAggregateActionCounter;
    private final boolean flippedBoard;

    public PlayerView(PlayerId id, List<PlayerId> health, int deaths, Map<PlayerId, Integer> marks, String nickname, List<WeaponView> weapons, List<PowerUpView> powerUps, Map<Color, Integer> ammo, int availableAggregateActionCounter, boolean flippedBoard) {
        this.id = id;
        this.health = health;
        this.deaths = deaths;
        this.marks = marks;
        this.nickname = nickname;
        this.weapons = weapons;
        this.powerUps = powerUps;
        this.ammo = ammo;
        this.availableAggregateActionCounter = availableAggregateActionCounter;
        this.flippedBoard = flippedBoard;
    }

    public PlayerId getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public int getDeaths() {
        return deaths;
    }

    public List<WeaponView> getWeapons() {
        return weapons;
    }

    public List<PowerUpView> getPowerUps() {
        return powerUps;
    }

    public Map<Color, Integer> getAmmo() {
        return ammo;
    }

    public List<PlayerId> getHealth() {
        return health;
    }

    public Map<PlayerId, Integer> getMarks() {
        return marks;
    }
}
