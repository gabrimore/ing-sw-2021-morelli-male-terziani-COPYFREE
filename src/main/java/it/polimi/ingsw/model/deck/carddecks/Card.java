package it.polimi.ingsw.model.deck.carddecks;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;
import java.net.URL;

public abstract class Card implements Serializable {

    @Expose
    private boolean state;

    @Expose
    private String imageURL;

    public Card(boolean state){
        this.state=state;
    }

    public boolean getState(){
        return state;
    }

    public void setState(boolean s){
        this.state=s;
    }

    public String getImageURL() {
        return imageURL;
    }

    /**
     * if in the DeckField, checks if it can be owned by player p- if so, sets the card's state to true
     * if card owned by the player, checks if usable during that turn
     * @param p player to be checked
     */
    public void checkRequirement(Player p){}

    /*@Override
    public String toString() {
        return "state=" + state ;
    }

     */
}
