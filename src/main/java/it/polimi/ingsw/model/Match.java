package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.Observable;

import java.util.ArrayList;
import java.util.List;

public class Match extends Observable {

    private static final int SKULLS = 8;

    private ArrayList<PlayerId> killshotTrack;
    private PowerUpDeck currentPowerUpDeck;
    private PowerUpDeck usedPowerUpDeck;
    private AmmoTileDeck currentAmmoTileDeck;
    private AmmoTileDeck usedAmmoTileDeck;
    private WeaponDeck currentWeaponDeck;
    private int deathsCounter = SKULLS;
    private ArrayList<Player> currentPlayers;
    private GameBoard board;

    public Match() {
        board = new GameBoard(1);
        killshotTrack = new ArrayList();
        currentPlayers = new ArrayList();
        currentAmmoTileDeck = new AmmoTileDeck();
        currentAmmoTileDeck.initializeDeck();
        currentWeaponDeck = new WeaponDeck();
        usedAmmoTileDeck = new AmmoTileDeck();
    }

    public Player getPlayer(PlayerId id) {
        for (Player tmp : currentPlayers)
            if (id.equals(tmp.getId())) {
                return tmp;
            }
        return null;
    }

    public List<Player> getCurrentPlayers() {
        return currentPlayers;
    }

    public int getPlayerKillshots(PlayerId id) {
        int count = 0;
        for (PlayerId tmp : killshotTrack)
            if (id.equals(tmp))
                count++;
        return count;
    }

    public List<PlayerId> getKillshotTrack() {
        return killshotTrack;
    }

    public int getDeathsCounter() {
        return deathsCounter;
    }

    public boolean decreaseDeathsCounter() {
        if (deathsCounter == 0) {
            return false;
        }
        this.deathsCounter--;
        return true;
    }

    public GameBoard getBoard() {
        return board;
    }

    public void addPlayer(Player player) {
        currentPlayers.add(player);
    }

    public void addKillshot(PlayerId player) {
        killshotTrack.add(player);
    }

    private AmmoTile drawAmmoTileCard() {
        AmmoTile tmp;
        tmp = currentAmmoTileDeck.drawAmmoTile();
        if (tmp == null) {
            currentAmmoTileDeck = usedAmmoTileDeck;
            usedAmmoTileDeck = new AmmoTileDeck();
            currentAmmoTileDeck.shuffle();
            tmp = currentAmmoTileDeck.drawAmmoTile();
        }
        return tmp;
    }

    private Weapon drawWeaponCard() {
        return currentWeaponDeck.drawWeapon();
    }

    public PowerUp drawPowerUpCard() {
        PowerUp powerup;
        powerup = currentPowerUpDeck.drawPowerUp();
        if (powerup == null) {
            currentPowerUpDeck = usedPowerUpDeck;
            usedPowerUpDeck = new PowerUpDeck(new ArrayList<>());
            currentPowerUpDeck.shuffle();
            powerup = currentPowerUpDeck.drawPowerUp();
        }
        return powerup;
    }

    public void discard(AmmoTile ammoTile) {
        usedAmmoTileDeck.add(ammoTile);
    }

    public void discard(PowerUp powerUp) {
        usedPowerUpDeck.add(powerUp);
    }

    public void undiscard(PowerUp powerUp) {
        usedPowerUpDeck.remove(powerUp);
    }

    public void restoreCards() {
        for (TurretSquare turret : board.getTurrets()) {
            if(turret.getAmmoTile() == null)
                turret.setAmmoTile(drawAmmoTileCard());
        }
        for (Color color : Color.values()) {
            while(board.getSpawn(color).lackWeapon())
                board.getSpawn(color).addWeapon(drawWeaponCard());
        }
    }
}
