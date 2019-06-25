package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.PlayerId;
import it.polimi.ingsw.model.PowerUpID;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.view.ModelView;
import it.polimi.ingsw.view.WeaponView;
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
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainGuiController {
    public HBox playerAggregateActionMMM;
    public HBox playerAggregateActionMG;
    public HBox playerAggregateActionS;
    public HBox playerActionReload;
    public VBox aggregateActionBox;
    public Pane boardWithoutAggregateAction;
    public HBox playerPowerUpContainer;
    public HBox playerWeaponContainer;
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
    private HBox killShootTrack;
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
    private Pane[] yellowWeaponsOverlay;
    private ModelView modelView;
    private final static String LANG = "IT";
    private final static String SPACE = "_";
    private final static String IMAGES_DIR = "/images/";
    private final static String IMAGE_EXTENSION = ".png";
    private final static String GAMEBOARD_IMAGES_DIR = IMAGES_DIR + "gameboards/GameBoard00";
    private final static String POWERUP_IMAGES_DIR = IMAGES_DIR + "cards/AD_powerups_" + LANG + SPACE;
    private final static String WEAPON_IMAGES_DIR = IMAGES_DIR + "cards/AD_weapons_" + LANG + SPACE;
    private final static String PLAYERBOARD_IMAGES_DIR = IMAGES_DIR + "playerboards/";
    private final static String AGGREGATE_ACTION_FILE_PATTERN = "_aggregate_action" + IMAGE_EXTENSION;
    private final static String AGGREGATE_ACTION_FLIPPED_FILE_PATTERN = "_aggregate_action_flipped" + IMAGE_EXTENSION;
    private final static String BOARD_FILE_PATTERN = "_board_without_aggregate_action" + IMAGE_EXTENSION;

    public void initialize() {
        String gameboardImageURI = GAMEBOARD_IMAGES_DIR + 1/* modelView.getMatch().getGameBoardId() */ + IMAGE_EXTENSION;
        Image boardImage = new Image(getClass().getResource(gameboardImageURI).toExternalForm());
        gameBoard.setBackground(new Background(new BackgroundFill(new ImagePattern(boardImage), CornerRadii.EMPTY, Insets.EMPTY)));


        String aggregateActionImageURI = PLAYERBOARD_IMAGES_DIR
                + /*modelView.getMe().getId().playerId() */ PlayerId.VIOLET.playerId()
                + /*(modelView.getMe().isFlippedBoard() ? AGGREGATE_ACTION_FLIPPED_FILE_PATTERN : AGGREGATE_ACTION_FILE_PATTERN)*/  AGGREGATE_ACTION_FLIPPED_FILE_PATTERN;
        Image aggregateActionImage = new Image(getClass().getResource(aggregateActionImageURI).toExternalForm());
        aggregateActionBox.setBackground(new Background(new BackgroundFill(new ImagePattern(aggregateActionImage), CornerRadii.EMPTY, Insets.EMPTY)));

//
        String playerBoardImageURI = PLAYERBOARD_IMAGES_DIR
                + /*modelView.getMe().getId().playerId() */ PlayerId.VIOLET.playerId()
                + BOARD_FILE_PATTERN;
        Image playerBoardImage = new Image(getClass().getResource(playerBoardImageURI).toExternalForm());
        boardWithoutAggregateAction.setBackground(new Background(new BackgroundFill(new ImagePattern(playerBoardImage), CornerRadii.EMPTY, Insets.EMPTY)));


        /* modelView.getMe().getPowerUps().forEach(powerUpView -> { */
        for (int i = 0; i < 4; i++) {  //TO BE DELTED
            HBox cardHBox = new HBox();

            cardHBox.setPrefHeight(playerPowerUpContainer.getPrefHeight());
            cardHBox.maxWidthProperty().bind(cardHBox.heightProperty().divide(264).multiply(169));
            String powerUpImageURI = POWERUP_IMAGES_DIR
                    + /* powerUpView.getColor().colorID() */ Color.BLUE.colorID()
                    + SPACE
                    + /* powerUpView.getType().powerUpID() */ PowerUpID.NEWTON.powerUpID()
                    + IMAGE_EXTENSION;
            Image powerUpImage = new Image(getClass().getResource(powerUpImageURI).toExternalForm());
            cardHBox.setBackground(new Background(new BackgroundFill(new ImagePattern(powerUpImage), CornerRadii.EMPTY, Insets.EMPTY)));
            playerPowerUpContainer.getChildren().add(cardHBox);
            playerPowerUpContainer.setHgrow(cardHBox, Priority.ALWAYS);
        }               //TO BE DELETED
        /* }); */


//         modelView.getMe().getWeapons().forEach(weaponView -> {
        for (int i = 0; i < 3; i++) {  //TO BE DELTED
            HBox cardHBox = new HBox();

            cardHBox.setPrefHeight(playerWeaponContainer.getPrefHeight());
            cardHBox.maxWidthProperty().bind(cardHBox.heightProperty().divide(406).multiply(240));
            String weaponImageURI = WEAPON_IMAGES_DIR
                    + /* weaponView.getType().weaponID() */ new WeaponView("Hellion", true).getID()
                    + IMAGE_EXTENSION;
            Image weaponImage = new Image(getClass().getResource(weaponImageURI).toExternalForm());
            cardHBox.setBackground(new Background(new BackgroundFill(new ImagePattern(weaponImage), CornerRadii.EMPTY, Insets.EMPTY)));
            playerWeaponContainer.getChildren().add(cardHBox);
            playerWeaponContainer.setHgrow(cardHBox, Priority.ALWAYS);
        }               //TO BE DELETED
//         });


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

        blueWeapons = new Pane[3];
        blueWeapons[0] = (Pane) blueSpawnWeapons.getChildren().get(1);
        blueWeapons[1] = (Pane) blueSpawnWeapons.getChildren().get(3);
        blueWeapons[2] = (Pane) blueSpawnWeapons.getChildren().get(5);


        redWeapons = new Pane[3];
        redWeapons[0] = (Pane) redSpawnWeapons.getChildren().get(1);
        redWeapons[1] = (Pane) redSpawnWeapons.getChildren().get(3);
        redWeapons[2] = (Pane) redSpawnWeapons.getChildren().get(5);


        yellowWeapons = new Pane[3];
        yellowWeapons[0] = (Pane) yellowSpawnWeapons.getChildren().get(1);
        yellowWeapons[1] = (Pane) yellowSpawnWeapons.getChildren().get(3);
        yellowWeapons[2] = (Pane) yellowSpawnWeapons.getChildren().get(5);

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

    public void setModelView(ModelView modelView) {
        this.modelView = modelView;
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

    public void setNodeClickable(Node node/*, CommandMessage command*/) {
        node.setStyle("-fx-background-color: yellow; -fx-background-radius: 10; -fx-opacity: 0.5");
        node.setOnMouseClicked(null);
    }

    public void setNodeUnClickable(Node node) {
        node.setStyle("-fx-background-color: yellow; -fx-background-radius: 10; -fx-opacity: 0");
        node.setOnMouseClicked(null);
    }

}
