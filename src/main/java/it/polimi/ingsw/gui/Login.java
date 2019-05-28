package it.polimi.ingsw.gui;

import it.polimi.ingsw.network.client.Gui;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class Login extends GridPane {

    private static final String ERROR_STYLE = "-fx-font: normal bold 12px 'sans-serif'; -fx-text-fill: #FF0000;";

    public Login(Stage stage, Gui gui) {
        DropShadow shadow = new DropShadow();
        shadow.setSpread(0.6);
        shadow.setColor(Color.BLACK);
        Text text = new Text("Immetti il tuo nickname");
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
        text.setFill(Color.WHITE);
        text.setEffect(shadow);

        Platform.setImplicitExit(false);

        Text invalidNameText = new Text("Invalid name. Try again.");
        invalidNameText.setStyle(ERROR_STYLE);
        invalidNameText.setVisible(false);

        Button submit = new Button("Invia");
        submit.setStyle("-fx-background-color: #222222; -fx-text-fill: white;");
        submit.setMinWidth(100);

        TextField nameField = new TextField();

        setMinSize(730, 510);

        setPadding(new Insets(10, 10, 10, 10));

        setVgap(10);
        setHgap(100);

        add(text, 0, 0 );
        add(nameField, 0, 1);
        add(invalidNameText, 0, 2);
        add(submit, 0, 3);

        submit.setOnAction(e -> {
            gui.setName(nameField.getText());
            gui.setType("Socket");
            gui.setInputReady(true);
            stage.hide();
        });

        setAlignment(Pos.CENTER);
        setHalignment(text, HPos.CENTER);
        setHalignment(submit, HPos.CENTER);

        setStyle("-fx-background-image: url('https://www.meeplemountain.com/wp-content/uploads/2017/11/adrenaline.jpg')");
    }

    private boolean isValidName(String input) {
        //TODO:
        return true;
    }
}

