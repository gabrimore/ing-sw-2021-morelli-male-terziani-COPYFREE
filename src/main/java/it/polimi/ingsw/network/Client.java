package it.polimi.ingsw.network;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private final String ip;
    private final int port;

    public static void main(String[] args){

        Scanner scanner  = new Scanner(System.in);

        System.out.print("Insert IP Address: ");
        String ip = scanner.nextLine();
        System.out.print("Insert Port Number: ");
        int port = scanner.nextInt();

        Client client = new Client(ip, port);
        client.startClient();
    }

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * Connects the client to the server
     */
    public void startClient() {

        try {
            System.out.println("Connecting...");
            Socket socket = new Socket(ip, port);
            ServerHandler serverHandler = new ServerHandler(socket);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("404 Not Found");
        }
    }
}