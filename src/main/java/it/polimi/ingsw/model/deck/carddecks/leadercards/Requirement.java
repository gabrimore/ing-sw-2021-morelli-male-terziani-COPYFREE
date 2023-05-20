package it.polimi.ingsw.model.deck.carddecks.leadercards;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.deck.carddecks.ColorLevel;
import it.polimi.ingsw.model.player.warehouse.Resources;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Requirement class
 */

public class Requirement implements Serializable {

    @Expose
    private ArrayList<ColorLevel> colorLevelRequirement;
    @Expose
    private ArrayList<Resources> resourcesRequirement;

    public Requirement(ArrayList<ColorLevel> colLev){ this.colorLevelRequirement=colLev; }

    public Requirement() {}

    public void setColorLevelRequirement(ArrayList<ColorLevel> colorLevelRequirement) {
        this.colorLevelRequirement = colorLevelRequirement;
    }

    public void setResourcesRequirement(ArrayList<Resources> res){
        this.resourcesRequirement=res;
    }

    public ArrayList<ColorLevel>  getColorLevelRequirement(){
        return colorLevelRequirement;
    }

    public ArrayList<Resources> getResourcesRequirement(){
        return resourcesRequirement;
    }
}
