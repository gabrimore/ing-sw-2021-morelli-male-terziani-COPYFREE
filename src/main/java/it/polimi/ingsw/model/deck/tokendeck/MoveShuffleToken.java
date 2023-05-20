package it.polimi.ingsw.model.deck.tokendeck;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.deck.carddecks.leadercards.AdditionalProduction;
import it.polimi.ingsw.model.deck.carddecks.leadercards.WhiteMarbleChanger;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.Player;

import java.io.*;
import java.util.ArrayList;

public class MoveShuffleToken extends SoloActionToken {

    /**
     * calls the method to move Lorenzo il Magnifico and the method to remake the token deck
     * @param pl
     * @param g
     */
    public void effect(Player pl, Game g){

        pl.moveLorenzo();

        g.remakeTokenDeck();

    }

    /**
     * static method to read a file of tokens of type "Move and Shuffle" and create an ArrayList of SoloActionToken
     * @param path path of the file
     * @return ArrayList of SoloActionToken read from file
     */
    public static ArrayList<SoloActionToken> createMoveShuffleTokenDeck(String path){
        Gson gson = new Gson();
        ArrayList<SoloActionToken> moveShuffleTokens = new ArrayList<>();
        BufferedReader buffReader = new BufferedReader(new InputStreamReader(MoveShuffleToken.class.getResourceAsStream(path)));
        moveShuffleTokens=gson.fromJson(buffReader,new TypeToken<ArrayList<MoveShuffleToken>>(){}.getType());
        return moveShuffleTokens;
    }

    public String toString(){
        return "LORENZO MOVES FORWARD. THE TOKEN DECK IS SHUFFLED AGAIN.";
    }

}
