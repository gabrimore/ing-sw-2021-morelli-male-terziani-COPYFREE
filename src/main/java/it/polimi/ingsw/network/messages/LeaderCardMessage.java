package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.model.deck.carddecks.leadercards.LeaderCard;
import it.polimi.ingsw.view.LocalModel;

import java.util.ArrayList;

public class LeaderCardMessage implements Message {

    private ArrayList<LeaderCard> leaderCards;

    public LeaderCardMessage(ArrayList<LeaderCard> leaderCards) {
        this.leaderCards = leaderCards;
    }

    @Override
    public void action(LocalModel localModel) {

        localModel.setInitialLeaderCardChoice(leaderCards);

        /*if(hiddenLeaderCards.isEmpty()) {

            localModel.setInitialLeaderCardChoice(leaderCards);
            int i = 0;
            for (LeaderCard lc : leaderCards) {
                System.out.print("(" + i + ") ");
                System.out.print("Type: " + lc.getClass() + "\n");
                System.out.print("Requirement: " + lc.getRequirement() + "\n");
                System.out.print("Victory Point: " + lc.getVictoryPoint() + "\n");
                System.out.print("\n");
                i++;
            }*/
    }

}