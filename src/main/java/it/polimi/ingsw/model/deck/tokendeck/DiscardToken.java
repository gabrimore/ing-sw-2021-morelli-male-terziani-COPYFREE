package it.polimi.ingsw.model.deck.tokendeck;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.deck.carddecks.EmptyDeckException;
import it.polimi.ingsw.model.deck.carddecks.leadercards.AdditionalProduction;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.SlotDevelopment;
import it.polimi.ingsw.network.Server;

import java.io.*;
import java.util.ArrayList;

public class DiscardToken extends SoloActionToken {

    private String color;
    int level=0;
    int discardedCounter=0;

    public DiscardToken(String color){
        this.color=color;
    }

    /**
     * calls the discardTokenEffect and passes the number of cards that the effect has eventually discarded,
     * the color of the cards to discard and the level (set to 1 at the beginning and eventually grows if exceptions
     * are thrown)
     * due to previous calls which have thrown an exception
     * @param pl
     * @param g
     */
    public void effect(Player pl, Game g){

        try {
            g.discardTokenEffect(level, SlotDevelopment.colorToInt(color), discardedCounter);
        }catch (EmptyDeckException e){
            if(e.checkIfLastDeck()){
                Server.setTypeOfEndgame(1);
                Server.setEndGame();
            }else {
                this.discardedCounter = e.getDiscardedQuantity();
                this.level++;
                effect(pl, g);
        }
    }


    }

    public String getColor() {
        return color;
    }


    /**
     * static method to read a file of tokens of type "Discard" and create an ArrayList of SoloActionToken
     * @param path path of the file
     * @return ArrayList of SoloActionToken read from file
     */
    public static ArrayList<SoloActionToken> createDiscardTokenDeck(String path){
        Gson gson = new Gson();
        ArrayList<SoloActionToken> discardTokens = new ArrayList<>();
        BufferedReader buffReader = new BufferedReader(new InputStreamReader(DiscardToken.class.getResourceAsStream(path)));
        discardTokens=gson.fromJson(buffReader,new TypeToken<ArrayList<DiscardToken>>(){}.getType());
        return discardTokens;
    }

    public String toString(){
        return "YOU LOOSE 2 " +getColor() + " CARD";
    }
}
