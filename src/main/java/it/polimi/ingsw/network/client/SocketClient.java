package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.Protocol;
import it.polimi.ingsw.view.Ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class represent a client using socket connection.
 */

public class SocketClient extends Client {

    private static final String TYPE = "Socket";
    private static final int SOCKET_TEST_TIMEOUT = 1000;
    private Socket socket;
    private Scanner fromServer;
    private PrintWriter toServer;

    SocketClient(Ui ui) {
        super(ui);
    }

    /**
     * Starts a client according to the socket communication and keeps listening to server.
     */

    @Override
    public void run() {
        String input;
        manageIpAndPortInsertion();
        while (true) {
            manageMessage(parser.serialize(new Message(Protocol.CONNECTING, TYPE, null)));
            try {
                socket = new Socket(ip, port);
                fromServer = new Scanner(socket.getInputStream());
                toServer = new PrintWriter(socket.getOutputStream(), true);
                break;
            } catch (IOException e) {
                manageMessage(parser.serialize(new Message(Protocol.INVALID_CONNECTION_PARAMETERS, "", null)));
                manageIpAndPortInsertion();
            }
        }
        clientReady = true;
        while (keepAlive) {
            try {
                input = fromServer.nextLine();
            } catch (NoSuchElementException e) {
                break;
            }
            toServer.println(manageMessage(input));
        }
    }

    /**
     * Detects if the socket server is reachable.
     *
     * @return true if is reachable, false otherwise
     */

    @Override
    boolean isServerReachable() {
        try {
            return socket.getInetAddress().isReachable(SOCKET_TEST_TIMEOUT);
        } catch (IOException e){
            return false;
        }
    }

    /**
     * Closes the socket client.
     */

    @Override
    public void stop() {
        keepAlive = false;
        toServer.close();
        fromServer.close();
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Impossibile chiudere il socket: " + e.getMessage());
        }
    }
}