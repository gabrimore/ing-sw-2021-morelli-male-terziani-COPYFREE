package it.polimi.ingsw.view.cli.basic;

import it.polimi.ingsw.view.cli.Utilities;
import it.polimi.ingsw.view.cli.CliAdapter;

import java.util.ArrayList;

public class DevelopmentCardDrawing implements BasicDrawing{

    public static final int HEIGHT = 10;
    public static final int LENGTH = 16;

    private CliAdapter cliAdapter;

    private char[][] card;

    public DevelopmentCardDrawing(CliAdapter cliAdapter) {
        this.card = new char[HEIGHT][LENGTH];
        this.cliAdapter=cliAdapter;
    }

    /**
     * draws a Development card
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
                else if (((j == 0 || j == LENGTH - 1) && i!=0) || i==2 && j== LENGTH-3) Utilities.drawChar(base, '│', i+y_position, j+x_position);
                //else if ((i>0 && i<4) && j== LENGTH/2) drawChar('║', i, j);
                else if (i==3) {
                    if(j==LENGTH-3) Utilities.drawChar(base, '╧', i+y_position, j+x_position);
                    else Utilities.drawChar(base, '═', i+y_position, j+x_position);
                }
                else Utilities.drawChar(base, ' ', i+y_position, j+x_position);
            }
        }
        drawDevCardWords(base,y_position, x_position);
    }

    /**
     * draws a Development Card choosing from a deck
     * @param deckChoice int indicating deck from where to choose the card
     * @param base 2dimensional character array on which to draw
     * @param y_position row of the 2dimensional array where the card starts
     * @param x_position column of the 2dimensional array where the card starts
     * @param cardRow row position of the card in the deck
     * @param cardColumn column position of the card in the deck
     */
    public void draw(int deckChoice, char[][] base, int y_position, int x_position, int cardRow, int cardColumn){
        draw(base, y_position, x_position);
        ArrayList<char[]> cardProperties =cliAdapter.getCard(deckChoice, cardRow, cardColumn);
        drawDevCardProperties(cardProperties, base, y_position, x_position);
    }

    /////da rivedere per le buyablecards

    /**
     *
     * @param deckChoice int indicating deck from where to choose the card
     * @param base 2dimensional character array on which to draw
     * @param y_position row of the 2dimensional array where the card starts
     * @param x_position column of the 2dimensional array where the card starts
     * @param cardPosition position of the card inside the deck
     */
    public void draw(int deckChoice, char[][] base, int y_position, int x_position, int cardPosition){
        draw(base, y_position, x_position);
        ArrayList<char[]> cardProperties =cliAdapter.getCard(deckChoice, cardPosition);
        drawDevCardProperties(cardProperties, base, y_position, x_position);
    }

    /**
     * draws Development card fixed texts
     * @param base 2dimensional character array on which to draw
     * @param y_position row of the 2dimensional array where the card starts
     * @param x_position column of the 2dimensional array where the card starts
     */
    private void drawDevCardWords(char[][] base, int y_position, int x_position){
        Utilities.stringToChar(base, y_position, x_position, "DEVELOPMENT CARD");
        Utilities.stringToChar(base, y_position+1, x_position+1, "COST:");
        Utilities.stringToChar(base, y_position+1, x_position+LENGTH-4, "PV:");
        Utilities.stringToChar(base, y_position+4, x_position+1, "you pay:");
        Utilities.stringToChar(base, y_position+6, x_position+1, "you get:");
        Utilities.stringToChar(base, y_position+8, x_position+1, "faith point:");

    }

    /**
     * draws Development card properties
     * @param cardProperties arraylist of char arrays containing the properties to be written
     * @param base 2dimensional character array on which to draw
     * @param y_position row of the 2dimensional array where the card starts
     * @param x_position column of the 2dimensional array where the card starts
     */
    private void drawDevCardProperties(ArrayList<char[]> cardProperties, char[][] base, int y_position, int x_position){

        //insert cost
        Utilities.insertCharToBase(base, cardProperties.get(0), y_position+2, x_position+1);
        Utilities.insertCharToBase(base, cardProperties.get(1), y_position+2, x_position+4);
        Utilities.insertCharToBase(base, cardProperties.get(2), y_position+2, x_position+7);

        //insert VictoryPoint
        Utilities.insertCharToBase(base, cardProperties.get(3), y_position+2, x_position+LENGTH-3);

        //insert input resources
        Utilities.insertCharToBase(base, cardProperties.get(4), y_position+5, x_position+1);
        Utilities.insertCharToBase(base, cardProperties.get(5), y_position+5, x_position+5);

        //insert output resources
        Utilities.insertCharToBase(base, cardProperties.get(6), y_position+7, x_position+1);
        Utilities.insertCharToBase(base, cardProperties.get(7), y_position+7, x_position+5);
        Utilities.insertCharToBase(base, cardProperties.get(8), y_position+7, x_position+9);

        //insert faith point
        Utilities.insertCharToBase(base, cardProperties.get(9), y_position+HEIGHT-2, x_position+LENGTH-2);

        //insert ColorLevel
        Utilities.insertCharToBase(base, cardProperties.get(10), y_position+HEIGHT-1, x_position+LENGTH/2-2);


    }



}
