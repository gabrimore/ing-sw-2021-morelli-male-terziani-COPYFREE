package it.polimi.ingsw.model.deck;

import it.polimi.ingsw.model.deck.carddecks.ColorLevel;
import it.polimi.ingsw.model.deck.carddecks.DevelopmentCard;
import it.polimi.ingsw.model.deck.carddecks.EmptyDeckException;
import it.polimi.ingsw.model.deck.carddecks.leadercards.*;
import it.polimi.ingsw.model.deck.tokendeck.DiscardToken;
import it.polimi.ingsw.model.deck.tokendeck.MoveForward2Token;
import it.polimi.ingsw.model.deck.tokendeck.MoveShuffleToken;
import it.polimi.ingsw.model.deck.tokendeck.SoloActionToken;
import it.polimi.ingsw.model.player.warehouse.Resources;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;


import static it.polimi.ingsw.model.player.warehouse.Resources.ResType.*;
import static org.junit.Assert.*;

public class DeckTest<T> {

    private Deck developmentDeck, leaderDeck, tokenDeck;
    private ArrayList<DevelopmentCard> developmentCards;
    private ArrayList<LeaderCard> leaderCards;
    private ArrayList<SoloActionToken> tokens;


    private DevelopmentCard dc1, dc2, dc3, dc4;

    private Resources resIN1, resIN2, resIN3, resIN4;
    private Resources resCost1, resCost2, resCost3, resCost4;
    private Resources resCoin, resShield, resServant, resStone;

    private Requirement rqColLev, rqRes;

    private LeaderCard lc1,lc2,lc3,lc4;

    private SoloActionToken tk1, tk2, tk3, tk4;


    @Before
    public void setUp() throws Exception {


        developmentCards =new ArrayList<>();


        resCoin= new Resources(COIN, 1);
        resShield= new Resources(SHIELD, 1);
        resServant= new Resources(SERVANT, 1);
        resStone= new Resources(STONE, 1);

        resIN1= new Resources(SERVANT, 2);
        resIN2= new Resources(COIN, 2);
        resIN3= new Resources(STONE, 2);
        resIN4= new Resources(SHIELD, 2);

        resCost1= new Resources(SERVANT, 3);
        resCost2= new Resources(COIN, 3);
        resCost3= new Resources(STONE, 3);
        resCost4= new Resources(SHIELD, 3);

        ArrayList<Resources> c1=new ArrayList<>(1);
        c1.add(resCost1);
        ArrayList<Resources> c2=new ArrayList<>(1);
        c2.add(resCost2);
        ArrayList<Resources> c3=new ArrayList<>(1);
        c3.add(resCost3);
        ArrayList<Resources> c4=new ArrayList<>(1);
        c4.add(resCost4);

        ArrayList<Resources> in1=new ArrayList<>(1);
        in1.add(resIN1);
        ArrayList<Resources> in2=new ArrayList<>(1);
        in2.add(resIN2);
        ArrayList<Resources> in3=new ArrayList<>(1);
        in3.add(resIN3);
        ArrayList<Resources> in4=new ArrayList<>(1);
        in4.add(resIN4);

        ArrayList<Resources> out1=new ArrayList<>(3);
        out1.add(resCoin);
        out1.add(resShield);
        out1.add(resStone);
        ArrayList<Resources> out2=new ArrayList<>(3);
        out2.add(resServant);
        out2.add(resShield);
        out2.add(resStone);
        ArrayList<Resources> out3=new ArrayList<>(3);
        out3.add(resCoin);
        out3.add(resServant);
        out3.add(resShield);
        ArrayList<Resources> out4=new ArrayList<>(3);
        out4.add(resCoin);
        out4.add(resServant);
        out4.add(resStone);


        dc1 = new DevelopmentCard(c1, 3, in1, out1, 0, ColorLevel.GREEN1);
        dc2 = new DevelopmentCard(c2, 3, in2, out2, 0, ColorLevel.BLUE1);
        dc3 = new DevelopmentCard(c3, 3, in3, out3, 0, ColorLevel.VIOLET1);
        dc4 = new DevelopmentCard(c4, 3, in4, out4, 0, ColorLevel.YELLOW1);

        developmentCards.add(dc1);
        developmentCards.add(dc2);
        developmentCards.add(dc3);
        developmentCards.add(dc4);


        developmentDeck =new Deck(developmentCards);

        tokens=new ArrayList<>();
        tk1=new MoveShuffleToken();
        tk2=new MoveForward2Token();
        tk3=new DiscardToken("GREEN");
        tk4=new DiscardToken("YELLOW");

        tokens.add(tk1);
        tokens.add(tk2);
        tokens.add(tk3);
        tokens.add(tk4);

        tokenDeck=new Deck(tokens);


        leaderCards=new ArrayList<>();
        rqColLev=new Requirement();
        ArrayList<ColorLevel> colLev=new ArrayList<>();
        colLev.add(ColorLevel.GREEN1);
        colLev.add(ColorLevel.BLUE1);
        rqRes=new Requirement();
        ArrayList<Resources> res =new ArrayList<>();
        res.add(resStone);
        rqRes.setResourcesRequirement(res);
        lc1=new AdditionalProduction(1, rqColLev, SHIELD);
        lc2=new Discount(2, rqColLev, STONE);
        lc3=new WhiteMarbleChanger(3, rqColLev, COIN);
        lc4=new ExtraSlot(4, rqRes, SERVANT);
        leaderCards.add(lc1);
        leaderCards.add(lc2);
        leaderCards.add(lc3);
        leaderCards.add(lc4);

        leaderDeck=new Deck(leaderCards);


    }

    @After
    public void tearDown() throws Exception {

    dc1=null;
    dc2=null;
    dc3=null;
    dc4=null;

    developmentDeck =null;
    leaderDeck=null;
    tokenDeck=null;

    developmentCards=null;
    leaderCards=null;
    tokens=null;

    }

    @Test
    public void draw() throws EmptyDeckException {

        assertEquals(developmentDeck.draw(), dc1);
        assertEquals(developmentDeck.draw(), dc2);
        assertEquals(developmentDeck.draw(), dc3);
        assertEquals(developmentDeck.draw(), dc4);

    }


    @Test
    public void shuffle() {
    }


    @Test
    public void drawDevelopmentCard() throws EmptyDeckException {

        assertEquals(developmentDeck.drawDevelopmentCard(), dc1);
    }

    @Test
    public void drawLeaderCard() throws EmptyDeckException {
        assertEquals(leaderDeck.drawLeaderCard(), lc1);
        assertEquals(leaderDeck.drawLeaderCard(), lc2);
        assertEquals(leaderDeck.drawLeaderCard(), lc3);
        assertEquals(leaderDeck.drawLeaderCard(), lc4);
    }

    @Test
    public void drawSoloActionToken() throws EmptyDeckException {
        assertEquals(tokenDeck.drawSoloActionToken(), tk1);
        assertEquals(tokenDeck.drawSoloActionToken(), tk2);
        assertEquals(tokenDeck.drawSoloActionToken(), tk3);
        assertEquals(tokenDeck.drawSoloActionToken(), tk4);
    }

    @Test
    public void getDeckSize() {
        assertEquals(leaderDeck.getDeckSize(), 4);
        assertEquals(tokenDeck.getDeckSize(), 4);
        assertEquals(developmentDeck.getDeckSize(), 4);
    }

    @Test
    public void getDeckItems() {
        assertEquals(leaderDeck.getDeckItems(), leaderCards);
        assertEquals(developmentDeck.getDeckItems(), developmentCards);
        assertEquals(tokenDeck.getDeckItems(), tokens);
    }

    @Test
    public void discardFromBottom() throws EmptyDeckException{
        developmentDeck.discardFromBottom();
        assert(developmentDeck.getDeckSize()==3);
        assertEquals(developmentDeck.drawDevelopmentCard(), dc1);
    }

    @Test
    public void getFirstCard() {
        assertEquals(developmentDeck.getFirstCard(), dc1);
        assert(developmentDeck.getDeckSize()==4);
    }
}
