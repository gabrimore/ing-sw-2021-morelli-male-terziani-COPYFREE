package it.polimi.ingsw.model.deck.carddecks.leadercards;

import it.polimi.ingsw.model.deck.Deck;
import it.polimi.ingsw.model.deck.carddecks.ColorLevel;
import it.polimi.ingsw.model.deck.carddecks.EmptyDeckException;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.warehouse.Resources;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;

public class AdditionalProductionTest {

    private Deck leadDeck;
    private Player p;
    private Game g;



    @Before
    public void setUp() throws Exception {
       leadDeck=new Deck(AdditionalProduction.createAdditionalProdDeck("/leaderdecks/additionalProductionLeaderDeck.json"));
       p=new Player("playerTest", false);
       g=new Game();
    }

    @After
    public void tearDown() throws Exception {
        leadDeck=null;
        p=null;
        g=null;
    }

    @Test
    public void specialAbility() throws EmptyDeckException {
        //leadDeck.draw().specialAbility();

    }

    @Test
    public void checkRequirement() {
        ArrayList<LeaderCard> c=leadDeck.getDeckItems();
        c.get(0).checkRequirement(p);


    }

    @Test
    public void createAdditionalProdDeck() {
        Deck leadDeck=new Deck(AdditionalProduction.createAdditionalProdDeck("/leaderdecks/additionalProductionLeaderDeck.json"));
        System.out.println(leadDeck);
    }

    @Test
    public void getimageURL() throws EmptyDeckException {
        Deck leadDeck=new Deck(AdditionalProduction.createAdditionalProdDeck("/leaderdecks/additionalProductionLeaderDeck.json"));
        System.out.println(leadDeck.drawLeaderCard().getImageURL());

    }

    @Test
    public void testToString() {
    }
}