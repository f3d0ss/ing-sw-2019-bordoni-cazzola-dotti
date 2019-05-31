package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.Protocol;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
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
        socket = new Socket(ip, port);
        fromServer = new Scanner(socket.getInputStream());
        //System.out.println("Connessione stabilita. Digitare quit per uscire");
        toServer = new PrintWriter(socket.getOutputStream(), true);
        while (keepAlive) {
            try {
                input = fromServer.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("Impossibile raggiungere il server.");
                break;
            }
            if (input == Protocol.ping)
                toServer.println(Protocol.ack);
            else
                toServer.println(manageMessage(input));
        }
        System.out.println("Disconnessione in corso.");
        toServer.close();
        fromServer.close();
        socket.close();
    }
}