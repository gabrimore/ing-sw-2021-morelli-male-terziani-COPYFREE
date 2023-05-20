package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.model.player.warehouse.Resources;
import it.polimi.ingsw.view.LocalModel;

import java.util.ArrayList;

public class ProductionMessage implements Message{

    private final ArrayList<Resources> produced;
    private final ArrayList<Resources> available;
    private int point;
    private final ArrayList<Integer> usedInput;
    private final ArrayList<Integer> usedLeaderInput;


    public ProductionMessage(ArrayList<Resources> produced, ArrayList<Resources> available, int point, ArrayList<Integer> usedInput, ArrayList<Integer> usedLeaderInput) {
        this.produced = produced;
        this.available = available;
        this.point = point;
        this.usedInput = usedInput;
        this.usedLeaderInput = usedLeaderInput;
    }

    @Override
    public void action(LocalModel localModel) {

        localModel.setProductionResources(produced, available, point);
        localModel.setDevCardProdUsed(usedInput);
        localModel.setLeadCardProdUsed(usedLeaderInput);

    }
}
