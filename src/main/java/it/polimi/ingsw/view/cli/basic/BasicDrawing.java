package it.polimi.ingsw.view.cli.basic;

public interface BasicDrawing {

    /**
     * modifies the given 2dimensional array of characters
     * @param base 2dimensional characters array to be modified
     * @param y_position row of the 2dimensional array where the modification starts
     * @param x_position column of the 2dimensional array where the modification starts
     */
    public void draw(char[][] base, int y_position, int x_position);

}
