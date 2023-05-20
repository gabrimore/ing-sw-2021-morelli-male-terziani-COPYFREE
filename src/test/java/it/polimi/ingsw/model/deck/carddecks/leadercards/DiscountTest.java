package it.polimi.ingsw.model.deck.carddecks.leadercards;

import it.polimi.ingsw.model.deck.Deck;
import it.polimi.ingsw.model.deck.carddecks.ColorLevel;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.warehouse.Resources;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DiscountTest {

    private Game g;
    private Player p;
    private Discount dcard;
    private Requirement rq;
    private ArrayList<ColorLevel> colLev;
    private Deck leadDeck;



    @Before
    public void setUp() throws Exception {
        p=new Player("testPlayer", false);
        colLev=new ArrayList<>();
        colLev.add(ColorLevel.YELLOW1);
        colLev.add(ColorLevel.BLUE3);
        rq=new Requirement(colLev);


        dcard= new Discount(5, rq, Resources.ResType.COIN);
        Resources r1=new Resources(Resources.ResType.COIN, 0);
        ArrayList<Resources> a1=new ArrayList<>();
        a1.add(r1);
        Resources r2=new Resources(Resources.ResType.COIN, 0);
        ArrayList<Resources> a2=new ArrayList<>();
        a2.add(r2);

        //p.setDevCard(new DevelopmentCard(a1, 0, null, null, 0, ColorLevel.YELLOW1), 0);
        //p.setDevCard(new DevelopmentCard(a2, 0, null, null, 0, ColorLevel.BLUE3), 1);

    }

    @After
    public void tearDown() throws Exception {
        p=null;
        colLev=null;
        dcard=null;
        rq=null;
    }

    @Test
    public void getDiscountResourceTest() {
        assertEquals(dcard.getDiscountResource(), Resources.ResType.COIN);
    }

    @Test
    public void specialAbilityTest() {
        dcard.specialAbility(p);

        //assertEquals(p.getDiscount(), dcard.getDiscountResource());
    }

    @Test
    public void getRequirementTest() {
        assertEquals(dcard.getRequirement(), rq);
    }

    @Test
    public void checkRequirementTest() {
    //not testable because of observer
    }


    @Test
    public void createDiscountDeckTest() {
        leadDeck= new Deck(WhiteMarbleChanger.createWhiteMarbleChangerDeck("/leaderdecks/discountLeaderDeck.json"));

        System.out.println(leadDeck);
    }
}