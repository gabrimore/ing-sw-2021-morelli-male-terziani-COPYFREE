package it.polimi.ingsw.model.deck.tokendeck;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.Player;

public abstract class SoloActionToken {

    public String getImageURL;
    private String imageURL;

    /**
     * effect to be called when the token has been drawn
     * @param pl
     * @param g
     */
    public abstract void effect(Player pl, Game g);

    public String getImageURL(){
        return this.imageURL;
    }
}
