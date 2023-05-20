package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.model.deck.carddecks.Card;
import it.polimi.ingsw.model.deck.carddecks.DevelopmentCard;
import it.polimi.ingsw.model.game.Double;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Triple;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.warehouse.*;
import it.polimi.ingsw.network.messages.*;



import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {

    public static void main(String[] args) {
        try {
            Server server = new Server(2580);
            server.startServer();
        } catch (IOException e) {
            System.out.println("500 Internal Server Error");
            System.exit(0);
        }
    }

    private MainController controller;

    private final List<ClientHandler> clientHandlers = new ArrayList<>();

    private final String[] players = new String[4];
    private final ArrayList<String> names = new ArrayList<>();

    private boolean chooseLobbySize = true;
    private static boolean endGame = false;
    private static int typeOfEndgame = 0;

    private int numOfPlayers;
    private int disconnected = 0;
    private boolean active = true;
    private int playerTurn = 0;

    private String path = "";

    private final int port;
    private final ServerSocket serverSocket;

    public Server(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
    }

    /**
     * Checks if all the players are connected and saves the Clienthandlers.
     * When the size of Clienthandlers reaches the number of players, it starts the game.
     * It also checks if there's a previous game saved and reloads it.
     * @param c Clienthandler of a Player
     * @param name Player's nickname
     */
    public void lobby(ClientHandler c, String name){

        clientHandlers.add(c);
        int j = controller.addNewPlayerToGame(name);

        ((SimpleWarehouse) controller.getGame().getPlayer(j - 1).getWarehouse()).addObserver(c);
        controller.getGame().getPlayer(j - 1).addObserver(c);
        controller.getGame().getPlayer(j - 1).getSlotDevelopment().addObserver(c);

        if(clientHandlers.size() == numOfPlayers){

            if(numOfPlayers == 1) {
                controller.setSoloGame();
            }

            List<Thread> threads = new ArrayList<>();

            names.sort(String::compareToIgnoreCase);

            for(String s : names){ path = path + s;}

            if(!controller.findOldGame(path)) {

                assignTurns();

                Player[] temp1 = new Player[numOfPlayers];

                for (Player pl : controller.getGame().getPlayers()){
                    for(int i = 0; i < numOfPlayers ; i++) {
                        if (players[i].equals(pl.getNickname())){
                            temp1[i] = pl;
                        }
                    }
                }

                controller.getGame().getPlayers().addAll(Arrays.asList(temp1));

                if (numOfPlayers > 0) {
                    controller.getGame().getPlayers().subList(0, numOfPlayers).clear();
                }


                ArrayList<ClientHandler> temp = new ArrayList<>();

                for (Player p : controller.getGame().getPlayers()){
                    for(ClientHandler cl: clientHandlers){

                        if(p.getNickname().equals(cl.getNickname())){
                            temp.add(cl);
                        }
                    }
                }

                clientHandlers.addAll(temp);

                if (numOfPlayers > 0) {
                    clientHandlers.subList(0, numOfPlayers).clear();
                }

                for (int i = 0; i < numOfPlayers; i++) {
                    ClientHandler clientHandler = clientHandlers.get(i);
                    if (clientHandler.getCliOrGui() == 1) {
                        clientHandler.send("\n\n");
                        clientHandler.send(new PrintCLIMessage(5));
                        clientHandler.send("\n\n");
                    }
                    clientHandler.send(new MarketMessage(controller.getGame().getMarketBoard()));
                    clientHandler.send(new DeckFieldMessage(controller.getGame().getTopCard()));
                    clientHandler.send(new FaithTrackMessage(controller.getGame().playersPositions()));

                    threads.add(controller.setStartLeaderCardsAndResources(clientHandler, i));
                }

                for (Thread t : threads) {
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }else {

                ArrayList<ClientHandler> temp = new ArrayList<>();

                int oldTurn = 0;

                for (Player p : controller.getGame().getPlayers()){
                    for(ClientHandler cl: clientHandlers){
                        if(p.getNickname().equals(cl.getNickname())){
                            cl.setMyTurn(oldTurn);
                            temp.add(cl);
                            resetLocalModel(cl, p);
                            players[oldTurn] = p.getNickname();
                        }
                    }
                    oldTurn++;
                }

                clientHandlers.addAll(temp);

                if (numOfPlayers > 0) {
                    clientHandlers.subList(0, numOfPlayers).clear();
                }

                for (int i = 0; i < numOfPlayers; i++) {
                    ClientHandler clientHandler = clientHandlers.get(i);
                    controller.getGame().getMarketBoard().addObserver(clientHandler);
                    controller.getGame().addObserver(clientHandler);
                    clientHandler.send(new CheckConnectionMessage());
                    clientHandler.send(new NameTurnMessage(clientHandler.getMyTurn(), clientHandler.getNickname()));
                }



            }

            playerTurn = 0;

            if (numOfPlayers == 1){
                if(clientHandlers.get(0).getCliOrGui() == 2) {
                    clientHandlers.get(0).send(new StartTurnMessage(true, getCurrentPlayer()));
                }
                soloGameHandler();
            }
            else {
                Thread t = new Thread(this::handleTurns);
                t.start();
            }
        }
    }

    /**
     * Handles the turns of a Solo Mode game
     */
    public void soloGameHandler(){

        while(!endGame){
            ClientHandler c = clientHandlers.get(0);
            Player pl = controller.getGame().getPlayer(0);
            c.send("\nSOLO MODE\n");
            try {

                beginTurn(c, pl);

                Thread t = c.whatToDo(c, 0);
                t.join();
                controller.drawSoloToken(c);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        ArrayList<Double<String, Integer>> ranking = new ArrayList<>();

        if(typeOfEndgame == 1){
            ranking.add(new Double<>("LorenzoIlMagnifico", 999));
            ranking.add(controller.countVPS(numOfPlayers).get(0));

            clientHandlers.get(0).send(new EndGameMessage(ranking, false, "LorenzoIlMagnifico", true));
        }else{
            ranking.add(controller.countVPS(numOfPlayers).get(0));
            ranking.add(new Double<>("LorenzoIlMagnifico", 999));

            clientHandlers.get(0).send(new EndGameMessage(ranking, false, controller.getGame().getPlayer(0).getNickname(), true));

        }
    }

    /**
     * Handles the turn of the game.
     * Starts a Thread for each Clienthandler connected and then wait for the end of the one playing.
     * If endGame is set to false, declares the winner.
     * It also checks if a Client associated to a Clienthandler is disconnected, making it skip the turn while disconnected
     */
    public void handleTurns(){
        ArrayList<Thread> threads = new ArrayList<>();

        for (ClientHandler cl : clientHandlers){
            cl.setTimeout();
        }

        while(!endGame){

            boolean done = false;
            disconnected = 0;

            System.out.println("it's the turn of " + playerTurn);

            for(int i=0; i<numOfPlayers; i++){

                try {
                    ClientHandler c = clientHandlers.get(i);
                    Player pl = controller.getGame().getPlayer(i);

                    beginTurn(c, pl);

                    if(!c.isDisconnected()) {
                        c.notDone();
                        threads.add(c.whatToDo(c, i));
                    }else{
                        names.remove(c.getNickname());

                        System.out.println("The " + c.getMyTurn() + "° players is not connected\n");
                        disconnected++;
                        System.out.println("No of disconnected: "+ disconnected);
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            }

            if(disconnected == numOfPlayers){
                setEndGame();
                for(ClientHandler cl : clientHandlers){
                    cl.closeConnection();
                }

                try {
                    active = false;
                    this.serverSocket.close();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            synchronized (this) {
                while (!done) {
                    int finished = 0;

                    for (ClientHandler clientHandler : clientHandlers) {
                        if (clientHandler.getDone()) { finished++; }

                        if (clientHandler.isClosed() && !clientHandler.isDisconnected()) {

                            clientHandler.setDisconnected(true);

                            for (Thread t : threads) { t.interrupt(); }

                            done = true;
                            break;
                        } else if (clientHandler.isClosed() && clientHandler.isDisconnected()) { finished++;}
                    }

                    if (finished == numOfPlayers) { break; }

                    try {
                        this.wait(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            threads.clear();
            setPlayerTurn();
        }

        if(playerTurn != 0) {

            while(playerTurn != 0) {

                boolean done = false;

                for (int i = playerTurn; i < numOfPlayers; i++) {
                    try {
                        ClientHandler c = clientHandlers.get(i);
                        Player pl = controller.getGame().getPlayer(i);

                        beginTurn(c, pl);
                        c.notDone();
                        threads.add(c.whatToDo(c, i));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                synchronized (this) {
                    while (!done) {
                        int finished = 0;

                        for (ClientHandler clientHandler : clientHandlers) {
                            if (clientHandler.getDone()) { finished++; }

                            if (clientHandler.isClosed() && !clientHandler.isDisconnected()) {

                                clientHandler.setDisconnected(true);

                                for (Thread t : threads) { t.interrupt(); }

                                done = true;
                                break;
                            } else if (clientHandler.isClosed() && clientHandler.isDisconnected()) { finished++;}
                        }

                        if (finished == numOfPlayers) { break; }

                        try {
                            this.wait(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                setPlayerTurn();
            }
        }

        int winner = 0, max = 0;

        ArrayList<Double<String, Integer>> victoryP = controller.countVPS(numOfPlayers);
        ArrayList<Double<String, Integer>> ranking = new ArrayList<>();

        for (int j=0; j< victoryP.size(); j++){

            if(victoryP.get(j).getPosition() > max){
                max = victoryP.get(j).getPosition();
                winner = j;
            }else if(victoryP.get(j).getPosition() == max){
                if(controller.countResources(j) > controller.countResources(winner)) winner = j;
            }
        }

        ranking.add(victoryP.get(winner));
        victoryP.remove(winner);

        int first = winner;

        do{

            max = 0;

            for (int j=0; j< victoryP.size(); j++) {
                if (victoryP.get(j).getPosition() > max) {
                    max = victoryP.get(j).getPosition();
                    winner = j;
                }
            }
            ranking.add(victoryP.get(winner));
            victoryP.remove(winner);

        }while(!victoryP.isEmpty());

        for(int i=0; i<clientHandlers.size(); i++){
            if(i==first){
                clientHandlers.get(i).send(new EndGameMessage(ranking, true, players[first], false));
            }else{
                clientHandlers.get(i).send(new EndGameMessage(ranking, false, players[first], false));
            }
        }

        Game.deleteGameFile(path);

        for(ClientHandler cl : clientHandlers){ cl.closeConnection(); }

        try {
            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Accept connection from the socket and creates a Clienthandler.
     * If it's the first connection, asks for the number of players
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    public void startServer() throws IOException {
        System.out.println("Server ready on port: " + port +" , and IP: " + InetAddress.getLocalHost().getHostAddress());
        controller = MainController.instance();

        while(active) {
            Socket socket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(socket, this);

            int choice = chooseCliGui(clientHandler);
            clientHandler.send(new CliOrGuiMessage(chooseLobbySize, choice));

            if(chooseLobbySize){
                clientHandler.send("How many players: ");
                numOfPlayers = clientHandler.read();
                chooseLobbySize = false;
            }

            clientHandler.addObserver(controller);
            controller.getGame().getMarketBoard().addObserver(clientHandler);
            controller.getGame().addObserver(clientHandler);

            clientHandler.send(new UsedNamesMessage(names));
            clientHandler.send("Connected at port: 2580\nHi, insert your name: ");
            String newPlayer = clientHandler.getName(names);

            if(disconnected > 0){
                int i = 0;

                for (ClientHandler cl : clientHandlers){
                    if (cl.getNickname().equals(newPlayer) && cl.isDisconnected()){
                        Player p = controller.getGame().getPlayers().get(i);
                        clientHandler.setMyTurn(cl.getMyTurn());
                        clientHandler.setDisconnected(false);
                        clientHandler.isDone();
                        resetLocalModel(clientHandler, p);
                        break;
                    }
                    i++;
                }
                clientHandlers.add(i, clientHandler);
                clientHandlers.remove(i+1);

            }else{
                names.add(newPlayer);
                lobby(clientHandler, newPlayer);
            }

        }
    }

    /**
     * Updates the current player
     */
    public void setPlayerTurn(){
        if(playerTurn == numOfPlayers - 1) playerTurn = 0;
        else playerTurn++;
    }

    /**
     * During a Solo Mode game, declares who won between Lorenzo and the Player
     * @param i if 1 Lorenzo has won else if 2 the player has won
     */
    public static void setTypeOfEndgame(int i){
        typeOfEndgame = i;
    }

    /**
     * Randomly assigns the turns to the players at the beginning of the game
     */
    private void assignTurns(){
        ArrayList<Integer> turns = new ArrayList<>(numOfPlayers);

        for(int i=0; i<numOfPlayers; i++){
            turns.add(i);
        }

        Random rand = new Random();
        for(ClientHandler cl : clientHandlers){
            int index = rand.nextInt(turns.size());
            cl.setMyTurn(turns.get(index));
            players[turns.get(index)] = cl.getNickname();
            turns.remove(index);
        }
    }

    /**
     * Asks to a player which mode does he want to play
     * @param cl Clienthandler of the player
     * @return The chosen mode. 1 means CLI mode, 2 means GUI mode
     */
    private int chooseCliGui(ClientHandler cl) {
        int graphicChoice;

        cl.send("With what you want to play (1)CLI (2)GUI: ");

        do {
            try {

                graphicChoice = cl.read();

                if (graphicChoice < 1 || graphicChoice > 2) {
                    throw new IOException();
                }
            } catch (IOException e) {
                System.out.println("Invalid input!");
                graphicChoice = -1;
            }

        } while (graphicChoice == -1);

        cl.setCliOrGui(graphicChoice);

        return graphicChoice;
    }

    /**
     * This method is used after a previous game is reloaded or when a Client reconnects after a disconnection
     * @param c Clienthandler of the player
     * @param p the player
     */
    private void resetLocalModel(ClientHandler c, Player p){

        Warehouse warehouse;
        if(p.getWarehouse() instanceof SecondExtraSlotWarehouse){
            warehouse = (SecondExtraSlotWarehouse) p.getWarehouse();
        }else if(p.getWarehouse() instanceof ExtraSlotWarehouse){
            warehouse = (ExtraSlotWarehouse) p.getWarehouse();
        }else {
            warehouse = (SimpleWarehouse) p.getWarehouse();
        }

        for(int shelfIndex = 0; shelfIndex<3; shelfIndex++ ){
            warehouse.getShelf(shelfIndex).setAllowedResTypes(warehouse.getAllowedResTypes());
        }

        c.send(new MarketMessage(controller.getGame().getMarketBoard()));
        c.send(new DeckFieldMessage(controller.getGame().getTopCard()));
        c.send(new FaithTrackMessage(controller.getGame().playersPositions()));

        c.send(new SlotDevMessage(p.getSlotDevelopment().getDevSpace()));
        c.send(new ActiveHiddenLeaderCardMessage(p.getHiddenLeaderCards(), p.getActiveLeaderCards()));
        c.send(new StrongBoxMessage(((Warehouse) p.getWarehouse()).getStrongBox().getResources()));
        c.send(new VaticanReportMessage(p.getFaithTrack().getPopePoint(), p.getFaithTrack().getPopeTiles()));

        if(p.getWarehouse() instanceof SimpleWarehouse){
            c.send(new ShelvesMessage(p.getWarehouse().getShelf(0),p.getWarehouse().getShelf(1),p.getWarehouse().getShelf(2) ));
            ((SimpleWarehouse) p.getWarehouse()).addObserver(c);
        }else if(p.getWarehouse() instanceof ExtraSlotWarehouse){
            c.send(new ShelvesMessage(p.getWarehouse().getShelf(0),p.getWarehouse().getShelf(1),p.getWarehouse().getShelf(2), p.getWarehouse().getShelf(3)));
            ((ExtraSlotWarehouse) p.getWarehouse()).addObserver(c);
            ((SimpleWarehouse)((ExtraSlotWarehouse) p.getWarehouse()).getWarehouseToBeDecorated()).addObserver(c);
        }else{
            c.send(new ShelvesMessage(p.getWarehouse().getShelf(0),p.getWarehouse().getShelf(1),p.getWarehouse().getShelf(2), p.getWarehouse().getShelf(3), p.getWarehouse().getShelf(4)));
            ((SecondExtraSlotWarehouse) p.getWarehouse()).addObserver(c);

            ExtraSlotWarehouse extra = (ExtraSlotWarehouse)((SecondExtraSlotWarehouse) p.getWarehouse()).getWarehouseToBeDecorated();
            extra.addObserver(c);
            ((SimpleWarehouse)(extra).getWarehouseToBeDecorated()).addObserver(c);
        }

        p.addObserver(c);
        p.getSlotDevelopment().addObserver(c);
    }

    /**
     * This method is used at the beginning of every turn for every player.
     * Sends messages to keep the LocalModels updated
     * @param cl Clienthandler of the player
     * @param pl the player
     */
    private void beginTurn(ClientHandler cl, Player pl){

        cl.send(new MarketMessage(controller.getGame().getMarketBoard()));
        cl.send(new DeckFieldMessage(controller.getGame().getTopCard()));
        cl.send(new FaithTrackMessage(controller.getGame().playersPositions()));
        cl.send(new VaticanReportMessage(pl.getFaithTrack().getPopePoint(), pl.getFaithTrack().getPopeTiles()));

        for(Card lc : pl.getHiddenLeaderCards()){ lc.checkRequirement(pl); }
        cl.send(new ActiveHiddenLeaderCardMessage(pl.getHiddenLeaderCards(), pl.getActiveLeaderCards()));

        ArrayList<Triple<DevelopmentCard, Integer, Integer>> topCards = controller.getGame().checkDeckFieldTopCard(pl);
        cl.send(new DevCardMessage(controller.getGame().getPossibleDevCard(topCards)));

        pl.getSlotDevelopment().checkSlotDevTopCards(pl);
        cl.send(new SlotDevMessage(pl.getSlotDevelopment().getDevSpace()));
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public String getCurrentPlayer(){
        return players[playerTurn];
    }

    public static void setEndGame(){
        endGame = true;
    }
}