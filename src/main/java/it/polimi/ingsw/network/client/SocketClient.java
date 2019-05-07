package it.polimi.ingsw.network.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class SocketClient implements Client {

    public static final int SLEEPTIME = 2000;

    private String ip = "127.0.0.1";
    private int port = 9000;
    private boolean messageArrived = false;
    private boolean answerSet = false;
    private boolean keepAlive = true;
    private String messageFromServer;
    private String answer;

    public void run() {
        try {
            startClient();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void sendAnswerToServer(String answer) {
        this.answer = answer;
        messageArrived = false;
        answerSet = true;
    }

    public void stopClient() {
        keepAlive = false;
    }

    public boolean isMessageArrived() {
        return messageArrived;
    }

    public void setMessageNotification() {
        messageArrived = true;
    }

    public String getMessageFromServer() {
        messageArrived = false;
        return messageFromServer;
    }

    public void startClient() throws IOException {
        Socket socket;
        Scanner fromServer;
        PrintWriter toServer;
        socket = new Socket(ip, port);
        fromServer = new Scanner(socket.getInputStream());
        toServer = new PrintWriter(socket.getOutputStream(), true);
        System.out.println("Client socket pronto.");
        while (keepAlive) {
            try {
                messageFromServer = fromServer.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("Impossibile raggiungere il server. Disconnessione in corso.");
                break;
            }
            messageArrived = true;
            while (!answerSet) {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                }
            }
            toServer.println(answer);
            answerSet = false;
        }
        toServer.close();
        fromServer.close();
        socket.close();
    }
}