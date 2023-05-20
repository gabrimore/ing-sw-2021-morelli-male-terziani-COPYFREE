package it.polimi.ingsw.model.player.warehouse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static it.polimi.ingsw.model.player.warehouse.Resources.ResType.*;

import java.util.*;

import static org.junit.Assert.*;

public class StrongBoxTest {

    private StrongBox strongBox;
    private ArrayList<Resources> resources;

    @Before
    public void setUp() throws Exception {
        strongBox = new StrongBox();
        resources = new ArrayList<>();
        assertNotNull(strongBox);
    }

    @After
    public void tearDown() throws Exception {
        strongBox =null;
        assertNull(strongBox);
    }

    @Test
    public void getResourcesTest() {
        resources.add( new Resources(COIN, 1 ));
        resources.add( new Resources(SERVANT, 8 ));
        resources.add( new Resources(SHIELD, 3 ));
        resources.add( new Resources(STONE, 4 ));

        strongBox.setResource(new Resources(COIN, 1 ));
        strongBox.setResource(new Resources(SERVANT, 8 ));
        strongBox.setResource(new Resources(SHIELD, 3 ));
        strongBox.setResource(new Resources(STONE, 4 ));

        assertTrue(resources.size() == strongBox.getResources().size());

        int i=0;
        for (Resources res : resources) {
            assertTrue(resources.get(i).equals(strongBox.getResources().get(i)) );
            i++;
        }
    }

    @Test
    public void setResourceTest() {
        resources.add( new Resources(COIN, 9 ));
        resources.add( new Resources(SHIELD, 7 ));

        strongBox.setResource(new Resources(COIN, 1 ));
        strongBox.setResource(new Resources(COIN, 8 ));
        strongBox.setResource(new Resources(SHIELD, 3 ));
        strongBox.setResource(new Resources(SHIELD, 4 ));

        assertTrue(resources.size() == strongBox.getResources().size());

        int i=0;
        for (Resources res : resources) {
            assertTrue(resources.get(i).equals(strongBox.getResources().get(i)) );
            i++;
        }

    }

    @Test
    public void removeResourceQuantityTest() {
        resources.add( new Resources(COIN, 1 ));
        resources.add( new Resources(STONE, 3 ));

        strongBox.setResource(new Resources(COIN, 1 ));
        strongBox.setResource(new Resources(SERVANT, 8 ));
        strongBox.setResource(new Resources(SHIELD, 3 ));
        strongBox.setResource(new Resources(STONE, 4 ));

        strongBox.removeResourceQuantity(3, SHIELD);
        strongBox.removeResourceQuantity(1, STONE);
        strongBox.removeResourceQuantity(8, SERVANT);


        assertTrue(resources.size() == strongBox.getResources().size());

        int i=0;
        for (Resources res : resources) {
            assertTrue(resources.get(i).equals(strongBox.getResources().get(i)) );
            i++;
        }

    }

}