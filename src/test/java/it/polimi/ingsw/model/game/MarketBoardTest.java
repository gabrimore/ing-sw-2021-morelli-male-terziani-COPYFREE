package it.polimi.ingsw.model.game;

import it.polimi.ingsw.network.messages.MarketMessage;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.LocalModel;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import static org.junit.Assert.*;

public class MarketBoardTest implements Observer<Message> {

    private MarketBoard board;
    private MarketBoard.Marble extraMarble;
    private LocalModel localModel;

    @Before
    public void start() {
        board = new MarketBoard();
        localModel = new LocalModel();
    }

    @Test
    public void chooseRowTest() {
        board.addObserver(this);

        List<MarketBoard.Marble> selected1 = board.chooseRow(1);
        extraMarble = board.getExtraMarble();
        List<MarketBoard.Marble> selected2 = board.chooseRow(1);
        assertNotEquals(selected1, selected2);
        //assertNotEquals(extraMarble, board.getExtraMarble());
    }

    @Test
    public void chooseColumnTest() {
        board.addObserver(this);

        List<MarketBoard.Marble> selected1 = board.chooseColumn(1);
        extraMarble = board.getExtraMarble();
        List<MarketBoard.Marble> selected2 = board.chooseColumn(1);
        assertNotEquals(selected1, selected2);
        //assertNotEquals(extraMarble, board.getExtraMarble());
    }

    @Override
    public void update(Message action) {
        action.action(localModel);
        assert(board.getStructure() == localModel.getMarketBoard());
    }
}