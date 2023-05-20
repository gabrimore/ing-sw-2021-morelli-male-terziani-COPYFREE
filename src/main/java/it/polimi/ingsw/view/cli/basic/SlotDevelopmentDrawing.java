package it.polimi.ingsw.view.cli.basic;

import it.polimi.ingsw.view.cli.CliAdapter;

public class SlotDevelopmentDrawing implements BasicDrawing {

    public static final int HEIGHT= DevelopmentCardDrawing.HEIGHT+3;
    public static final int LENGTH=(DevelopmentCardDrawing.LENGTH);

    private final int SLOTDEVELOPMENT_CHOICE=2;

    private int slotRow;
    private int numOfCards;
    private DevelopmentCardDrawing developmentCard_drawing;

    public SlotDevelopmentDrawing(CliAdapter cliAdapter, int slotRow, int howManyCards){

        this.slotRow=slotRow;
        this.numOfCards= howManyCards;
        this.developmentCard_drawing=new DevelopmentCardDrawing(cliAdapter);
    }

    /**
     * draws the slot development on a 2dimensional array
     * @param base 2dimensional characters array to be modified
     * @param y_position row of the 2dimensional array where the modification starts
     * @param x_position column of the 2dimensional array where the modification starts
     */
    public void draw(char[][] base, int y_position, int x_position){
        for(int z=0; z<numOfCards; z++){
            developmentCard_drawing.draw(SLOTDEVELOPMENT_CHOICE, base, HEIGHT - DevelopmentCardDrawing.HEIGHT -z + y_position, + x_position, slotRow , z);
        }
    }

}
