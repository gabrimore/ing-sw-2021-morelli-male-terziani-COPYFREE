package it.polimi.ingsw.view.cli.basic;

import it.polimi.ingsw.view.cli.Utilities;

import java.util.ArrayList;
import java.util.Arrays;

public class TileDrawing implements BasicDrawing{

    public static final int HEIGHT=3;
    public static final int LENGTH=7;

    public static char[][] tile;
    private int numberOfTile=0;
    private static ArrayList<Integer> popeSpaceTiles=new ArrayList<>(Arrays.asList(5, 6, 7, 8, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24));

    private int victoryPoint=0;
    private int victoryPointCounter=0;




    public TileDrawing(){
        this.tile =new char[HEIGHT][LENGTH];
    }

    /**
     * draws a Tile
     * @param base 2dimensional characters array to be modified
     * @param y_position row of the 2dimensional array where the modification starts
     * @param x_position column of the 2dimensional array where the modification starts
     */
    public void draw(char[][] base, int y_position, int x_position) {
        if (popeSpaceTiles.contains(numberOfTile)) {
            drawPopeSpaceTile(base, y_position, x_position);
        } else drawNormalTile(base, y_position, x_position);
        drawTileNumber(base, HEIGHT / 2 + y_position, LENGTH / 2 + x_position);
        drawVictoryPoints(base, HEIGHT-1+ y_position, LENGTH/2+ x_position);
    }

    /**
     * draws a normal tile
     * @param base 2dimensional characters array to be modified
     * @param y_position row of the 2dimensional array where the modification starts
     * @param x_position column of the 2dimensional array where the modification starts
     */
    private void drawNormalTile(char[][] base, int y_position, int x_position){
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < LENGTH; j++) {
                if (i == 0) {
                    if (j == 0) Utilities.drawChar(base, '┌', i + y_position, j + x_position);
                    if (j == LENGTH - 1) Utilities.drawChar(base, '┐', i + y_position, j + x_position);
                    else if (j != 0 && j != LENGTH - 1)
                        Utilities.drawChar(base, '─', i + y_position, j + x_position);
                    //else drawChar(' ', i, j);
                } else if (i == HEIGHT - 1) {
                    if (j == 0) Utilities.drawChar(base, '└', i + y_position, j + x_position);
                    if (j == LENGTH - 1) Utilities.drawChar(base, '┘', i + y_position, j + x_position);
                    else if (j != 0 && j != LENGTH - 1)
                        Utilities.drawChar(base, '─', i + y_position, j + x_position);
                } else if ((j == 0 || j == LENGTH - 1))
                    Utilities.drawChar(base, '│', i + y_position, j + x_position);
                else Utilities.drawChar(base, ' ', i + y_position, j + x_position);
            }
        }
    }

    /**
     * draws a popeSpace special tile
     * @param base 2dimensional character array on which to draw
     * @param y_position row of the 2dimensional array where the card starts
     * @param x_position column of the 2dimensional array where the card starts
     */
    private void drawPopeSpaceTile(char[][] base, int y_position, int x_position){
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < LENGTH; j++) {
                if (i == 0) {
                    if (j == 0) Utilities.drawChar(base, '╔', i + y_position, j + x_position);
                    if (j == LENGTH - 1) Utilities.drawChar(base, '╗', i + y_position, j + x_position);
                    else if (j != 0 && j != LENGTH - 1)
                        Utilities.drawChar(base, '═', i + y_position, j + x_position);
                    //else drawChar(' ', i, j);
                } else if (i == HEIGHT - 1) {
                    if (j == 0) Utilities.drawChar(base, '╚', i + y_position, j + x_position);
                    if (j == LENGTH - 1) Utilities.drawChar(base, '╝', i + y_position, j + x_position);
                    else if (j != 0 && j != LENGTH - 1)
                        Utilities.drawChar(base, '═', i + y_position, j + x_position);
                } else if ((j == 0 || j == LENGTH - 1))
                    Utilities.drawChar(base, '║', i + y_position, j + x_position);
                else Utilities.drawChar(base, ' ', i + y_position, j + x_position);
            }
        }
    }

    /**
     * draws the number of the tile
     * @param base 2dimensional character array on which to draw
     * @param y row of the 2dimensional array where the tile number starts
     * @param x column of the 2dimensional array where the tile number starts
     */
    private void drawTileNumber(char[][] base, int y, int x){
        if (numberOfTile==8 || numberOfTile==16 || numberOfTile==24) Utilities.drawChar(base, '†', y, x);
        else Utilities.intToChar(base, y, x-2, numberOfTile);
    }

    /**
     * draws the victory point of the tile
     * @param base 2dimensional character array on which to draw
     * @param y row of the 2dimensional array where the tile starts
     * @param x column of the 2dimensional array where the tile starts
     */
    public void drawVictoryPoints(char[][] base, int y, int x){
        if(numberOfTile%3==0){
            Utilities.intToChar(base, y, x, victoryPoint);
            if(numberOfTile%2==0 ) victoryPointCounter=victoryPointCounter+1;
            victoryPoint=victoryPoint+victoryPointCounter;
        }
        if (numberOfTile==24){
            victoryPoint=0;
            victoryPointCounter=1;
        }

    }

    /**
     * sets the tile number
     * @param numberOfTile
     */
    public void setNumberOfTile(int numberOfTile) {
        this.numberOfTile = numberOfTile;
    }
}
