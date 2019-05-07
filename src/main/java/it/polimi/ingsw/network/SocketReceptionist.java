package it.polimi.ingsw.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketReceptionist implements Runnable {

    private ServerSocket serverSocket;
    private SocketServer socketServer;
    private Socket client;

    public SocketReceptionist(ServerSocket serverSocket, SocketServer server){
        this.serverSocket = serverSocket;
        this.socketServer = server;
    }

    public void run(){
        while (true) {
            try {
                Socket client = serverSocket.accept();
                socketServer.registry(client);
            } catch(IOException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }
}
