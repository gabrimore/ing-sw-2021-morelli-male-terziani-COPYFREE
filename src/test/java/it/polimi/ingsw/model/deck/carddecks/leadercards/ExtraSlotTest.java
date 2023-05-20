package it.polimi.ingsw.model.deck.carddecks.leadercards;

import it.polimi.ingsw.model.deck.Deck;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.warehouse.Resources;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ExtraSlotTest {

    private Game g;
    private Player p;
    private ExtraSlot escard, escard2;
    private Requirement rq;
    private ArrayList<Resources> resReq;
    private Deck leadDeck;

    @Before
    public void setUp() throws Exception {
        p=new Player("testPlayer", false);
        resReq=new ArrayList<>();
        resReq.add(new Resources(Resources.ResType.COIN,1));
        resReq.add(new Resources(Resources.ResType.STONE, 1));

        rq=new Requirement();
        rq.setResourcesRequirement(resReq);

        escard=new ExtraSlot(2, rq , Resources.ResType.COIN);
    }

    @After
    public void tearDown() throws Exception {
        p=null;
        resReq=null;
        escard=null;
        rq=null;
    }


    @Test
    public void getExtraSlotResourceTest() {
        assertEquals(escard.getExtraSlotResource(), Resources.ResType.COIN);
    }

    @Test
    public void specialAbilityTest() {
        escard.specialAbility(p);

        assertEquals(p.getWarehouse().getShelf(3).getShelfCurrentType(), escard.getExtraSlotResource());
;
        escard2=new ExtraSlot(0, rq, Resources.ResType.SHIELD);
        escard2.specialAbility(p);

        assertEquals(p.getWarehouse().getShelf(4).getShelfCurrentType(), escard2.getExtraSlotResource());


    }

    @Test
    public void checkRequirementTest() {
/* not doable because of notify

        p.setResourceInShelf(0, new Resources(Resources.ResType.COIN,1));
        p.setResourceInShelf(2, new Resources(Resources.ResType.STONE,1));
        escard.checkRequirement(p);
        assertTrue(escard.getState());

 */
    }

    @Test
    public void createExtraSlotDeckTest() {
        leadDeck= new Deck(WhiteMarbleChanger.createWhiteMarbleChangerDeck("/leaderdecks/extraSlotLeaderDeck.json"));

        System.out.println(leadDeck);
    }
}