package it.polimi.ingsw.network.server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class is the executable class for the server-side of the game.
 */

@SuppressWarnings("squid:S106")
class Server {

    private static final int MIN_PORT = 1000;
    private static final int MAX_PORT = 50000;
    private static final int MIN_CONNECTION_TIME = 5;
    private static final int MAX_CONNECTION_TIME = 5000;
    private static final int MIN_TURN_TIME = 5;
    private static final int MAX_TURN_TIME = 5000;
    private static final int MIN_SKULLS = 1;
    private static final int MAX_SKULLS = 8;

    /**
     * Runs the server-side process asking for some parameters and then starts the server manager.
     * and connection technology (socket or rmi).
     *
     * @param args are command line inputs
     */

    public static void main(String[] args) {
        int socketPort;
        int rmiPort;
        ServerManager serverManager;
        Scanner stdin = new Scanner(System.in);
        int secondsAfterThirdConnection;
        int secondsDuringTurn;
        int skulls;
        System.out.println("Avvio servers in corso...");
        try {
            System.out.println("local ip: " + InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            System.out.println("local ip: unknown");
        }
        System.out.println("Specifica il numero della porta per il SocketServer:");
        socketPort = manageIntInput(stdin, MIN_PORT, MAX_PORT, 0);
        System.out.println("Specifica il numero della porta per il RmiServer:");
        rmiPort = manageIntInput(stdin, MIN_PORT, MAX_PORT, socketPort);
        System.out.println("Specifica il numero di secondi del timeout dopo la terza connessione:");
        secondsAfterThirdConnection = manageIntInput(stdin, MIN_CONNECTION_TIME, MAX_CONNECTION_TIME, 0);
        System.out.println("Specifica il numero di secondi concessi ai giocatori per ogni mossa:");
        secondsDuringTurn = manageIntInput(stdin, MIN_TURN_TIME, MAX_TURN_TIME, 0);
        System.out.println("Specifica il numero di teschi iniziali nelle partite:");
        skulls = manageIntInput(stdin, MIN_SKULLS, MAX_SKULLS, 0);
        serverManager = new ServerManager(secondsAfterThirdConnection, secondsDuringTurn, socketPort, rmiPort, skulls);
        serverManager.run();
    }

    /**
     * Manages the insertion of an integer on command line input,
     * asking it again until it not a valid value.
     *
     * @param stdin          is the input scanner
     * @param minValue       is the minimum acceptable value of the input
     * @param maxValue       is the maximum acceptable value of the input
     * @param forbiddenValue is a forbidden value of the input
     * @return the value of the input
     */

    private static int manageIntInput(Scanner stdin, int minValue, int maxValue, int forbiddenValue) {
        int output;
        try {
            output = stdin.nextInt();
        } catch (InputMismatchException e) {
            output = minValue - 1;
            stdin.nextLine();
        }
        while (output > maxValue || output < minValue || output == forbiddenValue) {
            System.out.println("Il valore deve essere compreso fra " + minValue + " e " + maxValue + ". Riprova:");
            try {
                output = stdin.nextInt();
            } catch (InputMismatchException e) {
                output = minValue - 1;
                stdin.nextLine();
            }
        }
        return output;
    }
}
