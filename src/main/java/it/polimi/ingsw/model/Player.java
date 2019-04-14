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

    private final static int MAX_AMMO = 3;
    private final static int MAX_POWERUP = 3;
    private final static int MAX_DAMAGE = 12;
    private final static int MAX_MARKS = 3;
    public final static int MAX_WEAPONS = 3;

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


    public Player(Match match, PlayerId id, String nickname, Square position) {
        this.match = match;
        this.id = id;
        Nickname = nickname;
        this.position = position;
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
        return dead;
    }

    public void setId(PlayerId id) {
        this.id = id;
    }

    public void changeState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public void move(CardinalDirection direction) {
        if (position.getConnection(direction) == Connection.MAP_BORDER || position.getConnection(direction) == Connection.WALL)
            throw new IllegalMoveException();
        switch (direction) {
            case NORTH:
                position = match.getBoard().getSquare(position.getRow() - 1, position.getCol());
                break;
            case SOUTH:
                position = match.getBoard().getSquare(position.getRow() + 1, position.getCol());
                break;
            case EAST:
                position = match.getBoard().getSquare(position.getRow(), position.getCol() + 1);
                break;
            case WEST:
                position = match.getBoard().getSquare(position.getRow(), position.getCol() - 1);
                break;
        }
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

        if (health.size() >= MAX_DAMAGE - 1)
            dead = true;

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

    public List<CardinalDirection> getAccessibleSquare() {
        //this.match.getAccessibleSquare(position);
        return null;
    }

    public Match getMatch() {
        return this.match;
    }

    public ArrayList<Player> getPossibleTarget() {
        ArrayList<Player> targets = new ArrayList<>();
        ArrayList<Square> visibles = match.getBoard().getVisibleSquares(position);
        for (Square s : visibles) {
            for (Player p : s.getHostedPlayers())
                targets.add(p);
        }
        return targets;
    }

}
