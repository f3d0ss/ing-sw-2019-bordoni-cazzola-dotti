package it.polimi.ingsw.view.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainGuiController {
    public HBox playerAggregateActionMMM;
    public HBox playerAggregateActionMG;
    public HBox playerAggregateActionS;
    public HBox playerActionReload;
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

    public void initialize() {
        List<Button> buttons = new ArrayList<>(Arrays.asList(otherPlayer1, otherPlayer3, otherPlayer2, otherPlayer4));
        buttons.forEach(this::handlePlayerButton);
        squares = new Pane[gameBoard.getColumnCount()][gameBoard.getRowCount()];
        //inizialize squares
        for (int i = 0; i < gameBoard.getColumnCount(); i++){
            for (int j = 0; j < gameBoard.getRowCount(); j++){
                squares[i][j] = new Pane();
                gameBoard.add(squares[i][j],i,j);
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

//        TODO: find a way to highlight wepon in respawn
//        yellowWeaponsOverlay = new Pane[3];
//        for (int i = 0; i < yellowWeapons.length; i++) {
//            yellowWeaponsOverlay[i] = new Pane();
//            yellowWeapons[i].getChildren().add(yellowWeaponsOverlay[i]);
//        }
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

    private void handlePlayerButton(Button button) {
        button.setOnAction(actionEvent -> {

            if (!button.getText().equals("Paolo")){
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

            }
            else{
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
