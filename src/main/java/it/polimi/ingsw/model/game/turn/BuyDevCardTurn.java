package it.polimi.ingsw.model.game.turn;

import it.polimi.ingsw.controller.BuyDevCardController;
import it.polimi.ingsw.model.deck.carddecks.DevelopmentCard;
import it.polimi.ingsw.model.deck.carddecks.EmptyDeckException;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Triple;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.Server;

import java.util.ArrayList;

public class BuyDevCardTurn implements Turn{

    private final BuyDevCardController buyDevCardController;
    private final Player player;
    private final Game game;

    public BuyDevCardTurn(BuyDevCardController buyDevCardController, Player player, Game game) {
        this.buyDevCardController = buyDevCardController;
        this.player = player;
        this.game = game;
        begin();
    }

    /**
     * handle the communication between the controller and the part of the model involved with the buying of the development cards
     */
    @Override
    public void begin() {
        ArrayList<Integer> indexes = new ArrayList<>();
        boolean stop = false;

        if(buyDevCardController.chooseAction() == 2) return;

        Triple<DevelopmentCard, Integer, Integer> chosenCard = buyDevCardController.chooseCard();

        for(int i=0; i<3; i++){
            if(player.getSlotDevelopment().checkIfCanSetCard(chosenCard.getCard(), i)){
                indexes.add(i);
            }
        }

        buyDevCardController.sendPossibleIndexes(indexes);

        buyDevCardController.send("\nCan be inserted only in: " );
        for (Integer index : indexes) {
            buyDevCardController.send(index + " ");
        }

        do {
            int column = buyDevCardController.chooseIndex();
            if (player.getSlotDevelopment().checkIfCanSetCard(chosenCard.getCard(), column)) {
                player.setDevCard(chosenCard.getCard(), column);
                try {
                    game.removeCard(chosenCard.getX(), chosenCard.getY());
                    stop = true;
                    if(player.getSlotDevelopment().getNumOfCards() == 7){
                        Server.setTypeOfEndgame(2);
                        Server.setEndGame();
                    }
                    buyDevCardController.isDone();
                } catch (EmptyDeckException ignored) {
                }
            } else {
                buyDevCardController.send("Invalid Index, can ONLY be inserted in: ");
                for (Integer index : indexes) {
                    buyDevCardController.send(index + " ");
                }
            }
        }while(!stop);
    }


}
