package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.PlayerId;

import java.util.List;
import java.util.Map;

public class PlayerView {
    private final PlayerId id;
    private final boolean isMe;
    private final List<PlayerId> health;
    private final int deaths;
    private final Map<PlayerId, Integer> marks;
    private final String nickname;
    private final List<WeaponView> weapons;
    private final List<PowerUpView> powerUps;
    private final Map<Color, Integer> ammo;
    private final boolean flippedBoard;
    private final boolean disconnected;
    private final boolean firstAdrenalina;
    private final boolean secondAdrenalina;

    @SuppressWarnings("squid:S00107")
    public PlayerView(PlayerId id, boolean isMe, List<PlayerId> health, int deaths, Map<PlayerId, Integer> marks, String nickname, List<WeaponView> weapons, List<PowerUpView> powerUps, Map<Color, Integer> ammo, boolean flippedBoard, boolean disconnected, boolean firstAdrenalina, boolean secondAdrenalina) {
        this.id = id;
        this.isMe = isMe;
        this.health = health;
        this.deaths = deaths;
        this.marks = marks;
        this.nickname = nickname;
        this.weapons = weapons;
        this.powerUps = powerUps;
        this.ammo = ammo;
        this.flippedBoard = flippedBoard;
        this.disconnected = disconnected;
        this.firstAdrenalina = firstAdrenalina;
        this.secondAdrenalina = secondAdrenalina;
    }

    public PlayerId getId() {
        return id;
    }

    public boolean isMe() {
        return isMe;
    }

    public boolean isDisconnected() {
        return disconnected;
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

    public boolean isFlippedBoard() {
        return flippedBoard;
    }

    public boolean isFirstAdrenalina() {
        return firstAdrenalina;
    }

    public boolean isSecondAdrenalina() {
        return secondAdrenalina;
    }
}
