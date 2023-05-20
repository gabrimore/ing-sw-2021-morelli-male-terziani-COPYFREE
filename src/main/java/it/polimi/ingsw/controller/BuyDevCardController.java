package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.deck.carddecks.DevelopmentCard;
import it.polimi.ingsw.model.game.Triple;
import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.network.messages.DevSpaceIndexesMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class BuyDevCardController {

    private final ClientHandler clientHandler;
    private final List<Triple<DevelopmentCard, Integer, Integer>> topCards;

    public BuyDevCardController(ClientHandler clientHandler, List<Triple<DevelopmentCard, Integer, Integer>> topCards) {
        this.clientHandler = clientHandler;
        this.topCards = topCards;
    }

    /**
     * this method handles the communication with the client asking whether he wants to buy a development card or to go back
     * @return index of the choice made
     */
    public int chooseAction(){
        int input;

        do {

            clientHandler.send("1) Choose a card\n2) Go back\nMake a choice: ");
            input = clientHandler.read();
            if(input < 1 || input > 2){
                clientHandler.send("Invalid input!\n");
                input = -1;
            }

        }while (input == -1);

        return input;
    }

    /**
     * this method handles the communication with the client asking which development card he wants to buy between those shown
     * @return index of the choice made
     */
    public Triple<DevelopmentCard, Integer, Integer> chooseCard(){  // columns indexes range: [1,3]
        int userInput;

        do {
            clientHandler.send("\nChoose a card: ");
            try {
                userInput = clientHandler.read();
                if(userInput >= topCards.size()) throw new IOException();
            } catch (IOException e) {
                clientHandler.send("Invalid input!");
                userInput = -1;
            }
        }while(userInput == -1);

        return topCards.get(userInput);
    }

    /**
     * this method handles the communication with the client asking in which column he wants to place the development card just bought
     * @return index of the column choosen
     */
    public int chooseIndex(){  // columns indexes range: [1,3]
        int userInput;

        do {
            clientHandler.send("Choose column: ");
            try {
                userInput = clientHandler.read();
                if(userInput <0 || userInput > 2) throw new IOException();
            } catch (IOException e) {
                clientHandler.send("Invalid input!\n");
                userInput = -1;
            }
        }while(userInput == -1);

        return userInput;
    }

    /**
     * this method sends to the client the CLI responses to the actions
     * @param s is the text sent to the client
     */
    public void send(String s){
        clientHandler.send(s);
    }

    /**
     * this method is invoked once the turn is over
     */
    public void isDone(){
        clientHandler.isDone();
    }

    /**
     * this method sends to the client the GUI message to set the right floating menu scene
     * @param indexes list of the possible indexes in which the player can place the development card
     */
    public void sendPossibleIndexes(ArrayList<Integer> indexes){
        clientHandler.send(new DevSpaceIndexesMessage(indexes));
    }


}
