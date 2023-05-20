package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.model.game.Double;
import it.polimi.ingsw.view.LocalModel;

import java.util.ArrayList;

public class EndGameMessage implements Message{

    private final ArrayList<Double<String, Integer>> allPoints;
    private final boolean isWinner;
    private final String winner;
    private boolean soloGame;

    public EndGameMessage(ArrayList<Double<String, Integer>> allPoints, boolean isWinner, String winner, boolean soloGame) {
        this.allPoints = allPoints;
        this.isWinner = isWinner;
        this.winner = winner;
        this.soloGame = soloGame;
    }

    public ArrayList<Double<String, Integer>> getAllPoints() {
        return allPoints;
    }

    @Override
    public void action(LocalModel localModel) {
        if(localModel.getCliOrGui() == 1) {
            if (isWinner) {
                System.out.println("\nYou are the Winner " + winner + "\n");
            } else {
                System.out.println("\nYou lost! The winner is " + winner + "\n");
            }
        }
    }
}
