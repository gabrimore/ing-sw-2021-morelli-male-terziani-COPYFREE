package it.polimi.ingsw.model.deck.carddecks;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static it.polimi.ingsw.model.deck.carddecks.ColorLevel.*;
import static org.junit.Assert.*;

public class ColorLevelTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getColorTest() {
        assertEquals(YELLOW2.getColor(), "YLW");
    }

    @Test
    public void getLevelTest() {
        assertEquals(BLUE3.getLevel(), 3);
    }


}