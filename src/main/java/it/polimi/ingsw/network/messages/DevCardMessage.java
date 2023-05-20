package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.model.deck.carddecks.DevelopmentCard;
import it.polimi.ingsw.view.LocalModel;

import java.util.ArrayList;

public class DevCardMessage implements Message{

    private final ArrayList<DevelopmentCard> possible;

    public DevCardMessage(ArrayList<DevelopmentCard> possible) {
        this.possible = possible;
    }

    @Override
    public void action(LocalModel localModel) {
        localModel.setPossibleDevCard(possible);
    }
}
