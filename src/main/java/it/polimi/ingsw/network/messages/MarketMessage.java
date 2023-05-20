package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.model.game.MarketBoard;
import it.polimi.ingsw.view.LocalModel;

public class MarketMessage implements Message{

    private final MarketBoard marketBoard;

    public MarketMessage(MarketBoard marketBoard) {
        this.marketBoard = marketBoard;
    }

    @Override
    public void action(LocalModel localModel) {

        localModel.setMarketBoard(marketBoard.getStructure());
        localModel.setExtraMarble(marketBoard.getExtraMarble());
    }
}
