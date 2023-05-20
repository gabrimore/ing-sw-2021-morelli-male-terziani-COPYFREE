package it.polimi.ingsw.model.deck.carddecks.leadercards;

import it.polimi.ingsw.model.deck.Deck;
import it.polimi.ingsw.model.deck.carddecks.ColorLevel;
import it.polimi.ingsw.model.deck.carddecks.EmptyDeckException;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.warehouse.Resources;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class WhiteMarbleChangerTest {

    private Player p;
    private WhiteMarbleChanger wc;
    private ArrayList<ColorLevel> colLev;
    private Deck leadDeck;
    private Requirement rq;


    @Before
    public void setUp() throws Exception {
        p=new Player("testPlayer", false);
        colLev=new ArrayList<>();
        colLev.add(ColorLevel.GREEN1);
        colLev.add(ColorLevel.BLUE1);
        rq=new Requirement(colLev);


        wc= new WhiteMarbleChanger(5, rq, Resources.ResType.STONE );


    }

    @After
    public void tearDown() throws Exception {
        p=null;
        colLev=null;
        wc=null;
    }

    @Test
    public void specialAbilityTest() {
        wc.specialAbility(p);
        assertEquals(p.getWhiteMarble().get(0).getResourceType(), wc.getOutputResource());
    }

    @Test
    public void checkRequirementTest() {
        wc.checkRequirement(p);

        assertFalse(wc.getState());
    }

    @Test
    public void toStringTest() {
        System.out.println(wc);
    }


    @Test
    public void createWhiteMarbleChangerDeckTest() throws EmptyDeckException {
        leadDeck= new Deck(WhiteMarbleChanger.createWhiteMarbleChangerDeck("/leaderdecks/whiteMarbleChangerLeaderDeck.json"));

        assertEquals(leadDeck.getDeckSize(), 4);
        try {
            assertEquals(leadDeck.drawLeaderCard().getClass(), WhiteMarbleChanger.class);
        } catch (EmptyDeckException e){
            e.printStackTrace();
        }
    }
}