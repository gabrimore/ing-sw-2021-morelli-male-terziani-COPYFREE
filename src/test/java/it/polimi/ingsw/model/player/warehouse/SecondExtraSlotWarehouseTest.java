package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.LocalModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static it.polimi.ingsw.model.player.warehouse.Resources.ResType.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class SecondExtraSlotWarehouseTest implements Observer<Message> {

    Warehouse waDecoSecond;
    LocalModel localModel = new LocalModel();

    @Before
    public void setUp()  {
        waDecoSecond = new SimpleWarehouse();
        ((SimpleWarehouse) waDecoSecond).addObserver(this);

        waDecoSecond = new ExtraSlotWarehouse(waDecoSecond, SHIELD);
        ((ExtraSlotWarehouse) waDecoSecond).addObserver(this);
        //waDecoSecond.setResourceInShelf(4, new Resources(BLUE,1));
        Shelf extraSlot = waDecoSecond.getShelf(3);

        waDecoSecond = new SecondExtraSlotWarehouse( waDecoSecond, SERVANT, extraSlot);
        ((SecondExtraSlotWarehouse) waDecoSecond).addObserver(this);

        assertNotNull(waDecoSecond);
    }

    @After
    public void tearDown() {
        waDecoSecond = null;
        assertNull(waDecoSecond);
    }

    @Test
    public void getShelfTest() {

        waDecoSecond.setResourceInShelf(0, new Resources(COIN, 1));
        waDecoSecond.setResourceInShelf(1, new Resources(SHIELD, 2));
        waDecoSecond.setResourceInShelf(2, new Resources(SERVANT, 3));
        waDecoSecond.setResourceInShelf(3, new Resources(SHIELD, 2));
        waDecoSecond.setResourceInShelf(4, new Resources(SERVANT, 2));


            assertEquals(COIN, waDecoSecond.getShelf(0).getShelfCurrentType());
            assertEquals(SHIELD, waDecoSecond.getShelf(1).getShelfCurrentType());
            assertEquals(SERVANT, waDecoSecond.getShelf(2).getShelfCurrentType());
            assertEquals(SHIELD, waDecoSecond.getShelf(3).getShelfCurrentType());
            assertEquals(SERVANT, waDecoSecond.getShelf(4).getShelfCurrentType());

    }

    @Test
    public void getStrongBoxTest() {
        ArrayList<Resources> resources = new ArrayList<>();
        resources.add( new Resources(COIN, 1 ));
        resources.add( new Resources(SERVANT, 8 ));
        resources.add( new Resources(SHIELD, 3 ));
        resources.add( new Resources(STONE, 4 ));

        waDecoSecond.setResourceInStrongBox(new Resources(COIN, 1 ));
        waDecoSecond.setResourceInStrongBox(new Resources(SERVANT, 8 ));
        waDecoSecond.setResourceInStrongBox(new Resources(SHIELD, 3 ));
        waDecoSecond.setResourceInStrongBox(new Resources(STONE, 4 ));


        int i=0;
        for (Resources res : resources) {
            assertTrue(res.equals(waDecoSecond.getStrongBox().getResources().get(i)) );
            i++;
        }
    }

    @Test
    public void setResourceInShelfTest() {
       assertFalse(waDecoSecond.setResourceInShelf(0, new Resources(COIN, 2)));

        assertTrue(waDecoSecond.setResourceInShelf(0, new Resources(COIN, 1)));
        assertTrue(waDecoSecond.setResourceInShelf(1, new Resources(SHIELD, 2)));

       assertFalse(waDecoSecond.setResourceInShelf(1, new Resources(SHIELD, 1)));

        assertTrue(waDecoSecond.setResourceInShelf(2, new Resources(SERVANT, 2)));
       assertFalse(waDecoSecond.setResourceInShelf(2, new Resources(SERVANT, 2)));
        assertTrue(waDecoSecond.setResourceInShelf(2, new Resources(SERVANT, 1)));

        //first extrashelf
        assertFalse(waDecoSecond.setResourceInShelf(3, new Resources(SERVANT, 2)));
        assertFalse(waDecoSecond.setResourceInShelf(3, new Resources(COIN, 2)));
        assertFalse(waDecoSecond.setResourceInShelf(3, new Resources(STONE, 2)));
        assertFalse(waDecoSecond.setResourceInShelf(3, new Resources(SHIELD, 3)));
        assertFalse(waDecoSecond.setResourceInShelf(3, new Resources(SHIELD, 5)));

        assertTrue(waDecoSecond.setResourceInShelf(3, new Resources(SHIELD, 1)));
        assertFalse(waDecoSecond.setResourceInShelf(3, new Resources(SHIELD, 2)));
        assertTrue(waDecoSecond.setResourceInShelf(3, new Resources(SHIELD, 1)));

        //second extra shelf
        assertFalse(waDecoSecond.setResourceInShelf(4, new Resources(COIN, 2)));
        assertFalse(waDecoSecond.setResourceInShelf(4, new Resources(STONE, 1)));
        assertFalse(waDecoSecond.setResourceInShelf(4, new Resources(SHIELD, 1)));
        assertFalse(waDecoSecond.setResourceInShelf(4, new Resources(SERVANT, 3)));
        assertFalse(waDecoSecond.setResourceInShelf(4, new Resources(SERVANT, 5)));

        assertTrue(waDecoSecond.setResourceInShelf(4, new Resources(SERVANT, 1)));assertFalse(waDecoSecond.setResourceInShelf(4, new Resources(SERVANT, 2)));
        assertTrue(waDecoSecond.setResourceInShelf(4, new Resources(SERVANT, 1)));


    }

    @Test
    public void setResourceInStrongBoxTest(){
        ArrayList<Resources> resources = new ArrayList<>();

        waDecoSecond.setResourceInStrongBox( new Resources(COIN, 1 ));
        waDecoSecond.setResourceInStrongBox( new Resources(SERVANT, 2 ));
        waDecoSecond.setResourceInStrongBox( new Resources(SHIELD, 1 ));
        waDecoSecond.setResourceInStrongBox( new Resources(STONE, 2 ));
        waDecoSecond.setResourceInStrongBox( new Resources(SERVANT, 2 ));
        waDecoSecond.setResourceInStrongBox( new Resources(STONE, 2 ));
        waDecoSecond.setResourceInStrongBox( new Resources(SHIELD, 2 ));
        waDecoSecond.setResourceInStrongBox( new Resources(SERVANT, 2 ));
        waDecoSecond.setResourceInStrongBox( new Resources(SERVANT, 2 ));

        resources.add(new Resources(COIN, 1 ));
        resources.add(new Resources(SERVANT, 8 ));
        resources.add(new Resources(SHIELD, 3 ));
        resources.add(new Resources(STONE, 4 ));


        int i=0;
        for (Resources res : resources) {
            assertTrue(res.equals(waDecoSecond.getStrongBox().getResources().get(i)) );
            i++;
        }

    }

    @Test
    public void switchShelvesContentTest(){

        assertTrue(waDecoSecond.switchShelvesContent(0, 1));
        assertTrue(waDecoSecond.switchShelvesContent(1, 0));

        assertTrue(waDecoSecond.switchShelvesContent(1, 2));
        assertTrue(waDecoSecond.switchShelvesContent(2, 1));

        assertTrue(waDecoSecond.switchShelvesContent(0, 2));
        assertTrue(waDecoSecond.switchShelvesContent(2, 0));

        //deposits.setResourceInShelf(0, new Resources(ResType.VIOLET, 1));
        waDecoSecond.setResourceInShelf(1, new Resources(Resources.ResType.STONE, 2));
        waDecoSecond.setResourceInShelf(2, new Resources(Resources.ResType.SHIELD, 1));

        assertTrue( waDecoSecond.switchShelvesContent(1, 2));
        assertTrue( waDecoSecond.switchShelvesContent(2, 1));
        assertTrue( waDecoSecond.switchShelvesContent(2, 1));
        assertTrue( waDecoSecond.switchShelvesContent(2, 1)); //same status as before



        assertFalse(waDecoSecond.switchShelvesContent(0, 1)); //error code: 2 = maxAllowed for a shelf violated


        assertTrue( waDecoSecond.switchShelvesContent(0, 2));
        assertTrue( waDecoSecond.switchShelvesContent(0, 2));  //same status as before



        assertFalse( waDecoSecond.switchShelvesContent(1, 0)); //error code: 2

        assertTrue( waDecoSecond.switchShelvesContent(1, 2));
        assertTrue( waDecoSecond.switchShelvesContent(0, 1));

    }

    @Test
    public void checkAvailabilityResourcesTest() {
        waDecoSecond.setResourceInShelf(0, new Resources(SERVANT, 1));
        waDecoSecond.setResourceInShelf(1, new Resources(STONE, 2));
        waDecoSecond.setResourceInShelf(2, new Resources(SHIELD, 3));
        waDecoSecond.setResourceInShelf(3, new Resources(SHIELD, 1)); // extra shelf!
        waDecoSecond.setResourceInShelf(4, new Resources(SERVANT, 2)); //second extra shelf!

        waDecoSecond.setResourceInStrongBox(new Resources(STONE, 6));
        waDecoSecond.setResourceInStrongBox(new Resources(COIN, 1));
        waDecoSecond.setResourceInStrongBox(new Resources(STONE, 1));
        waDecoSecond.setResourceInStrongBox(new Resources(COIN, 3));


        ArrayList<Resources> test = new ArrayList<>();
        // The order of the list is not relevant
        test.add(new Resources(COIN, 4));
        test.add(new Resources(SHIELD, 4));
        test.add(new Resources(SERVANT, 3));
        test.add(new Resources(STONE, 9));

        // Both the lists have exactly the same quantity so far
        assertTrue(waDecoSecond.checkAvailabilityResources(test));


        test.get(0).setCounter(1);
        test.get(1).setCounter(2);
        test.get(2).setCounter(1);
        test.get(3).setCounter(1);

        // required less than available
        assertTrue(waDecoSecond.checkAvailabilityResources(test));


        test.get(0).setCounter(1);
        test.get(1).setCounter(2);
        test.get(2).setCounter(4);
        test.get(3).setCounter(1);

        // required more than available
        assertFalse(waDecoSecond.checkAvailabilityResources(test));

        //particular test to extra shelf
        test = new ArrayList<>();
        test.add(new Resources(SERVANT, 3));

        waDecoSecond.useResources(test); //now both the shelves and the extra shelf don't have servants

        waDecoSecond.setResourceInShelf(4, new Resources(SERVANT, 2));

        test.remove(0);
        test.add(new Resources(SERVANT, 1));
        assertTrue(waDecoSecond.checkAvailabilityResources(test));

        test.remove(0);
        test.add(new Resources(SERVANT, 2));
        assertTrue(waDecoSecond.checkAvailabilityResources(test));

    }

    @Test
    public void useResourcesTest() {

        // resources available in the deposits
        waDecoSecond.setResourceInShelf(0, new Resources(SERVANT, 1));
        waDecoSecond.setResourceInShelf(1, new Resources(STONE, 2));
        waDecoSecond.setResourceInShelf(2, new Resources(SHIELD, 3));
        waDecoSecond.setResourceInShelf(3, new Resources(SHIELD, 1));
        waDecoSecond.setResourceInShelf(4, new Resources(SERVANT, 1));

        waDecoSecond.setResourceInStrongBox(new Resources(STONE, 6));
        waDecoSecond.setResourceInStrongBox(new Resources(COIN, 1));
        waDecoSecond.setResourceInStrongBox(new Resources(STONE, 1));
        waDecoSecond.setResourceInStrongBox(new Resources(COIN, 3));

        //Resources we're going to use
        // The order of the list is not relevant
        ArrayList<Resources> test = new ArrayList<>();
        test.add(new Resources(STONE, 8));
        test.add(new Resources(COIN, 2));
        test.add(new Resources(SHIELD, 3));
        test.add(new Resources(SERVANT, 2));


        //List of resources that should be in the deposits after their usage
        ArrayList<Resources> wareHouseRES = new ArrayList<>();
        wareHouseRES.add(new Resources(STONE, 1));
        wareHouseRES.add(new Resources(COIN, 2));
        wareHouseRES.add(new Resources(SHIELD, 1));


        assertTrue(waDecoSecond.checkAvailabilityResources(test));

        waDecoSecond.useResources(test);


        ArrayList<Resources> toCheck = waDecoSecond.allResources();

        assertTrue(toCheck.size() == wareHouseRES.size());


        int i=0;
        for (Resources res : wareHouseRES) {
            // uncomment for debugging purposes
            //System.out.println("exptected: "+ wareHouseRES.get(i).getResourceType()+ wareHouseRES.get(i).getCounter()+"   current: "+toCheck.get(i).getResourceType()+ toCheck.get(i).getCounter());
            assertTrue(res.equals(toCheck.get(i)) );
            i++;
        }


    }

    @Test //to be adjusted if we want to implement the insertion of 3/4 resources summing also the extrashelf
    public void possibleResourcesTest() {

        waDecoSecond.setResourceInShelf(1, new Resources(STONE, 1));
        waDecoSecond.setResourceInShelf(2, new Resources(SHIELD, 1));

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
        // of the second extra slot
        wareHouseRES.add( new Resources(SERVANT, 1));
        wareHouseRES.add( new Resources(SERVANT, 2));

        /* //uncomment for debugging purposes
        for (int i =0; i<3; i++){
            System.out.println("shelf " + i+ ":" + deposits.getShelf(i).getShelfCurrentType()+" "+ deposits.getShelf(i).getShelfNumberOfResources() );
        }
         */

        ArrayList<Resources> test = waDecoSecond.possibleResources();


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

        waDecoSecond.setResourceInShelf(0, new Resources(SERVANT, 1));
        waDecoSecond.setResourceInShelf(1, new Resources(STONE, 2));
        waDecoSecond.setResourceInShelf(2, new Resources(SHIELD, 3));
        waDecoSecond.setResourceInShelf(3, new Resources(SHIELD, 1));
        waDecoSecond.setResourceInShelf(3, new Resources(SHIELD, 1));
        waDecoSecond.setResourceInShelf(4, new Resources(SERVANT, 1));
        waDecoSecond.setResourceInShelf(4, new Resources(SERVANT, 1));

        waDecoSecond.setResourceInStrongBox(new Resources(STONE, 6));
        waDecoSecond.setResourceInStrongBox(new Resources(COIN, 1));
        waDecoSecond.setResourceInStrongBox(new Resources(STONE, 1));
        waDecoSecond.setResourceInStrongBox(new Resources(COIN, 3));

        //List of resources that should be in the deposits after their usage
        // 1)strong box; 2)extra shelf; 3)shelves; 4)second extrashelf;
        // the order of the element in this list is important due to how the check has been written,
        // since the assertions are checked by iteration on the list doing the comparison element by element.
        // in the standard usage the order is not relevant.
        ArrayList<Resources> test = new ArrayList<>();
        test.add(new Resources(STONE, 9));
        test.add(new Resources(COIN, 4));
        test.add(new Resources(SHIELD, 5));
        test.add(new Resources(SERVANT, 3));


        ArrayList<Resources> wareHouseRES = waDecoSecond.allResources();

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
        assertSame(localModel.getShelves()[0].getShelfCurrentType(), waDecoSecond.getShelf(0).getShelfCurrentType());
        assertSame(localModel.getShelves()[1].getShelfCurrentType(), waDecoSecond.getShelf(1).getShelfCurrentType());
        assertSame(localModel.getShelves()[2].getShelfCurrentType(), waDecoSecond.getShelf(2).getShelfCurrentType());
        assertSame(localModel.getShelves()[0].getShelfNumberOfResources(), waDecoSecond.getShelf(0).getShelfNumberOfResources());
        assertSame(localModel.getShelves()[1].getShelfNumberOfResources(), waDecoSecond.getShelf(1).getShelfNumberOfResources());
        assertSame(localModel.getShelves()[2].getShelfNumberOfResources(), waDecoSecond.getShelf(2).getShelfNumberOfResources());
    }


}