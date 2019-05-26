package it.polimi.ingsw.gui;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class ChooseConnection extends Application {
    @Override
    public void start(Stage stage) {
        DropShadow shadow = new DropShadow();
        shadow.setRadius(24.0);
        shadow.setColor(Color.BLACK);
        Text text = new Text("Scegli la modalità di connessione");
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
        text.setFill(Color.WHITE);
        text.setEffect(shadow);

        final ComboBox connectionType = new ComboBox();
        connectionType.getItems().addAll(
                "RMI",
                "Socket"
        );

        Button button = new Button("Connetti");
        button.setStyle("-fx-background-color: #222222; -fx-text-fill: white;");

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(730, 510);

        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.setVgap(10);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHalignment(connectionType, HPos.CENTER);
        gridPane.setHalignment(text, HPos.CENTER);
        gridPane.setHalignment(button, HPos.CENTER);

        gridPane.add(connectionType, 0,0);
        gridPane.add(text, 0,1);
        gridPane.add(button, 0, 2);

        gridPane.setStyle("-fx-background-image: url('https://www.meeplemountain.com/wp-content/uploads/2017/11/adrenaline.jpg')");

        Scene scene = new Scene(gridPane);
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String args[]){
        launch(args);
    }
}
