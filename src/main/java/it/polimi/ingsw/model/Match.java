package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.utils.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.view.ViewInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Match {

    private static final int SKULLS = 8;

    private List<PlayerId> killshotTrack;
    private PowerUpDeck currentPowerUpDeck;
    private PowerUpDeck usedPowerUpDeck;
    private AmmoTileDeck currentAmmoTileDeck;
    private AmmoTileDeck usedAmmoTileDeck;
    private WeaponDeck currentWeaponDeck;
    private int deathsCounter = SKULLS;
    private List<Player> currentPlayers;
    private GameBoard board;
    private boolean firstPlayerPlayedLastTurn;
    private List<ViewInterface> views;

    public Match(int gameBoardNumber) {
        initializeGameBoard(gameBoardNumber);
        killshotTrack = new ArrayList();
        currentPlayers = new ArrayList();
        firstPlayerPlayedLastTurn = false;
        initializeAmmoTiles();
        initializeWeapons();
    }

    public Match() {
        this(1);
    }

    private void initializeGameBoard(int gameBoardNumber) {
        RuntimeTypeAdapterFactory<Square> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                .of(Square.class, "type")
                .registerSubtype(SpawnSquare.class, "spawn")
                .registerSubtype(TurretSquare.class, "turret");

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(runtimeTypeAdapterFactory)
                .create();

        try {
            board = gson.fromJson(new FileReader("src/resources/gameboards/gameboard_" +
                    String.format("%03d", gameBoardNumber) + ".json"), GameBoard.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        board.initialize();
    }


    private void initializeAmmoTiles() {
        Gson gson = new Gson();
        try {
            currentAmmoTileDeck = gson.fromJson(new FileReader("src/resources/cards/default_ammo_tiles_deck.json"), AmmoTileDeck.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        usedAmmoTileDeck = new AmmoTileDeck();
        for (TurretSquare turretSquare : getBoard().getTurrets()) {
            turretSquare.setAmmoTile(currentAmmoTileDeck.drawAmmoTile());
        }
    }

    private void initializeWeapons() {
        Gson gson = new Gson();
        List<Weapon> weaponList = new ArrayList<>();
        File file = new File("src/resources/weapons/");
        File[] files = file.listFiles();
        for (File f : files) {
            try {
                weaponList.add(gson.fromJson(new FileReader(f.getPath()), Weapon.class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        Collections.shuffle(weaponList);
        for (Color color : Color.values()) {
            for (int i = 0; i < SpawnSquare.MAX_WEAPON; i++)
                board.getSpawn(color).addWeapon(weaponList.remove(0));
        }
        currentWeaponDeck = new WeaponDeck(weaponList);
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

    public boolean isLastTurn() {
        return deathsCounter == 0;
    }

    public boolean hasFirstPlayerPlayedLastTurn() {
        return firstPlayerPlayedLastTurn;
    }

    /**
     * This method replaces any stuff taken. Replace ammo tiles with new tiles from the ammo stacks.
     * If you empty the stacks, shuffle the discard pile (including any tiles you grabbed this turn) and make new ammo stacks.
     * Replace weapons by drawing from the weapons deck. If the deck is empty, no new weapons will appear for the rest of the game.
     */
    public void restoreCards() {
        for (TurretSquare turret : board.getTurrets()) {
            if (turret.getAmmoTile() == null)
                turret.setAmmoTile(drawAmmoTileCard());
        }

        for (SpawnSquare spawnSquare : board.getSpawnSquares()) {
            while (spawnSquare.lackWeapon() && !currentWeaponDeck.isEmpty()) {
                spawnSquare.addWeapon(currentWeaponDeck.drawWeapon());
            }
        }
    }

    public void firstPlayerPlayedLastTurn() {
        this.firstPlayerPlayedLastTurn = true;
    }

    public List<ViewInterface> getVirtualViews() {
        return views;
    }
}
