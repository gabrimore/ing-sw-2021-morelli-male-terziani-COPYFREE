package it.polimi.ingsw.model.deck.carddecks;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.warehouse.Resources;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class DevelopmentCard extends Card {

    @Expose
    private final ArrayList<Resources> cost;
    @Expose
    private final int victoryPoint;
    @Expose
    private final ArrayList<Resources> inputResources;
    @Expose
    private final ArrayList<Resources> outputResources;
    @Expose
    private final int outputFaithPoint;
    @Expose
    private final ColorLevel colorLevel;

    public DevelopmentCard(ArrayList<Resources> cost, int victoryPoint, ArrayList<Resources> inputResources, ArrayList<Resources> outputResources, int outputPoint, ColorLevel colorLevel) {
        super(false);
        this.cost = cost;
        this.victoryPoint = victoryPoint;
        this.inputResources = inputResources;
        this.outputResources = outputResources;
        this.outputFaithPoint = outputPoint;
        this.colorLevel = colorLevel;
    }

    public ArrayList<Resources> getCost() {
        return cost;
    }

    public int getVictoryPoint() {
        return victoryPoint;
    }

    public ArrayList<Resources> getInputResources() {
        return inputResources;
    }

    public ArrayList<Resources> getOutputResources() {
        return outputResources;
    }

    public int getOutputFaithPoint() {
        return outputFaithPoint;
    }

    public ColorLevel getColorLevel(){
        return this.colorLevel;
    }


    /**
     * sets the state to true if the player can afford the card
     * @param pl player for whom we check if he can afford that card
     */
    public void checkRequirement(Player pl){
        if(!pl.getDiscount().isEmpty()){
            ArrayList<Resources> newRequirement = new ArrayList<>();

            for (Resources re : cost) {
                Resources re1;
                if(pl.getDiscount().contains(re.getResourceType())) {
                    re1 = new Resources(re.getResourceType(), re.getCounter() - 1);
                }else{
                    re1 = new Resources(re.getResourceType(), re.getCounter());
                }
                newRequirement.add(re1);
            }
            super.setState(pl.getWarehouse().checkAvailabilityResources(newRequirement));

        }else {
            super.setState(pl.getWarehouse().checkAvailabilityResources(cost));
        }
    }

    /**
     * checks if the card's input resource is in the player's warehouse and eventually sets the card's state to true
     * @param pl player whose warehouse is checked
     */
    public void checkProduction(Player pl){super.setState(pl.getWarehouse().checkAvailabilityResources(inputResources));}

    /**
     * static method to read a file of Development cards and create an ArrayList of DevelopmentCard
     * @param path path of the file
     * @return ArrayList of DevelopmentCard read from file
     */
    public static ArrayList<DevelopmentCard> createDevelopmentDeck(String path){
        Gson gson = new Gson();
        ArrayList<DevelopmentCard> myCards = new ArrayList<>();
        BufferedReader buffReader = new BufferedReader(new InputStreamReader(DevelopmentCard.class.getResourceAsStream(path)));
        myCards=gson.fromJson(buffReader,new TypeToken<ArrayList<DevelopmentCard>>(){}.getType());
        return myCards;
    }

    public static ArrayList<DevelopmentCard> uploadDevelopmentDeck(String path){
        Gson gson = new Gson();
        ArrayList<DevelopmentCard> myCards = new ArrayList<>();

        try (BufferedReader buffReader = new BufferedReader(new FileReader(path))) {

            myCards=gson.fromJson(buffReader,new TypeToken<ArrayList<DevelopmentCard>>(){}.getType());



        } catch (IOException e) {
            e.printStackTrace();
        }

        return myCards;
    }

    @Override //to be cancelled
    public String toString() {
        return "DevelopmentCard{" +
                "cost=" + cost +
                ", victoryPoint=" + victoryPoint +
                ", inputResources=" + inputResources +
                ", outputResources=" + outputResources +
                ", outputFaithPoint=" + outputFaithPoint +
                ", colorLevel=" + colorLevel +
                '}';
    }


}
