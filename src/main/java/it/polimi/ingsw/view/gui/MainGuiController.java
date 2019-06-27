package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.PlayerId;
import it.polimi.ingsw.utils.Lock;
import it.polimi.ingsw.view.ModelView;
import it.polimi.ingsw.view.PlayerView;
import it.polimi.ingsw.view.WeaponView;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MainGuiController {


    private static final int MAX_SKULL_PLAYERBOARD = 6;
    private static final int POWERUP_HEIGHT = 264;
    private static final int POWERUP_WIDTH = 169;
    private static final int WEAPON_HEIGHT = 406;
    private static final int WEAPON_WIDTH = 240;
    public HBox playerAggregateActionMMM;
    public HBox playerAggregateActionMG;
    public HBox playerAggregateActionS;
    public HBox playerActionReload;
    public VBox aggregateActionBox;
    public Pane boardWithoutAggregateAction;
    public HBox playerPowerUpContainer;
    public HBox playerWeaponContainer;
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
    private HBox otherPlayerContainer;
    @FXML
    private GridPane killShotTrackGrid;
    @FXML
    private Button otherPlayer1;
    @FXML
    private Button otherPlayer2;
    @FXML
    private Button otherPlayer3;
    @FXML
    private Button otherPlayer4;
    @FXML
    private HBox playerBoard;
    @FXML
    private HBox playerAggregateActionMMG;
    @FXML
    private HBox playerAggregateActionMS;
    @FXML
    private GridPane playerHealthGrid;
    @FXML
    private GridPane playerDeathsGrid;
    @FXML
    private HBox playerMarks;
    private Pane[][] squares;
    private Pane[] blueWeapons;
    private Pane[] redWeapons;
    private Pane[] yellowWeapons;
    private ModelView modelView;
    private final static String LANG = "IT";
    private final static String SPACE = "_";
    private final static String IMAGES_DIR = "/images/";
    private final static String IMAGE_EXTENSION = ".png";
    private final static String GAMEBOARD_IMAGES_DIR = IMAGES_DIR + "gameboards/GameBoard00";
    private final static String POWERUP_IMAGES_DIR = IMAGES_DIR + "cards/AD_powerups_" + LANG + SPACE;
    private final static String WEAPON_IMAGES_DIR = IMAGES_DIR + "cards/AD_weapons_" + LANG + SPACE;
    private final static String TOKEN_IMAGES_DIR = IMAGES_DIR + "tokens/";
    private final static String SKULL_IMAGE_URI = IMAGES_DIR + "other/skull.jpeg";
    private final static String PLAYERBOARD_IMAGES_DIR = IMAGES_DIR + "playerboards/";
    private final static String AGGREGATE_ACTION_FILE_PATTERN = "_aggregate_action" + IMAGE_EXTENSION;
    private final static String AGGREGATE_ACTION_FLIPPED_FILE_PATTERN = "_aggregate_action_flipped" + IMAGE_EXTENSION;
    private final static String BOARD_FILE_PATTERN = "_board_without_aggregate_action" + IMAGE_EXTENSION;
    private int selectedCommand;

    public void initialize() {

        List<Button> buttons = new ArrayList<>(Arrays.asList(otherPlayer1, otherPlayer3, otherPlayer2, otherPlayer4));
        buttons.forEach(this::handlePlayerButton);
        squares = new Pane[gameBoard.getColumnCount()][gameBoard.getRowCount()];
        //inizialize squares
        for (int i = 0; i < gameBoard.getColumnCount(); i++) {
            for (int j = 0; j < gameBoard.getRowCount(); j++) {
                squares[i][j] = new Pane();
                gameBoard.add(squares[i][j], i, j);
            }
        }


//
//
//        for (Pane redWeapon : redWeapons) {
//            redWeapon.getChildren().add(new Pane());
//        }

//        JUST FOR TEST (highlight boxes to see if they are in the right place)
//        setNodeClickable(playerAggregateActionMMM);
//        setNodeClickable(playerAggregateActionMG);
//        setNodeClickable(playerAggregateActionS);
//        setNodeClickable(playerActionReload);
//        setNodeClickable(playerAggregateActionMMG);
//        setNodeClickable(playerAggregateActionMS);
//        setNodeClickable(playerHealthGrid);
//        setNodeClickable(playerMarks);

    }

    public int getSelectedCommand() {
        return selectedCommand;
    }

    public void updateModelView(ModelView modelView) {
        this.modelView = modelView;
        printSpawnWeapons(modelView.getWeaponsOnSpawn());
        printKillshotTrack(modelView.getMatch().getKillshotTrack());
        printPlayerBoard(modelView.getMe());
    }

    private void printKillshotTrack(List<PlayerId> killShotTrack) {
        int i;
        for (i = 0; i < killShotTrack.size() && i < killShotTrackGrid.getColumnCount() - 1; i++) {
            HBox killBox = getHBoxWithTokenBackground(killShotTrack.get(i), killShotTrackGrid);
            GridPane.setConstraints(killBox, 0, i);
            playerHealthGrid.getChildren().add(killBox);
//            playerHealthGrid.setHgrow(healthBox, Priority.ALWAYS);
        }
        while (i < killShotTrack.size()) {
            HBox killBox = getHBoxWithTokenBackground(killShotTrack.get(i), killShotTrackGrid);
            GridPane.setConstraints(killBox, 0, killShotTrackGrid.getColumnCount() - 1);
            playerHealthGrid.getChildren().add(killBox);
        }
    }

    private HBox getHBoxWithTokenBackground(PlayerId playerId, Pane parent) {
        HBox hbox = new HBox();
        hbox.setPrefHeight(parent.getPrefHeight());
        hbox.maxWidthProperty().bind(hbox.heightProperty());
        String tokenImageURI = TOKEN_IMAGES_DIR
                + playerId.playerId()
                + IMAGE_EXTENSION;
        Image tokenImage = new Image(getClass().getResource(tokenImageURI).toExternalForm());
        hbox.setBackground(new Background(new BackgroundFill(new ImagePattern(tokenImage), CornerRadii.EMPTY, Insets.EMPTY)));
        return hbox;
    }

    private void printPlayerBoard(PlayerView playerView) {

        String aggregateActionImageURI = PLAYERBOARD_IMAGES_DIR
                + playerView.getId().playerId()
                + (playerView.isFlippedBoard() ? AGGREGATE_ACTION_FLIPPED_FILE_PATTERN : AGGREGATE_ACTION_FILE_PATTERN);
        Image aggregateActionImage = new Image(getClass().getResource(aggregateActionImageURI).toExternalForm());
        aggregateActionBox.setBackground(new Background(new BackgroundFill(new ImagePattern(aggregateActionImage), CornerRadii.EMPTY, Insets.EMPTY)));

        playerPowerUpContainer.getChildren().clear();
        for (int i = 0; i < playerView.getPowerUps().size(); i++) {
            HBox cardHBox = new HBox();

            cardHBox.setPrefHeight(playerPowerUpContainer.getPrefHeight());
            cardHBox.maxWidthProperty().bind(cardHBox.heightProperty());
            String powerUpImageURI = POWERUP_IMAGES_DIR
                    + playerView.getPowerUps().get(i).getColor().colorID()
                    + SPACE
                    + playerView.getPowerUps().get(i).getType().powerUpID()
                    + IMAGE_EXTENSION;
            Image powerUpImage = new Image(getClass().getResource(powerUpImageURI).toExternalForm());
            cardHBox.setBackground(new Background(new BackgroundFill(new ImagePattern(powerUpImage), CornerRadii.EMPTY, Insets.EMPTY)));
            playerPowerUpContainer.getChildren().add(cardHBox);
            HBox.setHgrow(cardHBox, Priority.ALWAYS);
        }

        playerWeaponContainer.getChildren().clear();
        playerView.getWeapons().forEach(weaponView -> {
            HBox cardHBox = new HBox();

            cardHBox.setPrefHeight(playerWeaponContainer.getPrefHeight());
            cardHBox.maxWidthProperty().bind(cardHBox.heightProperty());
            String weaponImageURI = WEAPON_IMAGES_DIR
                    + weaponView.getID()
                    + IMAGE_EXTENSION;
            Image weaponImage = new Image(getClass().getResource(weaponImageURI).toExternalForm());
            cardHBox.setBackground(new Background(new BackgroundFill(new ImagePattern(weaponImage), CornerRadii.EMPTY, Insets.EMPTY)));
            HBox.setHgrow(cardHBox, Priority.ALWAYS);
            playerWeaponContainer.getChildren().add(cardHBox);
        });

        playerMarks.getChildren().clear();
        playerView.getMarks().forEach((playerId, integer) -> {
            for (int i = 0; i < integer; i++) {
                HBox markBox = getHBoxWithTokenBackground(playerId, playerMarks);
                HBox.setHgrow(markBox, Priority.ALWAYS);
                playerMarks.getChildren().add(markBox);
            }
        });

        playerHealthGrid.getChildren().clear();
        for (int i = 0; i < playerView.getHealth().size(); i++) {
            HBox healthBox = getHBoxWithTokenBackground(playerView.getHealth().get(i), playerHealthGrid);
            GridPane.setConstraints(healthBox, i, 0);
            playerHealthGrid.getChildren().add(healthBox);
        }

        playerDeathsGrid.getChildren().clear();
        for (int i = 0; i < playerView.getDeaths() && i < MAX_SKULL_PLAYERBOARD; i++) {
            HBox skullBox = new HBox();
            skullBox.setPrefHeight(playerDeathsGrid.getPrefHeight());
            skullBox.maxWidthProperty().bind(skullBox.heightProperty());
            Image skullImage = new Image(getClass().getResource(SKULL_IMAGE_URI).toExternalForm());
            skullBox.setBackground(new Background(new BackgroundFill(new ImagePattern(skullImage), CornerRadii.EMPTY, Insets.EMPTY)));
            GridPane.setConstraints(skullBox, i, 0);
            playerDeathsGrid.getChildren().add(skullBox);
//            playerDeathsGrid.setHgrow(skullBox, Priority.ALWAYS);
        }

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
        for (int i = 0; i < weaponViews.size(); i++) {
            HBox weaponBox = new HBox();

            weaponBox.setPrefHeight(spawn.getPrefHeight());
            weaponBox.maxWidthProperty().bind(weaponBox.heightProperty().divide(WEAPON_HEIGHT).multiply(WEAPON_WIDTH));
            String weaponImageURI = WEAPON_IMAGES_DIR
                    + weaponViews.get(i).getID()
                    + IMAGE_EXTENSION;
            Image weaponImage = new Image(getClass().getResource(weaponImageURI).toExternalForm());
            weaponBox.setBackground(new Background(new BackgroundFill(new ImagePattern(weaponImage), CornerRadii.EMPTY, Insets.EMPTY)));
            HBox.setMargin(weaponBox, new Insets(0, 20, 0, 0));
            spawn.getChildren().add(weaponBox);
            spawn.setHgrow(weaponBox, Priority.ALWAYS);
        }
    }

    private void handlePlayerButton(Button button) {
        button.setOnAction(actionEvent -> {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(MainGuiController.class.getResource("/fx/PlayerBoard.fxml"));
                Parent root1 = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!button.getText().equals("Paolo")) {
                button.setText("Paolo");
//                JUST FOR TEST
//                setNodeClickable(squares[0][0]);
//                yellowWeaponsOverlay[1].setStyle("-fx-background-image: url('../images/gameboards/redSpawn.png'); -fx-background-repeat: no-repeat;-fx-background-size: 100%;");
//                yellowWeaponsOverlay[1].minWidth(10000);
//                yellowWeaponsOverlay[1].minHeight(10000);
//                setNodeClickable(playerAggregateActionMMM);
//                setNodeClickable(playerAggregateActionMG);
//                setNodeClickable(playerAggregateActionS);
//                setNodeClickable(playerActionReload);
//                setNodeClickable(playerAggregateActionMMG);
//                setNodeClickable(playerAggregateActionMS);
//                setNodeClickable(playerHealthGrid);

            } else {
                button.setText("Commit");
//                JUST FOR TEST
//                setNodeUnClickable(squares[0][0]);
            }
        });
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
        String gameboardImageURI = GAMEBOARD_IMAGES_DIR + modelView.getMatch().getGameBoardId() + IMAGE_EXTENSION;
        Image boardImage = new Image(getClass().getResource(gameboardImageURI).toExternalForm());
        gameBoard.setBackground(new Background(new BackgroundFill(new ImagePattern(boardImage), CornerRadii.EMPTY, Insets.EMPTY)));


        String playerBoardImageURI = PLAYERBOARD_IMAGES_DIR
                + modelView.getMe().getId().playerId()
                + BOARD_FILE_PATTERN;
        Image playerBoardImage = new Image(getClass().getResource(playerBoardImageURI).toExternalForm());
        boardWithoutAggregateAction.setBackground(new Background(new BackgroundFill(new ImagePattern(playerBoardImage), CornerRadii.EMPTY, Insets.EMPTY)));


        updateModelView(modelView);
    }
}
