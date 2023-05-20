package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.model.deck.carddecks.leadercards.LeaderCard;
import it.polimi.ingsw.view.LocalModel;

import java.util.ArrayList;

public class ActiveHiddenLeaderCardMessage implements Message{

    private ArrayList<LeaderCard> hiddenLeaderCards;
    private ArrayList<LeaderCard> activeLeaderCards;

    public ActiveHiddenLeaderCardMessage(ArrayList<LeaderCard> hiddenLeaderCards, ArrayList<LeaderCard> activeLeaderCards) {
        this.hiddenLeaderCards = hiddenLeaderCards;
        this.activeLeaderCards = activeLeaderCards;
    }

    @Override
    public void action(LocalModel localModel) {
        localModel.setActiveHiddenLeaderCards(activeLeaderCards, hiddenLeaderCards);
    }
}
