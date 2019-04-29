package it.polimi.ingsw.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiServer {
    private int port;

    public MultiServer(int port) {
        this.port = port;
    }

    public void startServer() {
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage()); // Port not available
            return;
        }
        System.out.println("Server ready");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                executor.submit(new ServerClientHandler(socket));
            } catch (IOException e) {
                break; // I would enter here if serverSocket has been closed
            }
        }
        executor.shutdown();
    }

    public static void main(String[] args) {
        MultiServer echoServer = new MultiServer(1337);
        echoServer.startServer();
    }
}