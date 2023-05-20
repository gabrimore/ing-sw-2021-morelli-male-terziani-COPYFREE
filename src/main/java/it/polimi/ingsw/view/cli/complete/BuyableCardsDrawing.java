package it.polimi.ingsw.view.cli.complete;

import it.polimi.ingsw.view.cli.Utilities;
import it.polimi.ingsw.view.cli.basic.DevelopmentCardDrawing;
import it.polimi.ingsw.view.cli.CliAdapter;

public class BuyableCardsDrawing implements CompleteDrawing{

    protected static final int HEIGHT= (DevelopmentCardDrawing.HEIGHT+2)*2;
    protected static final int LENGTH= (DevelopmentCardDrawing.LENGTH+2)*6;

    private static final int BUYABLEDECK_CHOICE=6;

    private char[][] buyableCards;
    private CliAdapter cliAdapter;

    private DevelopmentCardDrawing developmentCard;


    public BuyableCardsDrawing(CliAdapter cliAdapter){
        this.cliAdapter=cliAdapter;
        this.developmentCard=new DevelopmentCardDrawing(cliAdapter);
        this.buyableCards=new char[HEIGHT][LENGTH];
    }

    /**
     * draws the buyable cards
     */
    public void draw(){
        draw(buyableCards, 0, 0);
    }

    /**
     * draws the cards you can buy on a 2dimensional array
     * @param base 2dimensional characters array to be modified
     * @param y_position row of the 2dimensional array where the modification starts
     * @param x_position column of the 2dimensional array where the modification starts
     */
    public void draw(char[][] base, int y_position, int x_position){
        Utilities.addEmptyChars(buyableCards);
        for(int i=0; i<cliAdapter.getNumOfPossibleDevCard(); i++){
            developmentCard.draw(BUYABLEDECK_CHOICE, base, y_position+(i/6)* DevelopmentCardDrawing.HEIGHT, x_position+ DevelopmentCardDrawing.LENGTH*i, i);
        }

    }

    /**
     * prints the cards you can buy
     */
    public void printElement(){
        for (int i = 0; i < buyableCards.length; i++) {
            for (int j = 0; j < buyableCards[0].length; j++) {
                System.out.print(buyableCards[i][j]);
            }
            System.out.println();


        }

    }

}
