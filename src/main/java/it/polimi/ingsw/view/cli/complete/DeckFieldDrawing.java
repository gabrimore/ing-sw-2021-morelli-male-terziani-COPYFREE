package it.polimi.ingsw.view.cli.complete;

import it.polimi.ingsw.view.cli.basic.DevelopmentCardDrawing;
import it.polimi.ingsw.view.cli.CliAdapter;

public class DeckFieldDrawing implements CompleteDrawing{

    protected static final int HEIGHT=(DevelopmentCardDrawing.HEIGHT+1)*3;
    protected static final int LENGTH=(DevelopmentCardDrawing.LENGTH+4)*4;//ovvero 5 spazi tra una carta e l'altra

    private final int DECKFIELD_CHOICE=1;

    public static char[][] deckField;
    private DevelopmentCardDrawing card; //eventualmente fare un array 3x4 di tipo Card_Drawing

    private CliAdapter cliAdapter;

    public DeckFieldDrawing(CliAdapter cliAdapter){
        this.deckField=new char[HEIGHT][LENGTH];
        this.card=new DevelopmentCardDrawing(cliAdapter);
    }

    /**
     * draws the deck field on a 2dimensional array
     */
    public void draw(){
        for (int i=0; i<HEIGHT; i++){
            for(int j=0; j<LENGTH; j++){
                if (i%(HEIGHT/3)==0 && j%(LENGTH/4)==0) card.draw(DECKFIELD_CHOICE, deckField, i,j+2, (i)/(DevelopmentCardDrawing.HEIGHT+1), (j)/(DevelopmentCardDrawing.LENGTH+4) );
                //else drawChar(' ', i, j);
            }
        }
    }


    /**
     * prints the deck field
     */
    public void printElement(){
        for (int i = 0; i < deckField.length; i++) {
            for (int j = 0; j < deckField[0].length; j++) {
                System.out.print(deckField[i][j]);
            }
            System.out.println();


        }

    }
}
