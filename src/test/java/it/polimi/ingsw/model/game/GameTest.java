package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.deck.Deck;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void createLeaderDeck() {

        Game g=new Game();
        Deck leaderDeck=g.createLeaderDeck();

        for(Object lc: leaderDeck.getDeckItems()) {
            System.out.println(lc);
        }

        assertNotNull(leaderDeck.getDeckItems());


    }

}