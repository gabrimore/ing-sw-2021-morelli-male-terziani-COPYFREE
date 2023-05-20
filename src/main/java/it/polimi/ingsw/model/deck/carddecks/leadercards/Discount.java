package it.polimi.ingsw.model.deck.carddecks.leadercards;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.player.*;

import java.io.*;
import java.util.ArrayList;
import static it.polimi.ingsw.model.player.warehouse.Resources.ResType;

/**
 * Discount class
 * @author Lorenzo
 */

public class Discount extends LeaderCard {

    @Expose
    private ResType discountResType;

    /**
     * Class constructor
     * @param victoryPoint
     * @param reqColLev ArrayList of ColorLevel required to play the card
     * @param discount ResType of the discount
     */
    public Discount(int victoryPoint, Requirement reqColLev, ResType discount){
        super(false, victoryPoint, reqColLev);
        this.discountResType=discount;
    }


    public ResType getDiscountResource(){
        return this.discountResType;
    }

    /**
     * calls the method of the player to set the ResType of the discount
     * @param pl player who has the card
     */
    @Override
    public void specialAbility(Player pl){
        pl.setDiscount(discountResType);
    }

    public Requirement getRequirement() {
        return super.getRequirement();
    }

    /**
     * sets the State of the card to true if player p has color-level requirements fulfilled
     * @param p player of the turn
     */
    @Override
    public void checkRequirement(Player p){
            this.setState(p.getSlotDevelopment().checkCard(super.getRequirement().getColorLevelRequirement()));
    }

    /**
     * static method to read a file of Discount cards and create an ArrayList of LeaderCard
     * @param path path of the file
     * @return ArrayList of type LeaderCard read from file
     */
    public static ArrayList<LeaderCard> createDiscountDeck(String path){
        Gson gson = new Gson();
        ArrayList<LeaderCard> discountLeaderCards = new ArrayList<>();
        BufferedReader buffReader = new BufferedReader(new InputStreamReader(Discount.class.getResourceAsStream(path)));
        discountLeaderCards=gson.fromJson(buffReader,new TypeToken<ArrayList<Discount>>(){}.getType());
        return discountLeaderCards;
    }

    /*@Override
    public String toString() {
        return "Discount{" +
                super.toString() +
                "discountResType=" + discountResType +
                '}';
    }

     */
}
