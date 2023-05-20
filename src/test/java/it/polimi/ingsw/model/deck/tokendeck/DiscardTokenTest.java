package it.polimi.ingsw.model.deck.tokendeck;

import it.polimi.ingsw.model.deck.Deck;
import it.polimi.ingsw.model.deck.carddecks.ColorLevel;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DiscardTokenTest {

    private DiscardToken token;
    private Deck deckTest;
    private Player p;
    private Game g;

    @Before
    public void setUp() throws Exception {
        token=new DiscardToken("VIOLET");
        p=new Player("testPlayer", false);
        g=new Game();
        p.soloGame();
    }

    @After
    public void tearDown() throws Exception {
        token=null;
        p=null;
        g=null;
    }

    @Test
    public void effect() {
        token.effect(p,g);
        assertEquals(g.chooseDeck(0,"VIOLET").getDeckSize(), 2);
        token.effect(p,g);
        token.effect(p,g);
        assertEquals(g.chooseDeck(1,"VIOLET").getDeckSize(), 2);
    }

    @Test
    public void getColor() {
        assertEquals(token.getColor(), "VIOLET");
    }

    @Test
    public void createDiscardTokenDeck() {
        deckTest=new Deck(DiscardToken.createDiscardTokenDeck("/tokendecks/discardTokenDeck.json"));

        assertEquals(deckTest.getDeckItems().get(0).getClass(), DiscardToken.class);
        assertEquals(deckTest.getDeckSize(), 4);
    }
}