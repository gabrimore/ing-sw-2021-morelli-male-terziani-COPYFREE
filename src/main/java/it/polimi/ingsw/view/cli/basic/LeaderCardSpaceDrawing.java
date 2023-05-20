package it.polimi.ingsw.view.cli.basic;

import it.polimi.ingsw.view.cli.Utilities;
import it.polimi.ingsw.view.cli.CliAdapter;

public class LeaderCardSpaceDrawing implements BasicDrawing {

    public static int HEIGHT=1+ (LeaderCardDrawing.HEIGHT)*2;
    public static int LENGTH= LeaderCardDrawing.LENGTH*2+1;

    private final int ACTIVEDECK_CHOICE=4;
    private final int HIDDENDECK_CHOICE=5;

    private LeaderCardDrawing leaderCard;
    private CliAdapter cliAdapter;


    public LeaderCardSpaceDrawing(CliAdapter cliAdapter){
        this.cliAdapter=cliAdapter;
        this.leaderCard= new LeaderCardDrawing(cliAdapter);
    }

    /**
     * draws the hidden and active leader cards
     * @param base 2dimensional characters array to be modified
     * @param y_position row of the 2dimensional array where the modification starts
     * @param x_position column of the 2dimensional array where the modification starts
     */
    public void draw(char[][] base, int y_position, int x_position){
        drawActiveLeaderCards(base, y_position, x_position);
        drawHiddenLeaderCards(base, y_position, x_position);
    }

    /**
     * draws active leader cards
     * @param base 2dimensional character array on which to draw
     * @param y_position row of the 2dimensional array where the card starts
     * @param x_position column of the 2dimensional array where the card starts
     */
    private void drawActiveLeaderCards(char[][] base, int y_position, int x_position){
        //begins to write them from 1st position forward
        Utilities.stringToChar(base, y_position, x_position, "ACTIVE");
        for(int z=0; z< cliAdapter.getNumActiveLeaderCard(); z++){
            leaderCard.draw(ACTIVEDECK_CHOICE, base, y_position+1+z* LeaderCardDrawing.HEIGHT, x_position, z);
        }
    }

    /**
     * draws hidden leader cards
     * @param base 2dimensional character array on which to draw
     * @param y_position row of the 2dimensional array where the card starts
     * @param x_position column of the 2dimensional array where the card starts
     */
    private void drawHiddenLeaderCards(char[][] base, int y_position, int x_position){
        //begins to write them from last position backwards
        Utilities.stringToChar(base, y_position, x_position+ LeaderCardDrawing.LENGTH+1, "NON-ACTIVE");

        for(int z=0; z< cliAdapter.getNumInactiveLeaderCard(); z++){
            leaderCard.draw(HIDDENDECK_CHOICE, base, y_position+1+z* LeaderCardDrawing.HEIGHT, x_position+ LeaderCardDrawing.LENGTH+1, z);
        }

    }
}
