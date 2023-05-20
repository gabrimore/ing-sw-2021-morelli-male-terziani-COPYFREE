package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.model.player.warehouse.Resources;
import it.polimi.ingsw.view.LocalModel;

import java.util.ArrayList;

public class ExtraProductionMessage implements Message{

    private final ArrayList<Resources> extraInput;

    public ExtraProductionMessage(ArrayList<Resources> extraInput) {
        this.extraInput = extraInput;
    }

    @Override
    public void action(LocalModel localModel) {
        localModel.setExtraInput(extraInput);
    }
}
