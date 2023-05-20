package it.polimi.ingsw.model.deck.carddecks.leadercards;

import it.polimi.ingsw.model.deck.carddecks.ColorLevel;
import it.polimi.ingsw.model.player.warehouse.Resources;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RequirementTest {

    private WhiteMarbleChanger wcard;
    private ExtraSlot escard;
    private ArrayList<ColorLevel> reqColLev;
    private ArrayList<Resources> reqRes;
    private Requirement rq;

    private Resources res;




    @Before
    public void setUp() throws Exception {
        reqColLev=new ArrayList<>();
        reqRes=new ArrayList<>();
        reqColLev.add(ColorLevel.BLUE1);
        res=new Resources(Resources.ResType.COIN, 3);
        reqRes.add(res);


        wcard=new WhiteMarbleChanger(8, rq, Resources.ResType.STONE);
    }

    @After
    public void tearDown() throws Exception {
        reqColLev=null;
        rq=null;
        wcard=null;
    }

    @Test
    public void setColorLevelRequirement() {
        rq=new Requirement();
        rq.setColorLevelRequirement(reqColLev);

        assertNotNull(rq);
    }

    @Test
    public void setResourcesRequirement() {
        rq=new Requirement();
        rq.setResourcesRequirement(reqRes);

        assertNotNull(rq);

    }

    @Test
    public void getColorLevelRequirement() {
        rq=new Requirement(reqColLev);

        assertEquals(rq.getColorLevelRequirement().get(0), ColorLevel.BLUE1);
    }

    @Test
    public void getResourcesRequirement() {
        rq=new Requirement();
        rq.setResourcesRequirement(reqRes);

        assertEquals(rq.getResourcesRequirement(), reqRes);
    }
}