package it.polimi.ingsw.view.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainGuiController {
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

    public void initialize() {
        List<Button> buttons = new ArrayList<>(Arrays.asList(otherPlayer1, otherPlayer3, otherPlayer2, otherPlayer4));
        buttons.forEach(this::handlePlayerButton);
    }

    private void handlePlayerButton(Button button) {
        button.setOnAction(actionEvent -> {
            if (!button.getText().equals("Paolo"))
                button.setText("Paolo");
            else
                button.setText("Commit");
        });
    }

}
