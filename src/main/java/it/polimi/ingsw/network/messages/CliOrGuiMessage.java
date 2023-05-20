package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.view.LocalModel;

public class CliOrGuiMessage implements Message{

    private final boolean chooseLobbySize;
    private final int choice;

    public CliOrGuiMessage(boolean chooseLobbySize, int choice) {
        this.chooseLobbySize = chooseLobbySize;
        this.choice = choice;
    }

    public int getChoice(){
        return choice;
    }

    @Override
    public void action(LocalModel localModel) {
        localModel.setLobbySize(chooseLobbySize);
        localModel.setCliOrGui(choice);
    }

}
