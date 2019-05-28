package it.polimi.ingsw.gui;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.Gui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GuiManager extends Application {

    private static Client client;
    private static Gui gui;
    private static Stage stage;
    private static GridPane gridPane;

    public static void setClient(Client c) {
        client = c;
    }

    public static void setGui(Gui gui) {
        GuiManager.gui = gui;
    }

    @Override
    public void start(Stage inputStage) {
        stage = inputStage;

        Scene scene = new Scene(new Window(stage, gui));
        stage.setTitle("Welcome");
        stage.setResizable(false);
        stage.setScene(scene);
        //stage.show();
        gui.setReady(true);
    }

    public static void showWindow(){
        stage.show();
    }

    public static void setScene() {
        gridPane = new Login(stage, gui);
    }
}
