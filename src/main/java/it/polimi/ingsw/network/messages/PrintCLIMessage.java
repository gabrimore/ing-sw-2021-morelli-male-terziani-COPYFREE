package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.view.LocalModel;

public class PrintCLIMessage implements Message {

    private int choice;

    public PrintCLIMessage(int choice) {
        this.choice = choice;
    }

    @Override
    public void action(LocalModel localModel) {}

    public int getChoice() {
        return choice;
    }
}
