package it.polimi.ingsw.network.client;

import com.google.gson.Gson;
import it.polimi.ingsw.gui.ChooseConnection;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.Protocol;
import javafx.application.Application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.StrictMath.random;

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
            ChooseConnection.setClient(client);
            Application.launch(ChooseConnection.class);
            connectionType = client.getType();
            ip = client.getIp();
            System.out.println("ciaone");
        } else {
            answers = new ArrayList<>();
            answers.add("Socket");
            answers.add("RMI");
            connectionType = client.manageMessage(new Gson().toJson(new Message(Protocol.CHOOSE_CONNECTION, "", answers, 0)));
            ip = client.manageMessage(new Gson().toJson(new Message(Protocol.INSERT_IP, "", null, 0)));
        }
        /*
        if (uiChoice.equals("GUI")) {
            ChooseConnection window = new ChooseConnection();
            window.setClient(client);
            Application.launch(ChooseConnection.class);
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
