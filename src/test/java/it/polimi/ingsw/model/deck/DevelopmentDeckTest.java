package it.polimi.ingsw.model.deck;

import it.polimi.ingsw.model.deck.carddecks.DevelopmentCard;
import it.polimi.ingsw.model.deck.carddecks.EmptyDeckException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;

public class DevelopmentDeckTest {



    @Before
    public void setUp() throws Exception {
        URL path=getClass().getClassLoader().getResource("green3Deck.json");

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createDevelopmentDeckTest() throws EmptyDeckException {
        Deck newDeck=new Deck(DevelopmentCard.createDevelopmentDeck("/developmentdecks/blue1Deck.json"));

      //DA FARE
    }




    @Test
    public void draw() {
    }

    @Test
    public void discardFromBottom() {
    }

}