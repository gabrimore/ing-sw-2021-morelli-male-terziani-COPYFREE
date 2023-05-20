package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.PlayerAction;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientHandler extends Observable<PlayerAction> implements Observer<Message>{

    private final Server server;
    private final Socket socket;
    private int myTurn; //from 0 to 3

    private int CliOrGui; //1) CLI 2) GUI

    private ObjectOutputStream output;
    private InputStream stream;
    private boolean disconnected = false;
    private boolean closed = false;

    private boolean done = false;
    private String name;


    public ClientHandler(Socket socket, Server server) {
        this.server = server;
        this.socket = socket;
        try {
            this.stream = socket.getInputStream();
            output = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send a Standard Message to the client
     * @param object contains the messages which is wrapped in a StandardMessage
     */
    public void send(String object){
        try {
            output.reset();
            output.writeObject(new StandardMessage(object));
            output.flush();
        } catch (IOException e) {
            closed = true;
            disconnected = true;
            done = false;
        }
    }

    /**
     * Send a generic Message to the client
     * @param msg contains the message
     */
    public void send(Message msg){
        try {
            output.reset();
            output.writeObject(msg);
            output.flush();
        } catch (IOException e) {
            closed = true;
            disconnected = true;
            done = false;
        }
    }

    /**
     * Reads inputs from the Client.
     * The only inputs accepted are numbers (0-9) and the symbol '?'
     * @return return the input
     */
    public int read(){

        try {
            if(stream.available() >=0){
                int in = stream.read();

                while(in < 48 || in > 57){
                    if(!(in == 10 || in == 13 || in == 63)) {
                        send("It's not a number\n");
                    }
                    if(in == 102){
                        send(new StartTurnMessage(true, name));
                    }

                    in = stream.read();
                }

                return in-48;
            }
        } catch (IOException e) {
            closed = true;
        }
        return -1;
    }

    /**
     * Reads the player's nickname at the beginning of the game
     * @return The chosen nickname
     */
    public String getName(ArrayList<String> names){
        String name = null;
        boolean checkName = false;

        do {
            try {
                Scanner scanner = new Scanner(socket.getInputStream());
                name = scanner.next();

                if(names.contains(name)){
                    send("Nickname already in use. Please insert another name: ");
                }else{
                    checkName = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }while(!checkName);

        this.name = name;
        return name;
    }

    /**
     * This method asks the player which type of turn to play and notify the Maincontroller.
     * If it's not the player's turn, the method ends.
     * @param c Clienthandler of the player
     * @param pos Player's position in the Game class
     * @return The Thread running thi method
     * @throws IOException Signals that an I/O exception of some sort has occurred
     */
    public Thread whatToDo(ClientHandler c, int pos) throws IOException {
        Thread t = new Thread(() -> {
            done = false;

            if (myTurn != server.getPlayerTurn()) {

                printView();
                send("Not your turn, it's the turn of " + server.getCurrentPlayer() + "\n");

                if(CliOrGui == 2) {
                    send(new StartTurnMessage(false, server.getCurrentPlayer()));
                }

                done = true;
                return;
            }

            printView();
            send("You are the " + (myTurn+1)  + "° player\n");

            if(CliOrGui == 2 && server.getNumOfPlayers() != 1) {
                send(new StartTurnMessage(true, server.getCurrentPlayer()));
            }

            int insert;

            do {
                send("\n1) Take Resource\n2) Activate or Discard a Leader Card\n3) Buy Development Card\n4) Produce Resources\n5) Show DeckField\n\nChoose an action: ");

                do {
                    try {
                        insert = read();
                        if (insert < 1 || insert > 5) {
                            throw new IOException();
                        }else if(insert == 5){
                            send(new PrintCLIMessage(3));
                        }

                    } catch (IOException e) {
                        send("Invalid input!");
                        insert = -1;
                    }
                } while (insert == -1);

                c.notify(new PlayerAction(c, myTurn, insert));

            }while(!done);
        });
        t.start();
        return t;
    }

    /**
     * Prints the GameBoard in CLI mode
     */
    public void printView(){
        send("\n");
        send(new PrintCLIMessage(0));
    }

    /**
     * Sets a TimeOut of 2 seconds to check if the Client is still connected.
     */
    public void setTimeout(){
        try {
            socket.setSoTimeout(2000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getMyTurn() { return myTurn; }

    public void setMyTurn(int myTurn) { this.myTurn = myTurn; }

    public boolean getDone(){ return this.done;}

    public void notDone(){ done = false;}

    public void isDone(){ done = true;}

    public void setDisconnected(boolean check){ disconnected = check;}

    public boolean isDisconnected(){ return disconnected;}

    public String getNickname(){ return name;}

    public int getCliOrGui() { return CliOrGui;}

    public void setCliOrGui(int cliOrGui) { CliOrGui = cliOrGui;}

    public boolean isClosed(){ return closed;}

    @Override
    public void update(Message message) { send(message);}

}