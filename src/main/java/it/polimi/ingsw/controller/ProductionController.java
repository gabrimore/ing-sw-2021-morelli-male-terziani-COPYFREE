package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.player.warehouse.Resources;
import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.network.messages.ExtraProductionMessage;
import it.polimi.ingsw.network.messages.ProductionMessage;
import it.polimi.ingsw.network.messages.SlotDevMessage;

import java.io.IOException;
import java.util.ArrayList;


public class ProductionController {

    private final ClientHandler clientHandler;
    int userInput;
    ArrayList<Integer> usedInput = new ArrayList<>();
    ArrayList<Integer> usedLeaderInput = new ArrayList<>();

    public ProductionController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    /**
     * this method handles the communication with the client asking which kind of production he wants to do
     * @return the index(int) representing the user's choice
     */
    public int chooseWhatToDo(){

        clientHandler.printView();
        clientHandler.send("1) Development Card Production\n2) Base Production\n3) Extra Production\n4) End Turn\n");

        do{
            try {
                clientHandler.send("Choose an option: ");
                userInput = clientHandler.read();
                if( userInput <1 || userInput > 4) throw new IOException();
            } catch (IOException e) {
                clientHandler.send("Invalid input!\n");
                userInput = -1;
            }
        }while(userInput == -1);

        return userInput;
    }

    /**
     * this method handles the communication with the client asking from which development card he wants to produce
     * @return the index(int) representing the user's choice
     */
    public int chooseIndex(){  // columns indexes range: [1,3]

        if(usedInput.size() > 3){
            clientHandler.send("\nNo Development Card Production available: ");
            return -1;
        }

        do {
        clientHandler.send("\nChoose column: ");
        try {
            userInput = clientHandler.read();
            if(userInput <0 || userInput > 2 || usedInput.contains(userInput)) throw new IOException();
        } catch (IOException e) {
            clientHandler.send("You already made this production or input is not valid\n");
            userInput = -1;
        }
        }while(userInput == -1);

        usedInput.add(userInput);
        return userInput;
    }

    /**
     * this method handles the communication with the client asking the inputs for the base production
     * @return a list containing the two input resources
     */
    public ArrayList<Resources> chooseInput(){

        ArrayList<Resources> input = new ArrayList<>();

        clientHandler.send("1) COIN  2) SERVANT  3) SHIELD  4) STONE\n");

        do {
            clientHandler.send("Choose First Input Resource: ");
            try {
                userInput = clientHandler.read();
                if(userInput <1 ||userInput> 4) throw new IOException();
            } catch (IOException e) {
                clientHandler.send("Invalid input!\n");
                userInput = -1;
            }
        }while(userInput == -1);
        input.add(new Resources(Resources.RES_TYPES.get( userInput - 1), 1));

        do {
            clientHandler.send("\nChoose Second Input Resource: ");
            try {
                userInput = clientHandler.read();
                if(userInput <1 || userInput > 4) throw new IOException();
            } catch (IOException e) {
                clientHandler.send("\nInvalid input!\n");
                userInput = -1;
            }
        }while(userInput == -1);

        if(input.get(0).getResourceType() == Resources.RES_TYPES.get(userInput - 1)){
            input.get(0).setCounter(2);
        }else {
            input.add(new Resources(Resources.RES_TYPES.get(userInput - 1), 1));
        }
        return input;
    }

    /**
     * this method handles the communication with the client asking the output resource for the base production
     * @return the resource chosen as output
     */
    public Resources chooseOutput(){

        do {
            clientHandler.send("\nChoose Output Resource: ");
            try {
                userInput = clientHandler.read();
                if(userInput <1 || userInput > 4) throw new IOException();
            } catch (IOException e) {
                clientHandler.send("\nInvalid input!\n");
                userInput = -1;
            }
        }while(userInput == -1);

        return new Resources(Resources.RES_TYPES.get(userInput - 1), 1);
    }

    /**
     * this method handles the communication with the client asking from which leader card he wants to produce from
     * @param size it's the number of leader cards with the extra production effect currently active
     * @return the index(int) representing the user's choice
     */
    public int chooseExtraInput(int size){

        clientHandler.send("\nChoose an extra production input: ");
        try {
            userInput = clientHandler.read();
            if(userInput <0 || userInput >= size || usedLeaderInput.contains(userInput)) throw new IOException();
        } catch (IOException e) {
            clientHandler.send("You already made this production or input is not valid\n");
            return -1;
        }
        usedLeaderInput.add(userInput);

        return userInput;
    }

    /**
     * this method returns the list of development card already utilized during this production turn
     * @return list of development cards already utilized during this production turn
     */
    public ArrayList<Integer> getUsedInput() {
        return usedInput;
    }

    /**
     * this method returns the list of leader cards with the extra production effect already utilized during this production turn
     * @return list of leader cards with the extra production effect already utilized during this production turn
     */
    public ArrayList<Integer> getUsedLeaderInput() {
        return usedLeaderInput;
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
     * this method sends to the client the GUI message to set the right floating menu scene showing the right quantities
     * @param produced list of resources produced during this production turn
     * @param available list of all the resources available in the warehouse
     * @param point number of faith points earned during this production turn
     * @param usedInput list of development cards already utilized during this production turn
     * @param usedLeaderInput list of leader cards with the extra production effect already utilized during this production turn
     */
    public void sendProductionMessage(ArrayList<Resources> produced, ArrayList<Resources> available, int point, ArrayList<Integer> usedInput, ArrayList<Integer> usedLeaderInput){
        clientHandler.send(new ProductionMessage(produced, available, point, usedInput, usedLeaderInput));
    }

    /**
     * this method sends to the client the GUI message that specify which leader cards with the extra production effect has been already used during this production turn
     * @param extraInput list of leader cards with the extra production effect already utilized during this production turn
     */
    public void sendExtraProductionMessage(ArrayList<Resources> extraInput){
        clientHandler.send(new ExtraProductionMessage(extraInput));
    }

    public void sendSlotDevMessage(SlotDevMessage message){
        clientHandler.send(message);
    }


}