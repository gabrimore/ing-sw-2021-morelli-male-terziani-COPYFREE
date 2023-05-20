package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.LocalModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import static it.polimi.ingsw.model.player.warehouse.Resources.ResType.*;

import static org.junit.Assert.*;


public class ExtraSlotWarehouseTest implements Observer<Message> {

    Warehouse waDeco;
    LocalModel localModel = new LocalModel();

    @Before
    public void setUp()  {
        SimpleWarehouse warehouse = new SimpleWarehouse();
        warehouse.addObserver(this);

        waDeco = new ExtraSlotWarehouse(warehouse, SHIELD);
        ((ExtraSlotWarehouse) waDeco).addObserver(this);
        assertNotNull(waDeco);
    }

    @After
    public void tearDown() {
        waDeco = null;
        assertNull(waDeco);
    }

    @Test
    public void getShelfTest() {
        waDeco.setResourceInShelf(0, new Resources(COIN, 1));
        waDeco.setResourceInShelf(1, new Resources(SHIELD, 2));
        waDeco.setResourceInShelf(2, new Resources(SERVANT, 3));
        waDeco.setResourceInShelf(3, new Resources(SHIELD, 2));

            assertEquals(COIN, waDeco.getShelf(0).getShelfCurrentType());
            assertEquals(SHIELD, waDeco.getShelf(1).getShelfCurrentType());
            assertEquals(SERVANT, waDeco.getShelf(2).getShelfCurrentType());
            assertEquals(SHIELD, waDeco.getShelf(3).getShelfCurrentType());
    }

    @Test
    public void getStrongBoxTest() {
        ArrayList<Resources> resources = new ArrayList<>();
        resources.add( new Resources(COIN, 1 ));
        resources.add( new Resources(SERVANT, 8 ));
        resources.add( new Resources(SHIELD, 3 ));
        resources.add( new Resources(STONE, 4 ));

        waDeco.setResourceInStrongBox(new Resources(COIN, 1 ));
        waDeco.setResourceInStrongBox(new Resources(SERVANT, 8 ));
        waDeco.setResourceInStrongBox(new Resources(SHIELD, 3 ));
        waDeco.setResourceInStrongBox(new Resources(STONE, 4 ));


        int i=0;
        for (Resources res : resources) {
            assertTrue(res.equals(waDeco.getStrongBox().getResources().get(i)) );
            i++;
        }
    }

    @Test
    public void setResourceInShelfTest() {
        assertFalse(waDeco.setResourceInShelf(0, new Resources(COIN, 2)));

        assertTrue(waDeco.setResourceInShelf(0, new Resources(COIN, 1)));
        assertTrue(waDeco.setResourceInShelf(1, new Resources(SHIELD, 2)));

        assertFalse(waDeco.setResourceInShelf(1, new Resources(SHIELD, 1)));

        assertTrue(waDeco.setResourceInShelf(2, new Resources(SERVANT, 2)));
       assertFalse(waDeco.setResourceInShelf(2, new Resources(SERVANT, 2)));
        assertTrue(waDeco.setResourceInShelf(2, new Resources(SERVANT, 1)));

        assertFalse(waDeco.setResourceInShelf(3, new Resources(SERVANT, 2)));
        assertFalse(waDeco.setResourceInShelf(3, new Resources(COIN, 2)));
        assertFalse(waDeco.setResourceInShelf(3, new Resources(STONE, 2)));
        assertFalse(waDeco.setResourceInShelf(3, new Resources(SHIELD, 3)));
        assertFalse(waDeco.setResourceInShelf(3, new Resources(SHIELD, 5)));

        assertTrue(waDeco.setResourceInShelf(3, new Resources(SHIELD, 1)));
       assertFalse(waDeco.setResourceInShelf(3, new Resources(SHIELD, 2)));
        assertTrue(waDeco.setResourceInShelf(3, new Resources(SHIELD, 1)));

    }

    @Test
    public void setResourceInStrongBoxTest(){
        ArrayList<Resources> resources = new ArrayList<>();

        waDeco.setResourceInStrongBox( new Resources(COIN, 1 ));
        waDeco.setResourceInStrongBox( new Resources(SERVANT, 2 ));
        waDeco.setResourceInStrongBox( new Resources(SHIELD, 1 ));
        waDeco.setResourceInStrongBox( new Resources(STONE, 2 ));
        waDeco.setResourceInStrongBox( new Resources(SERVANT, 2 ));
        waDeco.setResourceInStrongBox( new Resources(STONE, 2 ));
        waDeco.setResourceInStrongBox( new Resources(SHIELD, 2 ));
        waDeco.setResourceInStrongBox( new Resources(SERVANT, 2 ));
        waDeco.setResourceInStrongBox( new Resources(SERVANT, 2 ));

        resources.add(new Resources(COIN, 1 ));
        resources.add(new Resources(SERVANT, 8 ));
        resources.add(new Resources(SHIELD, 3 ));
        resources.add(new Resources(STONE, 4 ));


        int i=0;
        for (Resources res : resources) {
            assertTrue(res.equals(waDeco.getStrongBox().getResources().get(i)) );
            i++;
        }

    }

    @Test
    public void switchShelvesContentTest(){

        assertTrue(waDeco.switchShelvesContent(0, 1));
        assertTrue(waDeco.switchShelvesContent(1, 0));

        assertTrue(waDeco.switchShelvesContent(1, 2));
        assertTrue(waDeco.switchShelvesContent(2, 1));

        assertTrue(waDeco.switchShelvesContent(0, 2));
        assertTrue(waDeco.switchShelvesContent(2, 0));

        //deposits.setResourceInShelf(0, new Resources(ResType.VIOLET, 1));
        waDeco.setResourceInShelf(1, new Resources(Resources.ResType.STONE, 2));
        waDeco.setResourceInShelf(2, new Resources(Resources.ResType.SHIELD, 1));

        assertTrue( waDeco.switchShelvesContent(1, 2));
        assertTrue( waDeco.switchShelvesContent(2, 1));
        assertTrue( waDeco.switchShelvesContent(2, 1));
        assertTrue( waDeco.switchShelvesContent(2, 1)); //same status as before



        assertFalse(waDeco.switchShelvesContent(0, 1)); //error code: 2 = maxAllowed for a shelf violated


        assertTrue( waDeco.switchShelvesContent(0, 2));
        assertTrue( waDeco.switchShelvesContent(0, 2));  //same status as before



        assertFalse( waDeco.switchShelvesContent(1, 0)); //error code: 2

        assertTrue( waDeco.switchShelvesContent(1, 2));
        assertTrue( waDeco.switchShelvesContent(0, 1));

    }

    @Test
    public void checkAvailabilityResourcesTest() {
        waDeco.setResourceInShelf(0, new Resources(SERVANT, 1));
        waDeco.setResourceInShelf(1, new Resources(STONE, 2));
        waDeco.setResourceInShelf(2, new Resources(SHIELD, 3));
        waDeco.setResourceInShelf(3, new Resources(SHIELD, 2)); //extra shelf!

        waDeco.setResourceInStrongBox(new Resources(STONE, 6));
        waDeco.setResourceInStrongBox(new Resources(COIN, 1));
        waDeco.setResourceInStrongBox(new Resources(STONE, 1));
        waDeco.setResourceInStrongBox(new Resources(COIN, 3));


        ArrayList<Resources> test = new ArrayList<>();
        // The order of the list is not relevant
        test.add(new Resources(COIN, 4));
        test.add(new Resources(SHIELD, 5));
        test.add(new Resources(SERVANT, 1));
        test.add(new Resources(STONE, 9));

        // Both the lists have exactly the same quantity so far
        assertTrue(waDeco.checkAvailabilityResources(test));


        test.get(0).setCounter(1);
        test.get(1).setCounter(2);
        test.get(2).setCounter(1);
        test.get(3).setCounter(1);

        // required less than available
        assertTrue(waDeco.checkAvailabilityResources(test));


        test.get(0).setCounter(1);
        test.get(1).setCounter(7);
        test.get(2).setCounter(1);
        test.get(3).setCounter(1);

        // required more than available
        assertFalse(waDeco.checkAvailabilityResources(test));

        //particular test to extra shelf
        test = new ArrayList<>();
        test.add(new Resources(SHIELD, 5));

        waDeco.useResources(test); //now both the shelves and the extra shelf don't have shields

        waDeco.setResourceInShelf(3, new Resources(SHIELD, 2));

        test.remove(0);
        test.add(new Resources(SHIELD, 1));
        assertTrue(waDeco.checkAvailabilityResources(test));

        test.remove(0);
        test.add(new Resources(SHIELD, 2));
        assertTrue(waDeco.checkAvailabilityResources(test));

    }

    @Test
    public void useResourcesTest() {

        // resources available in the deposits
        waDeco.setResourceInShelf(0, new Resources(SERVANT, 1));
        waDeco.setResourceInShelf(1, new Resources(STONE, 2));
        waDeco.setResourceInShelf(2, new Resources(SHIELD, 3));
        waDeco.setResourceInShelf(3, new Resources(SHIELD, 1));

        waDeco.setResourceInStrongBox(new Resources(STONE, 6));
        waDeco.setResourceInStrongBox(new Resources(COIN, 1));
        waDeco.setResourceInStrongBox(new Resources(STONE, 1));
        waDeco.setResourceInStrongBox(new Resources(COIN, 3));

        //Resources we're going to use
        // The order of the list is not relevant
        ArrayList<Resources> test = new ArrayList<>();
        test.add(new Resources(STONE, 8));
        test.add(new Resources(COIN, 2));
        test.add(new Resources(SHIELD, 3));


        //List of resources that should be in the deposits after their usage
        // 1)strong box; 2)extra shelf; 3)shelves;
        // the order of the element in this list is important due to how the check has been written,
        // since the assertions are checked by iteration on the list doing the comparison element by element.
        // in the standard usage the order is not relevant.
        ArrayList<Resources> wareHouseRES = new ArrayList<>();
        wareHouseRES.add(new Resources(STONE, 1));
        wareHouseRES.add(new Resources(COIN, 2));
        wareHouseRES.add(new Resources(SERVANT, 1));
        wareHouseRES.add(new Resources(SHIELD, 1));


        //Checks whether the resources in the test list are present in the warehouse
        assertTrue(waDeco.checkAvailabilityResources(test));

        //we actually use the resources by eliminating them from the warehouse
        waDeco.useResources(test);


        ArrayList<Resources> toCheck = waDeco.allResources();

        assertTrue(toCheck.size() == wareHouseRES.size());


        int i=0;
        for (Resources res : wareHouseRES) {
            // uncomment for debugging purposes
            System.out.println("exptected: "+ wareHouseRES.get(i).getResourceType()+ wareHouseRES.get(i).getCounter()+"   current: "+toCheck.get(i).getResourceType()+ toCheck.get(i).getCounter());
            assertTrue(res.equals(toCheck.get(i)) );
            i++;
        }


    }

    @Test
    public void possibleResourcesTest() {

        waDeco.setResourceInShelf(1, new Resources(STONE, 1));
        waDeco.setResourceInShelf(2, new Resources(SHIELD, 1));

        //the expected list
        ArrayList<Resources> wareHouseRES = new ArrayList<>();

        wareHouseRES.add( new Resources(COIN, 1));
        wareHouseRES.add( new Resources(SERVANT, 1));
        wareHouseRES.add( new Resources(STONE, 1));
        wareHouseRES.add( new Resources(SHIELD, 1));
        wareHouseRES.add( new Resources(SHIELD, 2));
        // of the extra slot
        wareHouseRES.add( new Resources(SHIELD, 1));
        wareHouseRES.add( new Resources(SHIELD, 2));

        /* //uncomment for debugging purposes
        for (int i =0; i<3; i++){
            System.out.println("shelf " + i+ ":" + deposits.getShelf(i).getShelfCurrentType()+" "+ deposits.getShelf(i).getShelfNumberOfResources() );
        }
         */

        ArrayList<Resources> test = waDeco.possibleResources();


        int i=0;
        for (Resources res : test) {
            //uncomment for debugging purposes
            //System.out.println("possible: "+ test.get(i).getResourceType()+ test.get(i).getCounter() );
            assertTrue(res.equals(wareHouseRES.get(i)) );
            i++;
        }
    }

    @Test
    public void allResourcesTest() {

        waDeco.setResourceInShelf(0, new Resources(SERVANT, 1));
        waDeco.setResourceInShelf(1, new Resources(STONE, 2));
        waDeco.setResourceInShelf(2, new Resources(SHIELD, 3));
        waDeco.setResourceInShelf(3, new Resources(SHIELD, 1));
        waDeco.setResourceInShelf(3, new Resources(SHIELD, 1));

        waDeco.setResourceInStrongBox(new Resources(STONE, 6));
        waDeco.setResourceInStrongBox(new Resources(COIN, 1));
        waDeco.setResourceInStrongBox(new Resources(STONE, 1));
        waDeco.setResourceInStrongBox(new Resources(COIN, 3));

        //List of resources that should be in the deposits after their usage
        // 1)strong box; 2)extra shelf; 3)shelves;
        // the order of the element in this list is important due to how the check has been written,
        // since the assertions are checked by iteration on the list doing the comparison element by element.
        // in the standard usage the order is not relevant.
        ArrayList<Resources> test = new ArrayList<>();
        test.add(new Resources(STONE, 9));
        test.add(new Resources(COIN, 4));
        test.add(new Resources(SHIELD, 5));
        test.add(new Resources(SERVANT, 1));


        ArrayList<Resources> wareHouseRES = waDeco.allResources();

        assertTrue(test.size() == wareHouseRES.size());

        int i=0;
        for (Resources res : wareHouseRES) {
            //debug purpose
            //System.out.println("allRes: "+ wareHouseRES.get(i).getResourceType()+ wareHouseRES.get(i).getCounter()+"   ExpectedTest: "+test.get(i).getResourceType()+ test.get(i).getCounter());
            assertTrue(res.equals(test.get(i)) );
            i++;
        }
    }

    @Override
    public void update(Message action) {
        action.action(localModel);
        assertSame(localModel.getShelves()[0].getShelfCurrentType(), waDeco.getShelf(0).getShelfCurrentType());
        assertSame(localModel.getShelves()[1].getShelfCurrentType(), waDeco.getShelf(1).getShelfCurrentType());
        assertSame(localModel.getShelves()[2].getShelfCurrentType(), waDeco.getShelf(2).getShelfCurrentType());
        assertSame(localModel.getShelves()[0].getShelfNumberOfResources(), waDeco.getShelf(0).getShelfNumberOfResources());
        assertSame(localModel.getShelves()[1].getShelfNumberOfResources(), waDeco.getShelf(1).getShelfNumberOfResources());
        assertSame(localModel.getShelves()[2].getShelfNumberOfResources(), waDeco.getShelf(2).getShelfNumberOfResources());
    }


}