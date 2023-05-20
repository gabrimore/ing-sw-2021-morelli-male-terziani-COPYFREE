package it.polimi.ingsw.model.player.faithtrack;

import com.google.gson.annotations.Expose;

public class Tile{

    @Expose
    private final int victoryPoint;

    public Tile(int victoryPoint) {
        this.victoryPoint = victoryPoint;
    }

    public int getVictoryPoint() {
        return victoryPoint;
    }
}
