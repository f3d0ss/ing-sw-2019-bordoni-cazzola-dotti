package it.polimi.ingsw.network.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SocketClient implements Client {

    private String ip = "127.0.0.1";
    private int port = 9000;
    private boolean keepAlive = true;
    private String messageToServer;
    private Socket socket;
    private Scanner fromServer;
    private PrintWriter toServer;

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
        toServer = new PrintWriter(socket.getOutputStream(), true);
        while (keepAlive) {
            try {
                System.out.println(fromServer.nextLine());
            } catch (NoSuchElementException e){
                System.out.println("Impossibile raggiungere il server.");
                break;
            }
            messageToServer = (stdin.nextLine());
            toServer.println(messageToServer);
            if(messageToServer.equals("quit"))
                break;
        }
        System.out.println("Disconnessione in corso.");
        toServer.close();
        fromServer.close();
        socket.close();
    }
}