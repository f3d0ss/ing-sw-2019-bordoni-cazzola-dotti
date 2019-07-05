package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.PlayerId;
import it.polimi.ingsw.utils.Lock;
import it.polimi.ingsw.view.*;
import it.polimi.ingsw.view.commandmessage.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class is the controller of the main GUI
 */
public class MainGuiController {


    static final int WEAPON_HEIGHT = 406;
    static final int WEAPON_WIDTH = 240;
    private static final int WEAPON_SPAWN_MARGIN_RATIO = 30;
    private static final double WEAPON_PREF_SPAWN_MARGIN_RATIO = 10;
    private static final int NUMBER_OF_STANDAR_SKULL = 8;
    private static final int HEIGHT_RATIO_SQUARE_INPUT = 1;
    private static final int WIDTH_RATIO_SQUARE_INPUT = 3;
    private static final int ROW_IN_SQUARE = 2;
    private static final int COL_IN_SQUARE = 3;
    private static final double OPACITY_FOR_SELECTION = 0.5;
    private static final String EXIT = "Esci dal gioco";
    private static final double RADIUS_HIGLIGHT = 0.1;
    private static final int GAMEBOARD_H_GAP_RATIO = 40;
    private static final int GAMEBOARD_V_GAP_RATIO = 40;
    private static final int MARING_FOR_TEXT_COMMAND = 5;
    private static final double OPACITY_FOR_DISCONNECTED_PLAYER = 0.5;
    private static final double MAX_OPACITY = 1;
    @FXML
    private VBox logContainer;
    @FXML
    private PlayerBoardController playerBoardController;
    @FXML
    private GridPane otherPlayerGrid;
    @FXML
    private HBox killShotTrackBoxStandard;
    @FXML
    private HBox killShotTrackBoxExtra;
    @FXML
    private VBox extraCommandContainer;
    @FXML
    private Pane mainPane;
    @FXML
    private GridPane gameBoard;
    @FXML
    private HBox blueSpawnWeapons;
    @FXML
    private HBox redSpawnWeapons;
    @FXML
    private HBox yellowSpawnWeapons;
    @FXML
    private HBox killShotTrackBox;

    private VBox[][] squareBoxes;
    private ModelView modelView;
    private static final String LANG = "IT";
    static final String SPACE = "_";
    private static final String IMAGES_DIR = "/images/";
    static final String IMAGE_EXTENSION = ".png";
    private static final String GAMEBOARD_IMAGES_DIR = IMAGES_DIR + "gameboards/GameBoard00";
    static final String POWERUP_IMAGES_DIR = IMAGES_DIR + "cards/AD_powerups_" + LANG + SPACE;
    static final String WEAPON_IMAGES_DIR = IMAGES_DIR + "cards/AD_weapons_" + LANG + SPACE;
    static final String TOKEN_IMAGES_DIR = IMAGES_DIR + "tokens/";
    private static final String AMMO_TILE_IMAGES_DIR = IMAGES_DIR + "ammotiles/";
    static final String AMMO_IMAGES_DIR = IMAGES_DIR + "ammos/";
    static final String SKULL_IMAGE_URI = IMAGES_DIR + "other/skull" + IMAGE_EXTENSION;
    static final String BACK = "back";
    static final String PLAYERBOARD_IMAGES_DIR = IMAGES_DIR + "playerboards/";
    static final String AGGREGATE_ACTION_FILE_PATTERN = "_aggregate_action" + IMAGE_EXTENSION;
    static final String AGGREGATE_ACTION_FLIPPED_FILE_PATTERN = "_aggregate_action_flipped" + IMAGE_EXTENSION;
    static final String BOARD_FILE_PATTERN = "_board_without_aggregate_action" + IMAGE_EXTENSION;
    static String FLIPPED_BOARD_FILE_PATTERN = "_flipped_board_without_aggregate_action" + IMAGE_EXTENSION;
    private static final String KILL_SHOT_TRACK_URI = IMAGES_DIR + "gameboards/killShootTrack" + IMAGE_EXTENSION;
    private static final String BLUE_SPAWN_WEAPON_URI = IMAGES_DIR + "gameboards/blueSpawn" + IMAGE_EXTENSION;
    private static final String RED_SPAWN_WEAPON_URI = IMAGES_DIR + "gameboards/redSpawn" + IMAGE_EXTENSION;
    private static final String YELLOW_SPAWN_WEAPON_URI = IMAGES_DIR + "gameboards/yellowSpawn" + IMAGE_EXTENSION;
    private Map<PlayerId, PlayerBoardController> otherPlayerBoardControllers = new EnumMap<>(PlayerId.class);
    private List<HBox> clickableNode = new ArrayList<>();
    private List<HBox> clickableSquare = new ArrayList<>();
    private int selectedCommand;
    private int startSkullNumber;
    private Map<String, HBox> weaponsOnSpawnBoxes = new HashMap<>();
    private Map<PlayerId, HBox> playerBoxesOnBoard = new EnumMap<>(PlayerId.class);
    private Map<PlayerId, Button> playerButtons = new EnumMap<>(PlayerId.class);
    private Stage stage;

    /**
     * This method initialize the main content of the GUI
     */
    public void initialize() {
        squareBoxes = new VBox[gameBoard.getRowCount()][gameBoard.getColumnCount()];
        gameBoard.hgapProperty().bind(gameBoard.widthProperty().divide(GAMEBOARD_H_GAP_RATIO));
        gameBoard.vgapProperty().bind(gameBoard.heightProperty().divide(GAMEBOARD_V_GAP_RATIO));
        //set squares
        for (int i = 0; i < gameBoard.getRowCount(); i++) {
            for (int j = 0; j < gameBoard.getColumnCount(); j++) {
                squareBoxes[i][j] = new VBox();
                for (int k = 0; k < ROW_IN_SQUARE; k++) {
                    HBox row = new HBox();
                    bindToParent(row, squareBoxes[i][j], ROW_IN_SQUARE, 1);
                    squareBoxes[i][j].getChildren().add(row);
                    VBox.setVgrow(row, Priority.ALWAYS);
                }
                gameBoard.add(squareBoxes[i][j], j, i);
            }
        }
    }

    /**
     * This method return the index of the selected command
     *
     * @return Index of the selected command
     */
    int getSelectedCommand() {
        return selectedCommand;
    }

    /**
     * This method update the content of the stage based on the data passed
     *
     * @param modelView The data to be displayed
     */
    private void updateModelView(ModelView modelView) {
        this.modelView = modelView;
        updatePlayer(modelView.getMe());
        updateMatchView(modelView.getMatch());
        for (SquareView[] squareViews : modelView.getBoard()) {
            for (SquareView squareView : squareViews) {
                if (squareView != null)
                    updateSquare(squareView);
            }
        }
    }

    /**
     * This method update a square in the stage based on the data passed
     *
     * @param squareView The square to be displayed
     */
    void updateSquare(SquareView squareView) {
        int row = squareView.getRow();
        int col = squareView.getCol();
        squareBoxes[row][col].getChildren().forEach(node -> ((Pane) node).getChildren().clear());
        if (squareView.getColor() == null) {
            HBox ammoBox = new HBox();
            AmmoTileView ammoTileView = ((TurretSquareView) squareView).getAmmoTile();
            String ammoTileURI = AMMO_TILE_IMAGES_DIR
                    + ((!ammoTileView.isEmpty()) ? ammoTileView.toString() : BACK)
                    + IMAGE_EXTENSION;
            setBackgroundImageFromURI(ammoBox, ammoTileURI);
            addToSquareBox(squareBoxes[row][col], ammoBox);
        } else
            printWeaponsOnSpawn(squareView.getColor(), ((SpawnSquareView) squareView).getWeapons());
        squareView.getHostedPlayers().forEach(playerId -> {
            HBox tokenBox = getHBoxWithTokenBackground(playerId);
            addToSquareBox(squareBoxes[row][col], tokenBox);
            playerBoxesOnBoard.put(playerId, tokenBox);
        });
    }

    /**
     * This method update a player in the stage based on the data passed
     *
     * @param playerView The player to be displayed
     */
    void updatePlayer(PlayerView playerView) {
        if (playerView.isMe())
            playerBoardController.update(playerView);
        else {
            if (playerView.isDisconnected()) {
                playerButtons.get(playerView.getId()).setOpacity(OPACITY_FOR_DISCONNECTED_PLAYER);
            }else {
                playerButtons.get(playerView.getId()).setOpacity(MAX_OPACITY);
            }
            if (otherPlayerBoardControllers.containsKey(playerView.getId())) {
                otherPlayerBoardControllers.get(playerView.getId()).update(playerView);
            }
        }
    }

    /**
     * This method update the match in the stage based on the data passed
     *
     * @param matchView The match to be displayed
     */
    void updateMatchView(MatchView matchView) {
        printKillshotTrack(matchView.getKillshotTrack());
        if (matchView.getLeaderBoard() == null && matchView.getPlayerOnDuty() != null && modelView.getMe().getId() != matchView.getPlayerOnDuty()) {
            playerButtons.forEach((playerId, button) -> button.setBorder(Border.EMPTY));
            playerButtons.get(matchView.getPlayerOnDuty()).setBorder(new Border(new BorderStroke(javafx.scene.paint.Color.GREEN,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));
        }
        playerBoardController.update(matchView.isLastTurn());
        otherPlayerBoardControllers.forEach((playerId, playerBoardController1) -> playerBoardController1.update(matchView.isLastTurn()));
    }

    /**
     * Print the killShot track
     *
     * @param killShotTrack The killShot track to be displayed
     */
    private void printKillshotTrack(List<PlayerId> killShotTrack) {
        killShotTrackBoxStandard.getChildren().clear();
        killShotTrackBoxExtra.getChildren().clear();
        int i;
        for (i = 0; i < modelView.getMatch().getDeathsCounter(); i++) {
            HBox skullBox = getHBoxWithSkullBackground(killShotTrackBoxStandard, NUMBER_OF_STANDAR_SKULL);
            killShotTrackBoxStandard.getChildren().add(0, skullBox);
            HBox.setHgrow(skullBox, Priority.ALWAYS);
        }
        for (i = 0; i < killShotTrack.size() && i < startSkullNumber - modelView.getMatch().getDeathsCounter(); i++) {
            HBox killBox = getHBoxWithTokenBackground(killShotTrack.get(i));
            killBox.setPrefHeight(killShotTrackBoxStandard.getPrefHeight());
            killBox.maxWidthProperty().bind(killShotTrackBoxStandard.widthProperty().divide(NUMBER_OF_STANDAR_SKULL));
            HBox.setMargin(killBox, new Insets(0, 0, 0, 1));
            killShotTrackBoxStandard.getChildren().add(0, killBox);
            HBox.setHgrow(killBox, Priority.ALWAYS);
        }
        int remainingKill = killShotTrack.size() - i;
        while (i < killShotTrack.size()) {
            HBox killBox = getHBoxWithTokenBackground(killShotTrack.get(i++));
            killBox.setPrefHeight(killShotTrackBoxExtra.getPrefHeight());
            killBox.maxWidthProperty().bind(killShotTrackBoxExtra.widthProperty().divide(remainingKill));
            killShotTrackBoxExtra.getChildren().add(killBox);
            HBox.setHgrow(killBox, Priority.ALWAYS);
        }
    }

    /**
     * Retrurn a HBox with the background image of the player's token
     *
     * @param playerId The ID of the player's token
     * @return The HBox with the background image
     */
    private HBox getHBoxWithTokenBackground(PlayerId playerId) {
        HBox tokenBox = new HBox();
        String tokenImageURI = TOKEN_IMAGES_DIR
                + playerId.playerId()
                + IMAGE_EXTENSION;
        setBackgroundImageFromURI(tokenBox, tokenImageURI);
        return tokenBox;
    }

    /**
     * Show the weapon of a single spawn
     *
     * @param color       Thw Color representing the spawn
     * @param weaponViews The weapons available in the spawn
     */
    private void printWeaponsOnSpawn(Color color, List<WeaponView> weaponViews) {
        HBox spawn = new HBox();
        switch (color) {
            case RED:
                spawn = redSpawnWeapons;
                break;
            case BLUE:
                spawn = blueSpawnWeapons;
                break;
            case YELLOW:
                spawn = yellowSpawnWeapons;
                break;
        }
        spawn.getChildren().clear();
        HBox finalSpawn = spawn;
        weaponViews.forEach(weaponView -> {
            HBox weaponBox = new HBox();
            bindHeightToParent(weaponBox, finalSpawn, WEAPON_HEIGHT, WEAPON_WIDTH);
            String weaponImageURI = WEAPON_IMAGES_DIR
                    + weaponView.getID()
                    + IMAGE_EXTENSION;
            setBackgroundImageFromURI(weaponBox, weaponImageURI);
            HBox.setMargin(weaponBox, new Insets(0, (finalSpawn.getWidth() != 0) ? finalSpawn.getWidth() / WEAPON_SPAWN_MARGIN_RATIO : finalSpawn.getPrefWidth() / WEAPON_PREF_SPAWN_MARGIN_RATIO, 0, (finalSpawn.getWidth() != 0) ? finalSpawn.getWidth() / WEAPON_SPAWN_MARGIN_RATIO : finalSpawn.getPrefWidth() / WEAPON_PREF_SPAWN_MARGIN_RATIO));
            finalSpawn.getChildren().add(weaponBox);
            HBox.setHgrow(weaponBox, Priority.ALWAYS);
            weaponsOnSpawnBoxes.put(weaponView.getName(), weaponBox);
        });
    }

    /**
     * Event handler for the button of the other player
     *
     * @param playerId The ID of the player associated with the button
     */
    private void handlePlayerButton(PlayerId playerId) {
        try {
            if (otherPlayerBoardControllers.get(playerId) != null) {
                return;
            }
            Stage otherPlayerStage = new Stage();
            PlayerBoardController controller = new PlayerBoardController(false, otherPlayerStage);
            otherPlayerBoardControllers.put(playerId, controller);
            otherPlayerBoardControllers.get(playerId).setPlayer(modelView.getEnemies().get(playerId), modelView.getMatch().isLastTurn());
            otherPlayerStage.setScene(new Scene(controller));
            otherPlayerStage.setAlwaysOnTop(true);
            otherPlayerStage.setOnCloseRequest(windowEvent -> otherPlayerBoardControllers.remove(playerId));
            otherPlayerStage.setTitle(playerId.playerIdName());
            otherPlayerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Event handler for the click of something associated with a command
     *
     * @param index The index of the command
     * @param lock  The lock used to let the other thread get the index of the command
     */
    private void handleCommandClick(int index, Lock lock) {
        clickableNode.forEach(this::setNodeUnClickable);
        clickableSquare.forEach(this::setSquareUnClickable);
        clickableNode.clear();
        clickableSquare.clear();
        extraCommandContainer.getChildren().clear();
        selectedCommand = index;
        lock.unlock();
    }

    /**
     * Set a node clickable and associate it with a command index
     *
     * @param node         The node that need to become clickable
     * @param commandIndex The index of the command
     * @param lock         The lock used to let the other thread get the index of the command
     */
    private void setNodeClickable(HBox node, int commandIndex, Lock lock) {
        HBox overlay = new HBox();
        overlay.setFocusTraversable(true);
        overlay.setBackground((new Background(new BackgroundFill(javafx.scene.paint.Color.YELLOW, new CornerRadii(RADIUS_HIGLIGHT), Insets.EMPTY))));
        overlay.setOpacity(OPACITY_FOR_SELECTION);
        overlay.setOnMouseClicked(mouseEvent -> handleCommandClick(commandIndex, lock));
        HBox.setHgrow(overlay, Priority.ALWAYS);
        clickableNode.add(node);
        if (!node.getChildren().isEmpty()) {
            overlay.getChildren().setAll(node.getChildrenUnmodifiable());
        }
        node.getChildren().setAll(overlay);
    }

    /**
     * Set a square clickable and associate it with a command index
     *
     * @param square       The square that need to become clickable
     * @param commandIndex The index of the command
     * @param lock         The lock used to let the other thread get the index of the command
     */
    private void setSquareClickable(VBox square, int commandIndex, Lock lock) {
        HBox overlay = new HBox();
        overlay.setBackground((new Background(new BackgroundFill(javafx.scene.paint.Color.YELLOW, new CornerRadii(RADIUS_HIGLIGHT), Insets.EMPTY))));
        overlay.setOpacity(OPACITY_FOR_SELECTION);
        overlay.setOnMouseClicked(mouseEvent -> handleCommandClick(commandIndex, lock));
        overlay.setFocusTraversable(true);
        clickableSquare.add(addToSquareBox(square, overlay));
    }

    /**
     * Restore the square as before it became clickable
     *
     * @param square The square to restore
     */
    private void setSquareUnClickable(HBox square) {
        square.getChildren().remove(square.getChildren().size() - 1);
    }

    /**
     * Restore the node as before it became clickable
     *
     * @param node The node to restore
     */
    private void setNodeUnClickable(HBox node) {
        node.getChildren().setAll(((HBox) node.getChildren().get(0)).getChildren());
    }

    /**
     * Return the root of the stage
     *
     * @return The root of the stage
     */
    Pane getRoot() {
        return mainPane;
    }

    /**
     * Display commands
     *
     * @param commands List of commands to be shown
     * @param undo     True when it need to show the undo
     * @param lock     The lock used to let the other thread get the index of the command
     */
    void showCommand(List<CommandMessage> commands, boolean undo, Lock lock) {
        int i;
        for (i = 0; i < commands.size(); i++) {
            switch (commands.get(i).getType()) {
                case SELECT_AMMO_PAYMENT:
                    setNodeClickable(playerBoardController.getAmmoBox(((ColorCommandMessage) commands.get(i)).getColor()), i, lock);
                    break;
                case SELECT_BUYING_WEAPON:
                    setNodeClickable(weaponsOnSpawnBoxes.get(((WeaponCommandMessage) commands.get(i)).getWeapon()), i, lock);
                    break;
                case SELECT_DISCARD_WEAPON:
                case SELECT_RELOADING_WEAPON:
                case SELECT_WEAPON:
                    setNodeClickable(playerBoardController.getWeaponBox(((WeaponCommandMessage) commands.get(i)).getWeapon()), i, lock);
                    break;
                case MOVE:
                case SELECT_TARGET_SQUARE:
                case USE_TELEPORT:
                case USE_NEWTON:
                    setSquareClickable(squareBoxes[((SquareCommandMessage) commands.get(i)).getRow()][((SquareCommandMessage) commands.get(i)).getCol()], i, lock);
                    break;
                case SELECT_TARGET_PLAYER:
                    setNodeClickable(playerBoxesOnBoard.get(((PlayerCommandMessage) commands.get(i)).getPlayerId()), i, lock);
                    break;
                case RESPAWN:
                case SELECT_POWER_UP:
                case SELECT_POWER_UP_PAYMENT:
                case SELECT_SCOPE:
                case USE_TAGBACK_GRENADE:
                    setNodeClickable(playerBoardController.getPowerUpBox(((PowerUpCommandMessage) commands.get(i)).getPowerUpID(), ((PowerUpCommandMessage) commands.get(i)).getColor()), i, lock);
                    break;
                case SELECT_AGGREGATE_ACTION:
                    showTextCommand(((AggregateActionCommandMessage) commands.get(i)).getAggregateActionID().toString(), i, lock);
                    break;
                case SELECT_WEAPON_MODE:
                    showTextCommand(commands.get(i).getType().getString() + ((WeaponModeCommandMessage) commands.get(i)).getWeaponMode(), i, lock);
                    break;
                case SHOOT:
                    ShootCommandMessage shootCommandMessage = (ShootCommandMessage) commands.get(i);
                    showTextCommand(shootCommandMessage.printCommand(), i, lock);
                    break;
                default:
                    showTextCommand(commands.get(i).getType().getString(), i, lock);
            }
        }
        if (undo)
            showTextCommand(CommandType.UNDO.getString(), i, lock);
    }

    /**
     * Show the command that need to be displayed as text
     *
     * @param command      The command
     * @param commandIndex The index of the command
     * @param lock         The lock used to let the other thread get the index of the command
     */
    private void showTextCommand(String command, int commandIndex, Lock lock) {
        Label commandLabel = createCommandLabel(command);
        commandLabel.setOnMouseClicked(actionEvent -> handleCommandClick(commandIndex, lock));
        extraCommandContainer.getChildren().add(commandLabel);
    }

    /**
     * Create the label for a command to be shown
     *
     * @param command The command
     * @return The label
     */
    private Label createCommandLabel(String command) {
        Label commandLabel = new Label(command);
        commandLabel.setWrapText(true);
        commandLabel.maxWidthProperty().bind(extraCommandContainer.widthProperty());
        commandLabel.setPadding(new Insets(MARING_FOR_TEXT_COMMAND));
        return commandLabel;
    }

    /**
     * Set for the first time the data to be displayed in the UI
     *
     * @param modelView The data to be shown
     */
    void setModelView(ModelView modelView) {
        this.modelView = modelView;
        startSkullNumber = modelView.getMatch().getDeathsCounter();
        String gameboardImageURI = GAMEBOARD_IMAGES_DIR + modelView.getMatch().getGameBoardId() + IMAGE_EXTENSION;
        setBackgroundImageFromURI(gameBoard, gameboardImageURI);

        playerBoardController.setPlayer(modelView.getMe(), modelView.getMatch().isLastTurn());

        setBackgroundImageFromURI(killShotTrackBox, KILL_SHOT_TRACK_URI);

        setBackgroundImageFromURI(blueSpawnWeapons, BLUE_SPAWN_WEAPON_URI);

        setBackgroundImageFromURI(redSpawnWeapons, RED_SPAWN_WEAPON_URI);

        setBackgroundImageFromURI(yellowSpawnWeapons, YELLOW_SPAWN_WEAPON_URI);

        AtomicInteger i = new AtomicInteger(0);
        AtomicInteger j = new AtomicInteger(0);
        modelView.getEnemies().forEach((playerId, playerView) -> {
            Button playerButton = new Button(playerId.playerIdName() + "\n(" + playerView.getNickname() + ")");
            playerButton.setOnMouseClicked(mouseEvent -> handlePlayerButton(playerId));
            bindToParent(playerButton, otherPlayerGrid, otherPlayerGrid.getRowCount(), otherPlayerGrid.getColumnCount());
            otherPlayerGrid.add(playerButton, i.getAndIncrement() % otherPlayerGrid.getColumnCount(), j.getAndIncrement() / otherPlayerGrid.getColumnCount());
            playerButtons.put(playerId, playerButton);
        });

        mainPane.setOnMouseClicked(mouseEvent -> {
            otherPlayerBoardControllers.forEach((playerId1, otherPlayerBoardController) -> otherPlayerBoardController.close());
            otherPlayerBoardControllers.clear();
        });

        killShotTrackBoxStandard.setAlignment(Pos.CENTER_RIGHT);

        updateModelView(modelView);
    }

    /**
     * Return the instance of this controller
     *
     * @return The instance of this controller
     */
    static MainGuiController getInstance() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainGuiController.class.getResource("/fx/MainGui.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fxmlLoader.getController();
    }

    /**
     * Bind the size of the child to the parent with the specified ratio
     *
     * @param child       The child
     * @param parent      The parent
     * @param heightRatio The ratio for the height
     * @param widthRatio  The ratio for the width
     */
    static void bindToParent(Region child, Pane parent, int heightRatio, int widthRatio) {
        child.maxHeightProperty().bind(parent.heightProperty().divide(heightRatio));
        child.maxWidthProperty().bind(parent.widthProperty().divide(widthRatio));
        HBox.setMargin(child, new Insets(0, 0, 1, 0));
    }

    /**
     * Return an HBox with the skull image as background with height fixed to the parent and width fixed to the parent with a specified ratio
     *
     * @param parent     The parent of the HBox
     * @param widthRatio The ratio of the width
     * @return The HBox created
     */
    private static HBox getHBoxWithSkullBackground(Pane parent, int widthRatio) {
        HBox tokenBox = new HBox();
        tokenBox.setPrefHeight(parent.getPrefHeight());
        tokenBox.maxWidthProperty().bind(parent.widthProperty().divide(widthRatio));
        HBox.setMargin(tokenBox, new Insets(0, 0, 0, 1));
        setBackgroundImageFromURI(tokenBox, SKULL_IMAGE_URI);
        return tokenBox;
    }

    /**
     * Return an HBox square with the height fixed to parent and background of player's token
     *
     * @param playerId The ID of the player
     * @param parent   The parent of the HBox
     * @return The HBox with the height fixed to parent and background of player's token
     */
    static HBox getHBoxWithTokenBackgroundWithHighFixedToParent(PlayerId playerId, Pane parent) {
        HBox hbox = new HBox();
        bindHeightToParent(hbox, parent);
        String tokenImageURI = TOKEN_IMAGES_DIR
                + playerId.playerId()
                + IMAGE_EXTENSION;
        setBackgroundImageFromURI(hbox, tokenImageURI);
        return hbox;
    }

    /**
     * Set the image pointed by the URI as background of the pane
     *
     * @param pane The pane
     * @param uri  The URI
     */
    static void setBackgroundImageFromURI(Pane pane, String uri) {
        Image image = ImageCache.getImage(uri);
        pane.setBackground(new Background(new BackgroundFill(new ImagePattern(image), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    /**
     * Set the height of child as his parent and bind width to be the right ratio
     *
     * @param child       is the Pane that will be insert in the parent
     * @param parent      is the parent Pane
     * @param childHeight is the eight of the child
     * @param childWidth  is the wanted width with the child childHeight
     */
    static void bindHeightToParent(Pane child, Pane parent, int childHeight, int childWidth) {
        child.setPrefHeight(parent.getPrefHeight());
        child.maxWidthProperty().bind(child.heightProperty().divide(childHeight).multiply(childWidth));
        HBox.setMargin(child, new Insets(0, 0, 0, 1));
    }

    /**
     * Set the height of child as his parent and fix width as height
     *
     * @param child  is the Pane that will be insert in the parent
     * @param parent is the parent Pane
     */
    private static void bindHeightToParent(Pane child, Pane parent) {
        child.setPrefHeight(parent.getPrefHeight());
        child.maxWidthProperty().bind(child.heightProperty());
        HBox.setMargin(child, new Insets(0, 0, 0, 1));
    }

    /**
     * Show the leader board
     *
     * @param leaderBoard The leader board to be shown
     */
    void showLeaderBoard(Map<PlayerId, Long> leaderBoard) {
        int i = 1;
        String playerRecord;
        for (Map.Entry<PlayerId, Long> entry : leaderBoard.entrySet()) {
            playerRecord = i + "° classificato: " + entry.getKey().playerIdName() + " con " + entry.getValue() + " punti";
            Text leaderBoardText = new Text(playerRecord);
            if (i == 1)
                leaderBoardText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            if (modelView.getPlayerView(entry.getKey()).isDisconnected())
                leaderBoardText.setStrikethrough(true);
            leaderBoardText.wrappingWidthProperty().bind(logContainer.widthProperty());
            logContainer.getChildren().add(leaderBoardText);
            i++;
        }
        Label commandLabel = createCommandLabel(EXIT);
        commandLabel.setOnMouseClicked(actionEvent -> {
            stage.close();
            System.exit(0);
        });
        extraCommandContainer.getChildren().add(commandLabel);
    }

    /**
     * Add the elementToAdd to the squareBox
     *
     * @param squareBox    The squareBox
     * @param elementToAdd The element to add
     * @return The row of the square where the element is added
     */
    private HBox addToSquareBox(VBox squareBox, Pane elementToAdd) {
        for (Node child : squareBox.getChildren()) {
            if (((HBox) child).getChildren().size() < COL_IN_SQUARE) {
                bindToParent(elementToAdd, ((HBox) child), HEIGHT_RATIO_SQUARE_INPUT, WIDTH_RATIO_SQUARE_INPUT);
                ((HBox) child).getChildren().add(elementToAdd);
                HBox.setHgrow(elementToAdd, Priority.ALWAYS);
                return ((HBox) child);
            }
        }
        bindToParent(elementToAdd, ((HBox) squareBox.getChildren().get(squareBox.getChildren().size() - 1)), HEIGHT_RATIO_SQUARE_INPUT, WIDTH_RATIO_SQUARE_INPUT);
        ((HBox) squareBox.getChildren().get(squareBox.getChildren().size() - 1)).getChildren().add(elementToAdd);
        HBox.setHgrow(elementToAdd, Priority.ALWAYS);
        return ((HBox) squareBox.getChildren().get(squareBox.getChildren().size() - 1));
    }

    /**
     * Set the stage
     *
     * @param stage The stage
     */
    void setStage(Stage stage) {
        this.stage = stage;
    }


}
