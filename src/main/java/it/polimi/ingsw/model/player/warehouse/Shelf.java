package it.polimi.ingsw.model.player.warehouse;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.*;
import static it.polimi.ingsw.model.player.warehouse.Resources.ResType;


public class Shelf implements Serializable {

    @Expose
    private Resources resource;
    @Expose
    private final int maxAllowed;
    @Expose
    private boolean leaderCardShelf=false;

    // This list is used in the check of the constraint of having not different resources of the same type in different shelves(checkShelvesAllowedTypesConstraint).
    // This list is updated in the methods: setResource() and removeResourceQuantity().
    // In the former, when a new resource is placed in a shelf, the type of that resource is deleted from this list.
    // In the latter, when a resource is removed from the shelf, the type of that resource is added to this list.

    private ArrayList<Resources.ResType> allowedResTypes;


    public Shelf(int maxAllowed, ArrayList<Resources.ResType> allowedResTypes) {
        this.allowedResTypes = allowedResTypes;
        this.maxAllowed = maxAllowed;
    }

    public void setAllowedResTypes(ArrayList<Resources.ResType> allowed){
        allowedResTypes = allowed;
    }

    // this constructor is to mark the shelf as a leader card's new shelf,
    // this is needed to allow the checks for the constraints
    // to identify the shelves created by the leader cards and so
    // to work properly.
    public Shelf(int maxAllowed, boolean leaderCardShelf, ResType theme){
        this(maxAllowed, new ArrayList<>());
        this.leaderCardShelf=leaderCardShelf;
        this.resource = new Resources(theme, 0);
    }


    /**
     * Returns the type of resource currently in this shelf
     * @return the type of the resource of the shelf or null if the shelf is empty
     */
    public ResType getShelfCurrentType(){
        if(resource == null) return null;
        return resource.getResourceType();
    }

    /**
     * Returns the quantity of resource currently in this shelf.
     * The if clause in this method is actually never called since the method is always called if and only if we had previously checked that resource !=null
     * @return the quantity of the resource of the shelf or 0 if the shelf is empty
     */
    public int getShelfNumberOfResources(){
        if(resource == null){
            return 0; //actually this is never called (?)
        }
        return resource.getCounter();
    }

    /**
     * This method allows to set resources in the shelf.
     * If the resType of the resource we're going to insert is already present in the shelf we increase the counter of such resource
     * otherwise we add the new resource.
     * @requires resToAdd !=null
     * @param resToAdd resource we're going to insert in the shelf
     * @return true or false depending whether the insertion of the new resource was successful
     */
    protected boolean setResource(Resources resToAdd){

        if(isPossible(resToAdd)){
            if (resource!=null) {
                resource.setCounter(resource.getCounter()+resToAdd.getCounter());
            }
            else{
                resource = new Resources(resToAdd.getResourceType(), resToAdd.getCounter() ) ;
                if(!leaderCardShelf) allowedResTypes.remove(resToAdd.getResourceType()); //because the leader card slot doesn't work like the others
                }
            return true;
        }

        return false; // we might think of throwing an exception instead of using true or false but we opted for boolean return.(WHY? see Warehouse's setResourceInShelf method comment )
    }

    /**
     * This method is called in warehouse by "useResources" method (Actually this is satisfied in the caller: can be called only if we have already checked that the resource is present and the quantity is greater than the quantity to be removed)
     * Decrease the quantity of the resource in this shelf by the @param. If the quantity goes to 0 then the resource is eliminated
     * @param quantityToBeRemoved quantity to be removed from the resource currently in the shelf
     */
    protected void removeResourceQuantity(int quantityToBeRemoved){
        if( resource.getCounter() == quantityToBeRemoved) {

            if(leaderCardShelf) resource.setCounter(0); //This is needed due to the fact that the theme of the extra shelf provided by the leader card is settled by storing in the shelves a resource with counter=0

            else {
                allowedResTypes.add(getShelfCurrentType());
                resource = null;
            }
        }

        else resource.setCounter( resource.getCounter()-quantityToBeRemoved );
    }


    ////////////////////////////////////////////CHECKS\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    /**
     * Checks whether the constraint of having one type of resource per shelf is respected
     * @requires rt != null
     * @param rt type of the resource we want to insert in the shelf
     * @return true or false depending whether the resource can be inserted in the shelf
     */
    private boolean checkTypeConstraint(ResType rt){
        return resource != null && rt.equals(resource.getResourceType());
    }

    /**
     * Checks whether the constraint of having a maximum allowed number of resources per shelf is respected
     * @requires i != 0
     * @param i quantity of the resource we want to insert in the shelf
     * @return true or false depending whether the quantity of the resource can be inserted in the shelf
     */
    private boolean checkNumberConstraint(int i){
        return i <= maxAllowed;
    }

    /**
     * Checks whether the constraint of having not different resources of the same type in different shelves
     * @param type type of the resource we want to insert in the shelves
     * @return true or false depending whether the resource can be inserted in the shelves, more precisely: if the type of the resource is present in the list of the allowedTypes for the shelves.
     */
    private boolean checkShelvesAllowedTypesConstraint(ResType type){
        return allowedResTypes.contains(type);
    }

    /**
     * Checks whether the amount of resource already present in the shelf plus the amount we're going to add is equal or less than the maximum allowed
     * @param quantityToAdd quantity of the resource we want to insert in the shelf
     * @return true or false depending whether the amount of resource already present in the shelf plus the amount we're going to add can be inserted in the shelf
     */
    private boolean checkAllowedQuantity(int quantityToAdd){
        return quantityToAdd + getShelfNumberOfResources() <= maxAllowed;
    }

    /**
     * Checks whether is legitimate to insert the resource in the shelf.
     * Aggregates the four check differentiating them depending on whether the resource is null or not.
     * @param resToAdd resource we want to add
     * @return true or false depending on whether the resource can be inserted in the shelf
     */
    public boolean isPossible(Resources resToAdd){

        if(leaderCardShelf){ // because the leader card shelf checks requires to work with a resource which isn't null, has a type but counter to 0 and has to not take into account the allowedResTypes();
            if (checkTypeConstraint(resToAdd.getResourceType()) && checkAllowedQuantity(resToAdd.getCounter()) ){
                return true;
            }
            return false;
        }

        if(resource == null) {
                if (checkShelvesAllowedTypesConstraint(resToAdd.getResourceType()) && checkNumberConstraint(resToAdd.getCounter()))
                    return true;
               }
        else if (checkTypeConstraint(resToAdd.getResourceType()) && checkAllowedQuantity(resToAdd.getCounter()) ) return true;

        return false;
    }


}
