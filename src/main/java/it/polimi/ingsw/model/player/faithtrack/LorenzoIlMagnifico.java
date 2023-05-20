package it.polimi.ingsw.model.player.faithtrack;


import com.google.gson.annotations.Expose;

public class LorenzoIlMagnifico{

    @Expose
    private int position;
    @Expose
    private final PopeSpaceTile[] popeTiles;

    public LorenzoIlMagnifico() {
        this.position = 0;
        this.popeTiles = new PopeSpaceTile[] {
                new PopeSpaceTile(2, 5),
                new PopeSpaceTile(3, 12),
                new PopeSpaceTile(4, 19)};
    }

    public int getPosition() {
        return position;
    }

    /**
     * Increase Lorenzo's position by one
     * @throws VaticanReportException when Lorenzo reaches a Pope Tile
     */
    public void setPosition() throws VaticanReportException {
        position++;
        if (position == 8 || position == 16 || position == 24){
            int i = position / 8;
            if (!popeTiles[i-1].isDiscarded()) {
                popeTiles[i-1].discard();
                throw new VaticanReportException(i-1);
            }
        }
    }

    /**
     * Discard Lorenzo's Pope Tile
     * @param pope Index of Pope Space that invoked the Vatican Report
     */
    public void vaticanReport(int pope){
        popeTiles[pope].discard();
    }
}
