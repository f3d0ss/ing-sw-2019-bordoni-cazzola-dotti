package it.polimi.ingsw.network.client;

import com.google.gson.Gson;
import it.polimi.ingsw.gui.GuiManager;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.Protocol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            client.setUi(gui);
            ui = gui;
        }
        answers = new ArrayList<>();
        answers.add("Socket");
        answers.add("RMI");
        connectionType = client.manageMessage(new Gson().toJson(new Message(Protocol.CHOOSE_CONNECTION, "", answers, 0)));
        ip = client.manageMessage(new Gson().toJson(new Message(Protocol.INSERT_IP, "", Arrays.asList("string"), 0)));
        while(!isValidIp(ip)){
            ip = client.manageMessage(new Gson().toJson(new Message(Protocol.INSERT_IP_AGAIN, "", Arrays.asList("string"), 0)));
        }
        System.out.println("Avvio client");
        if (connectionType.equals("Socket"))
            client = new SocketClient(ip, 9000, ui);
        else {
            client = new RmiClient(ip, (int) (random() * 10000), ui);
        }
        new Thread(client).start();
    }

    private static boolean isValidIp(String input) {
        return input.matches("^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
    }
}
