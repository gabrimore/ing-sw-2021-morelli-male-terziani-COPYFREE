package it.polimi.ingsw.model.deck.carddecks.leadercards;
import static it.polimi.ingsw.model.player.warehouse.Resources.ResType;


import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.deck.carddecks.DevelopmentCard;
import it.polimi.ingsw.model.player.*;

import java.io.*;
import java.util.ArrayList;


public class AdditionalProduction extends LeaderCard {

    @Expose
    private ResType inputResource;

    /**
     * Class constructor
     * @param victoryPoint
     * @param reqColorLev ArrayList of ColorLevel required to play the card
     * @param inputRes ResType of the input of the additional production power
     */
    public AdditionalProduction(int victoryPoint, Requirement reqColorLev, ResType inputRes){
        super(false, victoryPoint, reqColorLev);
        this.inputResource=inputRes;
    }

    public void setAddProdResource(ResType res){
        this.inputResource=res;
    }

    public ResType getAddProdResource(){
        return this.inputResource;
    }

    /**
     * calls the method of the player to set the ResType of the additional production power
     * @param pl player who has the card
     */
    @Override
    public void specialAbility(Player pl){
        pl.setExtraProduction(inputResource);
    }

    /**
     * sets the State of the card on true if player p has color-level requirements fulfilled
     * @param p player who has the card
     */
    @Override
    public void checkRequirement(Player p){
        this.setState(p.getSlotDevelopment().checkCard(super.getRequirement().getColorLevelRequirement()));
    }

    /**
     * static method to read a file of AdditionalProduction cards and create an ArrayList of LeaderCard
     * @param path path of the file
     * @return ArrayList of LeaderCards read from file
     */
    public static ArrayList<LeaderCard> createAdditionalProdDeck(String path){
        Gson gson = new Gson();
        ArrayList<LeaderCard> addProdLeaderCards = new ArrayList<>();
        BufferedReader buffReader = new BufferedReader(new InputStreamReader(AdditionalProduction.class.getResourceAsStream(path)));
        addProdLeaderCards=gson.fromJson(buffReader,new TypeToken<ArrayList<AdditionalProduction>>(){}.getType());
        return addProdLeaderCards;
    }

    /*@Override
    public String toString() {
        return "AdditionalProduction{" +
                super.toString() +
                "inputResource=" + inputResource +
                '}';
    }

     */
}
