package it.polimi.ingsw.model;

import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.MoveCommand;
import it.polimi.ingsw.model.exception.IllegalMoveException;
import it.polimi.ingsw.model.playerstate.AfterSelectedAggregateActionState;
import it.polimi.ingsw.model.playerstate.IdleState;
import it.polimi.ingsw.model.playerstate.PendingPaymentWeaponOptionState;
import it.polimi.ingsw.model.playerstate.PlayerState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {

    public static final int MAX_AMMO = 3;
    public static final int MAX_POWERUP = 3;
    public static final int MAX_DAMAGE = 12;
    public static final int MAX_MARKS = 3;
    public static final int MAX_WEAPONS = 3;

    private Match match;
    private PlayerId id;
    private ArrayList<PlayerId> health = new ArrayList<>();
    private int deaths = 0;
    private Map<PlayerId, Integer> marks = new HashMap<>();
    private int points = 0;
    private String Nickname;
    private ArrayList<Weapon> weapons = new ArrayList<>();
    private ArrayList<PowerUp> powerUps = new ArrayList<>();
    private Map<Color, Integer> ammo = new HashMap<Color, Integer>();
    private boolean disconnetted = false;
    private boolean dead = false;
    private int availableAggregateActionCounter = 2;
    private PlayerState playerState = new IdleState();
    private Square position;
    private int usedAggregateAction;
    private PlayerId lastShooter;


    public Player(Match match, PlayerId id, String nickname, Square position) {
        this.match = match;
        this.id = id;
        Nickname = nickname;
        this.position = position;
        usedAggregateAction = 0;
    }

    public Map<Color, Integer> getAmmo() {
        return ammo;
    }

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public List<Weapon> getWeapons() {
        return this.weapons;
    }

    public PlayerId getId() {
        return id;
    }

    public boolean isDead() {
        return health.size() >= MAX_DAMAGE - 1;
    }

    public void setId(PlayerId id) {
        this.id = id;
    }

    public void changeState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public void move(Square square) {
        position = square;
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
        for (int i = this.marks.getOrDefault(color, 0); i > 0; i--) {
            if (possibleDamage <= 0)
                break;
            this.health.add(color);
            possibleDamage--;
        }
        this.marks.put(color, 0);
        //TODO: do something if is dead
        //if (isDead()) ....
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

    public void teleport(Square square) {
        this.position = square;
    }

    public void respawn(Color color) {
        this.position = match.getBoard().getSpawn(color);
    }

    public List<Command> getPossibleCommands() {
        return playerState.getPossibleCommands(this);
    }


    public List<AggregateAction> getPossibleAggregateAction() {
        //Algorithm that calculate Aggregate Actions based on health
        return null;
    }

    public List<Square> getAccessibleSquare(int maxDistance) {
        //this.match.getAccessibleSquare(position);
        return null;
    }

    public Match getMatch() {
        return this.match;
    }

    public void pay(Color color, int amount) {
        if(ammo.getOrDefault(color, 0) < amount)
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

    public void addWeapon(Weapon weapon){
        if (weapons.size() >= MAX_WEAPONS)
            throw new IllegalStateException();
        weapons.add(weapon);
    }

    public void removeWeapon(Weapon selectedWeapon) {
        weapons.remove(selectedWeapon);
    }

    public boolean hasScope() {
        //TODO: implement this method
        return false;
    }

    public void selectAggregateAction() {
        usedAggregateAction++;
    }

    public void deselectAggregateAction() {
        usedAggregateAction--;
    }

    public Player getLastShooter() {
        return match.getPlayer(lastShooter);
    }
}
