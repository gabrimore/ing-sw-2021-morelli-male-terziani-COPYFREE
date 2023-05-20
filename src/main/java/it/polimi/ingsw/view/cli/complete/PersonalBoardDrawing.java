package it.polimi.ingsw.view.cli.complete;

import it.polimi.ingsw.view.cli.Utilities;
import it.polimi.ingsw.view.cli.basic.*;
import it.polimi.ingsw.view.cli.CliAdapter;

public class PersonalBoardDrawing implements CompleteDrawing{

    private static final int HEIGHT= FaithtrackDrawing.HEIGHT +1 + LeaderCardSpaceDrawing.HEIGHT+2 ;
    private static final int LENGTH= FaithtrackDrawing.LENGTH +5;

    private char[][] personalBoard;

    private LegendaDrawing legenda;
    private FaithtrackDrawing faithTrack;
    private WarehouseDrawing warehouse;
    private StrongboxDrawing strongbox;
    private SlotDevelopmentDrawing[] slotDevelopment;
    private LeaderCardSpaceDrawing leaderCardSpace;
    private MarketBoardDrawing marketBoard;



    private CliAdapter cliAdapter;

    public PersonalBoardDrawing(CliAdapter cliAdapter){

        this.cliAdapter = cliAdapter;
        this.legenda=new LegendaDrawing(cliAdapter);
        this.personalBoard=new char[HEIGHT][LENGTH];
        this.faithTrack= new FaithtrackDrawing(cliAdapter);
        this.warehouse= new WarehouseDrawing(cliAdapter);
        this.strongbox= new StrongboxDrawing(cliAdapter);
        this.slotDevelopment= new SlotDevelopmentDrawing[3];
        this.leaderCardSpace=new LeaderCardSpaceDrawing(cliAdapter);
        this.marketBoard=new MarketBoardDrawing(cliAdapter);

    }

    /**
     * draws the player's personal board
     */
    public void draw(){

        Utilities.addEmptyChars(personalBoard);

        faithTrack.draw(personalBoard, 0, 0);
        legenda.draw(personalBoard, 0,0);
        warehouse.draw(personalBoard, HEIGHT- WarehouseDrawing.HEIGHT-2 - StrongboxDrawing.HEIGHT +1, 1);
        strongbox.draw(personalBoard, HEIGHT- StrongboxDrawing.HEIGHT-1, 4);
        slotDevelopment[0]=new SlotDevelopmentDrawing(cliAdapter, 0, cliAdapter.getNumOfDevCard(0));
        slotDevelopment[1]=new SlotDevelopmentDrawing(cliAdapter, 1, cliAdapter.getNumOfDevCard(1));
        slotDevelopment[2]=new SlotDevelopmentDrawing(cliAdapter, 2, cliAdapter.getNumOfDevCard(2));
        slotDevelopment[0].draw(personalBoard, HEIGHT- SlotDevelopmentDrawing.HEIGHT, LENGTH-3*(SlotDevelopmentDrawing.LENGTH+2));
        slotDevelopment[1].draw(personalBoard, HEIGHT- SlotDevelopmentDrawing.HEIGHT, LENGTH-2*(SlotDevelopmentDrawing.LENGTH+2));
        slotDevelopment[2].draw(personalBoard, HEIGHT- SlotDevelopmentDrawing.HEIGHT, LENGTH- SlotDevelopmentDrawing.LENGTH);
        leaderCardSpace.draw(personalBoard, HEIGHT- LeaderCardSpaceDrawing.HEIGHT, LENGTH- 3*(SlotDevelopmentDrawing.LENGTH+2)- LeaderCardSpaceDrawing.LENGTH -2);
        marketBoard.draw(personalBoard, TileDrawing.HEIGHT, LENGTH- MarketBoardDrawing.LENGTH-25);
    }

    /**
     * prints the player's personal board
     */
    public void printElement(){
        for (int i = 0; i < personalBoard.length; i++) {
            for (int j = 0; j < personalBoard[0].length; j++) {
                System.out.print(personalBoard[i][j]);
            }
            System.out.println();
        }
    }

}
