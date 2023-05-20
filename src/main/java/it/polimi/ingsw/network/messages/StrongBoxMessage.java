package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.model.player.warehouse.Resources;
import it.polimi.ingsw.view.LocalModel;

import java.util.ArrayList;

public class StrongBoxMessage implements Message{

    private ArrayList<Resources> strongBox;

    public StrongBoxMessage(ArrayList<Resources> strongBox){
        this.strongBox=strongBox;
    }


    @Override
    public void action(LocalModel localModel) {
        localModel.setStrongBox(strongBox);

        //System.out.println(strongBox);
    }

}
