package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.view.LocalModel;

import java.util.ArrayList;

public class DevSpaceIndexesMessage implements Message{

    private final ArrayList<Integer> indexes;

    public DevSpaceIndexesMessage(ArrayList<Integer> indexes) {
        this.indexes = indexes;
    }

    @Override
    public void action(LocalModel localModel) {
        localModel.setPossibleDevSpaceColumn(indexes);
    }
}
