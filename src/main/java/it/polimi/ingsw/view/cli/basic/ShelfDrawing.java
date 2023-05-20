package it.polimi.ingsw.view.cli.basic;

import it.polimi.ingsw.view.cli.Utilities;
import it.polimi.ingsw.view.cli.CliAdapter;

public class ShelfDrawing implements BasicDrawing{

    protected static final int HEIGHT = 3;
    protected static final int LENGTH = 6;
    private int effectiveLength;

    private int maxAllowed;
    private int numberOfShelf;
    private char[][] shelf;

    private CliAdapter cliAdapter;

    public ShelfDrawing(int maxAllowed, int numberOfShelf, CliAdapter cliAdapter){
        this.cliAdapter=cliAdapter;
        this.maxAllowed=maxAllowed;
        this.numberOfShelf=numberOfShelf;
        this.effectiveLength=LENGTH*maxAllowed;
        this.shelf = new char[HEIGHT][LENGTH*maxAllowed];
    }

    /**
     * draws the shelf
     * @param base 2dimensional characters array to be modified
     * @param y_position row of the 2dimensional array where the modification starts
     * @param x_position column of the 2dimensional array where the modification starts
     */
    public void draw(char[][] base, int y_position, int x_position){
        for(int i = 0; i< HEIGHT; i++) {
            for (int j = 0; j < effectiveLength; j++) {
                if (i == 0 && j == 0) Utilities.drawChar(base, '┌', y_position + i, x_position + j);
                else if (i == 0 && j == effectiveLength - 1)
                    Utilities.drawChar(base, '┐', y_position + i, x_position + j);
                else if (i == HEIGHT - 1 && j == 0)
                    Utilities.drawChar(base, '└', y_position + i, x_position + j);
                else if (i == HEIGHT - 1 && j == effectiveLength - 1)
                    Utilities.drawChar(base, '┘', y_position + i, x_position + j);
                else if (i == 0 || i == HEIGHT - 1)
                    Utilities.drawChar(base, '—', y_position + i, x_position + j);
                else if (j == 0 || j == effectiveLength - 1)
                    Utilities.drawChar(base, '|', y_position + i, x_position + j);
                else Utilities.drawChar(base,' ', y_position + i, x_position + j);
            }
        }
    drawShelfContent(base, y_position+1, effectiveLength/2-1+x_position);
}

    /**
     * draws the shelf's resources
     * @param base
     * @param y_position
     * @param x_position
     */
    private void drawShelfContent(char[][] base, int y_position, int x_position){
        Utilities.insertCharToBase(base, cliAdapter.getShelf(numberOfShelf), y_position, x_position);
    }


    public int getEffectiveLength(){
        return effectiveLength;
    }

}
