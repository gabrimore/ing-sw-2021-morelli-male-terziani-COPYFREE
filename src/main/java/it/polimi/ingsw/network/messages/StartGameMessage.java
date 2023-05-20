package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.view.LocalModel;

public class StartGameMessage implements Message{

    private final boolean isNotReady;
    private final int myTurn;
    private final String nickname;

    public StartGameMessage(boolean isNotReady, int myTurn, String name) {
        this.isNotReady = isNotReady;
        this.myTurn = myTurn;
        this.nickname = name;
    }

    @Override
    public void action(LocalModel localModel) {
        localModel.setNotReady(isNotReady);
        localModel.setMyTurn(myTurn);
        localModel.setNickname(nickname);
    }
}
