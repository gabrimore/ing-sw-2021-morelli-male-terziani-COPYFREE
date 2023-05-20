package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.warehouse.ExtraSlotWarehouse;
import it.polimi.ingsw.model.player.warehouse.Resources;
import it.polimi.ingsw.model.player.warehouse.SecondExtraSlotWarehouse;
import it.polimi.ingsw.model.player.warehouse.Warehouse;
import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.network.messages.PrintCLIMessage;
import it.polimi.ingsw.network.messages.SetResourcesMessage;
import it.polimi.ingsw.network.messages.WhiteMarbleMessage;

import java.io.IOException;
import java.util.ArrayList;


public class TakeResourcesController {


    private final ClientHandler clientHandler;
    private int userInput;

    private final Player player;
    private final MainController mainController;

    public TakeResourcesController(ClientHandler clientHandler, Player player, MainController mainController){
        this.clientHandler = clientHandler;
        this.player = player;
        this.mainController = mainController;

    }


    /**
     * this method handles the communication with the client asking whether he want to choose a row or a column of the market
     * @return the index(int) representing the user's choice (row =1, column =2)
     */
    public int chooseRowColumn(){
        //row =1, column =2
        clientHandler.send("\n1) Row\n2) Column\nChoose:");
        do {
            try {
                userInput = clientHandler.read();
                if (!(userInput ==1) && !(userInput ==2) ) throw new IOException();
            } catch (IOException e) {
                clientHandler.send("Invalid input!\n");
                userInput = -1;
            }
        } while (userInput == -1);
        return userInput;
    }

    /**
     * this method handles the communication with the client asking which index of row (or column, depending on the previous choice) he wants
     * @return the index(int) representing the user's choice
     */
    public int chooseIndex(){
        // rows indexes range: [1,3]
        // columns indexes range: [1,4]

        String row_column;
        if(userInput==1) row_column ="row";
        else row_column ="column";

        clientHandler.send("Choose "+row_column+ " :");

        do {
            try {
                userInput = clientHandler.read();
                if(!isInRange(row_column, userInput) ) throw new IOException();
            } catch (IOException e) {
                clientHandler.send("Invalid input!\n");
                userInput = -1;
            }
        }while( userInput == -1);

        return userInput-1;
    }

    /**
     * this method checks whether the choice of the index made by the user is in the range of possible indexes
     * @param row_column the choice made by the user between row and column
     * @param in the index chosen by the user
     * @return a boolean representing whether the index is in the range
     */
    private boolean isInRange(String row_column, int in){
        // rows indexes range: [1,3]
        // columns indexes range: [1,4]
        if(row_column.equals("row") && in >= 1 && in <=3 ) return true;
        return row_column.equals("column") && in >= 1 && in <= 4;
    }

    /**
     * this method creates the list of resources taken from the market and, depending on whether the player is playing with the GUI or the CLI, sends the list to the client
     * @param resources is the list of resources taken from the market
     */
    public void queryPlayer(ArrayList<Resources> resources){

        if(resources.isEmpty()){
            clientHandler.isDone();
        }else {
            ArrayList<Resources> resourcesClone = new ArrayList<>();

            ArrayList<Resources.ResType> resTypes = new ArrayList<>();

            for (Resources re : resources) {
                Resources re1 = new Resources(re.getResourceType(), re.getCounter());
                resourcesClone.add(re1);
                resTypes.add(re.getResourceType());
            }

            if(clientHandler.getCliOrGui() == 2) {
                clientHandler.send(new SetResourcesMessage(resTypes));
            }

            takeResources(resourcesClone);
        }
    }

    /**
     * this method takes the list of resources taken from the market and manage the communication with the client to handle the decisions of the player(switch shelves,  discard, set in shelf)
     * @param resources list of resources to allocate in the shelves or to discard
     */
    private void takeResources(ArrayList<Resources> resources) {

        int input2, input3, input4;



        do{
            clientHandler.send(new PrintCLIMessage(6));
            clientHandler.send("\n");
            printResourcesTaken(resources);

            clientHandler.send("\nChoose a resource: ");
            int resChosen = chooseResource(resources.size());

            clientHandler.send("\n1)Switch shelves\n2)Discard\n3)Set in shelf\nChoose an action: ");
            input2 = chooseAnAction();


            switch (input2){

                case 1 :
                    input3 = chooseShelf();
                    input4 = chooseShelf();

                    if(!player.getWarehouse().switchShelvesContent(input3-1, input4-1)){
                        System.out.println("Illegal action!\n");
                    }
                    break;

                case 2 :
                    resources.remove(resChosen);
                    mainController.moveFaithMark(player);
                    break;

                case 3 :
                    input3 = chooseShelf();

                    if(player.setResourceInShelf( input3-1, resources.get(resChosen))) {
                        resources.remove(resChosen);
                    }else{
                        clientHandler.send("Illegal action!\n");
                    }

                    break;

            }

        }while(!resources.isEmpty());

        clientHandler.isDone();
    }

    /**
     * this method is used in "takeResources()" to print in the CLI the list of resources taken from the market
     * @param resources list of resources taken in the market to be printed on the CLI
     */
    private void printResourcesTaken(ArrayList<Resources> resources){
        int x=0;
        for(Resources res : resources){
            clientHandler.send("("+x+") "+res.getResourceType()+" \t");
            x++;
        }
    }

    /**
     * this method is used in "takeResources()" to ask which resource to take in the CLI
     * @param resourcesSize the size of the list of resources to allocate, needed to check whether the resource chosen exist
     * @return the index of the resource chosen by the player
     */
    private int chooseResource(int resourcesSize){

        do {

            try {
                userInput = clientHandler.read();
                if (userInput >= resourcesSize || userInput < 0 ) throw new IOException();

            } catch (IOException e) {
                clientHandler.send("Invalid input!\n");
                userInput = -1;
                clientHandler.send("Choose a resource: ");
            }

        }while(userInput == -1);

        return userInput;

    }

    /**
     * this method is used in "takeResources()" to ask which action to take in the CLI
     * @return the index of the action chosen by the player
     */
    private int chooseAnAction() {
        do {

            try {
                userInput = clientHandler.read();
                if (!(userInput==1) && !(userInput==2) && !(userInput==3)) throw new IOException();

            } catch (IOException e) {
                clientHandler.send("Invalid input!\n");
                userInput = -1;
            }

        }while(userInput == -1);

        return userInput;
    }

    /**
     * this method is used in "takeResources()" to ask in which shelf allocate the resources in the CLI
     * @return the index of the shelf chosen by the player
     */
    private int chooseShelf() {

        do {
            try {
                clientHandler.send("Choose a shelf: ");
                userInput = clientHandler.read();

                if(player.getWarehouse() instanceof ExtraSlotWarehouse){
                    if ( userInput > 6 || userInput < 1) {
                        throw new IOException();
                    }
                }else if(player.getWarehouse() instanceof SecondExtraSlotWarehouse){
                    if (userInput >5 || userInput<1) {
                        throw new IOException();
                    }
                }else {
                    if (userInput > 3 || userInput < 1) {
                        throw new IOException();
                    }
                }

            } catch (IOException e) {
                clientHandler.send("Invalid Input!\n");
                userInput = -1;
            }
        } while (userInput == -1);

        return userInput;
    }

    /**
     * this method manage the communication with the client when there's to choose which white marble changer effect to use
     * @return the index of the active leader card with the white marble changer chosen
     */
    public int chooseWhiteMarble(){

        clientHandler.send("(" + 0 + ") " + player.getWhiteMarble().get(0).toString() + "\n");
        clientHandler.send("(" + 1 + ") " + player.getWhiteMarble().get(1).toString() + "\n");
        clientHandler.send("\nChoose a Resource for the White Marble: ");

        do {
            try {
                userInput = clientHandler.read();
                if ( !(userInput==0) && !( userInput==1)) throw new IOException();
            } catch (IOException e) {
                clientHandler.send("Invalid input!\n");
                userInput = -1;
            }
        } while (userInput == -1);
        return userInput;
    }


    /**
     * this method sends to the client the message for the GUI menu for choosing which leader card to use
     * @param white index of the white marble to change
     */
    public void sendNumOfWhiteMarble(int white){
        if (clientHandler.getCliOrGui() == 2){
            clientHandler.send(new WhiteMarbleMessage(white));
        }

    }


}
