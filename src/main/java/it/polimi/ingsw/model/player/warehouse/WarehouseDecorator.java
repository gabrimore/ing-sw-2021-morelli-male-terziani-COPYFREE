package it.polimi.ingsw.model.player.warehouse;


import com.google.gson.annotations.Expose;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observer.Observable;

import java.util.*;

public abstract class WarehouseDecorator extends Observable<Message> implements Warehouse {

    @Expose
    private Warehouse warehouseToBeDecorated;

    public WarehouseDecorator( Warehouse warehouseToBeDecorated ){
        this.warehouseToBeDecorated = warehouseToBeDecorated;
    }

    public void setWarehouseToBeDecorated(Warehouse warehouseToBeDecorated){
        this.warehouseToBeDecorated = warehouseToBeDecorated;
    }


    public Shelf getShelf (int i)  {return warehouseToBeDecorated.getShelf(i);}

    public StrongBox getStrongBox(){ return warehouseToBeDecorated.getStrongBox();}

    public boolean setResourceInShelf(int i, Resources res) {
        return warehouseToBeDecorated.setResourceInShelf(i,res);
    }

    public void setResourceInStrongBox(Resources res){
        warehouseToBeDecorated.setResourceInStrongBox(res);
    }

    public boolean checkAvailabilityResources(ArrayList<Resources> wantToUse){
        return warehouseToBeDecorated.checkAvailabilityResources(wantToUse);
    }

    public void useResources(ArrayList<Resources> used){
        warehouseToBeDecorated.useResources(used);
    }

    public boolean switchShelvesContent(int firstShelf, int secondShelf){
        return warehouseToBeDecorated.switchShelvesContent(firstShelf, secondShelf);
    }

    public ArrayList<Resources> possibleResources(){
        return warehouseToBeDecorated.possibleResources();
    }

    public ArrayList<Resources> allResources(){return warehouseToBeDecorated.allResources();}

    public ArrayList<Resources.ResType> getAllowedResTypes() {
        return warehouseToBeDecorated.getAllowedResTypes();
    }

    public Warehouse getWarehouseToBeDecorated() {
        return warehouseToBeDecorated;
    }
}
