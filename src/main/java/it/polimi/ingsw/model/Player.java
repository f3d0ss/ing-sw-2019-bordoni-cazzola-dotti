package it.polimi.ingsw.model;

import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.playerstate.IdleState;
import it.polimi.ingsw.model.playerstate.PlayerState;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Player {

    public static final int MAX_AMMO = 3;
    public static final int MAX_POWERUP = 3;
    public static final int MAX_DAMAGE = 12;
    public static final int MAX_MARKS = 3;
    public static final int MAX_WEAPONS = 3;
    public static final int DAMAGE_BEFORE_FIRST_ADRENALINA = 2;
    public static final int DAMAGE_BEFORE_SECOND_ADRENALINA = 5;
    public static final int DAMAGE_BEFORE_DEAD = 10;
    public static final int INITIAL_AMMO_NUMBER = 1;

    private Match match;
    private PlayerId id;
    private List<PlayerId> health = new ArrayList<>();
    private int deaths = 0;
    private Map<PlayerId, Integer> marks = new EnumMap<>(PlayerId.class);
    private int points = 0;
    private String nickname;
    private List<Weapon> weapons = new ArrayList<>();
    private List<PowerUp> powerUps = new ArrayList<>();
    private Map<Color, Integer> ammo = new EnumMap<>(Color.class);
    private boolean disconnected = false;
    private int availableAggregateActionCounter;
    private PlayerState playerState = new IdleState();
    private Square position;
    private PlayerId lastShooter;

    public Player(Match match, PlayerId id, String nickname) {
        this.match = match;
        this.id = id;
        this.nickname = nickname;
        for (Color c : Color.values()) {
            ammo.put(c, INITIAL_AMMO_NUMBER);
        }
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

    public void untracedMove(Square square) {
        position.removePlayer(this);
        position = square;
        position.addPlayer(this);
    }

    public void move(Square square) {
        untracedMove(square);
    }

    private void addAmmo(Color color, Integer number) {
        int newAmmoNumber = this.ammo.getOrDefault(color, 0) + number;
        this.ammo.put(color, newAmmoNumber < MAX_AMMO ? newAmmoNumber : MAX_AMMO);
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
    }

    public void addDamage(int damage, PlayerId color) {
        int possibleDamage = MAX_DAMAGE - health.size();
        for (int i = damage; i > 0; i--) {
            if (possibleDamage <= 0)
                break;
            this.health.add(color);
            possibleDamage--;
        }
        for (int i = marks.getOrDefault(color, 0); i > 0; i--) {
            if (possibleDamage <= 0)
                break;
            this.health.add(color);
            possibleDamage--;
        }
        marks.put(color, 0);
        lastShooter = color;
    }

    public void addMarks(int marks, PlayerId color) {
        int possibleMarks = MAX_MARKS - this.marks.getOrDefault(color, 0);
        for (int i = marks; i > 0; i--) {
            if (possibleMarks <= 0)
                return;
            this.marks.put(color, this.marks.getOrDefault(color, 0) + 1);
            possibleMarks--;
        }

    }

    public Square getPosition() {
        return position;
    }

    public void respawn(Color color) {
        if (position != null)
            position.removePlayer(this);
        position = match.getBoard().getSpawn(color);
        position.addPlayer(this);

    }

    public List<Command> getPossibleCommands() {
        return playerState.getPossibleCommands(this);
    }


    public List<AggregateAction> getPossibleAggregateAction() {
        List<AggregateAction> aggregateActions = new ArrayList<>();
        if (match.isLastTurn()) {
            if (match.hasFirstPlayerPlayedLastTurn()) {
                aggregateActions.add(new AggregateAction(2, false, true, true));
                aggregateActions.add(new AggregateAction(3, true, false, false));
            } else {
                aggregateActions.add(new AggregateAction(1, false, true, true));
                aggregateActions.add(new AggregateAction(4, false, false, false));
                aggregateActions.add(new AggregateAction(2, true, false, false));
            }
        } else {
            if (health.size() <= DAMAGE_BEFORE_FIRST_ADRENALINA) {
                aggregateActions.add(new AggregateAction(3, false, false, false));
                aggregateActions.add(new AggregateAction(1, true, false, false));
                aggregateActions.add(new AggregateAction(0, false, true, false));
            } else if (health.size() <= DAMAGE_BEFORE_SECOND_ADRENALINA) {
                aggregateActions.add(new AggregateAction(3, false, false, false));
                aggregateActions.add(new AggregateAction(2, true, false, false));
                aggregateActions.add(new AggregateAction(0, false, true, false));
            } else {
                aggregateActions.add(new AggregateAction(1, false, true, false));
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
    }

    public void pay(PowerUp powerUp) {
        match.discard(powerUp);
        powerUps.remove(powerUp);
    }

    public void refund(Color color, Integer amount) {
        addAmmo(color, amount);
    }

    public void refund(PowerUp powerUp) {
        match.undiscard(powerUp);
        if (powerUps.size() >= MAX_POWERUP)
            throw new IllegalStateException();
        powerUps.add(powerUp);
    }

    public void addWeapon(Weapon weapon) {
        if (weapons.size() >= MAX_WEAPONS)
            throw new IllegalStateException();
        weapons.add(weapon);
    }

    public void removeWeapon(Weapon selectedWeapon) {
        weapons.remove(selectedWeapon);
    }

    public boolean hasScope() {
        return getTargetingScopes().isEmpty();
    }

    public void selectAggregateAction() {
        availableAggregateActionCounter--;
    }

    public void deselectAggregateAction() {
        availableAggregateActionCounter++;
    }

    public Player getLastShooter() {
        return match.getPlayer(lastShooter);
    }

    public boolean isDisconnected() {
        return disconnected;
    }

    public void setDisconnetted(boolean disconnected) {
        this.disconnected = disconnected;
    }

    public String getNickname() {
        return nickname;
    }

    public int getDeaths() {
        return deaths;
    }

    public void addDeaths() {
        deaths++;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
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
        if (match.isLastTurn() && match.hasFirstPlayerPlayedLastTurn())
            availableAggregateActionCounter = 1;
        else
            availableAggregateActionCounter = 2;
    }
}
