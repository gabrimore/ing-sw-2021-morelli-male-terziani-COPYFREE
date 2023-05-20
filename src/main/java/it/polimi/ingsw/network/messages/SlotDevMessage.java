package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.model.deck.carddecks.DevelopmentCard;
import it.polimi.ingsw.view.LocalModel;

public class SlotDevMessage implements Message{

    private DevelopmentCard[][] developmentCards;

    public SlotDevMessage(DevelopmentCard[][] developmentCards) {
        this.developmentCards = developmentCards;
    }

    @Override
    public void action(LocalModel localModel) {
        localModel.setDevSpace(developmentCards);
    }
}
