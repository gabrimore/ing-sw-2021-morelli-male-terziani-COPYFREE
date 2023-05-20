package it.polimi.ingsw.model.player.warehouse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static it.polimi.ingsw.model.player.warehouse.Resources.ResType;


public class ResourcesTest {

    private Resources res;

    @Before
    public void setUp() throws Exception {
        res = new Resources(ResType.COIN, 3);
        assertNotNull(res);
    }

    @After
    public void tearDown() throws Exception {
            res = null;
            assertNull(res);
    }

    @Test
    public void getResourceTypeTest() {
        assertEquals("Type of resource correctly setted", res.getResourceType(), ResType.COIN);
    }

    @Test
    public void getCounterTest() {
        assertEquals("Quantity of resource correctly settled", res.getCounter(),3);
    }

    @Test
    public void setCounterTest() {
        res.setCounter(5);
        assertEquals("Quantity of resource correctly changed", res.getCounter(),5);
    }

    @Test
    public void equalsTest(){
        Resources res1 = new Resources(ResType.COIN, 3);
        Resources res2 = new Resources(ResType.COIN, 3);
        Resources res3 = new Resources(ResType.COIN, 1);
        Resources res4 = new Resources(ResType.SERVANT, 3);

        assertTrue(res1.equals(res2) );
        assertFalse(res1.equals(res3));
        assertFalse(res1.equals(res4));
        assertFalse(res3.equals(res4));
        assertFalse(res2.equals(res3));
    }


}