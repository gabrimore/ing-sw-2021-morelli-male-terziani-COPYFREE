package it.polimi.ingsw.model.player.faithtrack;

import com.google.gson.annotations.Expose;

/**
 * Faith track class
 */
public class FaithTrack {

    @Expose
    private int position;
    @Expose
    private final Tile[] tiles;
    @Expose
    private final PopeSpaceTile[] popeTiles;
    @Expose
    private int popePoint;


    public FaithTrack() {
        this.position = 0;
        this.tiles = new Tile[] {
                new Tile(0),new Tile(0),new Tile(0),new Tile(1),
                new Tile(1),new Tile(1),new Tile(2),new Tile(2),
                new Tile(2),new Tile(4),new Tile(4),new Tile(4),
                new Tile(6),new Tile(6),new Tile(6),new Tile(9),
                new Tile(9),new Tile(9),new Tile(12),new Tile(12),
                new Tile(12),new Tile(16),new Tile(16),new Tile(16),
                new Tile(20)};
        this.popeTiles = new PopeSpaceTile[] {
                new PopeSpaceTile(2, 5),
                new PopeSpaceTile(3, 12),
                new PopeSpaceTile(4, 19)
        };
        this.popePoint = 0;
    }

    /**
     *
     * @return player's position
     */
    public int getPosition() {
        return position;
    }


    /**
     *
     * @return all point earned in the Faith Track during the game
     */
    public int getPoint() {
        return tiles[position].getVictoryPoint() + popePoint;
    }

    public int getPopePoint() {
        return popePoint;
    }

    public PopeSpaceTile[] getPopeTiles() {
        return popeTiles;
    }

    /**
     * Increase player position by one
     * @throws VaticanReportException when player reaches a Pope Tile
     */
    public void setPosition() throws VaticanReportException {
        if(position < 24) {
            position++;
        }
        if (position == 8 || position == 16 || position == 24){
            int i = position / 8;
            if (!popeTiles[i-1].isDiscarded()) {
                popePoint = popePoint + popeTiles[i-1].getPopePoint();
                popeTiles[i-1].discard();
                throw new VaticanReportException(i-1);
            }
        }
    }

    /**
     * Check if a player is in a Vatican Report Zone
     * @param pope Index of Pope Space that invoked the Vatican Report
     */
    public void vaticanReport (int pope){
        if (position >= popeTiles[pope].getVaticanReportSection()){
            popePoint = popePoint + popeTiles[pope].getPopePoint();
        }
        popeTiles[pope].discard();
    }
}
