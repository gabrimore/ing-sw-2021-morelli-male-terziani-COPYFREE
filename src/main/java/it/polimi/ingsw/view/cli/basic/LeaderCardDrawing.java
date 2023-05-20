package it.polimi.ingsw.view.cli.basic;

import it.polimi.ingsw.view.cli.Utilities;
import it.polimi.ingsw.view.cli.CliAdapter;

import java.util.ArrayList;

public class LeaderCardDrawing implements BasicDrawing{
    public static final int HEIGHT =6;
    public static final int LENGTH = 26;

    private char[][] card;

    private CliAdapter cliAdapter;

    public LeaderCardDrawing(CliAdapter cliAdapter) {
        this.card = new char[HEIGHT][LENGTH];
        this.cliAdapter=cliAdapter;
    }


    /**
     * draws a Leader card
     * @param base 2dimensional characters array to be modified
     * @param y_position row of the 2dimensional array where the modification starts
     * @param x_position column of the 2dimensional array where the modification starts
     */
    public void draw(char[][] base, int y_position, int x_position) {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < LENGTH; j++) {
                if (i == 1 && j == 0) Utilities.drawChar(base,'╔', i+y_position, j+x_position);
                else if (i == 1 && j == LENGTH - 1) Utilities.drawChar(base, '╗', i+y_position, j+x_position);
                else if (i == HEIGHT - 1 && j == 0) Utilities.drawChar(base, '╚', i+y_position, j+x_position);
                else if (i == HEIGHT - 1 && j == LENGTH - 1) Utilities.drawChar(base, '╝', i+y_position, j+x_position);
                else if (i == 1 || i == HEIGHT - 1) Utilities.drawChar(base, '-', i+y_position, j+x_position);
                else if ((j == 0 || j == LENGTH - 1) && i!=0) Utilities.drawChar(base, '│', i+y_position, j+x_position);
                else if (i==3) Utilities.drawChar(base, '═', i+y_position, j+x_position);
                else Utilities.drawChar(base, ' ', i+y_position, j+x_position);
            }
        }


        drawLeaderCardWords(base,y_position, x_position);

    }

    /**
     *
     * @param deckChoice
     * @param base
     * @param y_position
     * @param x_position
     * @param cardIndex
     */
    public void draw(int deckChoice, char[][] base, int y_position, int x_position, int cardIndex){
        draw(base, y_position, x_position);
        drawLeaderCardProperties(deckChoice, base, y_position, x_position, cardIndex);
    }

    /**
     * draws Leader card fixed texts
     * @param base 2dimensional character array on which to draw
     * @param y_position row of the 2dimensional array where the card starts
     * @param x_position column of the 2dimensional array where the card starts
     */
    private void drawLeaderCardWords(char[][] base, int y_position, int x_position){
        Utilities.stringToChar(base, y_position, x_position+1, "LEADER CARD");
        Utilities.stringToChar(base, y_position+1, x_position+1, "REQUIREMENT:");
        Utilities.stringToChar(base, y_position+1, x_position+LENGTH-4, "PV:");
    }

    /**
     * draws Leader card's properties
     * @param deckChoice int indicating the deck from where to take the card
     * @param base 2dimensional character array on which to draw
     * @param y_position row of the 2dimensional array where the card starts
     * @param x_position column of the 2dimensional array where the card starts
     * @param cardIndex index of the card in the deck
     */
    private void drawLeaderCardProperties(int deckChoice, char[][] base, int y_position, int x_position, int cardIndex){

        ArrayList<char[]> properties =cliAdapter.getCard(deckChoice, cardIndex);

        //insert requirements
        Utilities.insertCharToBase(base, properties.get(0), y_position+2, x_position+1);
        Utilities.insertCharToBase(base, properties.get(1), y_position+2, x_position+6);
        Utilities.insertCharToBase(base, properties.get(2), y_position+2, x_position+11);

        //insert VictoryPoints
        Utilities.insertCharToBase(base, properties.get(3), y_position+2, x_position+LENGTH-4);

        //insert Effect
        Utilities.insertCharToBase(base, properties.get(4), y_position+HEIGHT-2, x_position+LENGTH/2-3);

        }

}
