package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.Protocol;
import it.polimi.ingsw.network.client.Gui;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.util.List;

public class LoginWindow extends GridPane {

    private static final String BUTTON_STYLE = "-fx-background-color: #222222; -fx-text-fill: white;";
    private static ComboBox<String> comboBox;
    private static String oldMessage = "";

    public LoginWindow(Stage stage, Gui gui, String message, List<String> answers, String defaultOption, boolean isAnswerRequired) {
        Text text = new Text(oldMessage + message);
        if(!isAnswerRequired)
            oldMessage = message + "\n";
        else
            oldMessage = "";
        DropShadow shadow = new DropShadow();
        TextField textField = new TextField();
        Button buttonNext = new Button("Next");

        shadow.setSpread(0.6);
        shadow.setColor(Color.BLACK);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
        text.setFill(Color.WHITE);
        text.setEffect(shadow);

        Platform.setImplicitExit(false);

        stage.setOnCloseRequest(e -> {
            Text secondLabel = new Text("Sicuro di voler uscire?\nLa tua registrazione andrà persa.");
            secondLabel.setTextAlignment(TextAlignment.CENTER);
            GridPane secondaryLayout = new GridPane();
            Button buttonQuit = new Button("Esci");
            buttonQuit.setStyle(BUTTON_STYLE);
            buttonQuit.setMinWidth(100);
            Button buttonCancel = new Button("Annulla");
            buttonCancel.setStyle(BUTTON_STYLE);
            buttonCancel.setMinWidth(100);

            Stage secondStage = new Stage();

            buttonQuit.setOnAction(a -> System.exit(1));
            buttonCancel.setOnAction(a -> secondStage.close());

            secondaryLayout.add(secondLabel, 0, 0);
            secondaryLayout.add(buttonCancel, 0, 1);
            secondaryLayout.add(buttonQuit, 0, 2);

            Scene secondScene = new Scene(secondaryLayout, 300, 200);

            secondaryLayout.setAlignment(Pos.CENTER);
            secondaryLayout.setHalignment(secondLabel, HPos.CENTER);
            secondaryLayout.setHalignment(buttonCancel, HPos.CENTER);
            secondaryLayout.setHalignment(buttonQuit, HPos.CENTER);
            secondaryLayout.setVgap(10);

            secondStage.setTitle("Confirm Exit");
            secondStage.setScene(secondScene);

            secondStage.show();
            e.consume();
        });

        comboBox = new ComboBox<>();
        setHalignment(comboBox, HPos.CENTER);

        buttonNext.setStyle(BUTTON_STYLE);
        buttonNext.setMinWidth(100);

        setMinSize(730, 510);

        setPadding(new Insets(10, 10, 10, 10));

        setVgap(10);
        //setHgap(100);

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

        //TODO: remove next button if user action is not required

        if(!isAnswerRequired) {
            gui.setAnswer(Protocol.ACK);
            gui.setInputReady(true);
            buttonNext.setText("Attendi");
            buttonNext.setDisable(true);
        } else {
            textField.setOnAction(e -> {
                if (answers == null)
                    gui.setAnswer(textField.getText());
                else
                    gui.setAnswer(comboBox.getValue());
                gui.setInputReady(true);
                comboBox.setDisable(false);
                textField.setDisable(false);
                buttonNext.setText("Attendi");
                buttonNext.setDisable(true);
            });

            buttonNext.setOnAction(e -> {
                if (answers == null)
                    gui.setAnswer(textField.getText());
                else
                    gui.setAnswer(comboBox.getValue());
                gui.setInputReady(true);
                comboBox.setDisable(false);
                textField.setDisable(false);
                buttonNext.setText("Attendi");
                buttonNext.setDisable(true);
            });
        }

        setAlignment(Pos.CENTER);
        setHalignment(text, HPos.CENTER);
        setHalignment(buttonNext, HPos.CENTER);

        setStyle("-fx-background-image: url('/images/other/loginscreen.jpg')");
    }

    public void setMessage(String string) {
        //message = string;
    }
}
