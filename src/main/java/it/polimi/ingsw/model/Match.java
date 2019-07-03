package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.Parser;
import it.polimi.ingsw.view.MatchView;
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.VirtualView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the Match, Contains the state of the match
 */
public class Match {

    private static final int DEFAULT_SKULLS = 8;

    private List<PlayerId> killshotTrack;
    private PowerUpDeck currentPowerUpDeck;
    private PowerUpDeck usedPowerUpDeck;
    private AmmoTileDeck currentAmmoTileDeck;
    private AmmoTileDeck usedAmmoTileDeck;
    private WeaponDeck currentWeaponDeck;
    private int deathsCounter;
    private List<Player> currentPlayers;
    private GameBoard board;
    private boolean firstPlayerPlayedLastTurn;
    private Map<PlayerId, ViewInterface> views;
    private Map<PlayerId, Long> leaderBoard = null;
    private PlayerId playerOnDuty;
    private int gameBoardNumber;

    public Match(int skulls) {
        views = new EnumMap<>(PlayerId.class);
        killshotTrack = new ArrayList<>();
        currentPlayers = new ArrayList<>();
        firstPlayerPlayedLastTurn = false;
        deathsCounter = skulls;
    }

    /**
     * Creates a new Match and Initializes it with game board 1 and standard settings
     */
    public Match() {
        this(DEFAULT_SKULLS);
        initializeFromStandardFiles(1);
    }

    /**
     * Initializes the Match with standard settings (maps, cards, etc..)
     *
     * @param gameBoardId Gameboard ID must be [1,4]
     */
    public void initializeFromStandardFiles(int gameBoardId) {
        Parser parser = new Parser();
        initializeGameBoard(gameBoardId, parser);
        initializeAmmoTiles(parser);
        initializeWeapons(parser);
        initializePowerUps(parser);
    }

    private void initializeGameBoard(int gameBoardNumber, Parser parser) {
        board = parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/gameboards/gameboard_" +
                String.format("%03d", gameBoardNumber) + ".json")), GameBoard.class);
        board.initialize();
        board.getSquareList().forEach(square -> square.setMatch(this));
        this.gameBoardNumber = gameBoardNumber;
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
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/PlasmaGun.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/RocketLauncher.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/Railgun.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/Cyberblade.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/Flamethrower.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/PowerGlove.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/TractorBeam.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/Whisper.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/GrenadeLauncher.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/MachineGun.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/LockRifle.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/Zx2.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/Shockwave.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/Hellion.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/VortexCannon.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/Furnace.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/Shotgun.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/Electroscythe.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/Thor.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/Sledgehammer.json"))));
        weaponList.add(parser.deserialize(new InputStreamReader(getClass().getResourceAsStream("/weapons/Heatseeker.json"))));

        for (Color color : Color.values()) {
            for (int i = 0; i < SpawnSquare.MAX_WEAPON; i++)
                board.getSpawn(color).addWeapon(weaponList.remove(0));
        }
        currentWeaponDeck = new WeaponDeck(weaponList);
        currentWeaponDeck.shuffle();
    }

    private void initializePowerUps(Parser parser) {
        currentPowerUpDeck = parser.deserialize(new InputStreamReader(
                        getClass().getResourceAsStream("/cards/default_powerup_deck.json")),
                PowerUpDeck.class);
        currentPowerUpDeck.shuffle();
        usedPowerUpDeck = new PowerUpDeck(new ArrayList<>());
    }

    /**
     * Sets the final leaderbaord of the match
     *
     * @param leaderBoard Leaderboard to set
     */
    public void setLeaderBoard(Map<PlayerId, Long> leaderBoard) {
        this.leaderBoard = leaderBoard;
        update();
        views.values().forEach(v -> ((VirtualView) v).setGameOver());
    }

    /**
     * @param id PlayerId of the player to get
     * @return Returns the Player with this playerId
     */
    public Player getPlayer(PlayerId id) {
        for (Player tmp : currentPlayers)
            if (id.equals(tmp.getId())) {
                return tmp;
            }
        return null;
    }

    /**
     * Gets players of the match
     *
     * @return list of the players
     */
    public List<Player> getCurrentPlayers() {
        return currentPlayers;
    }

    /**
     * Gets number of kill shots of a player
     *
     * @param id player to use
     * @return number of kill shots of the player
     */
    public int getPlayerKillshots(PlayerId id) {
        int count = 0;
        for (PlayerId tmp : killshotTrack)
            if (id.equals(tmp))
                count++;
        return count;
    }

    /**
     * Gets the kill shoot track of the match
     *
     * @return kill shoot track of the match
     */
    public List<PlayerId> getKillshotTrack() {
        return killshotTrack;
    }

    /**
     * Decreases deaths counter, if deaths counter is already 0 it can't be decreased.
     *
     * @return true if deaths counter value has been decreased
     */
    boolean decreaseDeathsCounter() {
        if (deathsCounter == 0) {
            return false;
        }
        this.deathsCounter--;
        update();
        return true;
    }

    /**
     * Gets the match's gameboard
     *
     * @return match's board
     */
    public GameBoard getBoard() {
        return board;
    }

    /**
     * Adds player to the match currentPlayers
     *
     * @param player Player to add
     */
    public void addPlayer(Player player) {
        currentPlayers.add(player);
    }

    /**
     * Adds player to the match currentPlayers
     *
     * @param player        Player to add
     * @param viewInterface View linked to {@code player}
     */
    public void addPlayer(Player player, ViewInterface viewInterface) {
        currentPlayers.add(player);
        views.put(player.getId(), viewInterface);
    }

    /**
     * Adds a token to the kill shoot track
     *
     * @param playerId PlayerId of who did the kill
     */
    public void addKillshot(PlayerId playerId) {
        killshotTrack.add(playerId);
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

    /**
     * Removes and return the first powerup from the powerups deck
     *
     * @return Powerup drawn
     */
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

    /**
     * Adds the ammotile to the used ammotiles
     *
     * @param ammoTile ammotile to discard
     */
    public void discard(AmmoTile ammoTile) {
        usedAmmoTileDeck.add(ammoTile);
    }

    /**
     * Adds the powerup to the used powerups
     *
     * @param powerUp powerup to discard
     */
    public void discard(PowerUp powerUp) {
        usedPowerUpDeck.add(powerUp);
    }

    /**
     * Removes the powerup from the used powerups
     *
     * @param powerUp power up to un-discard
     */
    public void undiscard(PowerUp powerUp) {
        usedPowerUpDeck.remove(powerUp);
    }

    /**
     * @return true if the match is in the last turn
     */
    public boolean isLastTurn() {
        return deathsCounter == 0;
    }

    /**
     * @return true if the first player has played in the last turn
     */
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

    /**
     * Sets that the first player has played in the last turn
     */
    public void firstPlayerPlayedLastTurn() {
        this.firstPlayerPlayedLastTurn = true;
    }

    /**
     * Gets views
     *
     * @return the views associated with the player
     */
    public List<ViewInterface> getAllVirtualViews() {
        return new ArrayList<>(views.values());
    }

    /**
     * Gets views
     *
     * @return the views associated with the player
     */
    public Map<PlayerId, ViewInterface> getVirtualViews() {
        return views;
    }

    private void update() {
        views.values().forEach(viewInterface -> viewInterface.update(new MatchView(killshotTrack, deathsCounter, gameBoardNumber, leaderBoard, isLastTurn(), playerOnDuty)));
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

    /**
     * This method sends the current state of the Model to the player that has just reconnected
     *
     * @param player PlayerId of the player to notify
     */
    public void sendModelAfterReconnection(PlayerId player) {
        views.get(player).update(new MatchView(killshotTrack, deathsCounter, gameBoardNumber, leaderBoard, isLastTurn(), playerOnDuty));
        currentPlayers.forEach(p -> views.get(player).update(p.getPlayerView(p.getId().equals(player))));
        board.getSquareList().forEach(s -> views.get(player).update(s.getSquareView()));
        views.get(player).setViewInitializationDone();
    }

    public void setPlayerOnDuty(PlayerId playerOnDuty) {
        this.playerOnDuty = playerOnDuty;
        update();
    }
}
