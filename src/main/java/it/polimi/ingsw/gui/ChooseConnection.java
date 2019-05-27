package it.polimi.ingsw.gui;

import it.polimi.ingsw.network.client.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class ChooseConnection extends Application {

    private static final String ERROR_STYLE = "-fx-font: normal bold 12px 'sans-serif'; -fx-text-fill: #FF0000;";
    private static Client client;
    private static Stage stage;

    public static void setClient(Client c) {
        client = c;
    }

    @Override
    public void start(Stage inputStage) {
        stage = inputStage;
        DropShadow shadow = new DropShadow();
        //shadow.setRadius(1.0);
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

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(730, 510);

        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.setVgap(10);
        gridPane.setHgap(100);

        gridPane.add(text, 0, 0, 2, 1);
        gridPane.add(ipField, 0, 1, 2, 1);
        gridPane.add(invalidIpText, 0, 2, 2, 1);
        gridPane.add(buttonSocket, 0, 3, 1, 1);
        gridPane.add(buttonRmi, 1, 3, 1, 1);

        ipField.textProperty().addListener((v, oldValue, newValue) -> {
            if (isValidIp(newValue))
                ipField.setStyle("-fx-background-color: white;");
            else
                ipField.setStyle("-fx-background-color: #FF9999;");
        });

        buttonSocket.setOnAction(e -> {
            if (isValidIp(ipField.getText())) {
                client.setIp(ipField.getText());
                client.setType("Socket");
                stage.close();
            } else
                invalidIpText.setVisible(true);
        });

        buttonRmi.setOnAction(e -> {
            if (isValidIp(ipField.getText())) {
                client.setIp(ipField.getText());
                client.setType("Rmi");
                stage.close();
            } else
                invalidIpText.setVisible(true);
        });

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHalignment(text, HPos.CENTER);
        gridPane.setHalignment(buttonSocket, HPos.CENTER);
        gridPane.setHalignment(buttonRmi, HPos.CENTER);

        gridPane.setStyle("-fx-background-image: url('https://www.meeplemountain.com/wp-content/uploads/2017/11/adrenaline.jpg')");

        Scene scene = new Scene(gridPane);
        stage.setTitle("Welcome");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private boolean isValidIp(String input) {
        return input.matches("^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
    }

    public static void show(){
        stage.show();
    }
}
