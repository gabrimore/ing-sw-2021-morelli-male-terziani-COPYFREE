package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.model.player.warehouse.Shelf;
import it.polimi.ingsw.view.LocalModel;

import java.util.ArrayList;
import java.util.Arrays;

public class ShelvesMessage implements Message {

    private Shelf shelf0;
    private Shelf shelf1;
    private Shelf shelf2;
    private Shelf shelf3=null;
    private Shelf shelf4=null;


    public ShelvesMessage(Shelf shelf0, Shelf shelf1, Shelf shelf2){
        this.shelf0=shelf0;
        this.shelf1=shelf1;
        this.shelf2=shelf2;
    }

    public ShelvesMessage(Shelf shelf0, Shelf shelf1, Shelf shelf2, Shelf shelf3){
        this(shelf0, shelf1, shelf2);
        this.shelf3=shelf3;
    }

    public ShelvesMessage(Shelf shelf0, Shelf shelf1, Shelf shelf2, Shelf shelf3, Shelf shelf4){
        this(shelf0, shelf1, shelf2, shelf3);
        this.shelf4=shelf4;
    }


    @Override
    public void action(LocalModel localModel) {
        ArrayList<Shelf> shelves = new ArrayList<>(Arrays.asList(shelf0,shelf1,shelf2,shelf3,shelf4));
        localModel.setShelves(shelves);
    }
}
