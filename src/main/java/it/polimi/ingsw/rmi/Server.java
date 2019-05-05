package it.polimi.ingsw.rmi;

import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Server {

    public static final int SLEEPTIME = 2000;

    public static void main(String[] args){
        SocketServer socketServer = new SocketServer();
        RmiServer rmiServer = new RmiServer();
        Scanner stdin = new Scanner(System.in);
        String message;
        String answer;
        new Thread(socketServer).start();
        System.out.println("SocketServer avviato. In attesa di una connessione.");
        new Thread(rmiServer).start();
        System.out.println("RmiServer avviato. In attesa di una connessione.");
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
        socketServer.stopServer();
    }
}
