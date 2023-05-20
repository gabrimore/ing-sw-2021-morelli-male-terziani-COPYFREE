package it.polimi.ingsw.view.cli.complete;

import it.polimi.ingsw.view.cli.Utilities;
import it.polimi.ingsw.view.cli.basic.LeaderCardDrawing;
import it.polimi.ingsw.view.cli.CliAdapter;

public class InitialLeaderCardDrawing implements CompleteDrawing{

    protected static final int HEIGHT= LeaderCardDrawing.HEIGHT;
    protected static final int LENGTH=(LeaderCardDrawing.LENGTH+4)*4;

    private final int INITIALLEADERCARDS_CHOICE=3;

    private char[][] initialLeaderCards;

    private LeaderCardDrawing card;
    private CliAdapter cliAdapter;


    public InitialLeaderCardDrawing(CliAdapter cliAdapter){
        this.cliAdapter=cliAdapter;
        this.initialLeaderCards=new char[HEIGHT][LENGTH];

        this.card =new LeaderCardDrawing(cliAdapter);
    }

    /**
     * draws the initial leader cards on a 2dimensional array
     */
    public void draw(){
        Utilities.addEmptyChars(initialLeaderCards);
        for(int z=0; z<4; z++){
            card.draw(INITIALLEADERCARDS_CHOICE, initialLeaderCards, 0, z*(LeaderCardDrawing.LENGTH+4) , z);
        }
    }

    /**
     * prints the initial leader cards
     */
    public void printElement(){

        for(int i=0; i<initialLeaderCards.length; i++){
            for(int j=0; j<initialLeaderCards[0].length; j++){
                System.out.print(initialLeaderCards[i][j]);
            }
            System.out.println();
        }
    }


}
