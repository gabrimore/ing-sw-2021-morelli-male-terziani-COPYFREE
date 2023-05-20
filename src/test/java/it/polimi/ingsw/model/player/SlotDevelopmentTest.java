package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.deck.carddecks.ColorLevel;
import it.polimi.ingsw.model.deck.carddecks.DevelopmentCard;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.LocalModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SlotDevelopmentTest implements Observer<Message> {

    private DevelopmentCard dc1, dc2, dc3, dc4, dc5;
    private SlotDevelopment slotDevelopment;
    private LocalModel localModel = new LocalModel();

    @Before
    public void start(){
        this.dc1 = new DevelopmentCard(null, 3, null, null, 1, ColorLevel.GREEN1);
        this.dc2 = new DevelopmentCard(null, 2, null, null, 1, ColorLevel.VIOLET1);
        this.dc3 = new DevelopmentCard(null, 1, null, null, 1, ColorLevel.BLUE2);
        this.dc4 = new DevelopmentCard(null, 4, null, null, 1, ColorLevel.YELLOW3);
        this.dc5 = new DevelopmentCard(null, 0, null, null,0, ColorLevel.YELLOW2);

        this.slotDevelopment = new SlotDevelopment();
    }



    @Test
    public void slotTest(){

        slotDevelopment.addObserver(this);


        assertTrue (slotDevelopment.checkIfCanSetCard(dc1, 0));
        slotDevelopment.setCard(dc1, 0);

        assertFalse(slotDevelopment.checkIfCanSetCard(dc2, 0));
        assertTrue (slotDevelopment.checkIfCanSetCard(dc2, 1));
        assertTrue (slotDevelopment.checkIfCanSetCard(dc3, 0));
        slotDevelopment.setCard(dc2, 1);
        slotDevelopment.setCard(dc3, 0);


        assertFalse(slotDevelopment.checkIfCanSetCard(dc4, 1));
        assertTrue (slotDevelopment.checkIfCanSetCard(dc4, 0));
        slotDevelopment.setCard(dc4, 0);


        assertEquals(slotDevelopment.getTopCard(0), dc4);
        assertEquals(slotDevelopment.getTopCard(1), dc2);
        assertNull(slotDevelopment.getTopCard(2));

        assertEquals(slotDevelopment.getPoints(), 10);

        assertTrue(slotDevelopment.checkIfCanSetCard(dc5, 1));
        slotDevelopment.setCard(dc5, 1);

        ArrayList<ColorLevel> requirement = new ArrayList<ColorLevel>();

        requirement.add(ColorLevel.BLUE2);
        assertTrue(slotDevelopment.checkCard(requirement));

        requirement.remove(0);

        requirement.add(ColorLevel.VIOLET2);
        assertFalse(slotDevelopment.checkCard(requirement));

        requirement.remove(0);

        requirement.add(ColorLevel.YELLOW1);
        requirement.add(ColorLevel.YELLOW1);
        requirement.add(ColorLevel.GREEN1);

        assertTrue(slotDevelopment.checkCard(requirement));

        requirement.remove(0);
        requirement.remove(1);

        requirement.add(ColorLevel.BLUE1);
        requirement.add(ColorLevel.BLUE1);
        assertFalse(slotDevelopment.checkCard(requirement));

        requirement.remove(1);
        requirement.add(ColorLevel.VIOLET1);


        assertTrue(slotDevelopment.checkCard(requirement));
    }

    @Override
    public void update(Message action) {
        action.action(localModel);
        assert(slotDevelopment.getDevSpace() == localModel.getDevSpace());
    }
}