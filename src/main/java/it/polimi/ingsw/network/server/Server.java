package it.polimi.ingsw.network.server;

public class Server {

    public static final int SLEEPTIME = 2000;

    public static void main(String[] args) {
        ServerManager serverManager = new ServerManager();
        serverManager.run();
        /*
        while(!socketServer.isClientReady()) {
            System.out.printf(".");
            try {
                sleep(SLEEPTIME);
            } catch (InterruptedException e) {}
        }
        System.out.println("Connessione stabilita.");
        while(true) {
            System.out.printf("Tu: ");
            message = stdin.nextLine();
            socketServer.sendMessage(message);
            System.out.printf("Attendi.");
            while (!socketServer.isAnswerReady()) {
                System.out.printf(".");
                try {
                    sleep(SLEEPTIME);
                } catch (InterruptedException e) {
                }
            }
            answer = socketServer.getAnswer();
            System.out.println("User: " + answer);
            if(answer.equals("quit"))
                break;
        }
        System.out.println("Non ho più connessioni. Chiudo tutto");
        socketServer.stopServer();*/
    }
}
