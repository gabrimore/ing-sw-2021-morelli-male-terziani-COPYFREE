package it.polimi.ingsw.model.player.faithtrack;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class PopeSpaceTile implements Serializable {

    @Expose
    private final int popePoint;
    @Expose
    private final int vaticanReportSection;
    @Expose
    private boolean discarded;

    public PopeSpaceTile(int popePoint, int vaticanReportSection) {
        this.popePoint = popePoint;
        this.vaticanReportSection = vaticanReportSection;
        this.discarded = false;
    }
    public int getPopePoint() {
        return popePoint;
    }
    public int getVaticanReportSection() {
        return vaticanReportSection;
    }
    public boolean isDiscarded() {
        return discarded;
    }
    public void discard(){
        discarded = true;
    }



}
