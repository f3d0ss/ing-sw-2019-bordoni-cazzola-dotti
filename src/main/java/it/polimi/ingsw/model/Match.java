package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Match {

    private ArrayList<PlayerId> killShootTrack = new ArrayList<PlayerId>();
//    private PowerUpDeck currentPowerUpDeck;
//    private PowerUpDeck usedPowerUpDeck;
//    private AmmoTileDeck currentAmmoTileDeck;
//    private AmmoTileDeck usedAmmoTileDeck;
//    private WeaponDeck currentWeaponDeck;
    private ArrayList<Player> deadPlayers = new ArrayList<Player>();
    private int deathsCounter;
    private PlayerId currentPlayer;
    private ArrayList<Player> currentPlayers = new ArrayList<Player>();

    public Player getPlayer(PlayerId id){
        for(Player tmp : currentPlayers)
            if (id.equals(tmp.getId())) {
                return tmp;
            }
        return null;
    }

    public void addPlayer(Player player){
        currentPlayers.add(player);
    }
}
