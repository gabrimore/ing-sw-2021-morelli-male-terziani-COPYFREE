package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.view.LocalModel;

public class StartTurnMessage implements Message{

    private final boolean isMyTurn;
    private final String currentPlayer;
    private boolean soloGame;

    public StartTurnMessage(boolean isMyTurn, String currentPlayer) {
        this.isMyTurn = isMyTurn;
        this.currentPlayer = currentPlayer;
    }

    public boolean isMyTurn() {
        return isMyTurn;
    }

    @Override
    public void action(LocalModel localModel){

        localModel.setCurrentPlayer(currentPlayer);
    }
}
