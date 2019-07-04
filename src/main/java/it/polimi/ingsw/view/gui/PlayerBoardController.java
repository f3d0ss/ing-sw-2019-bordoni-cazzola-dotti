package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PowerUpID;
import it.polimi.ingsw.view.PlayerView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.view.gui.MainGuiController.IMAGE_EXTENSION;
import static it.polimi.ingsw.view.gui.MainGuiController.bindToParent;

public class PlayerBoardController extends HBox {
    private static final int POWERUP_HEIGHT = 264;
    private static final int POWERUP_WIDTH = 169;
    private static final int MAX_SKULL_PLAYERBOARD = 6;
    private static final int PLAYER_LIFE = 12;
    private static final double OPACITY_WEAPON_UNLOADED = 0.5;
    private static final int DEATH_MARGIN_RATIO_NOT_FLIPPED = 6;
    private static final int DEATH_MARGIN_RATIO_FLIPPED = 4;
    @FXML
    private HBox boardWithoutAggregateAction;
    @FXML
    private VBox aggregateActionBox;
    @FXML
    private HBox playerPowerUpContainer;
    @FXML
    private HBox playerWeaponContainer;
    @FXML
    private HBox playerMarks;
    @FXML
    private HBox playerHealthBox;
    @FXML
    private HBox playerDeaths;
    @FXML
    private VBox playerAmmo;

    private boolean isMe;
    private Stage stage;
    private Map<Color, HBox> ammoBoxes = new EnumMap<>(Color.class);
    private Map<String, HBox> weaponBoxes = new HashMap<>();
    private Map<PowerUpID, Map<Color, HBox>> powerUpBoxes = new EnumMap<>(PowerUpID.class);
    private boolean lastTurn;

    public PlayerBoardController(boolean isMe, Stage stage) {
        this.stage = stage;
        this.isMe = isMe;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fx/PlayerBoard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public PlayerBoardController() {
        this.isMe = true;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fx/PlayerBoard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    void setPlayer(PlayerView playerView, boolean isLastTurn) {
        this.lastTurn = isLastTurn;
        printPlayerBoard(playerView);
    }

    void printPlayerBoard(PlayerView playerView) {
        printPlayerBoard(playerView, aggregateActionBox, playerMarks, playerHealthBox, playerDeaths, playerAmmo);
        printPlayerCard(playerView, playerPowerUpContainer, playerWeaponContainer, isMe);
    }

    public void close() {
        stage.close();
    }

    public void update(PlayerView playerView) {
        printPlayerBoard(playerView);
    }

    private void printPlayerCard(PlayerView playerView,
                                 HBox playerPowerUpContainer,
                                 HBox playerWeaponContainer,
                                 boolean isMe) {
        playerPowerUpContainer.getChildren().clear();
        powerUpBoxes.clear();
        for (int i = 0; i < playerView.getPowerUps().size(); i++) {
            HBox cardHBox = new HBox();

            MainGuiController.bindHeightToParent(cardHBox, playerPowerUpContainer, POWERUP_HEIGHT, POWERUP_WIDTH);
            String powerUpImageURI;
            if (isMe) {
                powerUpImageURI = MainGuiController.POWERUP_IMAGES_DIR
                        + playerView.getPowerUps().get(i).getColor().colorID()
                        + MainGuiController.SPACE
                        + playerView.getPowerUps().get(i).getType().powerUpID()
                        + IMAGE_EXTENSION;
                Map<Color, HBox> tempMap = powerUpBoxes.getOrDefault(playerView.getPowerUps().get(i).getType(), new EnumMap<>(Color.class));
                tempMap.put(playerView.getPowerUps().get(i).getColor(), cardHBox);
                powerUpBoxes.put(playerView.getPowerUps().get(i).getType(), tempMap);
            } else
                powerUpImageURI = MainGuiController.POWERUP_IMAGES_DIR
                        + MainGuiController.BACK
                        + IMAGE_EXTENSION;

            MainGuiController.setBackgroundImageFromURI(cardHBox, powerUpImageURI);
            playerPowerUpContainer.getChildren().add(cardHBox);
            HBox.setHgrow(cardHBox, Priority.ALWAYS);
        }

        playerWeaponContainer.getChildren().clear();
        weaponBoxes.clear();
        playerView.getWeapons().forEach(weaponView -> {
            HBox cardHBox = new HBox();

            MainGuiController.bindHeightToParent(cardHBox, playerWeaponContainer, MainGuiController.WEAPON_HEIGHT, MainGuiController.WEAPON_WIDTH);
            String weaponImageURI;
            if (isMe || !weaponView.isLoaded()) {
                weaponImageURI = MainGuiController.WEAPON_IMAGES_DIR
                        + weaponView.getID()
                        + IMAGE_EXTENSION;
                weaponBoxes.put(weaponView.getName(), cardHBox);
            } else
                weaponImageURI = MainGuiController.WEAPON_IMAGES_DIR
                        + MainGuiController.BACK
                        + IMAGE_EXTENSION;
            MainGuiController.setBackgroundImageFromURI(cardHBox, weaponImageURI);
            if (isMe && !weaponView.isLoaded())
                cardHBox.setOpacity(OPACITY_WEAPON_UNLOADED);
            playerWeaponContainer.getChildren().add(cardHBox);
            HBox.setHgrow(cardHBox, Priority.ALWAYS);
        });
    }

    private void printPlayerBoard(PlayerView playerView,
                                  VBox aggregateActionBox,
                                  HBox playerMarks,
                                  HBox playerHealthBox,
                                  HBox playerDeaths,
                                  VBox playerAmmo) {

        String playerBoardImageURI = MainGuiController.PLAYERBOARD_IMAGES_DIR
                + playerView.getId().playerId()
                + (playerView.isFlippedBoard() ? MainGuiController.FLIPPED_BOARD_FILE_PATTERN : MainGuiController.BOARD_FILE_PATTERN);
        MainGuiController.setBackgroundImageFromURI(boardWithoutAggregateAction, playerBoardImageURI);

        String aggregateActionImageURI = MainGuiController.PLAYERBOARD_IMAGES_DIR
                + playerView.getId().playerId()
                + (lastTurn ? MainGuiController.AGGREGATE_ACTION_FLIPPED_FILE_PATTERN : MainGuiController.AGGREGATE_ACTION_FILE_PATTERN);
        MainGuiController.setBackgroundImageFromURI(aggregateActionBox, aggregateActionImageURI);


        playerMarks.getChildren().clear();
        playerView.getMarks().forEach((playerId, integer) -> {
            for (int i = 0; i < integer; i++) {
                HBox markBox = MainGuiController.getHBoxWithTokenBackgroundWithHighFixedToParent(playerId, playerMarks);
                HBox.setHgrow(markBox, Priority.ALWAYS);
                playerMarks.getChildren().add(markBox);
            }
        });

        playerHealthBox.getChildren().clear();
        for (int i = 0; i < playerView.getHealth().size(); i++) {
            HBox healthBox = new HBox();
            MainGuiController.bindToParent(healthBox, playerHealthBox, 1, Player.MAX_DAMAGE);
            String tokenImageURI = MainGuiController.TOKEN_IMAGES_DIR
                    + playerView.getHealth().get(i).playerId()
                    + IMAGE_EXTENSION;
            MainGuiController.setBackgroundImageFromURI(healthBox, tokenImageURI);

            healthBox.maxWidthProperty().bind(playerHealthBox.widthProperty().divide(PLAYER_LIFE));
            playerHealthBox.getChildren().add(healthBox);
            HBox.setHgrow(healthBox, Priority.ALWAYS);
        }

        playerDeaths.getChildren().clear();
        HBox margin = new HBox();
        if (!playerView.isFlippedBoard())
            bindToParent(margin, playerDeaths, 1, DEATH_MARGIN_RATIO_NOT_FLIPPED);
        else
            bindToParent(margin, playerDeaths, 1, DEATH_MARGIN_RATIO_FLIPPED);
        HBox.setHgrow(margin, Priority.ALWAYS);
        playerDeaths.getChildren().add(margin);
        for (int i = 0; i < playerView.getDeaths() && i < MAX_SKULL_PLAYERBOARD; i++) {
            HBox deathBox = new HBox();
            MainGuiController.setBackgroundImageFromURI(deathBox, MainGuiController.SKULL_IMAGE_URI);
            MainGuiController.bindToParent(deathBox, playerDeaths, 1, Player.MAX_DAMAGE);
            HBox.setHgrow(deathBox, Priority.ALWAYS);
            playerDeaths.getChildren().add(deathBox);
            HBox.setHgrow(deathBox, Priority.ALWAYS);
        }


        playerAmmo.getChildren().clear();
        ammoBoxes.clear();
        Color[] colors = Color.values();
        for (Color color : Color.values()) {
            HBox colorAmmoContainer = new HBox();
            MainGuiController.bindToParent(colorAmmoContainer, playerAmmo, colors.length, 1);
            playerAmmo.getChildren().add(colorAmmoContainer);
            VBox.setVgrow(colorAmmoContainer, Priority.ALWAYS);
            ammoBoxes.put(color, colorAmmoContainer);

            for (int j = 0; j < playerView.getAmmo().getOrDefault(color, 0); j++) {
                HBox singleAmmoBox = new HBox();
                String ammoURI = MainGuiController.AMMO_IMAGES_DIR
                        + color.colorID()
                        + IMAGE_EXTENSION;
                MainGuiController.setBackgroundImageFromURI(singleAmmoBox, ammoURI);
                MainGuiController.bindToParent(singleAmmoBox, colorAmmoContainer, 1, Player.MAX_AMMO);
                colorAmmoContainer.getChildren().add(singleAmmoBox);
                HBox.setHgrow(singleAmmoBox, Priority.ALWAYS);
            }
        }


    }

    HBox getAmmoBox(Color color) {
        return ammoBoxes.get(color);
    }

    public HBox getWeaponBox(String weapon) {
        return weaponBoxes.get(weapon);
    }

    public HBox getPowerUpBox(PowerUpID powerUpID, Color color) {
        return powerUpBoxes.get(powerUpID).get(color);
    }

    public void update(boolean lastTurn) {
        this.lastTurn = lastTurn;
    }
}
