package it.polimi.ingsw.model;

import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.RespawnCommand;
import it.polimi.ingsw.model.playerstate.IdleState;
import it.polimi.ingsw.model.playerstate.ManageTurnState;
import it.polimi.ingsw.model.playerstate.PlayerState;
import it.polimi.ingsw.view.PlayerView;
import it.polimi.ingsw.view.PowerUpView;
import it.polimi.ingsw.view.WeaponView;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Contains all player's data
 */
public class Player {

    public static final int MAX_DAMAGE = 12;
    public static final int MAX_WEAPONS = 3;
    static final int INITIAL_AMMO_NUMBER = 1;
    private static final int MAX_AMMO = 3;
    private static final int MAX_POWERUP = 3;
    private static final int MAX_MARKS = 3;
    private static final int DAMAGE_BEFORE_FIRST_ADRENALINA = 2;
    private static final int DAMAGE_BEFORE_SECOND_ADRENALINA = 5;
    private static final int DAMAGE_BEFORE_DEAD = 10;
    private final Match match;
    private final PlayerId id;
    private final String nickname;
    private int points = 0;
    private boolean disconnected = false;
    private PlayerState playerState = new IdleState();
    private Square position;
    private List<PlayerId> health = new ArrayList<>();
    private int deaths = 0;
    private Map<PlayerId, Integer> marks = new EnumMap<>(PlayerId.class);
    private List<Weapon> weapons = new ArrayList<>();
    private List<PowerUp> powerUps = new ArrayList<>();
    private Map<Color, Integer> ammo = new EnumMap<>(Color.class);
    private int availableAggregateActionCounter;
    private PlayerId lastShooter;
    private boolean flippedBoard = false;

    public Player(Match match, PlayerId id, String nickname) {
        this.match = match;
        this.id = id;
        this.nickname = nickname;
        for (Color c : Color.values()) {
            ammo.put(c, INITIAL_AMMO_NUMBER);
        }
    }

    public void update() {
        match.getVirtualViews().forEach((playerId, view) -> view.update(getPlayerView(id == playerId)));

    }

    PlayerView getPlayerView(boolean isMe) {
        List<WeaponView> wvs = new ArrayList<>();
        List<PowerUpView> pvs = new ArrayList<>();
        weapons.forEach(weapon -> wvs.add(new WeaponView(weapon.getName(), weapon.isLoaded())));
        powerUps.forEach(powerUp -> pvs.add(new PowerUpView(powerUp.getType(), powerUp.getColor())));
        return new PlayerView(id, isMe, health, deaths, marks, nickname, wvs, pvs, ammo, availableAggregateActionCounter, flippedBoard, disconnected, health.size() > DAMAGE_BEFORE_FIRST_ADRENALINA, health.size() > DAMAGE_BEFORE_SECOND_ADRENALINA);
    }

    public Map<Color, Integer> getAmmo() {
        return ammo;
    }

    public List<PowerUp> getPowerUps() {
        return powerUps;
    }

    public List<Weapon> getWeapons() {
        return this.weapons;
    }

    public PlayerId getId() {
        return id;
    }

    public boolean isDead() {
        return health.size() > DAMAGE_BEFORE_DEAD;
    }

    public String toString() {
        return nickname;
    }

    public List<PlayerId> getHealth() {
        return health;
    }

    public Map<PlayerId, Integer> getMarks() {
        return marks;
    }

    public void changeState(PlayerState playerState) {
        this.playerState = playerState;
    }

    /**
     * This method moves the player without notifying observers
     *
     * @param square Square the player is moving into
     */
    void untracedMove(Square square) {
        position.untracedRemovePlayer(this);
        position = square;
        position.untracedAddPlayer(this);
    }

    public void move(Square square) {
        position.removePlayer(this);
        position = square;
        position.addPlayer(this);
    }

    private void addAmmo(Color color, Integer number) {
        int newAmmoNumber = this.ammo.getOrDefault(color, 0) + number;
        this.ammo.put(color, newAmmoNumber < MAX_AMMO ? newAmmoNumber : MAX_AMMO);
    }

    public void pay(PowerUp powerUp) {
        powerUps.remove(powerUp);
        match.discard(powerUp);
        update();
    }

    private void addPowerUp(int number) {
        while (number > 0) {
            if (this.powerUps.size() >= MAX_POWERUP)
                return;
            this.powerUps.add(match.drawPowerUpCard());
            number--;
        }
    }

    public void addAmmoTile(AmmoTile ammoTile) {
        ammoTile.getAmmo().forEach(this::addAmmo);
        addPowerUp(ammoTile.getPowerUp());
        update();
    }

    /**
     * This method adds damage without considering marks (Must be called for damages in the same action) (ex. Targeting Scope powerup)
     *
     * @param damage ammount of damage
     * @param color  shooter ID
     */
    public void addDamageSameAction(int damage, PlayerId color) {
        setDamage(damage, color);
        lastShooter = color;
        update();
    }

    public void addDamage(int damage, PlayerId color) {
        int possibleDamage = setDamage(damage, color);
        for (int i = marks.getOrDefault(color, 0); i > 0; i--) {
            if (possibleDamage <= 0)
                break;
            this.health.add(color);
            possibleDamage--;
        }
        marks.put(color, 0);
        lastShooter = color;
        update();
    }

    private int setDamage(int damage, PlayerId color) {
        int possibleDamage = MAX_DAMAGE - health.size();
        for (int i = damage; i > 0; i--) {
            if (possibleDamage <= 0)
                break;
            this.health.add(color);
            possibleDamage--;
        }
        return possibleDamage;
    }

    public void addMarks(int marks, PlayerId color) {
        int possibleMarks = MAX_MARKS - this.marks.getOrDefault(color, 0);
        for (int i = marks; i > 0; i--) {
            if (possibleMarks <= 0)
                return;
            this.marks.put(color, this.marks.getOrDefault(color, 0) + 1);
            possibleMarks--;
        }
        update();
    }

    public Square getPosition() {
        return position;
    }

    public void respawn(Color color) {
        if (position != null)
            position.removePlayer(this);
        position = match.getBoard().getSpawn(color);
        position.addPlayer(this);
        health = new ArrayList<>();
        if (match.isLastTurn())
            flippedBoard = true;
        update();
    }

    public List<Command> getPossibleCommands() {
        return playerState.getPossibleCommands(this);
    }

    public List<RespawnCommand> getRespawnCommands() {
        List<RespawnCommand> respawnCommands = new ArrayList<>();
        addExtraPowerUp();
        powerUps.forEach(powerUp -> respawnCommands.add(new RespawnCommand(this, powerUp)));
        return respawnCommands;
    }

    public List<RespawnCommand> getSpawnCommands() {
        addExtraPowerUp();
        return getRespawnCommands();
    }

    private void addExtraPowerUp() {
        this.powerUps.add(match.drawPowerUpCard());
        update();
    }

    public List<AggregateActionID> getPossibleAggregateAction() {
        List<AggregateActionID> aggregateActions = new ArrayList<>();
        if (match.isLastTurn()) {
            if (match.hasFirstPlayerPlayedLastTurn()) {
                aggregateActions.add(AggregateActionID.MOVE_MOVE_RELOAD_SHOOT);
                aggregateActions.add(AggregateActionID.MOVE_MOVE_MOVE_GRAB);
            } else {
                aggregateActions.add(AggregateActionID.MOVE_RELOAD_SHOOT);
                aggregateActions.add(AggregateActionID.MOVE_MOVE_MOVE_MOVE);
                aggregateActions.add(AggregateActionID.MOVE_MOVE_GRAB);
            }
        } else {
            if (health.size() <= DAMAGE_BEFORE_FIRST_ADRENALINA) {
                aggregateActions.add(AggregateActionID.MOVE_MOVE_MOVE);
                aggregateActions.add(AggregateActionID.MOVE_GRAB);
                aggregateActions.add(AggregateActionID.SHOOT);
            } else if (health.size() <= DAMAGE_BEFORE_SECOND_ADRENALINA) {
                aggregateActions.add(AggregateActionID.MOVE_MOVE_MOVE);
                aggregateActions.add(AggregateActionID.MOVE_MOVE_GRAB);
                aggregateActions.add(AggregateActionID.SHOOT);
            } else {
                aggregateActions.add(AggregateActionID.MOVE_MOVE_MOVE);
                aggregateActions.add(AggregateActionID.MOVE_MOVE_GRAB);
                aggregateActions.add(AggregateActionID.MOVE_SHOOT);
            }
        }
        return aggregateActions;
    }

    public List<Square> getAccessibleSquare(int maxDistance) {
        return this.match.getBoard().getReachableSquare(position, maxDistance);
    }

    public Match getMatch() {
        return this.match;
    }

    public void pay(Color color, int amount) {
        if (ammo.getOrDefault(color, 0) < amount)
            throw new IllegalStateException();
        ammo.put(color, ammo.get(color) - amount);
        update();
    }

    public void refund(Color color, Integer amount) {
        addAmmo(color, amount);
        update();
    }

    public void refund(PowerUp powerUp) {
        match.undiscard(powerUp);
        if (powerUps.size() >= MAX_POWERUP)
            throw new IllegalStateException();
        powerUps.add(powerUp);
        update();
    }

    public void addWeapon(Weapon weapon) {
        if (weapons.size() >= MAX_WEAPONS)
            throw new IllegalStateException();
        weapons.add(weapon);
        update();
    }

    public void removeWeapon(Weapon selectedWeapon) {
        weapons.remove(selectedWeapon);
        update();
    }

    public boolean hasScope() {
        return !getTargetingScopes().isEmpty();
    }

    public void selectAggregateAction() {
        availableAggregateActionCounter--;
        update();
    }

    public void deselectAggregateAction() {
        availableAggregateActionCounter++;
        update();
    }

    public Player getLastShooter() {
        return match.getPlayer(lastShooter);
    }

    public boolean isDisconnected() {
        return disconnected;
    }

    public void setDisconnected() {
        disconnected = true;
        changeState(new IdleState());
        update();
    }

    public void setConnected() {
        disconnected = false;
        match.sendModelAfterReconnection(id);
        update();
    }

    public String getNickname() {
        return nickname;
    }

    public int getDeaths() {
        return deaths;
    }

    public void addDeaths() {
        deaths++;
        match.decreaseDeathsCounter();
        update();
    }

    public int getAvailableAggregateActionCounter() {
        return availableAggregateActionCounter;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    /**
     * @return true if the player's board is flipped
     */
    public boolean isFlippedBoard() {
        return flippedBoard;
    }

    /**
     * This method gives the player the flipped board (Final frenzy only player board) This board offers no point for first blood.
     */
    public void flipBoard() {
        this.flippedBoard = true;
        update();
    }


    public List<PowerUp> getTargetingScopes() {
        return powerUps.stream().filter(powerUp -> powerUp.getType() == PowerUpID.TARGETING_SCOPE).collect(Collectors.toList());
    }

    public List<PowerUp> getTagbackGrenades() {
        return powerUps.stream().filter(powerUp -> powerUp.getType() == PowerUpID.TAGBACK_GRENADE).collect(Collectors.toList());
    }

    public List<PowerUp> getTeleports() {
        return powerUps.stream().filter(powerUp -> powerUp.getType() == PowerUpID.TELEPORTER).collect(Collectors.toList());
    }

    public List<PowerUp> getNewtons() {
        return powerUps.stream().filter(powerUp -> powerUp.getType() == PowerUpID.NEWTON).collect(Collectors.toList());
    }

    public void initialize() {
        playerState = new ManageTurnState();
        if (match.isLastTurn() && match.hasFirstPlayerPlayedLastTurn())
            availableAggregateActionCounter = 1;
        else
            availableAggregateActionCounter = 2;
        update();
    }
}
