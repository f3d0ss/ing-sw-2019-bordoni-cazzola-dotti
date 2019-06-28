package it.polimi.ingsw.network.server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Server {

    public final static int MILLIS_TO_WAIT = 10;
    private static boolean keepAlive = true;
    private static int socketPort;
    private static int rmiPort;
    private final static int INPUT_UPDATE = -1;
    private final static int MIN_PORT = 1000;
    private final static int MAX_PORT = 50000;
    private final static int MIN_CONNECTION_TIME = 5;
    private final static int MAX_CONNECTION_TIME = 5000;
    private final static int MIN_TURN_TIME = 5;
    private final static int MAX_TURN_TIME = 5000;
    private final static int MIN_SKULLS = 1;
    private final static int MAX_SKULLS = 8;

    public static void main(String[] args) {
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
        while (!serverManager.allServerReady()) {
            try {
                sleep(MILLIS_TO_WAIT);
            } catch (InterruptedException e) {
            }
        }
        while (keepAlive) {
        }
        //serverManager.shutDownAllServers();
    }

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
