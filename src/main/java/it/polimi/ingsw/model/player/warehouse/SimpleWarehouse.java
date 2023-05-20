package it.polimi.ingsw.model.player.warehouse;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.ShelvesMessage;
import it.polimi.ingsw.network.messages.StrongBoxMessage;
import it.polimi.ingsw.observer.Observable;

import java.util.*;

import static it.polimi.ingsw.model.player.warehouse.Resources.RES_TYPES;

public class SimpleWarehouse extends Observable<Message> implements Warehouse {

    @Expose
    private final Shelf[] shelves;
    @Expose
    private final StrongBox strongBox;
    @Expose
    private ArrayList<Resources.ResType> allowedResTypes;

    public SimpleWarehouse() {
        allowedResTypes = new ArrayList<>(Arrays.asList(Resources.ResType.COIN, Resources.ResType.SERVANT, Resources.ResType.SHIELD, Resources.ResType.STONE));

        shelves = new Shelf[3];
        shelves[0]= new Shelf(1, allowedResTypes);
        shelves[1]= new Shelf(2, allowedResTypes);
        shelves[2]= new Shelf(3, allowedResTypes);

        strongBox = new StrongBox();
    }

    /**
     * Gives the deep copy of the chosen shelf
     * @param i index of the shelf
     * @return the deep copy of the shelf with such index
     */
    public Shelf getShelf(int i){
        return shelves[i];
    }

    /**
     * Gives the deep copy of the chosen strongBox
     * @return the deep copy of the strongBox
     */
    public StrongBox getStrongBox(){
        return strongBox;
    }

    /**
     * Allow to insert a resource in a shelf
     * @param i index of the shelf in which to put the resource
     * @param res resource to be stored
     */
    public boolean setResourceInShelf(int i, Resources res){
        if(i>=0 && i<=2) {
            if( shelves[i].setResource(res) ) {
                notify(new ShelvesMessage(getShelf(0), getShelf(1), getShelf(2)));
                return true;
            }
        }
        return false;
        // using a boolean value to check whether the insertion was successful
        // allows to avoid to throw exception: this may make the program seem less stable, but actually it is not,
        // since we check the valid operations to be performed on the shelves in the caller
        // exploiting the return of the "possibleResources" method!
    }

    /**
     * Allow to insert a resource in the strongbox. (this is always possible)
     * @param res resource to be stored
     */
    public void setResourceInStrongBox(Resources res){
        strongBox.setResource(res);
        notify(new StrongBoxMessage(getStrongBox().getResources()));
    }

    /**
     * Compare the wantToUse arraylist with the one created by the method "allResources()"
     * which represent all the resources that I have between the shelves and the strongbox
     * @param wantToUse set of resources that I would like to use
     * @return true or false depending whether the resources are available
     */
    public boolean checkAvailabilityResources(ArrayList<Resources> wantToUse){
        int check = 0;
        ArrayList<Resources> allResources=allResources();
        int indexOfResource;


        for(Resources resUse : wantToUse){
            indexOfResource = Warehouse.extractIndex(resUse.getResourceType(), allResources );
            if( indexOfResource != -1 )
                if(resUse.getCounter() <= allResources.get(indexOfResource).getCounter()) check++;
        }

        return check == wantToUse.size();
    }

    /**
     * This method can be called only after having called "checkAvailabilityResources()" and only if it returns true! (the caller of this method has to check this)
     * Decrease the amount of resources, first, from the shelf and then,if remaining, from strongbox
     * @param used set of resources that I'm going to remove
     */
    public void useResources(ArrayList<Resources> used){
        for(Resources resUsed : used) {
            int i = resUsed.getCounter();

            for(Shelf shelf : shelves)
                    if(resUsed.getResourceType().equals(shelf.getShelfCurrentType()) ) { //in case of shelf.getShelfCurrentType is null, the if is false
                        if(shelf.getShelfNumberOfResources() >= i) { //we don't have to take care of the null since already the previous if checks it
                                shelf.removeResourceQuantity(i);
                                 i=0;
                            }
                        if(shelf.getShelfNumberOfResources() < i){
                                i -= shelf.getShelfNumberOfResources();
                                shelf.removeResourceQuantity(shelf.getShelfNumberOfResources());
                            }
                }


            if(i!=0) {
                 // Before calling useResource() we have checked that removeResourceQuantity() can legitimately operate
                // by checking the actual availability of the resources, this ensure that if the resources are not in
                // the shelves it's consequent that they must be in the strongbox
                strongBox.removeResourceQuantity(i, resUsed.getResourceType());
            }


        }

        notify(new ShelvesMessage(getShelf(0), getShelf(1), getShelf(2)));
        notify( new StrongBoxMessage(getStrongBox().getResources()));

    }

    /**
     * Switch the content of the firstShelf with the content of the secondShelf
     * @param firstShelf index of the first shelf to switch
     * @param secondShelf index of the second shelf to switch
     * @return true or false depending on whether the switch can be done or not
     */
    public boolean switchShelvesContent(int firstShelf, int secondShelf){

        ArrayList<Resources> temporary = new ArrayList<>();  // I create a temporary list which stores the content of both the shelves
        int[] indexArray ={firstShelf, secondShelf}; // I store the indexes in an array to be able to perform some operations on it
        int n=-1; //int variable which tells me which shelf is not empty whether the first or the second

        // in the case both the shelves were empty
        if(shelves[indexArray[0]].getShelfCurrentType() == null && shelves[indexArray[1]].getShelfCurrentType() == null){return true;}

        // I store in the temporary list the content of the shelves and I ensure that the content of both the shelves is now null, or, if it's a leader card shelf, is 0
        for(int i=0; i<2 ; i++){
            if(shelves[indexArray[i]].getShelfCurrentType() == null){
                n=i;
            }
            else {
                temporary.add(new Resources(shelves[indexArray[i]].getShelfCurrentType(),shelves[indexArray[i]].getShelfNumberOfResources() ));
                shelves[indexArray[i]].removeResourceQuantity(shelves[indexArray[i]].getShelfNumberOfResources());
            }
        }

        // I check whether the switch can be done and in case I do it, else I bring everything to the previous state
        if(n==-1) {
            if (shelves[firstShelf].isPossible(temporary.get(1)) && shelves[secondShelf].isPossible(temporary.get(0))) {
                shelves[firstShelf].setResource(temporary.get(1));
                shelves[secondShelf].setResource(temporary.get(0));
                notify(new ShelvesMessage(getShelf(0), getShelf(1), getShelf(2)));
                return true;
             }
            else {
                shelves[firstShelf].setResource(temporary.get(0));
                shelves[secondShelf].setResource(temporary.get(1));
            }
        }

        if(n!=-1) {
                if (shelves[indexArray[n]].isPossible(temporary.get(0))) {
                    shelves[indexArray[n]].setResource(temporary.get(0));
                    notify(new ShelvesMessage(getShelf(0), getShelf(1), getShelf(2)));
                    return true;
                }
                else shelves[indexArray[1-n]].setResource(temporary.get(0));
         }

        return false;
    }

    /**
     * Return a list of all the possible resources and their quantity that can be accepted in the shelves
     * @return the list of the possible resources that can be accepted in the shelves
     */
    public ArrayList<Resources> possibleResources(){
        ArrayList<Resources> possibles = new ArrayList<>();
        int index;

        for ( int shelfCounter=0 ; shelfCounter<3; shelfCounter++){
            for (int i=1; i <= shelfCounter+1; i++){
                for (Resources.ResType type : RES_TYPES){
                    if(shelves[shelfCounter].isPossible(new Resources(type, i))) {
                        index= Warehouse.extractIndex(type, possibles);
                        if(index == -1) possibles.add(new Resources(type, i));
                        else if(possibles.get(index).getCounter() !=i ) possibles.add(new Resources(type, i));

                    }
                }
            }
        }
        return possibles;
    }

    /**
     * This method is used in checkAvailabilityResources!
     * Return a list of all the resources that I have, all the resources appear in the list one time and the counter in incremented
     * @return the list of all the resources in both the shelves and the strongbox
     */
    public ArrayList<Resources> allResources() { //to set back to private?
        int indexOfResource;
        ArrayList<Resources> allResources = new ArrayList<>();
        allResources.addAll(getStrongBox().getResources()); //here we use the deepCopy

        for(Shelf shelf : shelves){
            if( shelf.getShelfCurrentType() != null){
                indexOfResource = Warehouse.extractIndex(shelf.getShelfCurrentType(),allResources );

                if(indexOfResource != -1){
                    allResources.get(indexOfResource).setCounter( allResources.get(indexOfResource).getCounter() + shelf.getShelfNumberOfResources() );
                }
                else allResources.add(new Resources(shelf.getShelfCurrentType(), shelf.getShelfNumberOfResources()));
            }
        }



        return allResources;
    }

    public ArrayList<Resources.ResType> getAllowedResTypes() {
        return allowedResTypes;
    }
}




