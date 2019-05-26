package it.polimi.ingsw.gui;

/*import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class App extends Application {

    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login");
        button = new Button("Click me");
        StackPane layout = new StackPane();
        layout.getChildren().add(button);
        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}*/

import javafx.application.Application;
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

public class App extends Application {
    @Override
    public void start(Stage stage) {
        DropShadow shadow = new DropShadow();
        shadow.setRadius(24.0);
        //shadow.setSpread(1.0);
        shadow.setColor(Color.color(0.0, 0.0, 0.0));

        Text text1 = new Text("Sono in attesa di giocare:\nnome1\nnome2\nINSERISCI IL TUO NICKNAME");
        text1.setTextAlignment(TextAlignment.CENTER);
        text1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 20));
        text1.setFill(Color.WHITE);
        text1.setStrokeWidth(1);
        text1.setStroke(Color.BLACK);
        //text1.setStyle("-fx-font: normal bold 20px 'sans-serif'; -fx-text-fill: white; ");
        text1.setEffect(shadow);

        TextField textField1 = new TextField();

        final ComboBox connectionType = new ComboBox();
        connectionType.getItems().addAll(
                "RMI",
                "Socket"
        );

        Button button1 = new Button("Inserisci il tuo nickname");
        button1.setStyle("-fx-background-color: #222222; -fx-text-fill: white;");

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(730, 510);

        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.setVgap(10);
        //gridPane.setHgap(5);

        gridPane.setAlignment(Pos.BOTTOM_CENTER);
        gridPane.setHalignment(text1, HPos.CENTER);
        gridPane.setHalignment(button1, HPos.CENTER);

        gridPane.add(connectionType, 0,0);
        gridPane.add(text1, 0,1);
        gridPane.add(textField1, 0, 2);
        gridPane.add(button1, 0, 3);

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