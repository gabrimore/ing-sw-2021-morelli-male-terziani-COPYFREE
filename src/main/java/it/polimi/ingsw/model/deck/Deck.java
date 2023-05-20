package it.polimi.ingsw.model.deck;


import it.polimi.ingsw.model.deck.carddecks.Card;
import it.polimi.ingsw.model.deck.carddecks.DevelopmentCard;
import it.polimi.ingsw.model.deck.carddecks.EmptyDeckException;
import it.polimi.ingsw.model.deck.carddecks.leadercards.LeaderCard;
import it.polimi.ingsw.model.deck.tokendeck.SoloActionToken;

import java.util.ArrayList;
import java.util.Collections;

public class Deck<T> {

    private final ArrayList<T> deckItems;

    public Deck(ArrayList<T> myCards) {
        this.deckItems = myCards;
    }

    /**
     * shuffles the deck
     */
    public void shuffle() {
        Collections.shuffle(deckItems);
    }

    /**
     * Returns the first object of the arraylist and then removes it
     *
     * @return first object of type T in the deck
     */
    public T draw() throws EmptyDeckException {
        if (deckItems.size() == 0) throw new EmptyDeckException(this);
        T temp = deckItems.get(0);
        deckItems.remove(0);
        return temp;
    }

    /**
     * draws the first DevelopmentCard in the deck and removes it from the deck
     * @return the DevelopmentCard that has been drawn
     * @throws EmptyDeckException
     */
    public DevelopmentCard drawDevelopmentCard() throws EmptyDeckException {
        return (DevelopmentCard) this.draw();
    }

    /**
     * draws the first LeaderCard in the deck and removes it from the deck
     * @return the LeaderCard that has been drawn
     * @throws EmptyDeckException
     */
    public LeaderCard drawLeaderCard() throws EmptyDeckException {
        return (LeaderCard) this.draw();
    }

    /**
     * draws the first SoloActionToken in the deck and removes it from the deck
     * @return the SoloActionToken that has been drawn
     * @throws EmptyDeckException
     */
    public SoloActionToken drawSoloActionToken() throws EmptyDeckException {
        return (SoloActionToken) this.draw();
    }

    /**
     * @return the number of items in the deck
     */
    public int getDeckSize() {
        return deckItems.size();
    }

    /**
     * @return the arraylist of items in the deck
     */
    public ArrayList<T> getDeckItems() {
        return deckItems;
    }


    /**
     * removes a card from the last position of the deck
     */
    public void discardFromBottom(){
        deckItems.remove(deckItems.size()-1);
    }

    /**
     * returns the first object of the deck as a Card without removing it from the deck
     * @return the first Card of the deck
     */
    public Card getFirstCard(){
        if (!deckItems.isEmpty()) {
        return (DevelopmentCard) deckItems.get(0);
    }else {
        return null;
    }
    }

    /*@Override
    public String toString() {
        return "Deck{" + "\n"
                + deckItems +
                '}' + "\n";
    }

     */
}
