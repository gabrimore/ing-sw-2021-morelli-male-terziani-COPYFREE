package it.polimi.ingsw.model.deck.carddecks.leadercards;
import static it.polimi.ingsw.model.player.warehouse.Resources.ResType;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.player.Player;

import java.io.*;
import java.util.ArrayList;

/**
 * WhiteMarbleChanger class
 * @author Lorenzo
 */

public class WhiteMarbleChanger extends LeaderCard {

    @Expose
    private ResType outputResource;

    /**
     * Class constructor
     * @param victoryPoint
     * @param reqColorLev ArrayList of ColorLevel required to play the card
     * @param outputRes ResType which the white marble will be transformed into
     */
    public WhiteMarbleChanger(int victoryPoint, Requirement reqColorLev, ResType outputRes){
        super(false, victoryPoint, reqColorLev);
        this.outputResource=outputRes;
    }

    public ResType getOutputResource(){
        return this.outputResource;
    }

    /**
     * calls the method of the player to set the ResType of white marble changer
     * @param pl player who has the card
     */
    @Override
    public void specialAbility(Player pl){
        pl.setWhiteMarble(outputResource);
    }

    /**
     * sets the State of the card to true if player p has color-level requirements fulfilled
     * @param p player of the turn
     */
    @Override
    public void checkRequirement(Player p){
       this.setState(p.getSlotDevelopment().checkCard( super.getRequirement().getColorLevelRequirement()));
    }

    /**
     * static method to read a file of WhiteMarbleChanger cards and create an ArrayList of LeaderCard
     * @param path path of the file
     * @return ArrayList of LeaderCards read from file
     */
    public static ArrayList<LeaderCard> createWhiteMarbleChangerDeck(String path){
        Gson gson = new Gson();
        ArrayList<LeaderCard> whiteMarbleChangerLeaderCards = new ArrayList<>();
        BufferedReader buffReader = new BufferedReader(new InputStreamReader(WhiteMarbleChanger.class.getResourceAsStream(path)));
        whiteMarbleChangerLeaderCards=gson.fromJson(buffReader,new TypeToken<ArrayList<WhiteMarbleChanger>>(){}.getType());
        return whiteMarbleChangerLeaderCards;
    }

    /*@Override
    public String toString() {
        return "WhiteMarbleChanger{ \n" +
                super.toString()+ "\n" +
                "outputResource= " + outputResource + "\n" +
                '}';
    }

     */
}
