package it.polimi.ingsw.network.client;

import com.google.gson.Gson;
import it.polimi.ingsw.network.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SocketClient implements Client {

    private String ip;
    private int port = 9000;
    private boolean keepAlive = true;
    private String messageToServer;
    private Socket socket;
    private Scanner fromServer;
    private PrintWriter toServer;
    private String input;

    public SocketClient(String ip) {
        this.ip = ip;
    }

    public void run() {
        try {
            startClient();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void startClient() throws IOException {
        Scanner stdin = new Scanner(System.in);
        socket = new Socket(ip, port);
        fromServer = new Scanner(socket.getInputStream());
        System.out.println("Connessione stabilita. Digitare quit per uscire");
        toServer = new PrintWriter(socket.getOutputStream(), true);
        while (keepAlive) {
            try {
                input = fromServer.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("Impossibile raggiungere il server.");
                break;
            }
            Gson gson = new Gson();
            Message fromServer = gson.fromJson(input, Message.class);
            System.out.println(fromServer.question);
            if(fromServer.requiresAnswer())
                toServer.println("ACK");
            else {
                messageToServer = stdin.nextLine();
                toServer.println(messageToServer);
                if (messageToServer.equals("quit"))
                    break;
            }
        }
        System.out.println("Disconnessione in corso.");
        toServer.close();
        fromServer.close();
        socket.close();
    }
}