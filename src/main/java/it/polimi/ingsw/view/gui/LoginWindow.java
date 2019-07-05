package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.Protocol;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.util.List;

/**
 * This class represents the window shown during login.
 */
public class LoginWindow extends GridPane {

    private static final String BUTTON_STYLE = "-fx-background-color: #222222; -fx-text-fill: white;";
    private static final String BACKGROUND_STYLE = "-fx-background-image: url('/images/other/loginscreen.jpg'); -fx-background-size: cover;";
    private static final int BUTTON_MIN_WIDTH = 100;
    private static final int INNER_GAP = 10;
    private static final String WAIT = "Attendi";
    private static final String NEXT = "Avanti";
    private static final String QUIT = "Esci";
    private static final String CANCEL = "Annulla";
    private static ComboBox<String> comboBox;

    public LoginWindow(Stage stage, Gui gui, String message, List<String> answers, String defaultOption, boolean isAnswerRequired) {
        Text text = new Text(message);
        DropShadow shadow = new DropShadow();
        TextField textField = new TextField();
        Button buttonNext = new Button(NEXT);

        shadow.setSpread(0.6);
        shadow.setColor(Color.BLACK);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
        text.setFill(Color.WHITE);
        text.setEffect(shadow);

        Platform.setImplicitExit(false);

        stage.setOnCloseRequest(e -> {
            Text secondLabel = new Text("Sicuro di voler uscire?");
            secondLabel.setTextAlignment(TextAlignment.CENTER);
            GridPane secondaryLayout = new GridPane();
            Button buttonQuit = new Button(QUIT);
            buttonQuit.setStyle(BUTTON_STYLE);
            buttonQuit.setMinWidth(BUTTON_MIN_WIDTH);
            Button buttonCancel = new Button(CANCEL);
            buttonCancel.setStyle(BUTTON_STYLE);
            buttonCancel.setMinWidth(BUTTON_MIN_WIDTH);

            Stage secondStage = new Stage();

            buttonQuit.setOnAction(a -> System.exit(0));
            buttonCancel.setOnAction(a -> secondStage.close());

            secondaryLayout.add(secondLabel, 0, 0);
            secondaryLayout.add(buttonCancel, 0, 1);
            secondaryLayout.add(buttonQuit, 0, 2);

            Scene secondScene = new Scene(secondaryLayout, 300, 200);

            secondaryLayout.setAlignment(Pos.CENTER);
            setHalignment(secondLabel, HPos.CENTER);
            setHalignment(buttonCancel, HPos.CENTER);
            setHalignment(buttonQuit, HPos.CENTER);
            secondaryLayout.setVgap(INNER_GAP);

            secondStage.setTitle("Confirm Exit");
            secondStage.setScene(secondScene);

            secondStage.show();
            e.consume();
        });

        comboBox = new ComboBox<>();
        setHalignment(comboBox, HPos.CENTER);

        buttonNext.setStyle(BUTTON_STYLE);
        buttonNext.setMinWidth(BUTTON_MIN_WIDTH);

        setMinSize(730, 510);

        setPadding(new Insets(INNER_GAP));

        setVgap(INNER_GAP);

        add(text, 0, 0);
        if (isAnswerRequired) {
            if (answers == null)
                add(textField, 0, 1);
            else {
                comboBox.getItems().addAll(answers);
                comboBox.setValue(defaultOption);
                add(comboBox, 0, 1);
            }
        }
        add(buttonNext, 0, 2);

        if (!isAnswerRequired) {
            gui.setAnswer(Protocol.ACK);
            gui.setInputReady(true);
            buttonNext.setText(WAIT);
            buttonNext.setDisable(true);
        } else {
            textField.setOnAction(e -> {
                gui.setAnswer(textField.getText());
                gui.setInputReady(true);
                textField.setDisable(false);
                buttonNext.setText(WAIT);
                buttonNext.setDisable(true);
            });

            comboBox.setOnKeyPressed(e -> {
                if (e.getCode().equals(KeyCode.ENTER)) {
                    gui.setAnswer(comboBox.getValue());
                    gui.setInputReady(true);
                    comboBox.setDisable(false);
                    buttonNext.setText(WAIT);
                    buttonNext.setDisable(true);
                }
            });

            buttonNext.setOnAction(e -> {
                if (answers == null)
                    gui.setAnswer(textField.getText());
                else
                    gui.setAnswer(comboBox.getValue());
                gui.setInputReady(true);
                comboBox.setDisable(false);
                textField.setDisable(false);
                buttonNext.setText(WAIT);
                buttonNext.setDisable(true);
            });
        }

        setAlignment(Pos.CENTER);
        setHalignment(text, HPos.CENTER);
        setHalignment(buttonNext, HPos.CENTER);

        setStyle(BACKGROUND_STYLE);
    }
}