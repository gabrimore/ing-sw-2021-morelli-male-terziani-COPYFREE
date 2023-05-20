package it.polimi.ingsw.model.game;

public class Triple<C, X, Y>{

    private final C card;
    private final X x;
    private final Y y;

    public Triple(C card, X x, Y y) {
        this.card = card;
        this.x = x;
        this.y = y;
    }

    public C getCard() {
        return card;
    }

    public X getX() {
        return x;
    }

    public Y getY() {
        return y;
    }
}
