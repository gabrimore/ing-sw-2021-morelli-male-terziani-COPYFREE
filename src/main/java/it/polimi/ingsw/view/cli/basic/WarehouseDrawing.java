package it.polimi.ingsw.view.cli.basic;

import it.polimi.ingsw.view.cli.Utilities;
import it.polimi.ingsw.view.cli.CliAdapter;
import it.polimi.ingsw.view.cli.complete.CompleteDrawing;

public class WarehouseDrawing implements BasicDrawing, CompleteDrawing {

    public static final int HEIGHT = (ShelfDrawing.HEIGHT-1)*3 +2;
    public static final int LENGTH = ShelfDrawing.LENGTH*3+ (ShelfDrawing.LENGTH+1)*4;

    public static char[][] warehouse;
    private ShelfDrawing s1;
    private ShelfDrawing s2;
    private ShelfDrawing s3;
    private ShelfDrawing extraShelf1;
    private ShelfDrawing extraShelf2;

    private CliAdapter cliAdapter;


    public WarehouseDrawing(CliAdapter cliAdapter){
        this.cliAdapter=cliAdapter;
        this.warehouse=new char[HEIGHT][LENGTH];
        this.s1=new ShelfDrawing(1, 0, cliAdapter);
        this.s2=new ShelfDrawing(2, 1, cliAdapter);
        this.s3=new ShelfDrawing(3, 2, cliAdapter);
        this.extraShelf1= new ShelfDrawing(2, 3, cliAdapter);
        this.extraShelf2= new ShelfDrawing(2, 4, cliAdapter);
    }

    /**
     * draws the Warehouse on a 2dimensional array
     * @param base 2dimensional characters array to be modified
     * @param y_position row of the 2dimensional array where the modification starts
     * @param x_position column of the 2dimensional array where the modification starts
     */
    public void draw(char[][] base, int y_position, int x_position){
        for(int i=0; i<HEIGHT-1; i++){
            for(int j=0; j<LENGTH; j++){
                warehouse[i][j]= ' ';
            }
        }
        s1.draw(base, y_position, (LENGTH-extraShelf1.getEffectiveLength()-extraShelf2.getEffectiveLength())/2-(s1.getEffectiveLength()/2)-1+x_position);
        Utilities.drawChar(base, '1', y_position+1, (LENGTH-extraShelf1.getEffectiveLength()-extraShelf2.getEffectiveLength())/2+(s1.getEffectiveLength()/2)+x_position);

        s2.draw(base, ShelfDrawing.HEIGHT-1+y_position, (LENGTH-extraShelf1.getEffectiveLength()-extraShelf2.getEffectiveLength())/2-(s2.getEffectiveLength()/2)-1+x_position);
        Utilities.drawChar(base, '2', ShelfDrawing.HEIGHT+y_position, (LENGTH-extraShelf1.getEffectiveLength()-extraShelf2.getEffectiveLength())/2+(s2.getEffectiveLength()/2)+x_position);

        s3.draw(base, ShelfDrawing.HEIGHT+ ShelfDrawing.HEIGHT-2+y_position, (LENGTH-extraShelf1.getEffectiveLength()-extraShelf2.getEffectiveLength())/2-(s3.getEffectiveLength()/2)-1+x_position);
        Utilities.drawChar(base, '3', ShelfDrawing.HEIGHT+ ShelfDrawing.HEIGHT-1+y_position, (LENGTH-extraShelf1.getEffectiveLength()-extraShelf2.getEffectiveLength())/2+(s3.getEffectiveLength()/2)+x_position);

        if(cliAdapter.checkExtraShelf(3)) {
            extraShelf1.draw(base, y_position, s3.getEffectiveLength()+x_position);
            Utilities.drawChar(base, '4', y_position+1, s3.getEffectiveLength()+extraShelf1.getEffectiveLength()+1+x_position);
        }
        if(cliAdapter.checkExtraShelf(4)) {
            extraShelf2.draw(base, +y_position, s3.getEffectiveLength()+extraShelf1.getEffectiveLength()+2+x_position);
            Utilities.drawChar(base, '5', y_position+1, s3.getEffectiveLength()+extraShelf1.getEffectiveLength()+extraShelf2.getEffectiveLength()+3+x_position);
        }

        Utilities.stringToChar(base, HEIGHT-1+y_position, s3.getEffectiveLength()/2-"WAREHOUSE".length()/2 +1+x_position, "WAREHOUSE");
    }

    /**
     * draws the Warehouse
     */
    public void draw(){
        draw(warehouse, 0, 0);
    }

    /**
     * prints the Warehouse
     */
    public void printElement() {
        for (int i = 0; i < warehouse.length; i++) {
            for (int j = 0; j < warehouse[0].length; j++) {
                System.out.print(warehouse[i][j]);
            }
            System.out.println();
        }
    }
}
