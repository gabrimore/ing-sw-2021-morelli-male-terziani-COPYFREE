package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.view.LocalModel;

public class NameTurnMessage implements Message{

    private final int myTurn;
    private final String nickname;

    public NameTurnMessage(int myTurn, String name) {
        this.myTurn = myTurn;
        this.nickname = name;
    }

    @Override
    public void action(LocalModel localModel) {
        localModel.setMyTurn(myTurn);
        localModel.setNickname(nickname);
    }
}
