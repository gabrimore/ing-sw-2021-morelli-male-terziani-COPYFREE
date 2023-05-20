package it.polimi.ingsw.view.cli.basic;

import it.polimi.ingsw.view.cli.Utilities;
import it.polimi.ingsw.view.cli.CliAdapter;
import it.polimi.ingsw.view.cli.complete.CompleteDrawing;

public class MarketBoardDrawing implements BasicDrawing, CompleteDrawing {

    public static final int HEIGHT=9;
    public static final int LENGTH=18;

    private char[][] marketBoard;

    private CliAdapter cliAdapter;

    public MarketBoardDrawing(CliAdapter cliAdapter){
        this.cliAdapter=cliAdapter;
    }
    public void draw(){
        this.marketBoard=new char[HEIGHT][LENGTH];
        //TransformIntoDrawing.addEmptyChars(marketBoard);
        draw(marketBoard, 0, 0);
    }

    /**
     * draws the marbles market
     * @param base 2dimensional characters array to be modified
     * @param y_position row of the 2dimensional array where the modification starts
     * @param x_position column of the 2dimensional array where the modification starts
     */
    public void draw(char[][] base, int y_position, int x_position){
        for(int i=0; i<HEIGHT; i++){
            for(int j=0; j<LENGTH; j++){
                    if (i==0 && j==0) Utilities.drawChar(base, '╔',y_position+i, x_position+j);
                    else if(i==0 && j==LENGTH-1) Utilities.drawChar(base, '╗',y_position+i, x_position+j);
                    else if(i==HEIGHT-1 && j==0) Utilities.drawChar(base, '╚',y_position+i, x_position+j);
                    else if(i==HEIGHT-1 && j==LENGTH-1) Utilities.drawChar(base, '╝',y_position+i, x_position+j);
                    else if(i==0 || i==HEIGHT-1) Utilities.drawChar(base, '═',y_position+i, x_position+j);
                    else if (j==0 || j==LENGTH-1) Utilities.drawChar(base, '║',y_position+i, x_position+j);
                    else if(i==HEIGHT-3 && (j+1)%3==0 && (j<3*4)) Utilities.drawChar(base, '↑' , y_position+i, x_position+j);
                    else if(i==HEIGHT-2 && (j+1)%3==0 && (j<3*4)) Utilities.drawChar(base, (char) (j/3+1+'0') , y_position+i, x_position+j);
                    else if(j==LENGTH-3 && (i>0 && i<4)) Utilities.drawChar(base, '←' , y_position+i, x_position+j);
                    else if(j==LENGTH-2 && (i>0 && i<4)) Utilities.drawChar(base, (char) (i+'0') , y_position+i, x_position+j);

                    else Utilities.drawChar(base, ' ', y_position+i , x_position+j);
            }
        }
        drawMarbles(base, y_position, x_position);
    }

    /**
     * draws the marbles
     * @param base 2dimensional character array on which to draw
     * @param y_position row of the 2dimensional array where the marble drawing starts
     * @param x_position column of the 2dimensional array where the marble drawing starts
     */
    private void drawMarbles(char[][] base, int y_position, int x_position){
        for(int i=0; i<=2; i++){
            for(int j=0; j<=3; j++){
                Utilities.insertCharToBase(base, cliAdapter.getMarble(i,j), y_position+1+i, x_position + 1 + 3*j);
            }
        }
        Utilities.insertCharToBase(base, cliAdapter.getMarble(4,4), y_position+4, x_position + 13);
    }

    /**
     * prints the marketboard
     */
    public void printElement(){
        for (int i = 0; i < marketBoard.length; i++) {
            for (int j = 0; j < marketBoard[0].length; j++) {
                System.out.print(marketBoard[i][j]);
            }
            System.out.println();


        }

    }
}
