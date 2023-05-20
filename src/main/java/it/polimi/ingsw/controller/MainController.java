package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.deck.Deck;
import it.polimi.ingsw.model.deck.carddecks.*;
import it.polimi.ingsw.model.deck.carddecks.leadercards.LeaderCard;
import it.polimi.ingsw.model.deck.tokendeck.SoloActionToken;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Triple;
import it.polimi.ingsw.model.game.turn.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.faithtrack.VaticanReportException;
import it.polimi.ingsw.model.player.warehouse.Resources;
import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.PlayerAction;
import it.polimi.ingsw.model.game.Double;

import java.io.IOException;
import java.util.ArrayList;

public class MainController implements Observer<PlayerAction> {

    private static Game game;
    private static MainController mainController;
    private Deck leaderDeck;
    private Deck soloActionTokenDeck;

    private boolean soloGame = false;

    public MainController(){
        game = new Game();
    }

    public static MainController instance(){ //Singleton
        if(mainController == null) mainController = new MainController();
        return mainController;
    }

    /**
     * Add a new player to the Game
     * @param name the nickname of the player
     * @return number of player added
     */
    public int addNewPlayerToGame(String name){
        return game.addNewPlayer(name);
    }

    public Game getGame() {
        return game;
    }

    public boolean findOldGame(String path) {
        Game g = Game.uploadGame(path);
        if (g != null) {
            game = g;
                return true;
            } else {
                System.out.println("No Game found\n");
                return false;
            }
    }

    /**
     * Set Game in solo mode
     */
    public void setSoloGame() {
        this.soloGame = true;
        this.soloActionTokenDeck = game.getSoloActionDeck();
        game.getPlayer(0).soloGame();
    }

    /**
     * Start a new Turn
     * @param act contains the turn type chosen by the player, the position and the Clienthandler of the player
     */
    private void startTurn(PlayerAction act){
        Player player = game.getPlayer(act.getTurn());

        switch(act.getChoice()) {

            case 1:
                act.getClientHandler().send(new PrintCLIMessage(2));
                new TakeResourcesTurn(new TakeResourcesController(act.getClientHandler(), player, this), player, game);
                break;

            case 2:
                int available = 0;
                for(Card lc : player.getHiddenLeaderCards()){ if(lc.getState()) available++;}

                new ActivateLeaderCardTurn(act.getClientHandler(), player, available);
                if (act.getClientHandler().getCliOrGui() == 2) {
                    act.getClientHandler().send(new StartTurnMessage(true, player.getNickname()));
                }
                break;

            case 3:
                ArrayList<Triple<DevelopmentCard, Integer, Integer>> topCards = game.checkDeckFieldTopCard(player);

                if(!topCards.isEmpty()) {

                    act.getClientHandler().send(new PrintCLIMessage(4));

                    new BuyDevCardTurn(new BuyDevCardController(act.getClientHandler(), topCards), player, game);

                }else{
                    act.getClientHandler().send("No Development Card Available\n");
                }
                break;

            case 4:
                new ProductionTurn(new ProductionController(act.getClientHandler()), player);
                break;
        }
        try {
            Game.saveGame(game);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Draw a token during the solo mode
     * @param ch the player's Clienthandler
     */
    public void drawSoloToken(ClientHandler ch){
        try {
            SoloActionToken token = soloActionTokenDeck.drawSoloActionToken();
            token.effect(game.getPlayer(0), game);
            soloActionTokenDeck = game.getSoloActionDeck();

            ch.send(new SoloTokenMessage(token.getImageURL(), token.toString()));

            ch.send("SoloToken: " + token.toString() + "\n");
        } catch (EmptyDeckException e) {
            System.out.println("Empty SoloDeck\n");
        }
    }

    @Override
    public void update(PlayerAction action) {
        startTurn(action);
    }

    /**
     * Move the FaithMark of other players
     * @param player the player that invoked the method
     */
    public void moveFaithMark(Player player){
        for(Player p : game.getPlayers()){
            if(p != player) {
                try {
                    p.moveFaithMarker(1);
                } catch (VaticanReportException e) {
                    vaticanReport(p, e);
                }
            }
        }
    }

    /**
     * Invoke a vatican report to other players (Or to Lorenzo in solo mode)
     * @param player The player that caused the Vatican report
     * @param e VaticanReportException that contains info about the Report
     */
    public static void vaticanReport (Player player, VaticanReportException e){
        if(mainController.soloGame){
            player.lorenzoVaticanReport(e.getPope());
            if (e.getPope() == 2) {
                Server.setTypeOfEndgame(2);
                Server.setEndGame();
            }

        }else {
            for (Player pl : game.getPlayers()) {
                if (pl != player){
                    pl.vaticanReport(e.getPope());
                }

            }
            if (e.getPope() == 2) {
                Server.setEndGame();
            }
        }
    }

    /**
     * Count all the Victory Point of the Players at the end of the game
     * @param numOfPlayers Number of player that played the game
     * @return an arraylist containing all the points earned along with the nicknames
     */
    public ArrayList<Double<String, Integer>> countVPS(int numOfPlayers){
        ArrayList<Double<String, Integer>> allPoints = new ArrayList<>();
        for(int i=0; i<numOfPlayers; i++){
            allPoints.add(new Double<>(game.getPlayer(i).getNickname(), game.getPlayer(i).countVictoryPoints()));
        }
        return allPoints;
    }

    /**
     * Count all the resources gained by the Players at the end of the game
     * @param player One player
     * @return the total of resources
     */
    public int countResources(int player){
        int count = 0;

        for(Resources res : game.getPlayer(player).getWarehouse().allResources()){
            count += res.getCounter();
        }
        return count;
    }

    /**
     * Asks a player to choose two leader card
     * @param clientHandler the Clienthandler associated to the player
     * @param pos the position of the player in the game
     * @return the Thread running this function
     */
    public Thread setStartLeaderCardsAndResources(ClientHandler clientHandler, int pos){
        Thread t = new Thread(() -> {

            this.leaderDeck = game.getLeaderDeck();

            ArrayList<LeaderCard> options = new ArrayList<>();
            Player pl = game.getPlayer(pos);

            int userInput;

            for (int i = 0; i < 4; i++) {
                try {
                    options.add(leaderDeck.drawLeaderCard());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            clientHandler.send(new LeaderCardMessage(options));
            clientHandler.send(new CheckConnectionMessage());

            if(clientHandler.getCliOrGui() == 2) {
                clientHandler.send(new StartGameMessage(false, clientHandler.getMyTurn() + 1, clientHandler.getNickname()));
            }else{
                clientHandler.send(new PrintCLIMessage(1));
            }

            for(int i=1; i<3; i++) {

                do{
                    try {
                        clientHandler.send("\nChoose index of the " + i +"° Leader Card: ");

                        userInput = clientHandler.read();
                        if(userInput <1 || userInput > 4) throw new IOException();
                    } catch (IOException e) {
                        clientHandler.send("\nInvalid input!\n");
                        userInput = -1;
                    }
                }while(userInput == -1);
                pl.addLeaderCard(options.get(userInput-1));
            }


            Thread resThread = setStartResources(clientHandler, pl);
            try {
                resThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

        t.start();
        return t;
    }


    /**
     * Asks a player to choose one (2nd and 3rd player) or two (4th player) resources
     * @param clientHandler the Clienthandler associated to the player
     * @param pl the player in the game
     * @return the Thread running this function
     */
    public Thread setStartResources(ClientHandler clientHandler, Player pl){

        Thread t = new Thread(() -> {
            int turn = clientHandler.getMyTurn();
            int userInput;
            Resources chosen;

            if(turn > 0){

                clientHandler.send("\n1) COIN 2) SERVANT 3) SHIELD 4) STONE\n");

                do {
                    clientHandler.send("\nChoose a Resource: ");
                    try {
                        userInput = clientHandler.read();
                        if(userInput <1 || userInput > 4) throw new IOException();
                    } catch (IOException e) {
                        clientHandler.send("\nInvalid input!\n");
                        userInput = -1;
                    }
                }while(userInput == -1);
                chosen = new Resources(Resources.RES_TYPES.get(userInput - 1), 1);

                do {
                    clientHandler.send("\nChoose Shelf Index: ");
                    try {
                        userInput = clientHandler.read();
                        if(userInput <1 || userInput > 3) throw new IOException();
                    } catch (IOException e) {
                        clientHandler.send("\nInvalid Index!\n");
                        userInput = -1;
                    }
                }while(userInput == -1);

                pl.setResourceInShelf(userInput-1, chosen);

                switch(turn){

                    case 2:
                        try {
                            pl.moveFaithMarker(1);
                        } catch (VaticanReportException ignored) {}
                        break;

                    case 3:

                        boolean stop = false;

                        do {

                            do {
                                clientHandler.send("\nChoose a second Resource: ");
                                try {
                                    userInput = clientHandler.read();
                                    if (userInput < 1 || userInput > 4) throw new IOException();
                                } catch (IOException e) {
                                    clientHandler.send("\nInvalid input!\n");
                                    userInput = -1;
                                }
                            } while (userInput == -1);
                            chosen = new Resources(Resources.RES_TYPES.get(userInput - 1), 1);

                            do {
                                clientHandler.send("\nChoose Shelf Index: ");
                                try {
                                    userInput = clientHandler.read();
                                    if (userInput < 1 || userInput > 3)
                                        throw new IOException();
                                } catch (IOException e) {
                                    clientHandler.send("\nInvalid input!\n");
                                    userInput = -1;
                                }

                            } while (userInput == -1);

                            if(pl.setResourceInShelf(userInput-1, chosen)){
                                stop = true;
                            }else{
                                clientHandler.send("Invalid choice!\n");
                            }

                        }while(!stop);

                        try {
                            pl.moveFaithMarker(1);
                        } catch (VaticanReportException ignored) {}
                        break;

                    default:
                        break;
                }
            }

        });

        t.start();
        return t;
    }

}