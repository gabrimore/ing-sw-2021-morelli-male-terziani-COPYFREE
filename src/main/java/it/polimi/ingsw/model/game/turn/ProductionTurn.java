package it.polimi.ingsw.model.game.turn;

import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.controller.ProductionController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.faithtrack.VaticanReportException;
import it.polimi.ingsw.model.player.warehouse.Resources;
import it.polimi.ingsw.model.player.warehouse.Warehouse;
import it.polimi.ingsw.network.messages.SlotDevMessage;

import java.util.ArrayList;
import java.util.List;

public class ProductionTurn implements Turn{

    private final ProductionController productionController;
    private final Player player;
    private boolean done = false;
    private boolean baseProduction = false;

    //it is the list of the resources produced in this production turn
    private final ArrayList<Resources> producedResources = new ArrayList<>();

    public ProductionTurn(ProductionController productionController, Player player) {
        this.productionController = productionController;
        this.player = player;
        begin();
    }

    /**
     * handle the communication between the controller and the part of the model involved with the production from leaders, dev cards or the base one
     */
    @Override
    public void begin() {
        boolean stop = false;
        int point = 0;

        do {
            productionController.sendProductionMessage(producedResources, player.getWarehouse().allResources(), point, productionController.getUsedInput(), productionController.getUsedLeaderInput());

            switch(productionController.chooseWhatToDo()) {

                case 1:
                    List<Resources> output = null;
                    int index = productionController.chooseIndex();

                    if (index != -1) {
                        try {
                            output = player.startProduction(index);
                        } catch (VaticanReportException e) {
                            MainController.vaticanReport(player, e);
                        }

                        if (output == null) {
                            productionController.send("\nInvalid Production\n");
                        } else {
                            for (Resources r : output) {
                                setResource(r);
                            }

                            if (player.getSlotDevelopment().getTopCard(index).getOutputFaithPoint() > 0) {
                                point = player.getSlotDevelopment().getTopCard(index).getOutputFaithPoint();
                            }

                            done = true;
                        }
                    }

                    break;

                case 2:
                    if (baseProduction) {
                        productionController.send("Can't activate another base production\n");
                    } else {
                        Resources baseOutput = player.startBaseProduction(productionController.chooseInput(), productionController.chooseOutput());
                        if (baseOutput == null) {
                            productionController.send("\nInvalid Base Production\n");
                        } else {
                            setResource(baseOutput);
                            baseProduction = true;
                            done = true;
                        }
                    }
                    break;

                case 3:
                    if (player.getExtraProductionInput().isEmpty() || productionController.getUsedLeaderInput().size() > 2) {
                        productionController.send("\nNo Extra Production Available\n");
                    } else {
                        int i = 0;

                        productionController.sendExtraProductionMessage(player.getExtraProductionInput());

                        for (Resources res : player.getExtraProductionInput()) {
                            productionController.send("(" + i + ") " + res.toString() + "\n");
                            i++;
                        }

                        Resources extraOutput = null;
                        int extraInput = productionController.chooseExtraInput(i);

                        if (extraInput != -1) {
                            try {
                                extraOutput = player.startExtraProduction(extraInput, productionController.chooseOutput());
                            } catch (VaticanReportException e) {
                                MainController.vaticanReport(player, e);
                            }

                            if (extraOutput == null) {
                                productionController.send("\nInvalid Production\n");
                            } else {
                                setResource(extraOutput);
                                done = true;
                            }

                        } else {
                            productionController.send("\nInvalid Production\n");
                        }
                    }
                    break;

                case 4:
                    stop = true;
                    break;
            }

            player.getSlotDevelopment().checkSlotDevTopCards(player);
            productionController.sendSlotDevMessage(new SlotDevMessage(player.getSlotDevelopment().getDevSpace()));

        }while(!stop);

        if(!done){
            productionController.send("No Production Activated!\n");
        }else{
            player.setResourcesInStrongbox(producedResources);
            productionController.isDone();
        }
    }

    /**
     * inserts the resource in the list of the resources produced in this turn: increments the counter if the resource is already present, otherwise adds a new one
     * @param res resource we're going to insert in the shelf
     */
    private void setResource(Resources res) {
        int indexOfResource = Warehouse.extractIndex(res.getResourceType(), producedResources);

        if(indexOfResource != -1){
            producedResources.get(indexOfResource).setCounter(producedResources.get(indexOfResource).getCounter() + res.getCounter());
        }
        else producedResources.add(res);
    }


}
