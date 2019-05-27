package it.polimi.ingsw.gui;

import it.polimi.ingsw.network.Message;
import javafx.application.Application;
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

public class GuiMessage extends Application {

    private static final String ERROR_STYLE = "-fx-font: normal bold 12px 'sans-serif'; -fx-text-fill: #FF0000;";
    private static Message message;

    public static void main(String[] args) {
        launch(args);
    }

    public void setMessage(Message message) {
        GuiMessage.message = message;
    }

    @Override
    public void start(Stage stage) {
        DropShadow shadow = new DropShadow();
        //shadow.setRadius(1.0);
        shadow.setSpread(0.6);
        shadow.setColor(Color.BLACK);
        Text text = new Text(message.type.getQuestion());
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
        text.setFill(Color.WHITE);
        text.setEffect(shadow);

        TextField textField1 = new TextField();

        Button button1 = new Button("Invia");
        button1.setStyle("-fx-background-color: #222222; -fx-text-fill: white;");

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(730, 510);

        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.setVgap(10);
        //gridPane.setHgap(5);

        gridPane.setAlignment(Pos.BOTTOM_CENTER);
        GridPane.setHalignment(text, HPos.CENTER);
        GridPane.setHalignment(button1, HPos.CENTER);

        gridPane.add(text, 0, 1);
        gridPane.add(textField1, 0, 2);
        gridPane.add(button1, 0, 3);

        gridPane.setStyle("-fx-background-image: url(\"file:src/resources/images/other/loginscreen.jpg\");");

        Scene scene = new Scene(gridPane);
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}