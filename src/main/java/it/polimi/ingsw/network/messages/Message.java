package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.view.LocalModel;

import java.io.Serializable;

public interface Message extends Serializable {

    public void action(LocalModel localModel);
}
