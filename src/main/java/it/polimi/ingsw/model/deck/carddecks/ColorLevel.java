package it.polimi.ingsw.model.deck.carddecks;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public enum ColorLevel implements Serializable {

    @Expose
    GREEN1("GRN" , 1),
    @Expose
    GREEN2("GRN" , 2),
    @Expose
    GREEN3("GRN" , 3),
    @Expose
    VIOLET1("VLT" , 1),
    @Expose
    VIOLET2("VLT" , 2),
    @Expose
    VIOLET3("VLT" , 3),
    @Expose
    BLUE1("BLU" , 1),
    @Expose
    BLUE2("BLU" , 2),
    @Expose
    BLUE3("BLU" , 3),
    @Expose
    YELLOW1("YLW" , 1),
    @Expose
    YELLOW2("YLW" , 2),
    @Expose
    YELLOW3("YLW" , 3);

    @Expose
    public final String color;
    @Expose
    public final int level;


    ColorLevel(String color, int level){
        this.color=color;
        this.level=level;
    }

    public String getColor(){
        return this.color;
    }

    public int getLevel(){
        return this.level;
    }



    @Override
    public String toString() {
        return color+level;
    }
}
