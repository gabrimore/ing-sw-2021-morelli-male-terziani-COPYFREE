package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.view.LocalModel;

import java.util.ArrayList;

public class UsedNamesMessage implements Message{

    private final ArrayList<String> usedNames;

    public UsedNamesMessage(ArrayList<String> usedNames) {
        this.usedNames = usedNames;
    }

    @Override
    public void action(LocalModel localModel) {
        localModel.setUsedNames(usedNames);
    }
}
