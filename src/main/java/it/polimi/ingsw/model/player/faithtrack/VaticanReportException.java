package it.polimi.ingsw.model.player.faithtrack;

public class VaticanReportException extends Exception{
    private final int pope;

    public VaticanReportException(int Pope){
        this.pope = Pope;
    }

    public int getPope() {
        return pope;
    }
}