package it.polimi.ingsw.network.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketHandler implements Runnable {
    boolean keepActive = true;
    boolean printed = true;
    String message;
    private Socket socket;
    private SocketServer server;

    public SocketHandler(Socket socket, SocketServer server) {
        this.socket = socket;
        this.server = server;
    }

    public void closeServer() {
        keepActive = false;
    }

    public void print(String message) {
        this.message = message;
        printed = false;
    }

    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println("Type what you want to communicate to all clients:");
            String line = in.nextLine();
            //server.printAll(line);
// Leggo e scrivo nella connessione finche' non ricevo "quit"
            while (keepActive) {
                while (printed) {
                }
                out.println(message);
                out.flush();
                printed = true;
                /*
                if (line.equals("quit")) {
                    break;
                } else {
                    out.println("Received: " + line);
                    out.flush();
                }*/
            }
// Chiudo gli stream e il socket
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
