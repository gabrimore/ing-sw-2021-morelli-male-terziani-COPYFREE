package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.model.game.Double;
import it.polimi.ingsw.view.LocalModel;

import java.util.ArrayList;


public class FaithTrackMessage implements Message {

    private ArrayList<Double<String, Integer>> positions;

    public FaithTrackMessage(ArrayList<Double<String, Integer>> positions){
        this.positions = positions;
    }

    public void action(LocalModel localModel){
        localModel.setPosition(positions);
    }

}
