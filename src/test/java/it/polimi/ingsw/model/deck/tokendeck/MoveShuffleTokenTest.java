package it.polimi.ingsw.model.deck.tokendeck;

import it.polimi.ingsw.model.deck.Deck;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoveShuffleTokenTest {

    private MoveShuffleToken token;
    private Deck tokenDeck;
    private Player p;
    private Game g;

    @Before
    public void setUp() throws Exception {
        token=new MoveShuffleToken();
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

        assertEquals(g.getSoloActionDeck().getDeckSize(), 7);
        assertEquals(p.getLorenzoPosition(), 1);
    }

    @Test
    public void createMoveShuffleTokenDeck() {
        Deck deckTest=new Deck(MoveShuffleToken.createMoveShuffleTokenDeck("/tokendecks/moveShuffleTokenDeck.json"));

        //assertEquals(deckTest.getDeckItems().get(0).getClass(), DiscardToken.class);
        assertEquals(deckTest.getDeckSize(), 1);
    }
}