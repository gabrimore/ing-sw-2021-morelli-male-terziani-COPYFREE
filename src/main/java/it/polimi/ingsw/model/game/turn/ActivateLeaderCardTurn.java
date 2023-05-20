
package it.polimi.ingsw.model.game.turn;

import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.faithtrack.VaticanReportException;
import it.polimi.ingsw.model.player.warehouse.ExtraSlotWarehouse;
import it.polimi.ingsw.model.player.warehouse.SecondExtraSlotWarehouse;
import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.network.messages.ShelvesMessage;

import java.io.IOException;


public class ActivateLeaderCardTurn implements Turn{

    private final ClientHandler clientHandler;
    private final Player player;
    private final int available;

    public ActivateLeaderCardTurn(ClientHandler clientHandler, Player player, int available) {
        this.clientHandler = clientHandler;
        this.player = player;
        this.available = available;
        begin();
    }

    /**
     * handle the communication between the controller and the part of the model involved with the leader cards
     */
    @Override
    public void begin() {
        if(player.getHiddenLeaderCards().isEmpty()){
            clientHandler.send("No Leader Cards available");
        }else {

            clientHandler.send("\n1) Activate a Leader Card\n2) Discard a Leader Card\n");
            int choice;

            do {
                clientHandler.send("Choose an action: ");
                try {
                    choice = clientHandler.read();
                    if(choice < 1 || choice >2) throw new IOException();
                } catch (IOException e) {
                    clientHandler.send("\nInvalid input!\n");
                    choice = -1;
                }
            }while(choice == -1);

            if(choice == 1){

                if(available > 0) {

                    boolean stop;
                    do {
                        int userInput;

                        do {
                            clientHandler.send("\nChoose a Leader Card: ");
                            try {
                                userInput = clientHandler.read();
                                if(userInput <0 || userInput >= player.getHiddenLeaderCards().size()) throw new IOException();
                            } catch (IOException e) {
                                clientHandler.send("\nInvalid input!\n");
                                userInput = -1;
                            }
                        }while(userInput == -1);


                        stop = player.setActiveLeaderCard(userInput);

                        if (!stop) {
                            clientHandler.send("\nChoose another Leader Card");
                        }
                    } while (!stop);

                    int check = player.activateSpecialAbility();

                    if(check == 1){
                        ((ExtraSlotWarehouse) player.getWarehouse()).addObserver(clientHandler);
                        clientHandler.send(new ShelvesMessage(player.getWarehouse().getShelf(0), player.getWarehouse().getShelf(1), player.getWarehouse().getShelf(2),player.getWarehouse().getShelf(3)));
                    }else if(check == 2){
                        ((SecondExtraSlotWarehouse) player.getWarehouse()).addObserver(clientHandler);
                        clientHandler.send(new ShelvesMessage(player.getWarehouse().getShelf(0), player.getWarehouse().getShelf(1), player.getWarehouse().getShelf(2),player.getWarehouse().getShelf(3), player.getWarehouse().getShelf(4)));
                    }

                }else{
                    clientHandler.send("\nCan't activate any of your Leader Cards\n");
                }

            }else{
                int userInput;

                do {
                    clientHandler.send("\nChoose a Leader Card: ");
                    try {
                        userInput = clientHandler.read();
                        if(userInput <0 || userInput >= player.getHiddenLeaderCards().size()) throw new IOException();
                    } catch (IOException e) {
                        clientHandler.send("\nInvalid input!\n");
                        userInput = -1;
                    }
                }while(userInput == -1);

                try {
                    player.discardLeaderCard(userInput);
                } catch (VaticanReportException e) {
                    MainController.vaticanReport(player, e);
                }
            }
        }
    }


}
