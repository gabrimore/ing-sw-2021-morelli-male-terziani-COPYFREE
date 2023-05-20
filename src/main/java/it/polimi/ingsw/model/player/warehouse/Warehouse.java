package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observer.Observable;

import java.util.ArrayList;
import java.util.Iterator;
import static it.polimi.ingsw.model.player.warehouse.Resources.ResType;


public interface Warehouse {


    /**
     * Gives the shelf
     * @param i index of the shelf
     * @return the shelf with such index
     */
     Shelf getShelf (int i);

    /**
     * Gives the strong box
     * @return the strongbox
     */
     StrongBox getStrongBox();

    /**
     * Allow to insert a resource in a shelf
     * @param i index of the shelf in which to put the resource
     * @param res resource to be stored
     */
     boolean setResourceInShelf(int i, Resources res);

    /**
     * Allow to insert a resource in the strongbox. (this is always possible)
     * @param res resource to be stored
     */
     void setResourceInStrongBox(Resources res);

    /**
     * Compare the wantToUse arraylist with the one created by the method "allResources()"
     * which represent all the resources that I have between the shelves and the strongbox
     * @param wantToUse set of resources that I would like to use
     * @return true or false depending whether the resources are available
     */
     boolean checkAvailabilityResources(ArrayList<Resources> wantToUse);

    /**
     * This method can be called only after having called "checkAvailabilityResources()" and only if it returns true! (the caller of this method has to check this)
     * Decrease the amount of resources, first, from the shelf and then,if remaining, from strongbox
     * @param used set of resources that I'm going to remove
     */
    void useResources(ArrayList<Resources> used);

    /**
     * Switch the content of the firstShelf with the content of the secondShelf
     * @param firstShelf index of the first shelf to switch
     * @param secondShelf index of the second shelf to switch
     * @return true or false depending on whether the switch can be done or not
     */
     boolean switchShelvesContent( int firstShelf, int secondShelf);

    /**
     * Return a list of all the possible resources and their quantity that can be accepted in the shelves
     * @return the list of the possible resources that can be accepted in the shelves
     */
     ArrayList<Resources> possibleResources();

    /**
     * This method searches for the type of a resource in the list that is passed
     *
     * @param type      type of the resource of which I want to find the index
     * @param resources list of resources in which I want to perform the research
     * @return the index of the type of resource. Return -1 if there isn't the resource in the list
     */
     static int extractIndex(ResType type, ArrayList<Resources> resources){

        int indexOfResource=-1;

        Iterator<Resources> it = resources.iterator();

        for (int i=0; it.hasNext(); i++) {
            if(it.next().getResourceType().equals(type)) indexOfResource= i ;
        }
        return indexOfResource;
    }

    /**
     * This method is used in checkAvailabilityResources!
     * Return a list of all the resources that I have, all the resources appear in the list one time and the counter in incremented
     * @return the list of all the resources in both the shelves and the strongbox
     */
    ArrayList<Resources> allResources();

    public ArrayList<Resources.ResType> getAllowedResTypes();



}
