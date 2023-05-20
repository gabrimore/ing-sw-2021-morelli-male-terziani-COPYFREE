package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.model.deck.carddecks.DevelopmentCard;
import it.polimi.ingsw.view.LocalModel;

import java.util.ArrayList;

public class DeckFieldMessage implements Message{

    private DevelopmentCard[][] topCards;

    public DeckFieldMessage(DevelopmentCard[][] topCards) {
        this.topCards = topCards;
    }

    @Override
    public void action(LocalModel localModel) {

        localModel.setDeckField(topCards);
        /*
        int i = 0;
        for(DevelopmentCard dc : topCards){
            System.out.print("(" + i + ") ");
            System.out.print("Type: " + dc.getClass() + "\n");
            System.out.print("ColorLevel: " + dc.getColorLevel() + "\n");
            System.out.print("Cost: " + dc.getCost() + "\n");
            System.out.print("Victory Point: " + dc.getVictoryPoint() + "\n");
            System.out.print("You pay: " + dc.getInputResources() + "\n");
            System.out.print("You get: " + dc.getOutputResources() + "\n");
            System.out.print("\n");
            i++;
        }
         */

    }


}
