package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.ClientHandler;

public class PlayerAction {

    private ClientHandler clientHandler;
    private int turn;
    private int choice;

    public PlayerAction(ClientHandler clientHandler, int turn, int choice) {
        this.clientHandler = clientHandler;
        this.turn = turn;
        this.choice = choice;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public int getTurn() {
        return turn;
    }

    public int getChoice() {
        return choice;
    }

}
