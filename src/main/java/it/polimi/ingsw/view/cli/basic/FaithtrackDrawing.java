package it.polimi.ingsw.view.cli.basic;

import it.polimi.ingsw.model.game.Double;
import it.polimi.ingsw.view.cli.Utilities;
import it.polimi.ingsw.view.cli.CliAdapter;

public class FaithtrackDrawing implements BasicDrawing {

    public static final int HEIGHT=(TileDrawing.HEIGHT)*3+2;
    public static final int LENGTH=(TileDrawing.LENGTH)*19;
    private static final int NUMBEROFTILES=24;

    public char[][] faithTrack;
    private TileDrawing tile;
    private CliAdapter cliAdapter;

    public FaithtrackDrawing(CliAdapter cliAdapter){
        this.faithTrack= new char[HEIGHT][LENGTH];
        this.tile=new TileDrawing();
        this.cliAdapter=cliAdapter;
    }

    /**
     * draws the Faithtrack
     * @param base 2dimensional characters array to be modified
     * @param y_position row of the 2dimensional array where the modification starts
     * @param x_position column of the 2dimensional array where the modification starts
     */
    public void draw(char[][] base, int y_position, int x_position){
        int[] firstseriesoftiles={0, 2};
        int[] secondseriesoftiles={4, 9};
        int[] thirdseriesoftiles={11, 16};
        int[] fourthseriesoftiles={18, 24};
        int[] secondlinetiles={3, 10, 17};

        for(int z=0; z<=NUMBEROFTILES; z++) {
            tile.setNumberOfTile(z);
            if (z >= firstseriesoftiles[0] && z <= firstseriesoftiles[1]) {
                tile.draw(base,  2* TileDrawing.HEIGHT + y_position, x_position + z * TileDrawing.LENGTH);
            } else if (z >= secondseriesoftiles[0] && z<=secondseriesoftiles[1]) {
                tile.draw(base, y_position, x_position + ((z-2) * TileDrawing.LENGTH));
            }else if (z>= thirdseriesoftiles[0] && z<= thirdseriesoftiles[1]){
                tile.draw(base, 2* TileDrawing.HEIGHT + y_position, x_position + ((z-4) * TileDrawing.LENGTH));
            }else if (z>= fourthseriesoftiles[0] && z<=fourthseriesoftiles[1]){
                tile.draw(base, + y_position, x_position + ((z-6) * TileDrawing.LENGTH));
            }else if (z == secondlinetiles[0]) {
                tile.draw(base,  TileDrawing.HEIGHT + y_position, x_position + ((z-1) * TileDrawing.LENGTH));
            }else if (z == secondlinetiles[1]){
                tile.draw(base, TileDrawing.HEIGHT + y_position, x_position + ((z-3) * TileDrawing.LENGTH));
            }else if(z == secondlinetiles[2]) {
                tile.draw(base, TileDrawing.HEIGHT + y_position, x_position + ((z - 5) * TileDrawing.LENGTH));
            }
        }
        drawPopeSpacePoints(base, y_position, x_position );
        drawPlayersPosition(base, y_position + HEIGHT-1, x_position +5);
    }

    /**
     * draws the symbols of the pope space points
     * @param base 2dimensional character array on which to draw
     * @param y_position row of the 2dimensional array where the card starts
     * @param x_position column of the 2dimensional array where the card starts
     */
    private void drawPopeSpacePoints(char[][] base, int y_position, int x_position){
        Utilities.drawNumber(2, base, TileDrawing.HEIGHT + y_position-1, 4 * TileDrawing.LENGTH + x_position);
        Utilities.drawNumber(3, base, y_position, 9* TileDrawing.LENGTH +2+ x_position);
        Utilities.drawNumber(4, base, TileDrawing.HEIGHT + y_position-1, 17 * TileDrawing.LENGTH + x_position);

    }

    /**
     * writes players' positions
     * @param base 2dimensional character array on which to draw
     * @param y_position row of the 2dimensional array where the card starts
     * @param x_position column of the 2dimensional array where the card starts
     */
    public void drawPlayersPosition(char[][] base, int y_position, int x_position){
        int i=0;
        Utilities.stringToChar(base, y_position, x_position, "PLAYER'S POSITION");
        for(Double pos : cliAdapter.getPositions()){
            Utilities.stringToChar(base, y_position, x_position+15*(i+2), pos.getName().toString());
            Utilities.intToChar(base, y_position, x_position+8+15*(i+2),(int)(pos.getPosition()) );
            i++;
        }
    }
}
