package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.PlayerId;

import java.util.List;
import java.util.Map;

/**
 * This class represent an {@link it.polimi.ingsw.model.Player} on view side.
 */
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

    /**
     * Gets the id of the player.
     *
     * @return the id of the player
     */
    public PlayerId getId() {
        return id;
    }

    /**
     * Tells if the player is the client's.
     *
     * @return true if the player is the client's, false if is an enemy of him
     */
    public boolean isMe() {
        return isMe;
    }

    /**
     * Tells if a player is disconnected.
     *
     * @return true if the player is disconnected
     */
    public boolean isDisconnected() {
        return disconnected;
    }

    /**
     * Gets the player's nickname.
     *
     * @return the player's nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Gets the amount of player's deaths.
     *
     * @return the amount of player's deaths
     */
    public int getDeaths() {
        return deaths;
    }

    /**
     * Gets the list of weapons owned.
     *
     * @return the list of weapons owned
     */
    public List<WeaponView> getWeapons() {
        return weapons;
    }

    /**
     * Gets the list of powerups owned.
     *
     * @return the list of powerups owned
     */
    public List<PowerUpView> getPowerUps() {
        return powerUps;
    }

    /**
     * Gets the list of ammos owned, sorted by color.
     *
     * @return the mapping between color and ammos owned
     */
    public Map<Color, Integer> getAmmo() {
        return ammo;
    }

    /**
     * Gets the track of player's damages.
     *
     * @return the track of player's damages
     */
    public List<PlayerId> getHealth() {
        return health;
    }

    /**
     * Gets the track of player's marks.
     *
     * @return the track of player's marks
     */
    public Map<PlayerId, Integer> getMarks() {
        return marks;
    }

    /**
     * Tells if the board has been flipped (in final frenzy turn).
     *
     * @return true if the board has been flipped
     */
    public boolean isFlippedBoard() {
        return flippedBoard;
    }

    /**
     * Tells if the first adrenaline action has been unlocked.
     *
     * @return true if the first adrenaline action has been unlocked
     */
    public boolean isFirstAdrenalina() {
        return firstAdrenalina;
    }

    /**
     * Tells if the second adrenaline action has been unlocked.
     *
     * @return true if the second adrenaline action has been unlocked
     */
    public boolean isSecondAdrenalina() {
        return secondAdrenalina;
    }
}
