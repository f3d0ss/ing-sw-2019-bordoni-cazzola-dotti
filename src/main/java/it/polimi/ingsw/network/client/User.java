package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.Protocol;
import it.polimi.ingsw.utils.Parser;
import it.polimi.ingsw.view.gui.GuiManager;

import java.util.Arrays;

import static java.lang.Thread.sleep;

/**
 * This class is the executable class for the client-side of the game.
 */

class User {

    private static final String CLI = "CLI";
    private static final String GUI = "GUI";
    private static final String SOCKET = "Socket";
    private static final String RMI = "RMI";
    private static final String EXIT = "Esci";
    private static final String RECONNECT = "Riconnetti";
    private static final int MILLIS_IN_SECOND = 1000;
    private static final int SECONDS_TO_PING_SERVER = 2;

    /**
     * Runs the client-side process asking for ui preference (cli or gui)
     * and connection technology (socket or rmi). Then checks periodically the connection to server.
     * When a problem occurs, it closes the process after having notified the disconnection
     *
     * @param args are command line inputs
     */

    public static void main(String[] args) {
        boolean keepAlive;
        boolean reconnect = true;
        String connectionType;
        String uiChoice;
        Ui ui = new Cli();
        Client client = new Client(ui);
        Parser parser = new Parser();
        uiChoice = client.manageMessage(parser.serialize(new Message(Protocol.CHOOSE_UI, "", Arrays.asList(CLI, GUI))));
        if (uiChoice.equals(GUI)) {
            Gui gui = new Gui();
            new Thread(gui).start();
            GuiManager.setGui(gui);
            System.out.print("Avvio Gui in corso.");
            while (!gui.isReady()) {
                try {
                    sleep(MILLIS_IN_SECOND);
                } catch (InterruptedException e) {
                }
                System.out.print(".");
            }
            client.setUi(gui);
            ui = gui;
        }
        while(reconnect) {
            keepAlive = true;
            connectionType = client.manageMessage(parser.serialize(new Message(Protocol.CHOOSE_CONNECTION, "", Arrays.asList(SOCKET, RMI))));
            if (connectionType.equals(SOCKET))
                client = new SocketClient(ui);
            else {
                client = new RmiClient(ui);
            }
            new Thread(client).start();
            while (keepAlive) {
                try {
                    sleep(MILLIS_IN_SECOND * SECONDS_TO_PING_SERVER);
                } catch (InterruptedException e) {
                    keepAlive = false;
                }
                if ((client.isClientReady() && !client.isServerReachable()))
                    keepAlive = false;
            }
            reconnect = client.manageMessage(parser.serialize(new Message(Protocol.UNREACHABLE_SERVER, "", Arrays.asList(EXIT, RECONNECT)))).equals(RECONNECT);
            client.stop();
        }
        System.exit(0);
    }
}
