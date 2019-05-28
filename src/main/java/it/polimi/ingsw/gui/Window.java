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

public class Window extends GridPane {

    private static final String ERROR_STYLE = "-fx-font: normal bold 12px 'sans-serif'; -fx-text-fill: #FF0000;";

    public Window(Stage stage, Gui gui) {
        DropShadow shadow = new DropShadow();
        shadow.setSpread(0.6);
        shadow.setColor(Color.BLACK);
        Text text = new Text("Immetti l'indirizzo ip del server\ne scegli la modalità di connessione");
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
        text.setFill(Color.WHITE);
        text.setEffect(shadow);

        Platform.setImplicitExit(false);

        Text invalidIpText = new Text("Invalid IP. Try again.");
        invalidIpText.setStyle(ERROR_STYLE);
        invalidIpText.setVisible(false);

        Button buttonSocket = new Button("Socket");
        Button buttonRmi = new Button("RMI");
        buttonSocket.setStyle("-fx-background-color: #222222; -fx-text-fill: white;");
        buttonRmi.setStyle("-fx-background-color: #222222; -fx-text-fill: white;");
        buttonSocket.setMinWidth(100);
        buttonRmi.setMinWidth(100);

        TextField ipField = new TextField("127.0.0.1");

        setMinSize(730, 510);

        setPadding(new Insets(10, 10, 10, 10));

        setVgap(10);
        setHgap(100);

        add(text, 0, 0, 2, 1);
        add(ipField, 0, 1, 2, 1);
        add(invalidIpText, 0, 2, 2, 1);
        add(buttonSocket, 0, 3, 1, 1);
        add(buttonRmi, 1, 3, 1, 1);

        ipField.textProperty().addListener((v, oldValue, newValue) -> {
            if (isValidIp(newValue))
                ipField.setStyle("-fx-background-color: white;");
            else
                ipField.setStyle("-fx-background-color: #FF9999;");
        });

        buttonSocket.setOnAction(e -> {
            if (isValidIp(ipField.getText())) {
                gui.setIp(ipField.getText());
                gui.setType("Socket");
                gui.setInputReady(true);
                stage.hide();
            } else
                invalidIpText.setVisible(true);
        });

        buttonRmi.setOnAction(e -> {
            if (isValidIp(ipField.getText())) {
                gui.setIp(ipField.getText());
                gui.setType("Rmi");
                gui.setInputReady(true);
                stage.hide();
            } else
                invalidIpText.setVisible(true);
        });

        setAlignment(Pos.CENTER);
        setHalignment(text, HPos.CENTER);
        setHalignment(buttonSocket, HPos.CENTER);
        setHalignment(buttonRmi, HPos.CENTER);

        setStyle("-fx-background-image: url('https://www.meeplemountain.com/wp-content/uploads/2017/11/adrenaline.jpg')");
    }

    private boolean isValidIp(String input) {
        return input.matches("^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
    }
}
