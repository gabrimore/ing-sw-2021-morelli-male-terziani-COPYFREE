package it.polimi.ingsw.model.deck.tokendeck;

import it.polimi.ingsw.model.deck.Deck;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MoveForward2TokenTest {

    private MoveForward2Token token;
    private Deck tokenDeck;
    private Player p;
    private Game g;


    @Before
    public void setUp() throws Exception {
        token=new MoveForward2Token();
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
        token.effect(p, g);

        assertEquals(p.getLorenzoPosition(), 2);

        token.effect(p,g);

        assertEquals(p.getLorenzoPosition(), 4);

    }

    @Test
    public void createMoveForward2TokenDeckTest() {
        tokenDeck=new Deck(MoveForward2Token.createMoveForward2TokenDeck("/tokendecks/moveForward2TokenDeck.json"));

        assertEquals(tokenDeck.getDeckItems().get(0).getClass(), MoveForward2Token.class);
        assertEquals(tokenDeck.getDeckSize(), 2);

    }
}