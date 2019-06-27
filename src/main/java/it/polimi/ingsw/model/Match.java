package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.Parser;
import it.polimi.ingsw.view.MatchView;
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.VirtualView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Match {

    private static final int SKULLS = 8;
    //TODO param server
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
    private Map<PlayerId, ViewInterface> views;
    private Map<PlayerId, Long> leaderBoard;

    public Match(int gameBoardNumber) {
        Parser parser = new Parser();
        initializeGameBoard(gameBoardNumber, parser);
        views = new HashMap<>();
        killshotTrack = new ArrayList<>();
        currentPlayers = new ArrayList<>();
        firstPlayerPlayedLastTurn = false;
        initializeAmmoTiles(parser);
        initializeWeapons(parser);
        initializePowerUps(parser);
    }

    public Match() {
        this(1);
    }

    private void initializeGameBoard(int gameBoardNumber, Parser parser) {
        board = parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/gameboards/gameboard_" +
                String.format("%03d", gameBoardNumber) + ".json")), GameBoard.class);
        board.initialize();
        board.getSquareList().forEach(square -> square.setMatch(this));
    }

    private void initializeAmmoTiles(Parser parser) {
        InputStream in = getClass().getResourceAsStream("/cards/default_ammo_tiles_deck.json");
        currentAmmoTileDeck = parser.deserialize(new InputStreamReader(in), AmmoTileDeck.class);
        usedAmmoTileDeck = new AmmoTileDeck();
        for (TurretSquare turretSquare : getBoard().getTurrets()) {
            turretSquare.setAmmoTile(currentAmmoTileDeck.drawAmmoTile());
        }
    }

    private void initializeWeapons(Parser parser) {
        List<Weapon> weaponList = new ArrayList<>();
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/Thor.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/Shotgun.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/TractorBeam.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/Cyberblade.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/Electroscythe.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/Flamethrower.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/Furnace.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/GrenadeLauncher.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/Heatseeker.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/Hellion.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/LockRifle.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/MachineGun.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/PlasmaGun.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/PowerGlove.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/Railgun.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/RocketLauncher.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/Shockwave.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/Sledgehammer.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/VortexCannon.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/Whisper.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/Zx2.json"))));
        for (Color color : Color.values()) {
            for (int i = 0; i < SpawnSquare.MAX_WEAPON; i++)
                board.getSpawn(color).addWeapon(weaponList.remove(0));
        }
        currentWeaponDeck = new WeaponDeck(weaponList);
        //currentWeaponDeck.shuffle();
    }

    private void initializePowerUps(Parser parser) {
        currentPowerUpDeck = parser.deserialize(new InputStreamReader(
                        getClass().getResourceAsStream("/cards/default_powerup_deck.json")),
                PowerUpDeck.class);
        currentPowerUpDeck.shuffle();
        usedPowerUpDeck = new PowerUpDeck(new ArrayList<>());
    }

    public void setLeaderBoard(Map<PlayerId, Long> leaderBoard) {
        this.leaderBoard = leaderBoard;
        views.values().forEach(v -> ((VirtualView) v).setGameOver());
        update();
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

    boolean decreaseDeathsCounter() {
        if (deathsCounter == 0) {
            return false;
        }
        this.deathsCounter--;
        update();
        return true;
    }

    public GameBoard getBoard() {
        return board;
    }

    public void addPlayer(Player player) {
        currentPlayers.add(player);
    }

    public void addPlayer(Player player, ViewInterface viewInterface) {
        currentPlayers.add(player);
        views.put(player.getId(), viewInterface);
    }

    public void addKillshot(PlayerId player) {
        killshotTrack.add(player);
        update();
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

    public List<ViewInterface> getAllVirtualViews() {
        return new ArrayList<>(views.values());
    }

    public Map<PlayerId, ViewInterface> getVirtualViews() {
        return views;
    }

    private void update() {
        views.values().forEach(viewInterface -> viewInterface.update(new MatchView(killshotTrack, deathsCounter, board.getId(), leaderBoard)));
    }

    /**
     * This method sends the current state of the Model to the views (must be called at the start to initialize clients)
     */
    public void updateAllModel() {
        update();
        currentPlayers.forEach(Player::update);
        board.getSquareList().forEach(Square::update);
        views.values().forEach(ViewInterface::setViewInitializationDone);
    }

    public void sendModelAfterReconnection(PlayerId player) {
        views.get(player).update(new MatchView(killshotTrack, deathsCounter, board.getId(), leaderBoard));
        currentPlayers.forEach(p -> views.get(player).update(p.getPlayerView(p.getId() == player)));
        board.getSquareList().forEach(s -> views.get(player).update(s.getSquareView()));
        views.get(player).setViewInitializationDone();
    }

}
