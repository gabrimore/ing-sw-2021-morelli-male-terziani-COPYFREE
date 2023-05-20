package it.polimi.ingsw.model.game;

import java.io.Serializable;

public class Double <X, Y> implements Serializable {

    private final X name;
    private final Y position;

    public Double(X x, Y y) {
        this.name = x;
        this.position = y;
    }

    public X getName() {
        return name;
    }

    public Y getPosition() {
        return position;
    }
}
