package it.polimi.ingsw.rmi;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class SocketServer implements Runnable {

    private int port = 9000;
    private ServerSocket server;
    private String message;
    private String answer;
    private boolean questionSend = true;
    private boolean answerReceived = false;
    private boolean clientReady = false;
    private boolean keepAlive = true;

    public void run() {
        try {
            startServer();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void sendMessage(String message){
        this.message = message;
        answerReceived = false;
        questionSend = false;
    }

    public String getAnswer(){
        return answer;
    }

    public boolean isAnswerReady(){
        return answerReceived;
    }

    public boolean isClientReady(){
        return clientReady;
    }

    public void stopServer(){
        keepAlive = false;
    }

    public void startServer() throws IOException {
        Socket socket;
        Scanner fromClient;
        PrintWriter toClient;
        server = new ServerSocket(port);
        socket = server.accept();
        clientReady = true;
        System.out.println("User accettato");
        fromClient = new Scanner(socket.getInputStream());
        toClient = new PrintWriter(socket.getOutputStream(), true);
        while (keepAlive) {
            while (questionSend){
                try {
                    sleep(2000);
                } catch (InterruptedException e) {}
            }
            toClient.println(message);
            questionSend = true;
            answer = fromClient.nextLine();
            answerReceived = true;
        }
        fromClient.close();
        toClient.close();
        socket.close();
        server.close();
        System.out.println("Chiuso");
    }

}