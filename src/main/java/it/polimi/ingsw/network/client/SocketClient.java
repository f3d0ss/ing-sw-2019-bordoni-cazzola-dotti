package it.polimi.ingsw.network.client;

import com.google.gson.Gson;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.Protocol;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SocketClient extends Client {

    private String ip;
    private int port;
    private boolean keepAlive = true;
    private Socket socket;
    private Scanner fromServer;
    private PrintWriter toServer;
    private String input;
    private final static String TYPE = "Socket";

    public SocketClient(String ip, int port, Ui ui) {
        super(ui);
        this.ip = ip;
        this.port = port;
    }

    public void run() {
        try {
            startClient();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void startClient() throws IOException {
        while(true) {
            manageMessage(new Gson().toJson(new Message(Protocol.CONNECTING, TYPE, null, 0)));
            try {
                socket = new Socket(ip, port);
                fromServer = new Scanner(socket.getInputStream());
                toServer = new PrintWriter(socket.getOutputStream(), true);
                break;
            } catch (SocketException e) {
                //System.out.println(e.getMessage());
                ip = manageMessage(new Gson().toJson(new Message(Protocol.INSERT_IP_AGAIN, "", null, 0)));
            }
        }
        while (keepAlive) {
            try {
                input = fromServer.nextLine();
            } catch (NoSuchElementException e) {
                break;
            }
            toServer.println(manageMessage(input));
        }
        manageMessage(new Gson().toJson(new Message(Protocol.UNREACHABLE_SERVER, "", null, 0)));
        toServer.close();
        fromServer.close();
        socket.close();
    }
}