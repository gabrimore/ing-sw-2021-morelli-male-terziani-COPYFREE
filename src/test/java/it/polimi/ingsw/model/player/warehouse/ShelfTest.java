package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observer.Observer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import static it.polimi.ingsw.model.player.warehouse.Resources.ResType;

import static org.junit.Assert.*;

public class ShelfTest implements Observer<Message> {

    private Warehouse simpleWarehouse;
    private ArrayList<Resources> test = new ArrayList<Resources>();

    @Before
    public void setUp() throws Exception {
        simpleWarehouse = new SimpleWarehouse();
        ((SimpleWarehouse) simpleWarehouse).addObserver(this);
        assertNotNull(simpleWarehouse);
    }

    @After
    public void tearDown() throws Exception {
        simpleWarehouse =null;
        assertNull(simpleWarehouse);
    }

    @Test
    public void getShelfCurrentTypeTest() {
        simpleWarehouse.getShelf(2).setResource(new Resources(ResType.COIN, 3));
        assertEquals(simpleWarehouse.getShelf(2).getShelfCurrentType(),ResType.COIN);

        simpleWarehouse.getShelf(2).removeResourceQuantity(3);
        assertNull(simpleWarehouse.getShelf(2).getShelfCurrentType());
    }

    @Test
    public void getNumberOfResourcesTest() {
        simpleWarehouse.getShelf(2).setResource(new Resources(ResType.COIN, 3));
        assertEquals(3, simpleWarehouse.getShelf(2).getShelfNumberOfResources());

        simpleWarehouse.getShelf(2).removeResourceQuantity(3);
        assertEquals( 0, simpleWarehouse.getShelf(2).getShelfNumberOfResources());
    }

    @Test
    public void setResourceTest() {
        // check if the set works smoothly in the standard case: shelves empty.
        simpleWarehouse.setResourceInShelf(2, new Resources(ResType.COIN, 2));
        test.add(new Resources(ResType.COIN, 2));
       assertTrue(simpleWarehouse.checkAvailabilityResources(test));

        // check if the set works smoothly in the standard case: shelves empty.
        simpleWarehouse.setResourceInShelf(1, new Resources(ResType.STONE, 1));
        test.remove(0);
        test.add(new Resources(ResType.STONE, 1));
        assertTrue(simpleWarehouse.checkAvailabilityResources(test));

        // check if the set works smoothly in the case of increasing a shelf resourc: shelves has already that type of resource and we want to increase the counter.
        simpleWarehouse.setResourceInShelf(1, new Resources(ResType.STONE, 1));
        test.remove(0);
        test.add(new Resources(ResType.STONE, 2));
        assertTrue(simpleWarehouse.checkAvailabilityResources(test));

        // check if the set forbids the insertion of a type of resource already present in another shelf
        simpleWarehouse.setResourceInShelf(0, new Resources(ResType.STONE, 1));
        test.remove(0);
        test.add(new Resources(ResType.STONE, 3));
        assertFalse(simpleWarehouse.checkAvailabilityResources(test));

        // checks if the set forbids the insertion of a quantity of resource bigger than the max allowed
        simpleWarehouse.setResourceInShelf(0, new Resources(ResType.SERVANT, 2));
        test.remove(0);
        test.add(new Resources(ResType.SERVANT, 2));
        assertFalse(simpleWarehouse.checkAvailabilityResources(test));

        // checks if the set forbids the insertion of a resource, whose type weren't already in the shelves, in a shelf that contains a different type of resource
        simpleWarehouse.setResourceInShelf(2, new Resources(ResType.SHIELD, 1));
        test.remove(0);
        test.add(new Resources(ResType.SHIELD, 1));
        assertFalse(simpleWarehouse.checkAvailabilityResources(test));

        // checks if the set forbids the insertion of a resource quantity which exceeds the max allowed-already present
        simpleWarehouse.setResourceInShelf(2, new Resources(ResType.COIN, 2));
        test.remove(0);
        test.add(new Resources(ResType.COIN, 4));
        assertFalse(simpleWarehouse.checkAvailabilityResources(test));
    }

    @Test
    public void removeResourceQuantityTest() {
        simpleWarehouse.getShelf(2).setResource(new Resources(ResType.COIN, 3));
        simpleWarehouse.getShelf(2).removeResourceQuantity(1);
        assertEquals( 2, simpleWarehouse.getShelf(2).getShelfNumberOfResources());
        simpleWarehouse.getShelf(2).removeResourceQuantity(1);
        assertEquals(1, simpleWarehouse.getShelf(2).getShelfNumberOfResources());
        simpleWarehouse.getShelf(2).removeResourceQuantity(1);
        assertEquals(0, simpleWarehouse.getShelf(2).getShelfNumberOfResources());
        assertNull(simpleWarehouse.getShelf(2).getShelfCurrentType());

    }

    @Test
    public void checkTypeConstraintTest() {
        simpleWarehouse.getShelf(2).setResource(new Resources(ResType.COIN, 3));
        assertEquals("corrected insert, type correctly setted",ResType.COIN, simpleWarehouse.getShelf(2).getShelfCurrentType());
        assertEquals("corrected insert, counter correctly setted",3, simpleWarehouse.getShelf(2).getShelfNumberOfResources());

        simpleWarehouse.getShelf(2).setResource(new Resources(ResType.COIN, 1));
        assertEquals("corrected insert, type correctly setted",ResType.COIN, simpleWarehouse.getShelf(2).getShelfCurrentType());
        assertEquals("corrected insert, counter correctly setted",3, simpleWarehouse.getShelf(2).getShelfNumberOfResources());

        simpleWarehouse.getShelf(2).removeResourceQuantity(3);
        assertNull(simpleWarehouse.getShelf(2).getShelfCurrentType());
        assertEquals(0, simpleWarehouse.getShelf(2).getShelfNumberOfResources());

        simpleWarehouse.getShelf(2).setResource(new Resources(ResType.SERVANT, 1));
        assertEquals("corrected insert, type correctly setted",ResType.SERVANT, simpleWarehouse.getShelf(2).getShelfCurrentType());
        assertEquals("corrected insert, counter correctly setted",1, simpleWarehouse.getShelf(2).getShelfNumberOfResources());

    }


    @Override
    public void update(Message action){}

}