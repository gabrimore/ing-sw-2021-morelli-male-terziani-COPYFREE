package it.polimi.ingsw.network.messages;


import it.polimi.ingsw.view.LocalModel;

public class StandardMessage implements Message{

    private final String msg;

    public StandardMessage(String msg) {
        this.msg = msg;
    }

    @Override
    public void action(LocalModel localModel) {
        //if(localModel.getCliOrGui() == 1) {
            System.out.print(msg);
        //}
    }

}
