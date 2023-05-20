package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.model.player.warehouse.Resources;
import it.polimi.ingsw.view.LocalModel;

import java.util.ArrayList;

public class SetResourcesMessage implements Message{

    private final ArrayList<Resources.ResType> resTypes;

    public SetResourcesMessage(ArrayList<Resources.ResType> resTypes) {
        this.resTypes = resTypes;
    }

    public ArrayList<Resources.ResType> getResTypes() {
        return resTypes;
    }

    @Override
    public void action(LocalModel localModel) {}
}
