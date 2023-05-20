package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.view.LocalModel;

public class WhiteMarbleMessage implements Message{

    private final int whiteNumber;

    public WhiteMarbleMessage(int whiteNumber) {
        this.whiteNumber = whiteNumber;
    }

    public int getWhiteNumber() {
        return whiteNumber;
    }


    @Override
    public void action(LocalModel localModel) {

    }
}
