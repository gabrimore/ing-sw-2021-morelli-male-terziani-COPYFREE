package it.polimi.ingsw.view.cli.basic;

import it.polimi.ingsw.view.cli.Utilities;
import it.polimi.ingsw.view.cli.CliAdapter;

public class LegendaDrawing implements BasicDrawing{

    public static final int HEIGHT=6;
    public static final int LENGTH=12;

    private CliAdapter cliAdapter;

    public LegendaDrawing(CliAdapter cliAdapter){
        this.cliAdapter=cliAdapter;
    }

    /**
     * draws the legenda
     * @param base 2dimensional characters array to be modified
     * @param y_position row of the 2dimensional array where the modification starts
     * @param x_position column of the 2dimensional array where the modification starts
     */
    public void draw(char[][] base, int y_position, int x_position){


        Utilities.stringToChar(base, y_position+1, x_position+2, "COIN: ");
        Utilities.drawChar(base, '©', y_position+1, x_position+11);

        Utilities.stringToChar(base, y_position+2, x_position+2, "SERVANT: ");
        Utilities.drawChar(base, '۩', y_position+2, x_position+11);

        Utilities.stringToChar(base, y_position+3, x_position+2, "SHIELD: ");
        Utilities.drawChar(base, '♦', y_position+3, x_position+11);

        Utilities.stringToChar(base, y_position+4, x_position+2, "STONE: ");
        Utilities.drawChar(base, '⚱', y_position+4, x_position+11);

        Utilities.stringToChar(base, y_position+4, x_position+2, "FAITHPOINT: ");
        Utilities.drawChar(base, '†', y_position+4, x_position+11);


    }


}
