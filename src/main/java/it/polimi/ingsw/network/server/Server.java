package it.polimi.ingsw.network.server;

import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Server {

    private static boolean keepAlive = true;

    public void shutDown() {
        keepAlive = false;
    }

    public static void main(String[] args) {
        ServerManager serverManager = new ServerManager();
        serverManager.run();
        Scanner stdin = new Scanner(System.in);
        String message;
        int client;
        System.out.println("Avvio servers in corso...");
        try {
            sleep(2000);
        } catch (InterruptedException e){}
        while (keepAlive) {
            System.out.printf("Clients attivi: ");
            serverManager.printClients();
            System.out.println("Scrivi con quale client vuoi comunicare, -1 per aggiornare la lista.");
            while (true) {
                try {
                    client = Integer.parseInt(stdin.nextLine());
                    if (serverManager.isActive(client) || client == -1)
                        break;
                    else
                        System.out.println("L'user " + client + " non è attivo. Riprova:");
                } catch (NumberFormatException e) {
                    System.out.println("Non è un numero valido, riprova:");
                }
            }
            if (client != -1) {
                System.out.println("Scrivi il messaggio:");
                message = stdin.nextLine();
                serverManager.sendMessage(client, "Server: " + message);
            }
        }
        serverManager.shutDownAllServers();
    }
}
