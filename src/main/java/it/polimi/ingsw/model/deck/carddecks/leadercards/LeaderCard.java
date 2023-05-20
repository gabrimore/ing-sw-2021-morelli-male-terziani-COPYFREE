package it.polimi.ingsw.model.deck.carddecks.leadercards;
import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.deck.carddecks.Card;
import it.polimi.ingsw.model.player.*;

/**
 * LeaderCard class
 */

public abstract class LeaderCard extends Card {

    @Expose
    private int victoryPoint;
    @Expose
    private Requirement requirement;

    /**
     * Class constructor
     * @param state
     * @param victoryPoint
     * @param req requirement of the LeaderCard
     */
    public LeaderCard(boolean state, int victoryPoint, Requirement req){
        super(state);
        this.victoryPoint=victoryPoint;
        this.requirement=req;
    }


    public int getVictoryPoint() {
        return victoryPoint;
    }

    public Requirement getRequirement() {
        return requirement;
    }


    public void setVictoryPoint(int x){
        this.victoryPoint=x;
    }

    //public void setRequirement(ArrayList<T> req){this.requirement=req;}

    /**
     * activates the power of the card
     * @param p player who owns the card
     */
    public void specialAbility(Player p){}

    /*@Override
    public String toString() {
        return  super.toString() + "\n"+
                "victoryPoint=" + victoryPoint + "\n"+
                "requirement=" + requirement;
    }

     */
}
