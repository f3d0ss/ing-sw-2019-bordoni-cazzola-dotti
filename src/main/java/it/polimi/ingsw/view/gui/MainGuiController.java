package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerId;
import it.polimi.ingsw.utils.Lock;
import it.polimi.ingsw.view.*;
import it.polimi.ingsw.view.commandmessage.AggregateActionCommandMessage;
import it.polimi.ingsw.view.commandmessage.CommandMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MainGuiController {


    private static final int MAX_SKULL_PLAYERBOARD = 6;
    private static final int POWERUP_HEIGHT = 264;
    private static final int POWERUP_WIDTH = 169;
    private static final int WEAPON_HEIGHT = 406;
    private static final int WEAPON_WIDTH = 240;
    private static final int WEAPON_SPAWN_MARGIN_RATIO = 33;
    private static final int PLAYER_LIFE = 12;
    private static final int NUMBER_OF_STANDAR_SKULL = 8;
    private static final int MAX_NUMBER_PLAYER_DEATH_SKULL = 6;
    private static final int HEIGHT_RATIO_SQUARE_INPUT = 2;
    private static final int WIDTH_RATIO_SQUARE_INPUT = 3;
    @FXML
    private VBox playerAmmo;
    @FXML
    private GridPane otherPlayerGrid;
    @FXML
    private HBox playerAggregateActionMMM;
    @FXML
    private HBox playerAggregateActionMG;
    @FXML
    private HBox playerAggregateActionS;
    @FXML
    private HBox playerActionReload;
    @FXML
    private VBox aggregateActionBox;
    @FXML
    private HBox boardWithoutAggregateAction;
    @FXML
    private HBox playerPowerUpContainer;
    @FXML
    private HBox playerWeaponContainer;
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
    @FXML
    private HBox playerBoard;
    @FXML
    private HBox playerAggregateActionMMG;
    @FXML
    private HBox playerAggregateActionMS;
    @FXML
    private HBox playerHealthBox;
    @FXML
    private HBox playerDeaths;
    @FXML
    private HBox playerMarks;
    private HBox[][] squareBoxes;
    private ModelView modelView;
    private static final String LANG = "IT";
    private static final String SPACE = "_";
    private static final String IMAGES_DIR = "/images/";
    private static final String IMAGE_EXTENSION = ".png";
    private static final String GAMEBOARD_IMAGES_DIR = IMAGES_DIR + "gameboards/GameBoard00";
    private static final String POWERUP_IMAGES_DIR = IMAGES_DIR + "cards/AD_powerups_" + LANG + SPACE;
    private static final String WEAPON_IMAGES_DIR = IMAGES_DIR + "cards/AD_weapons_" + LANG + SPACE;
    private static final String TOKEN_IMAGES_DIR = IMAGES_DIR + "tokens/";
    private static final String AMMO_TILE_IMAGES_DIR = IMAGES_DIR + "ammotiles/";
    private static final String AMMO_IMAGES_DIR = IMAGES_DIR + "ammos/";
    private static final String SKULL_IMAGE_URI = IMAGES_DIR + "other/skull" + IMAGE_EXTENSION;
    private static final String AMMO_TILE_BACK = "back";
    static final String PLAYERBOARD_IMAGES_DIR = IMAGES_DIR + "playerboards/";
    static final String AGGREGATE_ACTION_FILE_PATTERN = "_aggregate_action" + IMAGE_EXTENSION;
    static final String AGGREGATE_ACTION_FLIPPED_FILE_PATTERN = "_aggregate_action_flipped" + IMAGE_EXTENSION;
    static final String BOARD_FILE_PATTERN = "_board_without_aggregate_action" + IMAGE_EXTENSION;
    private int selectedCommand;
    private int startSkullNumber;
    private Stage otherPlayerStage;

    public void initialize() {
        squareBoxes = new HBox[gameBoard.getRowCount()][gameBoard.getColumnCount()];
        //inizialize squares
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
        printPlayerBoard(modelView.getMe());
        printBoard(modelView.getBoard());
    }

    private void printBoard(SquareView[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != null) {
                    squareBoxes[i][j].getChildren().clear();
                    if (board[i][j].getColor() == null) {
                        HBox ammoBox = new HBox();
                        AmmoTileView ammoTileView = ((TurretSquareView) board[i][j]).getAmmoTile();
                        String ammoTileURI = AMMO_TILE_IMAGES_DIR
                                + ((!ammoTileView.isEmpty()) ? ammoTileView.toString() : AMMO_TILE_BACK)
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

    private void printPlayerBoard(PlayerView playerView) {
        MainGuiController.printPlayerBoard(playerView, aggregateActionBox, playerPowerUpContainer, playerWeaponContainer, playerMarks, playerHealthBox, playerDeaths, playerAmmo);
    }

    private void printSpawnWeapons(Map<Color, List<WeaponView>> weaponsOnSpawn) {
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
        });
    }

    private void handlePlayerButton(PlayerId playerId) {

        try {
            if (otherPlayerStage != null) {
                otherPlayerStage.close();
            }
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainGuiController.class.getResource("/fx/PlayerBoard.fxml"));
            Parent root1 = fxmlLoader.load();
            PlayerBoardController playerBoardController = fxmlLoader.getController();
            playerBoardController.setPlayer(modelView.getEnemies().get(playerId));
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            otherPlayerStage = stage;
            mainPane.setOnMouseClicked(mouseEvent -> stage.close());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void handleCommandClick(int index, Lock lock) {
        extraCommandContainer.getChildren().clear();
        selectedCommand = index;
        lock.unlock();
    }

    public void setNodeClickable(Node node/*, CommandMessage command*/) {
        node.setStyle("-fx-background-color: yellow; -fx-background-radius: 10; -fx-opacity: 0.5");
        node.setOnMouseClicked(null);
    }

    public void setNodeUnClickable(Node node) {
        node.setStyle("-fx-background-color: yellow; -fx-background-radius: 10; -fx-opacity: 0");
        node.setOnMouseClicked(null);
    }

    public Pane getRoot() {
        return mainPane;
    }

    public void showCommand(List<CommandMessage> commands, Lock lock) {
        for (int i = 0; i < commands.size(); i++) {
            Text commandText;
            int finalI = i;
            switch (commands.get(i).getType()) {
                case SELECT_AGGREGATE_ACTION:
                    commandText = new Text(((AggregateActionCommandMessage) commands.get(i)).getAggregateActionID().toString());
                    commandText.setOnMouseClicked(actionEvent -> handleCommandClick(finalI, lock));
                    extraCommandContainer.getChildren().add(commandText);
                    break;
                default:
                    commandText = new Text(commands.get(i).getType().getString());
                    commandText.setOnMouseClicked(actionEvent -> handleCommandClick(finalI, lock));
                    extraCommandContainer.getChildren().add(commandText);
            }

        }
    }

    public void setModelView(ModelView modelView) {
        this.modelView = modelView;
        startSkullNumber = modelView.getMatch().getDeathsCounter();
        String gameboardImageURI = GAMEBOARD_IMAGES_DIR + modelView.getMatch().getGameBoardId() + IMAGE_EXTENSION;
        setBackgroundImageFromURI(gameBoard, gameboardImageURI);


        String playerBoardImageURI = PLAYERBOARD_IMAGES_DIR
                + modelView.getMe().getId().playerId()
                + BOARD_FILE_PATTERN;
        setBackgroundImageFromURI(boardWithoutAggregateAction, playerBoardImageURI);

        AtomicInteger i = new AtomicInteger(0);
        AtomicInteger j = new AtomicInteger(0);
        modelView.getEnemies().forEach((playerId, playerView) -> {
            Button playerButton = new Button(playerId.toString());
            playerButton.setOnMouseClicked(mouseEvent -> handlePlayerButton(playerId));
            bindToParent(playerButton, otherPlayerGrid, otherPlayerGrid.getRowCount(), otherPlayerGrid.getColumnCount());
            otherPlayerGrid.add(playerButton, i.getAndIncrement() % otherPlayerGrid.getColumnCount(), j.getAndIncrement() / otherPlayerGrid.getColumnCount());
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

    private static void bindToParent(Region child, Pane parent, int heightRatio, int widthRatio) {
        child.maxHeightProperty().bind(parent.heightProperty().divide(heightRatio));
        child.maxWidthProperty().bind(parent.widthProperty().divide(widthRatio));
        HBox.setMargin(child, new Insets(0, 0, 0, 1));
    }

    private static HBox getHBoxWithSkullBackground(Pane parent, int widthRatio) {
        HBox tokenBox = new HBox();
        tokenBox.setPrefHeight(parent.getPrefHeight());
        tokenBox.maxWidthProperty().bind(parent.widthProperty().divide(widthRatio));
        HBox.setMargin(tokenBox, new Insets(0, 0, 0, 1));
        setBackgroundImageFromURI(tokenBox, SKULL_IMAGE_URI);
        return tokenBox;
    }

    private static HBox getHBoxWithTokenBackgroundWithHighFixedToParent(PlayerId playerId, Pane parent) {
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
    private static void bindHeightToParent(Pane child, Pane parent, int childHeight, int childWidth) {
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

    static void printPlayerBoard(PlayerView playerView,
                                 VBox aggregateActionBox,
                                 HBox playerPowerUpContainer,
                                 HBox playerWeaponContainer,
                                 HBox playerMarks,
                                 HBox playerHealthBox,
                                 HBox playerDeaths,
                                 VBox playerAmmo) {
        String aggregateActionImageURI = PLAYERBOARD_IMAGES_DIR
                + playerView.getId().playerId()
                + (playerView.isFlippedBoard() ? AGGREGATE_ACTION_FLIPPED_FILE_PATTERN : AGGREGATE_ACTION_FILE_PATTERN);
        setBackgroundImageFromURI(aggregateActionBox, aggregateActionImageURI);

        playerPowerUpContainer.getChildren().clear();
        for (int i = 0; i < playerView.getPowerUps().size(); i++) {
            HBox cardHBox = new HBox();

            bindHeightToParent(cardHBox, playerPowerUpContainer, POWERUP_HEIGHT, POWERUP_WIDTH);
            String powerUpImageURI = POWERUP_IMAGES_DIR
                    + playerView.getPowerUps().get(i).getColor().colorID()
                    + SPACE
                    + playerView.getPowerUps().get(i).getType().powerUpID()
                    + IMAGE_EXTENSION;
            setBackgroundImageFromURI(cardHBox, powerUpImageURI);
            playerPowerUpContainer.getChildren().add(cardHBox);
            HBox.setHgrow(cardHBox, Priority.ALWAYS);
        }

        playerWeaponContainer.getChildren().clear();
        playerView.getWeapons().forEach(weaponView -> {
            HBox cardHBox = new HBox();

            bindHeightToParent(cardHBox, playerWeaponContainer, WEAPON_HEIGHT, WEAPON_WIDTH);
            String weaponImageURI = WEAPON_IMAGES_DIR
                    + weaponView.getID()
                    + IMAGE_EXTENSION;
            setBackgroundImageFromURI(cardHBox, weaponImageURI);
            HBox.setHgrow(cardHBox, Priority.ALWAYS);
            playerWeaponContainer.getChildren().add(cardHBox);
        });

        playerMarks.getChildren().clear();
        playerView.getMarks().forEach((playerId, integer) -> {
            for (int i = 0; i < integer; i++) {
                HBox markBox = getHBoxWithTokenBackgroundWithHighFixedToParent(playerId, playerMarks);
                HBox.setHgrow(markBox, Priority.ALWAYS);
                playerMarks.getChildren().add(markBox);
            }
        });

        playerHealthBox.getChildren().clear();
        for (int i = 0; i < playerView.getHealth().size(); i++) {
            HBox healthBox = getHBoxWithTokenBackgroundWithHighFixedToParent(playerView.getHealth().get(i), playerHealthBox);
            healthBox.maxWidthProperty().bind(playerHealthBox.widthProperty().divide(PLAYER_LIFE));
            playerHealthBox.getChildren().add(healthBox);
            HBox.setHgrow(healthBox, Priority.ALWAYS);
        }

        playerDeaths.getChildren().clear();
        for (int i = 0; i < playerView.getDeaths() && i < MAX_SKULL_PLAYERBOARD; i++) {
            HBox deathBox = getHBoxWithSkullBackground(playerDeaths, MAX_NUMBER_PLAYER_DEATH_SKULL);
            playerDeaths.getChildren().add(deathBox);
            HBox.setHgrow(deathBox, Priority.ALWAYS);
        }

        playerAmmo.getChildren().clear();
        Color[] colors = Color.values();
        for (Color color : Color.values()) {
            HBox colorAmmoContainer = new HBox();
            bindToParent(colorAmmoContainer, playerAmmo, colors.length, 1);
            playerAmmo.getChildren().add(colorAmmoContainer);
            VBox.setVgrow(colorAmmoContainer, Priority.ALWAYS);

            for (int j = 0; j < playerView.getAmmo().getOrDefault(color, 0); j++) {
                HBox singleAmmoBox = new HBox();
                String ammoURI = AMMO_IMAGES_DIR
                        + color.colorID()
                        + IMAGE_EXTENSION;
                setBackgroundImageFromURI(singleAmmoBox, ammoURI);
                bindToParent(singleAmmoBox, colorAmmoContainer, 1, Player.MAX_AMMO);
                colorAmmoContainer.getChildren().add(singleAmmoBox);
                HBox.setHgrow(singleAmmoBox, Priority.ALWAYS);
            }
        }


    }
}
