package it.polimi.ingsw.view.cli.basic;

import it.polimi.ingsw.view.cli.Utilities;
import it.polimi.ingsw.view.cli.CliAdapter;

public class StrongboxDrawing implements BasicDrawing{

    public static final int HEIGHT =7;
    public static final int LENGTH = 14;

    private CliAdapter cliAdapter;
    private char[][] strongbox;

    public StrongboxDrawing(CliAdapter cliAdapter) {
        this.strongbox = new char[HEIGHT][LENGTH];
        this.cliAdapter=cliAdapter;
    }

    /**
     * draws the strongbox on a 2dimensional array
     * @param base 2dimensional characters array to be modified
     * @param y_position row of the 2dimensional array where the modification starts
     * @param x_position column of the 2dimensional array where the modification starts
     */
    public void draw(char[][] base, int y_position, int x_position) {
        for (int i = 0; i < HEIGHT-1; i++) {
            for (int j = 0; j < LENGTH; j++) {
                if (i == 0 && j == 0) Utilities.drawChar(base, '╔', i +y_position, j+x_position);
                else if (i == 0 && j == LENGTH - 1) Utilities.drawChar(base, '╗', i+y_position, j+x_position);
                else if (i == HEIGHT - 2 && j == 0) Utilities.drawChar(base, '╚', i+y_position, j+x_position);
                else if (i == HEIGHT - 2 && j == LENGTH - 1) Utilities.drawChar(base, '╝', i+y_position, j+x_position);
                else if (i == 0 || i == HEIGHT - 2) Utilities.drawChar(base, '═', i+y_position, j+x_position);
                else if (j == 0 || j == LENGTH - 1) Utilities.drawChar(base, '║', i+y_position, j+x_position);
                else Utilities.drawChar(base, ' ', i+y_position, j+x_position);
            }
        }

        Utilities.stringToChar(base, y_position+HEIGHT-1, x_position+LENGTH/2-"STRONGBOX".length()/2-1, "STRONGBOX");
        drawStrongboxQuantities(base, y_position, x_position);

    }

    /**
     * draws the resources' quantities on a 2dimensional array
     * @param base 2dimensional characters array to be modified
     * @param y_position row of the 2dimensional array where the modification starts
     * @param x_position column of the 2dimensional array where the modification starts
     */
    public void drawStrongboxQuantities(char[][] base, int y_position, int x_position){
        Utilities.insertCharToBase(base, cliAdapter.getResourceFromStrongbox(0), y_position+1, x_position+2); //getStrongbox(z) torna char array dimens 4: simbolo spazio x num
        Utilities.insertCharToBase(base, cliAdapter.getResourceFromStrongbox(1), y_position+2, x_position+2); // nel metodo gestire in modo che se ci son 3 risorse sole, ritorni char arry con soli spazi
        Utilities.insertCharToBase(base, cliAdapter.getResourceFromStrongbox(2), y_position+3, x_position+2);
        Utilities.insertCharToBase(base, cliAdapter.getResourceFromStrongbox(3), y_position+4, x_position+2);

    }

}
