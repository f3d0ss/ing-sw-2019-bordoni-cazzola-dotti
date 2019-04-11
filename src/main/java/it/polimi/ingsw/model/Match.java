package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Match {

    private final static int SKULLS = 8;

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
    }

    public Player getPlayer(PlayerId id){
        for(Player tmp : currentPlayers)
            if (id.equals(tmp.getId())) {
                return tmp;
            }
        return null;
    }

    public int getPlayerKillshots(PlayerId id) {
        int count = 0;
        for(PlayerId tmp : killshotTrack)
            if(id.equals(tmp))
                count++;
        return count;
    }

    public int getDeathsCounter() {
        return deathsCounter;
    }

    public boolean decreaseDeathsCounter() {
        if(deathsCounter==0){
            return false;
        }
        this.deathsCounter--;
        return true;
    }

    public GameBoard getBoard() {
        return board;
    }

    public void addPlayer(Player player){
        currentPlayers.add(player);
    }

    public void addKillshot(PlayerId player){
        killshotTrack.add(player);
    }

    public AmmoTile drawAmmoTileCard(){
        AmmoTile tmp;
        AmmoTileDeck emptyDeck;
        tmp = currentAmmoTileDeck.drawAmmoTile();
        if (tmp == null){
            emptyDeck = currentAmmoTileDeck;
            currentAmmoTileDeck = usedAmmoTileDeck;
            usedAmmoTileDeck = emptyDeck;
            currentAmmoTileDeck.shuffle();
            tmp = currentAmmoTileDeck.drawAmmoTile();
        }
        return tmp;
    }

    public Weapon drawWeaponCard(){
        return currentWeaponDeck.drawWeapon();
    }

    public PowerUp drawPowerUpCard(){
        PowerUp powerup;
        PowerUpDeck emptyDeck;
        powerup = currentPowerUpDeck.drawPowerUp();
        if (powerup == null){
            emptyDeck = currentPowerUpDeck;
            currentPowerUpDeck = usedPowerUpDeck;
            usedPowerUpDeck = emptyDeck;
            currentPowerUpDeck.shuffle();
            powerup = currentPowerUpDeck.drawPowerUp();
        }
        return powerup;
    }
}
