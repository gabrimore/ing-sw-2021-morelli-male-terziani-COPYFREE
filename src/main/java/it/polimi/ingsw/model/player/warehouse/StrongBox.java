package it.polimi.ingsw.model.player.warehouse;


import com.google.gson.annotations.Expose;

import java.util.*;
import static it.polimi.ingsw.model.player.warehouse.Resources.ResType;


public class StrongBox {

    @Expose
    private final ArrayList<Resources> resources;

    public StrongBox(){
     resources = new ArrayList<>();
    }


    /**
     * Returns a deep copy (in order to perform some actions on it without affecting the real strongbox) of the list of all the resources in the strongbox
     * @return a list containing all the resources in the strongbox
     */
    public ArrayList<Resources> getResources() {

        ArrayList<Resources> resourcesClone = new ArrayList<>();

        for(Iterator<Resources> it = resources.iterator(); it.hasNext(); ){
            Resources re = it.next();
            Resources re1 = new Resources( re.getResourceType(),re.getCounter() );
            resourcesClone.add( re1 );
        }

        return resourcesClone;
    }

    /**
     * This method allows to set resources in the strongBox.
     * If the resType of the resource we're going to insert is already present in the shelf we increase the counter of such resource
     * otherwise we add a new resource.
     * @requires resToAdd !=null
     * @param res resource we're going to insert in the strongbox
     */
    protected void setResource(Resources res) {
        int indexOfResource = Warehouse.extractIndex(res.getResourceType(), resources);

        if(indexOfResource != -1){
              resources.get(indexOfResource).setCounter( resources.get(indexOfResource).getCounter() + res.getCounter() );
            }
        else resources.add(res);
    }

   /**
     * This method is called in warehouse by "useResources" method (Actually this is satisfied in the caller: can be called only if we have already checked that the resource is present and the quantity is greater than the quantity to be removed)
     * Decrease the quantity of the resource in this shelf by the @param. If the quantity goes to 0 then the resource is eliminated
     * @param quantityToBeRemoved quantity to be removed from the resource currently in the shelf
     * @param type type of the resource of which we want to remove the quantity
     */
   protected void removeResourceQuantity(int quantityToBeRemoved, ResType type){

        int indexOfResource = Warehouse.extractIndex(type, resources);

        if(getNumberOfResources(type) == quantityToBeRemoved) resources.remove(indexOfResource);
        else resources.get(indexOfResource).setCounter(getNumberOfResources(type) - quantityToBeRemoved );

    }

    /**
     * This method is used only by the removeResourceQuantity().
     * This method gives back the quantity of the type of resource, passed as parameter, in the strongbox
     * @param type type of the resource of which we want to know the quantity
     * @return quantity of the type of resource, passed as parameter, in the strongbox
     */
    private int getNumberOfResources (ResType type){
        int indexOfResource = Warehouse.extractIndex(type, resources);

        if(indexOfResource != -1){
            return resources.get(indexOfResource).getCounter();
        }
        else return 0;
    }


}
