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

    /**
     * Maximum damage that counts
     */
    public static final int MAX_DAMAGE = 12;
    /**
     * Max number a player can have at the same moment
     */
    public static final int MAX_WEAPONS = 3;
    /**
     * Max number of each type of ammo cube a player can have at same moment
     */
    public static final int MAX_AMMO = 3;
    /**
     * Initial number of each ammo cube
     */
    static final int INITIAL_AMMO_NUMBER = 1;
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

    /**
     * Notifies observers
     */
    public void update() {
        match.getVirtualViews().forEach((playerId, view) -> view.update(getPlayerView(id == playerId)));

    }

    /**
     * Gets player view
     *
     * @param isMe
     * @return Player's view
     */
    PlayerView getPlayerView(boolean isMe) {
        List<WeaponView> wvs = new ArrayList<>();
        List<PowerUpView> pvs = new ArrayList<>();
        weapons.forEach(weapon -> wvs.add(new WeaponView(weapon.getName(), weapon.isLoaded())));
        powerUps.forEach(powerUp -> pvs.add(new PowerUpView(powerUp.getType(), powerUp.getColor())));
        return new PlayerView(id, isMe, health, deaths, marks, nickname, wvs, pvs, ammo, availableAggregateActionCounter, flippedBoard, disconnected, health.size() > DAMAGE_BEFORE_FIRST_ADRENALINA, health.size() > DAMAGE_BEFORE_SECOND_ADRENALINA);
    }

    /**
     * Gets Player's ID
     *
     * @return value of id
     */
    public PlayerId getId() {
        return id;
    }

    /**
     * Gets player's weapons
     *
     * @return player's weapons
     */
    public List<Weapon> getWeapons() {
        return weapons;
    }

    /**
     * Gets powerUps
     *
     * @return player's powerUps
     */
    public List<PowerUp> getPowerUps() {
        return powerUps;
    }

    /**
     * Gets ammo
     *
     * @return player's ammo
     */
    public Map<Color, Integer> getAmmo() {
        return ammo;
    }

    /**
     * Returns true if player is dead
     *
     * @return true if player is dead
     */
    public boolean isDead() {
        return health.size() > DAMAGE_BEFORE_DEAD;
    }

    public String toString() {
        return nickname;
    }

    /**
     * Gets player's damage track
     *
     * @return player's damage track
     */
    public List<PlayerId> getHealth() {
        return health;
    }

    /**
     * Gets marks on the player
     *
     * @return marks on the player
     */
    public Map<PlayerId, Integer> getMarks() {
        return marks;
    }

    /**
     * Changes the state of the player
     *
     * @param playerState state to set
     */
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

    /**
     * Moves the player
     *
     * @param square square to move player into
     */
    public void move(Square square) {
        position.removePlayer(this);
        position = square;
        position.addPlayer(this);
    }

    private void addAmmo(Color color, Integer number) {
        int newAmmoNumber = this.ammo.getOrDefault(color, 0) + number;
        this.ammo.put(color, newAmmoNumber < MAX_AMMO ? newAmmoNumber : MAX_AMMO);
    }

    /**
     * Removes a powerup
     *
     * @param powerUp powerup to remove
     */
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

    /**
     * Adds resources contained in an ammotile to the player
     *
     * @param ammoTile ammotile to use
     */
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

    /**
     * This method adds damage
     *
     * @param damage ammount of damage
     * @param color  shooter ID
     */
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

    /**
     * Adds marks
     *
     * @param marks number of marks
     * @param color PlayerId of who is marking the player
     */
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

    /**
     * Gets player's position
     *
     * @return player's position
     */
    public Square getPosition() {
        return position;
    }

    /**
     * Respawns the player on the given spawn
     *
     * @param color Color of the spawn
     */
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

    /**
     * Gets all possible actions that a player can do in his current state
     *
     * @return List of all possible commands
     */
    public List<Command> getPossibleCommands() {
        return playerState.getPossibleCommands(this);
    }

    /**
     * Gets all possible commands to respawn the player
     *
     * @return list of all possible commands to respawn the player
     */
    public List<RespawnCommand> getRespawnCommands() {
        List<RespawnCommand> respawnCommands = new ArrayList<>();
        addExtraPowerUp();
        powerUps.forEach(powerUp -> respawnCommands.add(new RespawnCommand(this, powerUp)));
        return respawnCommands;
    }

    /**
     * Gets all possible commands to spawn the player for the first time
     *
     * @return list of all possible commands to spawn the player
     */
    public List<RespawnCommand> getSpawnCommands() {
        addExtraPowerUp();
        return getRespawnCommands();
    }

    private void addExtraPowerUp() {
        this.powerUps.add(match.drawPowerUpCard());
        update();
    }

    /**
     * Gets the possible aggregate actions
     *
     * @return possible aggregate actions
     */
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

    /**
     * Gets all squares accessible by the player within {@code #maxDistance} steps
     *
     * @param maxDistance Max distance
     * @return list of all the squares accessible by the player within {@code #maxDistance} steps
     */
    public List<Square> getAccessibleSquare(int maxDistance) {
        return this.match.getBoard().getReachableSquare(position, maxDistance);
    }

    /**
     * Gets the match the player is in
     *
     * @return The match played by the player
     */
    public Match getMatch() {
        return this.match;
    }

    /**
     * This method removes ammos from the player
     *
     * @param color  Color of the ammo cube
     * @param amount number of cubes to remove
     */
    public void pay(Color color, int amount) {
        if (ammo.getOrDefault(color, 0) < amount)
            throw new IllegalStateException();
        ammo.put(color, ammo.get(color) - amount);
        update();
    }

    /**
     * Adds ammocubes to the player's resources
     *
     * @param color  Color of the ammo cube
     * @param amount number of cubes to add
     */
    public void refund(Color color, Integer amount) {
        addAmmo(color, amount);
        update();
    }

    /**
     * Adds a power up to player's hand
     *
     * @param powerUp power up to add
     */
    public void refund(PowerUp powerUp) {
        match.undiscard(powerUp);
        if (powerUps.size() >= MAX_POWERUP)
            throw new IllegalStateException();
        powerUps.add(powerUp);
        update();
    }

    /**
     * Adds a weapon to player's weapons list
     *
     * @param weapon weapon to add
     */
    public void addWeapon(Weapon weapon) {
        if (weapons.size() >= MAX_WEAPONS)
            throw new IllegalStateException();
        weapons.add(weapon);
        update();
    }

    /**
     * Removes a weapon from the player
     *
     * @param selectedWeapon weapon to remove
     */
    public void removeWeapon(Weapon selectedWeapon) {
        weapons.remove(selectedWeapon);
        update();
    }

    /**
     * Gets if the player has at least one Scope PowerUp
     *
     * @return true if the player has at least one Scope PowerUp
     */
    public boolean hasScope() {
        return !getTargetingScopes().isEmpty();
    }

    /**
     * Decrease the aggregate actions available
     */
    public void selectAggregateAction() {
        availableAggregateActionCounter--;
        update();
    }

    /**
     * increase the aggregate actions available
     */
    public void deselectAggregateAction() {
        availableAggregateActionCounter++;
        update();
    }

    /**
     * Gets the last enemy's ID that shot the player
     *
     * @return enemy's ID
     */
    public Player getLastShooter() {
        return match.getPlayer(lastShooter);
    }

    /**
     * Return true if player is disconnected
     *
     * @return true if player is disconnected
     */
    public boolean isDisconnected() {
        return disconnected;
    }

    /**
     * Sets the disconnected status
     */
    public void setDisconnected() {
        disconnected = true;
        changeState(new IdleState());
        update();
    }

    /**
     * Sets the connected status and sends update of the current status of the match to the client
     */
    public void setConnected() {
        disconnected = false;
        match.sendModelAfterReconnection(id);
        update();
    }

    /**
     * Gets player's nickname
     *
     * @return player's nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Gets Player's deaths number
     *
     * @return Player's deaths number
     */
    public int getDeaths() {
        return deaths;
    }

    /**
     * Increase deaths number
     */
    public void addDeaths() {
        deaths++;
        match.decreaseDeathsCounter();
        update();
    }

    /**
     * Gets number of available aggregate actions
     *
     * @return number of available aggregate actions
     */
    public int getAvailableAggregateActionCounter() {
        return availableAggregateActionCounter;
    }

    /**
     * Gets player's points
     *
     * @return player's points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Adds points
     *
     * @param points number of points to add
     */
    public void addPoints(int points) {
        this.points += points;
    }

    /**
     * Gets if the player's board is flipped
     *
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

    /**
     * Gets player's Scopes
     *
     * @return List of player's scopes
     */
    public List<PowerUp> getTargetingScopes() {
        return powerUps.stream().filter(powerUp -> powerUp.getType() == PowerUpID.TARGETING_SCOPE).collect(Collectors.toList());
    }

    /**
     * Gets player's Tagback grenades
     *
     * @return List of player's tagback grenades
     */
    public List<PowerUp> getTagbackGrenades() {
        return powerUps.stream().filter(powerUp -> powerUp.getType() == PowerUpID.TAGBACK_GRENADE).collect(Collectors.toList());
    }

    /**
     * Gets player's Teleporters
     *
     * @return List of player's teleporters
     */
    public List<PowerUp> getTeleports() {
        return powerUps.stream().filter(powerUp -> powerUp.getType() == PowerUpID.TELEPORTER).collect(Collectors.toList());
    }

    /**
     * Gets player's Newtons
     *
     * @return List of player's newtons
     */
    public List<PowerUp> getNewtons() {
        return powerUps.stream().filter(powerUp -> powerUp.getType() == PowerUpID.NEWTON).collect(Collectors.toList());
    }

    /**
     * Initializes the player at the start of its turn.
     */
    public void initialize() {
        playerState = new ManageTurnState();
        if (match.isLastTurn() && match.hasFirstPlayerPlayedLastTurn())
            availableAggregateActionCounter = 1;
        else
            availableAggregateActionCounter = 2;
        update();
    }
}
