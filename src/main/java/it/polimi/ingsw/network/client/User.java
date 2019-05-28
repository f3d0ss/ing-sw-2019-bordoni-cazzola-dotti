package it.polimi.ingsw.network.client;

import com.google.gson.Gson;
import it.polimi.ingsw.gui.GuiManager;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.Protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.StrictMath.random;
import static java.lang.Thread.sleep;

public class User {

    public static void main(String[] args) {
        String connectionType;
        String uiChoice;
        Ui ui = new Cli();
        String ip;
        Client client = new Client(ui);
        List<String> answers = new ArrayList<>();
        answers.add("GUI");
        answers.add("CLI");
        uiChoice = client.manageMessage(new Gson().toJson(new Message(Protocol.CHOOSE_UI, "", answers, 0)));
        if (uiChoice.equals("GUI")) {
            Gui gui = new Gui();
            new Thread(gui).start();
            GuiManager.setGui(gui);
            System.out.printf("Avvio Gui in corso.");
            while (!gui.isReady()) {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                }
                System.out.printf(".");
            }
            System.out.println(".");
            gui.setScene();
            System.out.println("Scene settata");
            gui.setInputReady(false);
            gui.show();
            while (!gui.isInputReady()) {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                }
            }
            connectionType = gui.getType();
            ip = gui.getIp();
            System.out.println("ciaone");
            ui = gui;
        } else {
            answers = new ArrayList<>();
            answers.add("Socket");
            answers.add("RMI");
            connectionType = client.manageMessage(new Gson().toJson(new Message(Protocol.CHOOSE_CONNECTION, "", answers, 0)));
            ip = client.manageMessage(new Gson().toJson(new Message(Protocol.INSERT_IP, "", null, 0)));
        }
        /*
        if (uiChoice.equals("GUI")) {
            GuiManager window = new GuiManager();
            window.setClient(client);
            Application.launch(GuiManager.class);
            ip = client.getIp();
            connectionType = client.getType();
        } else {
            answers = new ArrayList<>();
            answers.add("Socket");
            answers.add("RMI");
            connectionType = client.manageMessage(new Gson().toJson(new Message(Protocol.CHOOSE_CONNECTION, "", answers, 0)));
            ip = client.manageMessage(new Gson().toJson(new Message(Protocol.INSERT_IP, "", null, 0)));
        }*/
        if (connectionType.equals("Socket"))
            client = new SocketClient(ip, 9000, ui);
        else {
            client = new RmiClient(ip, (int) (random() * 10000), ui);
        }
        new Thread(client).start();
    }

    public static String askQuestion(String question, String retType, Scanner in) {
        String out;
        System.out.println(question);
        out = in.nextLine();
        return out;
    }
}
