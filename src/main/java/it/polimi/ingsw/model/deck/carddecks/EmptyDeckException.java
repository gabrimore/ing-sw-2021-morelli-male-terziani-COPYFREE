package it.polimi.ingsw.model.deck.carddecks;

import it.polimi.ingsw.model.deck.Deck;

public class EmptyDeckException extends Exception{

    public Deck deck;
    private int color;
    private int level;
    private int discardedQuantity;

    public EmptyDeckException(int level, int color, int discardedQuantity){
        this.level=level;
        this.color=color;
        this.discardedQuantity=discardedQuantity;
    }

    public EmptyDeckException(Deck deck){
        this.deck=deck;
    }

    public int getDiscardedQuantity() {
        return discardedQuantity;
    }

    public boolean checkIfLastDeck(){
        return (this.level==2);
    }

    public void deckIsEmpty(){
        System.out.println("the deck is empty");
    }
}
