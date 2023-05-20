package it.polimi.ingsw.model.deck.carddecks.leadercards;
import static it.polimi.ingsw.model.player.warehouse.Resources.ResType;


import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.player.*;

import java.io.*;
import java.util.ArrayList;

/**
 * ExtraSlot class
 * @author Lorenzo
 */

public class ExtraSlot extends LeaderCard {

    @Expose
    private ResType typeExtraResource;

    /**
     * Class constructor
     * @param victoryPoint
     * @param reqRes ArrayList of Resources required to play the card
     * @param typeExtraRes ResType of the ExtraSlot
     */
    public ExtraSlot(int victoryPoint, Requirement reqRes, ResType typeExtraRes){
        super(false, victoryPoint, reqRes);
        this.typeExtraResource =typeExtraRes;
    }


    public ResType getExtraSlotResource(){
        return this.typeExtraResource;
    }
    /**
     * calls the method of the player to set the ResType of the extra shelf
     * @param pl player who has the card
     */
    @Override
    public void specialAbility(Player pl){
        pl.setExtraShelf(typeExtraResource);
    }

    /**
     * sets the State of the card to true if player p has resources requirements fulfilled
     * @param p player to be checked
     */
    @Override
    public void checkRequirement(Player p){
            this.setState(p.getWarehouse().checkAvailabilityResources(super.getRequirement().getResourcesRequirement()));
    }

    /**
     * static method to read a file of ExtraSlot cards and create an ArrayList of LeaderCard
     * @param path path of the file
     * @return ArrayList of LeaderCards read from file
     */
    public static ArrayList<LeaderCard> createExtraSlotDeck(String path){
        Gson gson = new Gson();
        ArrayList<LeaderCard> extraSlotLeaderCards = new ArrayList<>();
        BufferedReader buffReader = new BufferedReader(new InputStreamReader(ExtraSlot.class.getResourceAsStream(path)));
        extraSlotLeaderCards=gson.fromJson(buffReader,new TypeToken<ArrayList<ExtraSlot>>(){}.getType());
        return extraSlotLeaderCards;
    }

    /*@Override
    public String toString() {
        return "ExtraSlot{" +
                super.toString() +
                "typeExtraResource=" + typeExtraResource +
                '}';
    }

     */
}
