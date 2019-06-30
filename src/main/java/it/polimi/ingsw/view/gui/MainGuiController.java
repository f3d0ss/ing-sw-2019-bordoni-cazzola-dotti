package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.PlayerId;
import it.polimi.ingsw.utils.Lock;
import it.polimi.ingsw.view.*;
import it.polimi.ingsw.view.commandmessage.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MainGuiController {


    static final int WEAPON_HEIGHT = 406;
    static final int WEAPON_WIDTH = 240;
    private static final int WEAPON_SPAWN_MARGIN_RATIO = 33;
    private static final int NUMBER_OF_STANDAR_SKULL = 8;
    private static final int HEIGHT_RATIO_SQUARE_INPUT = 2;
    private static final int WIDTH_RATIO_SQUARE_INPUT = 3;
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
    private VBox right;
    @FXML
    private HBox blueSpawnWeapons;
    @FXML
    private HBox redSpawnWeapons;
    @FXML
    private HBox yellowSpawnWeapons;
    @FXML
    private HBox killShotTrackBox;

    private HBox[][] squareBoxes;
    private ModelView modelView;
    private static final String LANG = "IT";
    static final String SPACE = "_";
    private static final String IMAGES_DIR = "/images/";
    static final String IMAGE_EXTENSION = ".png";
    private static final String GAMEBOARD_IMAGES_DIR = IMAGES_DIR + "gameboards/GameBoard00";
    static final String POWERUP_IMAGES_DIR = IMAGES_DIR + "cards/AD_powerups_" + LANG + SPACE;
    static final String WEAPON_IMAGES_DIR = IMAGES_DIR + "cards/AD_weapons_" + LANG + SPACE;
    private static final String TOKEN_IMAGES_DIR = IMAGES_DIR + "tokens/";
    private static final String AMMO_TILE_IMAGES_DIR = IMAGES_DIR + "ammotiles/";
    static final String AMMO_IMAGES_DIR = IMAGES_DIR + "ammos/";
    private static final String SKULL_IMAGE_URI = IMAGES_DIR + "other/skull" + IMAGE_EXTENSION;
    static final String BACK = "back";
    static final String PLAYERBOARD_IMAGES_DIR = IMAGES_DIR + "playerboards/";
    static final String AGGREGATE_ACTION_FILE_PATTERN = "_aggregate_action" + IMAGE_EXTENSION;
    static final String AGGREGATE_ACTION_FLIPPED_FILE_PATTERN = "_aggregate_action_flipped" + IMAGE_EXTENSION;
    static final String BOARD_FILE_PATTERN = "_board_without_aggregate_action" + IMAGE_EXTENSION;
    private static final String KILL_SHOT_TRACK_URI = IMAGES_DIR + "gameboards/killShootTrack" + IMAGE_EXTENSION;
    private static final String BLUE_SPAWN_WEAPON_URI = IMAGES_DIR + "gameboards/blueSpawn" + IMAGE_EXTENSION;
    private static final String RED_SPAWN_WEAPON_URI = IMAGES_DIR + "gameboards/redSpawn" + IMAGE_EXTENSION;
    private static final String YELLOW_SPAWN_WEAPON_URI = IMAGES_DIR + "gameboards/yellowSpawn" + IMAGE_EXTENSION;
    private Map<PlayerId, PlayerBoardController> otherPlayerBoardControllers = new EnumMap<>(PlayerId.class);
    private List<HBox> clickableNode = new ArrayList<>();
    private int selectedCommand;
    private int startSkullNumber;
    private Map<String, HBox> weaponsOnSpawnBoxes = new HashMap<>();
    private Map<PlayerId, HBox> playerBoxes = new EnumMap<>(PlayerId.class);

    public void initialize() {
        squareBoxes = new HBox[gameBoard.getRowCount()][gameBoard.getColumnCount()];
        //set squares
        for (int i = 0; i < gameBoard.getRowCount(); i++) {
            for (int j = 0; j < gameBoard.getColumnCount(); j++) {
                squareBoxes[i][j] = new HBox();
                gameBoard.add(squareBoxes[i][j], j, i);
            }
        }
    }

    public int getSelectedCommand() {
        return selectedCommand;
    }

    public void updateModelView(ModelView modelView) {
        this.modelView = modelView;
        printSpawnWeapons(modelView.getWeaponsOnSpawn());
        printKillshotTrack(modelView.getMatch().getKillshotTrack());
        playerBoardController.printPlayerBoard(modelView.getMe());
        printBoard(modelView.getBoard());
        otherPlayerBoardControllers.forEach((playerId, otherPlayerBoardController) -> otherPlayerBoardController.update(modelView.getEnemies().get(playerId)));
    }

    private void printBoard(SquareView[][] board) {
        playerBoxes.clear();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != null) {
                    squareBoxes[i][j].getChildren().clear();
                    if (board[i][j].getColor() == null) {
                        HBox ammoBox = new HBox();
                        AmmoTileView ammoTileView = ((TurretSquareView) board[i][j]).getAmmoTile();
                        String ammoTileURI = AMMO_TILE_IMAGES_DIR
                                + ((!ammoTileView.isEmpty()) ? ammoTileView.toString() : BACK)
                                + IMAGE_EXTENSION;
                        setBackgroundImageFromURI(ammoBox, ammoTileURI);
                        bindToParent(ammoBox, squareBoxes[i][j], HEIGHT_RATIO_SQUARE_INPUT, WIDTH_RATIO_SQUARE_INPUT);
                        squareBoxes[i][j].getChildren().add(ammoBox);
                        HBox.setHgrow(ammoBox, Priority.ALWAYS);
                    }
                    int finalI = i;
                    int finalJ = j;
                    board[i][j].getHostedPlayers().forEach(playerId -> {
                        HBox tokenBox = getHBoxWithTokenBackground(playerId);
                        bindToParent(tokenBox, squareBoxes[finalI][finalJ], HEIGHT_RATIO_SQUARE_INPUT, WIDTH_RATIO_SQUARE_INPUT);
                        squareBoxes[finalI][finalJ].getChildren().add(tokenBox);
                        HBox.setHgrow(tokenBox, Priority.ALWAYS);
                        playerBoxes.put(playerId, tokenBox);
                    });
                }
            }
        }
    }

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

    private HBox getHBoxWithTokenBackground(PlayerId playerId) {
        HBox tokenBox = new HBox();
        String tokenImageURI = TOKEN_IMAGES_DIR
                + playerId.playerId()
                + IMAGE_EXTENSION;
        setBackgroundImageFromURI(tokenBox, tokenImageURI);
        return tokenBox;
    }

    private void printSpawnWeapons(Map<Color, List<WeaponView>> weaponsOnSpawn) {
        weaponsOnSpawnBoxes.clear();
        weaponsOnSpawn.forEach((color, weaponViews) -> {
            switch (color) {
                case RED:
                    printWeaponsOnSpawn(redSpawnWeapons, weaponViews);
                    break;
                case BLUE:
                    printWeaponsOnSpawn(blueSpawnWeapons, weaponViews);
                    break;
                case YELLOW:
                    printWeaponsOnSpawn(yellowSpawnWeapons, weaponViews);
                    break;
            }
        });
    }

    private void printWeaponsOnSpawn(HBox spawn, List<WeaponView> weaponViews) {
        spawn.getChildren().clear();
        weaponViews.forEach(weaponView -> {
            HBox weaponBox = new HBox();
            bindHeightToParent(weaponBox, spawn, WEAPON_HEIGHT, WEAPON_WIDTH);
            String weaponImageURI = WEAPON_IMAGES_DIR
                    + weaponView.getID()
                    + IMAGE_EXTENSION;
            setBackgroundImageFromURI(weaponBox, weaponImageURI);
            HBox.setMargin(weaponBox, new Insets(0, spawn.getWidth() / WEAPON_SPAWN_MARGIN_RATIO, 0, spawn.getWidth() / WEAPON_SPAWN_MARGIN_RATIO));
            spawn.getChildren().add(weaponBox);
            HBox.setHgrow(weaponBox, Priority.ALWAYS);
            weaponsOnSpawnBoxes.put(weaponView.getName(), weaponBox);
        });
    }

    private void handlePlayerButton(PlayerId playerId) {

        try {
            if (otherPlayerBoardControllers.get(playerId) != null) {
                return;
            }
            Stage stage = new Stage();
            PlayerBoardController controller = new PlayerBoardController(false, stage);
            otherPlayerBoardControllers.put(playerId, controller);
            otherPlayerBoardControllers.get(playerId).setPlayer(modelView.getEnemies().get(playerId));
            stage.setScene(new Scene(controller));
            stage.setAlwaysOnTop(true);
            stage.setOnCloseRequest(windowEvent -> otherPlayerBoardControllers.remove(playerId));
            stage.setTitle(playerId.toString());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void handleCommandClick(int index, Lock lock) {
        clickableNode.forEach(this::setNodeUnClickable);
        clickableNode.clear();
        extraCommandContainer.getChildren().clear();
        selectedCommand = index;
        lock.unlock();
    }

    public void setNodeClickable(HBox node, int commandIndex, Lock lock) {
        HBox overlay = new HBox();
        overlay.setStyle("-fx-background-color: yellow; -fx-background-radius: 10; -fx-opacity: 0.5");
        overlay.setOnMouseClicked(mouseEvent -> handleCommandClick(commandIndex, lock));
        if (!node.getChildren().isEmpty())
            overlay.getChildren().setAll(node.getChildrenUnmodifiable());
        node.getChildren().setAll(overlay);
        HBox.setHgrow(overlay, Priority.ALWAYS);
        clickableNode.add(node);
    }

    public void setNodeUnClickable(HBox node) {
        node.getChildren().setAll(((HBox) node.getChildren().get(0)).getChildren());
    }

    public Pane getRoot() {
        return mainPane;
    }

    public void showCommand(List<CommandMessage> commands, boolean undo, Lock lock) {
        int i;
        for (i = 0; i < commands.size(); i++) {
            Text commandText;
            int finalI = i;
            switch (commands.get(i).getType()) {
                case SELECT_AGGREGATE_ACTION:
                    commandText = new Text(((AggregateActionCommandMessage) commands.get(i)).getAggregateActionID().toString());
                    commandText.setOnMouseClicked(actionEvent -> handleCommandClick(finalI, lock));
                    extraCommandContainer.getChildren().add(commandText);
                    break;
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
                    setNodeClickable(squareBoxes[((SquareCommandMessage) commands.get(i)).getRow()][((SquareCommandMessage) commands.get(i)).getCol()], i, lock);
                    break;
                case SELECT_TARGET_PLAYER:
                    setNodeClickable(playerBoxes.get(((PlayerCommandMessage) commands.get(i)).getPlayerId()), i, lock);
                    break;
                case RESPAWN:
                case SELECT_POWER_UP:
                case SELECT_POWER_UP_PAYMENT:
                case SELECT_SCOPE:
                    setNodeClickable(playerBoardController.getPowerUpBox(((PowerUpCommandMessage) commands.get(i)).getPowerUpID(), ((PowerUpCommandMessage) commands.get(i)).getColor()), i, lock);
                    break;
                default:
                    commandText = new Text(commands.get(i).getType().getString());
                    commandText.setOnMouseClicked(actionEvent -> handleCommandClick(finalI, lock));
                    extraCommandContainer.getChildren().add(commandText);
            }
        }
        if (undo) {
            Text commandText = new Text(CommandType.UNDO.getString());
            int finalI1 = i;
            commandText.setOnMouseClicked(actionEvent -> handleCommandClick(finalI1, lock));
            extraCommandContainer.getChildren().add(commandText);
        }
    }

    public void setModelView(ModelView modelView) {
        this.modelView = modelView;
        startSkullNumber = modelView.getMatch().getDeathsCounter();
        String gameboardImageURI = GAMEBOARD_IMAGES_DIR + modelView.getMatch().getGameBoardId() + IMAGE_EXTENSION;
        setBackgroundImageFromURI(gameBoard, gameboardImageURI);


        playerBoardController.setPlayer(modelView.getMe());

        setBackgroundImageFromURI(killShotTrackBox, KILL_SHOT_TRACK_URI);

        setBackgroundImageFromURI(blueSpawnWeapons, BLUE_SPAWN_WEAPON_URI);

        setBackgroundImageFromURI(redSpawnWeapons, RED_SPAWN_WEAPON_URI);

        setBackgroundImageFromURI(yellowSpawnWeapons, YELLOW_SPAWN_WEAPON_URI);

        AtomicInteger i = new AtomicInteger(0);
        AtomicInteger j = new AtomicInteger(0);
        modelView.getEnemies().forEach((playerId, playerView) -> {
            Button playerButton = new Button(playerId.toString());
            playerButton.setOnMouseClicked(mouseEvent -> handlePlayerButton(playerId));
            bindToParent(playerButton, otherPlayerGrid, otherPlayerGrid.getRowCount(), otherPlayerGrid.getColumnCount());
            otherPlayerGrid.add(playerButton, i.getAndIncrement() % otherPlayerGrid.getColumnCount(), j.getAndIncrement() / otherPlayerGrid.getColumnCount());
        });

        mainPane.setOnMouseClicked(mouseEvent -> {
            otherPlayerBoardControllers.forEach((playerId1, otherPlayerBoardController) -> otherPlayerBoardController.close());
            otherPlayerBoardControllers.clear();
        });

        updateModelView(modelView);
    }

    public static MainGuiController getInstance() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainGuiController.class.getResource("/fx/MainGui.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fxmlLoader.getController();
    }

    static void bindToParent(Region child, Pane parent, int heightRatio, int widthRatio) {
        child.maxHeightProperty().bind(parent.heightProperty().divide(heightRatio));
        child.maxWidthProperty().bind(parent.widthProperty().divide(widthRatio));
        HBox.setMargin(child, new Insets(0, 0, 0, 1));
    }

    static HBox getHBoxWithSkullBackground(Pane parent, int widthRatio) {
        HBox tokenBox = new HBox();
        tokenBox.setPrefHeight(parent.getPrefHeight());
        tokenBox.maxWidthProperty().bind(parent.widthProperty().divide(widthRatio));
        HBox.setMargin(tokenBox, new Insets(0, 0, 0, 1));
        setBackgroundImageFromURI(tokenBox, SKULL_IMAGE_URI);
        return tokenBox;
    }

    static HBox getHBoxWithTokenBackgroundWithHighFixedToParent(PlayerId playerId, Pane parent) {
        HBox hbox = new HBox();
        bindHeightToParent(hbox, parent);
        String tokenImageURI = TOKEN_IMAGES_DIR
                + playerId.playerId()
                + IMAGE_EXTENSION;
        setBackgroundImageFromURI(hbox, tokenImageURI);
        return hbox;
    }

    static void setBackgroundImageFromURI(Pane pane, String uri) {
        Image image = new Image(MainGuiController.class.getResource(uri).toExternalForm());
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


}
