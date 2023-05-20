package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.view.cli.CliManager;
import it.polimi.ingsw.view.cli.CliAdapter;
import it.polimi.ingsw.view.gui.GUImain;
import it.polimi.ingsw.view.gui.GuiAdapter;
import it.polimi.ingsw.view.LocalModel;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerHandler {

    private final Socket server;
    private final LocalModel localModel;
    private final CliManager cliManager;
    private GuiAdapter guiAdapter;

    private Thread GUI;

    private static PrintWriter sOutput;

    private boolean stop = true;
    private boolean checkConnection = false;

    public synchronized boolean isStop() { return stop; }

    public synchronized void setStop(boolean stop) {
        this.stop = stop;
    }

    public ServerHandler(Socket server) throws IOException {
        this.server = server;
        this.localModel = new LocalModel();
        CliAdapter cliAdapter = new CliAdapter(localModel);
        this.cliManager = new CliManager(cliAdapter);
        runHandler();
    }

    /**
     * This Thread receives and handles Messages from the server
     * @param sInput ObjectInputStream from the server
     * @return the Thread running this method
     */
    public Thread readFromSocket(ObjectInputStream sInput) {
        Thread t = new Thread(() -> {
            try {
                while (isStop()) {
                    Message message = (Message) sInput.readObject();

                    if(message instanceof PrintCLIMessage){

                        printCLI(((PrintCLIMessage) message).getChoice());

                    }else if(message instanceof CliOrGuiMessage) {
                        message.action(localModel);

                        if(((CliOrGuiMessage) message).getChoice() == 2) {

                            guiAdapter = new GuiAdapter(localModel);

                            GUI = new Thread(() -> GUImain.main(null, guiAdapter));
                            GUI.start();
                        }

                    }else if(message instanceof StartGameMessage){

                        if(!checkConnection){
                            isAlive(sOutput).start();
                            checkConnection = true;
                        }

                        message.action(localModel);
                        guiAdapter.getView().startMatch();

                    }else if (message instanceof StartTurnMessage){

                        message.action(localModel);

                        if( ((StartTurnMessage) message).isMyTurn()){
                            guiAdapter.getView().startTurn();

                        }else{
                            guiAdapter.getView().updateGameBoard();

                        }

                    }else if(message instanceof SetResourcesMessage){

                        guiAdapter.getView().setResourcesInShelves( ((SetResourcesMessage) message).getResTypes());

                    }else if(message instanceof WhiteMarbleMessage){

                        guiAdapter.getView().chooseWhiteMarbleEffect( ((WhiteMarbleMessage) message).getWhiteNumber());

                    }else if(message instanceof EndGameMessage){
                        if (localModel.getCliOrGui() == 2){
                            guiAdapter.getView().winnerEndGame( ((EndGameMessage) message).getAllPoints());
                        }else{
                            message.action(localModel);
                        }

                    }else if(message instanceof SoloTokenMessage){
                        if (localModel.getCliOrGui() == 2) {
                            guiAdapter.getView().tokenExtracted(((SoloTokenMessage) message).getUrl(), ((SoloTokenMessage) message).getEffect());
                        }

                    }else if (message instanceof CheckConnectionMessage){
                        if(!checkConnection){
                            isAlive(sOutput).start();
                            checkConnection = true;
                        }
                    }else {
                        message.action(localModel);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                setStop(false);
            }
        });
        t.start();
        return t;
    }

    /**
     * This Thread sends input from the keyboard to the server (Used mostly with CLI mode)
     * @param keyboardIn System.in scanner
     * @param sOutput PrintWriter for the server
     * @return the Thread running this method
     */
    public Thread writeToSocket(Scanner keyboardIn, PrintWriter sOutput) {
        Thread t = new Thread(() -> {
            try {
                while (isStop()) {
                    String inputLine = keyboardIn.nextLine();
                    sOutput.println(inputLine);
                    sOutput.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
                setStop(false);
            }
        });
        t.start();
        return t;
    }

    /**
     * This Thread sends a "?" every second to confirm that the client is still connected to the server
     * @param sOutput PrintWriter for the server
     * @return the Thread running this method
     */
    public Thread isAlive(PrintWriter sOutput) {
        return new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    synchronized (this) {
                        while (isStop()) {
                            sOutput.print("?");
                            sOutput.flush();
                            this.wait(1000);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    setStop(false);
                }
            }
        });
    }

    /**
     * Sends an Integer to the server (Used by GUI methods)
     * @param input An input
     */
    public static void sendToServer(int input){
        sOutput.println(input);
        sOutput.flush();
    }

    /**
     * Sends the player's name to the server (Used by GUI method)
     * @param name The player's nickname
     */
    public static void sendNameToServer(String name){
        name = name.replaceAll("\\s+", "");

        sOutput.println(name);
        sOutput.flush();
    }

    /**
     * Main method of the Client. Closes the connection after the Threads are over
     * @throws IOException Signals that an I/O exception of some sort has occurred
     */
    public void runHandler() throws IOException {

        ObjectInputStream sInput = new ObjectInputStream(server.getInputStream());
        sOutput = new PrintWriter(server.getOutputStream());
        Scanner keyboardIn = new Scanner(System.in);

        try {

            Thread tRead = readFromSocket(sInput);
            Thread tWrite = writeToSocket(keyboardIn, sOutput);
            tWrite.join();
            tRead.join();

        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        } finally {
            keyboardIn.close();
            sInput.close();
            sOutput.close();
            server.close();
        }

    }

    /**
     * Invoke the draw method in cliManager and prints some parts of the CLI
     * @param i indicates which part of the CLI needs to be printed
     */
    public void printCLI(int i){ cliManager.draw(i);}
}