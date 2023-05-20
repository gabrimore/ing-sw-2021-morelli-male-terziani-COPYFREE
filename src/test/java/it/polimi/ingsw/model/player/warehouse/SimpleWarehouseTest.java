package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.LocalModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static it.polimi.ingsw.model.player.warehouse.Resources.ResType;



import static org.junit.Assert.*;

public class SimpleWarehouseTest implements Observer<Message> {

    private Warehouse deposits;
    private ArrayList<Resources> test = new ArrayList<>();
    private ArrayList<Resources> wareHouseRES = new ArrayList<>();
    private LocalModel localModel = new LocalModel();

    @Before
    public void setUp() throws Exception {
        deposits = new SimpleWarehouse();
        ((SimpleWarehouse) deposits).addObserver(this);
        assertNotNull(deposits);
    }

    @After
    public void tearDown() throws Exception {
        deposits = null;
        assertNull(deposits);
    }

    @Test
    public void getShelfTest(){
        deposits.setResourceInShelf(0, new Resources(ResType.COIN, 1));
        deposits.setResourceInShelf(1, new Resources(ResType.SHIELD, 2));
        deposits.setResourceInShelf(2, new Resources(ResType.SERVANT, 2));

        assertEquals(ResType.COIN, deposits.getShelf(0).getShelfCurrentType());
        assertEquals(ResType.SHIELD, deposits.getShelf(1).getShelfCurrentType());
        assertEquals(ResType.SERVANT, deposits.getShelf(2).getShelfCurrentType());

    }

    @Test
    public void getStrongBox(){
        ArrayList<Resources> resources = new ArrayList<>();
        resources.add( new Resources(ResType.COIN, 1 ));
        resources.add( new Resources(ResType.SERVANT, 8 ));
        resources.add( new Resources(ResType.SHIELD, 3 ));
        resources.add( new Resources(ResType.STONE, 4 ));

        deposits.setResourceInStrongBox(new Resources(ResType.COIN, 1 ));
        deposits.setResourceInStrongBox(new Resources(ResType.SERVANT, 8 ));
        deposits.setResourceInStrongBox( new Resources(ResType.SHIELD, 3 ));
        deposits.setResourceInStrongBox(new Resources(ResType.STONE, 4 ));


        int i=0;
        for (Resources res : resources) {
            assertTrue(resources.get(i).equals(deposits.getStrongBox().getResources().get(i)) );
            i++;
        }

    }

    @Test
    public void setResourceInShelfTest(){
        assertFalse(deposits.setResourceInShelf(0, new Resources(ResType.COIN, 2)));

        assertTrue(deposits.setResourceInShelf(0, new Resources(ResType.COIN, 1)));
        assertTrue(deposits.setResourceInShelf(1, new Resources(ResType.SHIELD, 2)));

        assertFalse(deposits.setResourceInShelf(1, new Resources(ResType.SHIELD, 1)));

        assertTrue(deposits.setResourceInShelf(2, new Resources(ResType.SERVANT, 2)));
        assertFalse(deposits.setResourceInShelf(2, new Resources(ResType.SERVANT, 2)));
        assertTrue(deposits.setResourceInShelf(2, new Resources(ResType.SERVANT, 1)));

    }

    @Test
    public void setResourceInStrongBox(){
        ArrayList<Resources> resources = new ArrayList<>();

        deposits.setResourceInStrongBox( new Resources(ResType.COIN, 1 ));
        deposits.setResourceInStrongBox( new Resources(ResType.SERVANT, 2 ));
        deposits.setResourceInStrongBox( new Resources(ResType.SHIELD, 1 ));
        deposits.setResourceInStrongBox( new Resources(ResType.STONE, 2 ));
        deposits.setResourceInStrongBox( new Resources(ResType.SERVANT, 2 ));
        deposits.setResourceInStrongBox( new Resources(ResType.STONE, 2 ));
        deposits.setResourceInStrongBox( new Resources(ResType.SHIELD, 2 ));
        deposits.setResourceInStrongBox( new Resources(ResType.SERVANT, 2 ));
        deposits.setResourceInStrongBox( new Resources(ResType.SERVANT, 2 ));

        resources.add(new Resources(ResType.COIN, 1 ));
        resources.add(new Resources(ResType.SERVANT, 8 ));
        resources.add( new Resources(ResType.SHIELD, 3 ));
        resources.add(new Resources(ResType.STONE, 4 ));


        int i=0;
        for (Resources res : resources) {
            assertTrue(resources.get(i).equals(deposits.getStrongBox().getResources().get(i)) );
            i++;
        }

    }

    @Test
    public void checkAvailabilityResourcesTest(){
        deposits.setResourceInShelf(0, new Resources(ResType.SERVANT, 1));
        deposits.setResourceInShelf(1, new Resources(ResType.STONE, 2));
        deposits.setResourceInShelf(2, new Resources(ResType.SHIELD, 3));

        deposits.setResourceInStrongBox(new Resources(ResType.STONE, 6));
        deposits.setResourceInStrongBox(new Resources(ResType.COIN, 1));
        deposits.setResourceInStrongBox(new Resources(ResType.STONE, 1));
        deposits.setResourceInStrongBox(new Resources(ResType.COIN, 3));


        // The order of the list is not relevant
        test.add(new Resources(ResType.COIN, 4));
        test.add(new Resources(ResType.SHIELD, 3));
        test.add(new Resources(ResType.SERVANT, 1));
        test.add(new Resources(ResType.STONE, 9));


       // wareHouseRES = deposits.allResources(); //this list contains all the resources both in the shelves and the strongbox

        /*
        int i=0;
        for (Resources res : wareHouseRES) {
            System.out.println(i+" A allRes: "+ wareHouseRES.get(i).getResourceType()+ wareHouseRES.get(i).getCounter()+"   needed: "+test.get(i).getResourceType()+ test.get(i).getCounter());
            i++;
        }
        System.out.println();
        */

       assertTrue(deposits.checkAvailabilityResources(test)); // Both the lists have exactly the same quantity so far

        /*
         i=0;
        for (Resources res : wareHouseRES) {
            System.out.println(i+" B allRes: "+ wareHouseRES.get(i).getResourceType()+ wareHouseRES.get(i).getCounter()+"   needed: "+test.get(i).getResourceType()+ test.get(i).getCounter());
            i++;
        }System.out.println();
         */

        test.get(0).setCounter(1);
        test.get(1).setCounter(2);
        test.get(2).setCounter(1);
        test.get(3).setCounter(1);
        //wareHouseRES.get(0).setCounter(2);

        assertTrue(deposits.checkAvailabilityResources(test)); // required less than available

        /*
         i=0;
        for (Resources res : wareHouseRES) {
            System.out.println(i+" C allRes: "+ wareHouseRES.get(i).getResourceType()+ wareHouseRES.get(i).getCounter()+"   needed: "+test.get(i).getResourceType()+ test.get(i).getCounter());
            i++;
        }System.out.println();
         */

        test.get(0).setCounter(5);
        test.get(1).setCounter(2);
        test.get(2).setCounter(1);
        test.get(3).setCounter(1);
        //wareHouseRES.get(0).setCounter(2);

        assertFalse(deposits.checkAvailabilityResources(test)); // required more than available

        /*
        i=0;
        for (Resources res : wareHouseRES) {
            System.out.println(i+" D allRes: "+ wareHouseRES.get(i).getResourceType()+ wareHouseRES.get(i).getCounter()+"   needed: "+test.get(i).getResourceType()+ test.get(i).getCounter());
            i++;
        }System.out.println();
        */
    }

    @Test
    public void useResourcesTest(){

        // resources available in the deposits
        deposits.setResourceInShelf(0, new Resources(ResType.SERVANT, 1));
        deposits.setResourceInShelf(1, new Resources(ResType.STONE, 2));
        deposits.setResourceInShelf(2, new Resources(ResType.SHIELD, 3));

        deposits.setResourceInStrongBox(new Resources(ResType.STONE, 6));
        deposits.setResourceInStrongBox(new Resources(ResType.COIN, 1));
        deposits.setResourceInStrongBox(new Resources(ResType.STONE, 1));
        deposits.setResourceInStrongBox(new Resources(ResType.COIN, 3));

        //Resources we're going to use
        // The order of the list is not relevant
        test.add(new Resources(ResType.STONE, 8));
        test.add(new Resources(ResType.COIN, 2));
        test.add(new Resources(ResType.SHIELD, 2));


        //List of resources that should be in the deposits after their usage
        wareHouseRES.add(new Resources(ResType.STONE, 1));
        wareHouseRES.add(new Resources(ResType.COIN, 2));
        wareHouseRES.add(new Resources(ResType.SERVANT, 1));
        wareHouseRES.add(new Resources(ResType.SHIELD, 1));



        assertTrue(deposits.checkAvailabilityResources(test));

        deposits.useResources(test);



        ArrayList<Resources> toCheck = deposits.allResources();

        assertTrue(toCheck.size() == wareHouseRES.size());

        int i=0;
        for (Resources res : wareHouseRES) {
            // uncomment for debugging purposes
            //System.out.println("allRes: "+ wareHouseRES.get(i).getResourceType()+ wareHouseRES.get(i).getCounter()+"   ExpectedTest: "+toCheck.get(i).getResourceType()+ toCheck.get(i).getCounter());
            assertTrue(wareHouseRES.get(i).equals(toCheck.get(i)) );
            i++;
        }



    }

    @Test
    public void switchShelvesContentTest(){

        assertTrue(deposits.switchShelvesContent(0, 1));
        assertTrue(deposits.switchShelvesContent(1, 0));

        assertTrue(deposits.switchShelvesContent(1, 2));
        assertTrue(deposits.switchShelvesContent(2, 1));

        assertTrue(deposits.switchShelvesContent(0, 2));
        assertTrue(deposits.switchShelvesContent(2, 0));

        //deposits.setResourceInShelf(0, new Resources(ResType.VIOLET, 1));
        deposits.setResourceInShelf(1, new Resources(ResType.STONE, 2));
        deposits.setResourceInShelf(2, new Resources(ResType.SHIELD, 1));

        assertTrue(deposits.switchShelvesContent(1, 2));
        assertTrue( deposits.switchShelvesContent(2, 1));
        assertTrue( deposits.switchShelvesContent(2, 1));
        assertTrue( deposits.switchShelvesContent(2, 1)); //same status as before



        assertFalse(deposits.switchShelvesContent(0, 1)); //error code: 2 = maxAllowed for a shelf violated


        assertTrue( deposits.switchShelvesContent(0, 2));
        assertTrue( deposits.switchShelvesContent(0, 2));  //same status as before



        assertFalse(deposits.switchShelvesContent(1, 0)); //error code: 2

        assertTrue( deposits.switchShelvesContent(1, 2));
        assertTrue( deposits.switchShelvesContent(0, 1));

    }

    @Test
    public void possibleResourcesTest(){

        deposits.setResourceInShelf(1, new Resources(ResType.STONE, 1));
        deposits.setResourceInShelf(2, new Resources(ResType.SHIELD, 1));

        //the expected list
        wareHouseRES.add( new Resources(ResType.COIN, 1));
        wareHouseRES.add( new Resources(ResType.SERVANT, 1));
        wareHouseRES.add( new Resources(ResType.STONE, 1));
        wareHouseRES.add( new Resources(ResType.SHIELD, 1));
        wareHouseRES.add( new Resources(ResType.SHIELD, 2));

        /* //uncomment for debugging purposes
        for (int i =0; i<3; i++){
            System.out.println("shelf " + i+ ":" + deposits.getShelf(i).getShelfCurrentType()+" "+ deposits.getShelf(i).getShelfNumberOfResources() );
        }
         */


        test = deposits.possibleResources();


        int i=0;
        for (Resources res : test) {
            //uncomment for debugging purposes
            //System.out.println("possible: "+ test.get(i).getResourceType()+ test.get(i).getCounter() );
            assertTrue(test.get(i).equals(wareHouseRES.get(i)) );
            i++;
        }

    }

    @Test
    public void allResourcesTest(){

        deposits.setResourceInShelf(0, new Resources(ResType.SERVANT, 1));
        deposits.setResourceInShelf(1, new Resources(ResType.STONE, 2));
        deposits.setResourceInShelf(2, new Resources(ResType.SHIELD, 3));

        deposits.setResourceInStrongBox(new Resources(ResType.STONE, 6));
        deposits.setResourceInStrongBox(new Resources(ResType.COIN, 1));
        deposits.setResourceInStrongBox(new Resources(ResType.STONE, 1));
        deposits.setResourceInStrongBox(new Resources(ResType.COIN, 3));

        test.add(new Resources(ResType.STONE, 9));
        test.add(new Resources(ResType.COIN, 4));
        test.add(new Resources(ResType.SERVANT, 1));
        test.add(new Resources(ResType.SHIELD, 3));


        wareHouseRES = deposits.allResources();

        assertTrue(test.size() == wareHouseRES.size());

        int i=0;
        for (Resources res : wareHouseRES) {
            //debug purpose
            //System.out.println("allRes: "+ wareHouseRES.get(i).getResourceType()+ wareHouseRES.get(i).getCounter()+"   ExpectedTest: "+test.get(i).getResourceType()+ test.get(i).getCounter());
            assertTrue(wareHouseRES.get(i).equals(test.get(i)) );
            i++;
        }

    }

    @Override
    public void update(Message action) {
        action.action(localModel);
        assertSame(localModel.getShelves()[0].getShelfCurrentType(), deposits.getShelf(0).getShelfCurrentType());
        assertSame(localModel.getShelves()[1].getShelfCurrentType(), deposits.getShelf(1).getShelfCurrentType());
        assertSame(localModel.getShelves()[2].getShelfCurrentType(), deposits.getShelf(2).getShelfCurrentType());
        assertSame(localModel.getShelves()[0].getShelfNumberOfResources(), deposits.getShelf(0).getShelfNumberOfResources());
        assertSame(localModel.getShelves()[1].getShelfNumberOfResources(), deposits.getShelf(1).getShelfNumberOfResources());
        assertSame(localModel.getShelves()[2].getShelfNumberOfResources(), deposits.getShelf(2).getShelfNumberOfResources());
    }
}