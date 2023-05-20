package it.polimi.ingsw.model.player.warehouse;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.network.messages.ShelvesMessage;
import it.polimi.ingsw.network.messages.StrongBoxMessage;

import java.util.ArrayList;
import static it.polimi.ingsw.model.player.warehouse.Resources.ResType;


public class SecondExtraSlotWarehouse extends WarehouseDecorator {

    @Expose
    private final Shelf secondExtraShelf;
    @Expose
    private final Shelf extraShelf;

    public SecondExtraSlotWarehouse(Warehouse warehouseToBeDecorated, ResType theme, Shelf firstExtraShelf ){
        super(warehouseToBeDecorated);
        this.extraShelf = firstExtraShelf;
        this.secondExtraShelf = new Shelf(2, true, theme);
    }

    /**
     * Gives the shelf. If i=3 or i=4 it returns the extraShelf provided by the respective active LeaderCard
     * @param i index of the shelf
     * @return the shelf with such index
     */
    @Override
    public Shelf getShelf (int i) {
        if(i==3) return this.extraShelf;
        if(i==4) return this.secondExtraShelf;
        return super.getShelf(i);
    }

    /**
     * Allow to insert a resource in a shelf or if i=3 to the firstExtraShelf, if  i=4 in the secondExtraShelf
     * @param i index of the shelf in which to put the resource
     * @param resToAdd resource to be stored
     */
    @Override
    public boolean setResourceInShelf(int i, Resources resToAdd) {
        if(i==3) {
            if(extraShelf.setResource(resToAdd)){
                notify(new ShelvesMessage(getShelf(0), getShelf(1), getShelf(2), getShelf(3)));
                return true;
            }
            return false;
        }
        if(i==4) {
            if(secondExtraShelf.setResource(resToAdd)){
                notify(new ShelvesMessage(getShelf(0), getShelf(1), getShelf(2), getShelf(3), getShelf(4)));
                return true;
            }
            return false;
        }
        return super.setResourceInShelf(i,resToAdd);
    }

    /**
     * Compare the wantToUse arraylist with the one created by the method "allResources()"
     * which represent all the resources that I have between the shelves and the strongbox, now also in the two extra shelves
     * @param wantToUse set of resources that I would like to use
     * @return true or false depending whether the resources are available
     */
    @Override
    public boolean checkAvailabilityResources(ArrayList<Resources> wantToUse){
        int check = 0;
        ArrayList<Resources> allResources = this.allResources();
        int indexOfResource;

        for(Resources resUse : wantToUse){
            indexOfResource = Warehouse.extractIndex(resUse.getResourceType(), allResources );
            if( indexOfResource != -1 )
                if(resUse.getCounter() <= allResources.get(indexOfResource).getCounter()) check++;
        }

        return (check == wantToUse.size());
    }

    /**
     * This method can be called only after having called "checkAvailabilityResources()" and only if it returns true! (the caller of this method has to check this)
     * Decrease the amount of resources, first, from the extras helves, second from the shelves and then,if remaining, from strongbox
     * @param used set of resources that I'm going to remove
     */
    @Override
    public void useResources(ArrayList<Resources> used){

        for(Resources resUsed : used) {
            int i = resUsed.getCounter();

            //in order to reuse the code below, we first create a list which contains all the shelves and then we iterate on it!
            //checks all the shelves starting from the ones provided by the leader card (if not empty) and then the other shelves
            for( Shelf shelf : listOfShelves()) {

                if (resUsed.getResourceType().equals(shelf.getShelfCurrentType())) { //in case of shelf.getShelfCurrentType is null, the if is false
                    if (shelf.getShelfNumberOfResources() >= i) { //we don't have to take care of the null since already the previous if checks it
                        shelf.removeResourceQuantity(i);
                        i = 0;
                    }
                    if (shelf.getShelfNumberOfResources() < i) {
                        i -= shelf.getShelfNumberOfResources();
                        shelf.removeResourceQuantity(shelf.getShelfNumberOfResources());
                    }
                }
            }


            if(i!=0) {
                // Before calling useResource() we have checked that removeResourceQuantity() can legitimately operate
                // by checking the actual availability of the resources, this ensure that if the resources are not in
                // the shelves it's consequent that they must be in the strongbox
                super.getStrongBox().removeResourceQuantity(i, resUsed.getResourceType());
            }


        }
        notify(new ShelvesMessage(getShelf(0), getShelf(1), getShelf(2), getShelf(3), getShelf(4)));
        notify( new StrongBoxMessage(getStrongBox().getResources()));

    }

    /**
     * Return a list of all the possible resources and their quantity that can be accepted in the shelves
     * @return the list of the possible resources that can be accepted in the shelves
     */
    @Override
    public ArrayList<Resources> possibleResources(){
        ArrayList<Resources> possibles = super.possibleResources();

        for(int i = 0; i<2- secondExtraShelf.getShelfNumberOfResources(); i++) possibles.add(new Resources(secondExtraShelf.getShelfCurrentType(), i+1));

        return possibles;
    }

    /**
     * This method is used in checkAvailabilityResources!
     * Return a list of all the resources that I have, all the resources appear in the list one time and the counter is incremented
     * @return the list of all the resources in both the shelves and the strongbox
     */
    @Override
    public ArrayList<Resources> allResources() {

        int indexOfResource;
        ArrayList<Resources> allResources = new ArrayList<>();
        allResources.addAll(super.getStrongBox().getResources()); //we use the deep copy here

        //in order to reuse the code below, we first create a list which contains all the shelves and then we iterate on it!
        //checks all the shelves starting from the one provided by the leader card (if not empty) and then the other shelves
        for(Shelf shelf : listOfShelves()){
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

    /**
     * This method is used in useResources() and allResources()!
     * Return a list of all the shelves placing the ones provided by the leaderCard in the first positions
     * * @return the list of all the shelves
     */
    private ArrayList<Shelf> listOfShelves(){
        ArrayList<Shelf> shelves = new ArrayList<>();
        if(extraShelf.getShelfNumberOfResources() !=0 )shelves.add(extraShelf);
        if(secondExtraShelf.getShelfNumberOfResources() !=0 )shelves.add(secondExtraShelf);
        for(int n=0; n<3; n++) {
            shelves.add(super.getShelf(n));
        }
        return shelves;
    }

    @Override
    public ArrayList<ResType> getAllowedResTypes() {
        return super.getAllowedResTypes();
    }

    @Override
    public Warehouse getWarehouseToBeDecorated() {
        return super.getWarehouseToBeDecorated();
    }
}



