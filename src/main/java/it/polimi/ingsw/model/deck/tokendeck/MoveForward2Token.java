package it.polimi.ingsw.model.deck.tokendeck;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.deck.carddecks.leadercards.AdditionalProduction;
import it.polimi.ingsw.model.deck.carddecks.leadercards.WhiteMarbleChanger;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.Player;

import java.io.*;
import java.util.ArrayList;

public class MoveForward2Token extends SoloActionToken {

    /**
     * calls two time the method to move Lorenzo il Magnifico
     * @param pl
     * @param g
     */
    public void effect(Player pl, Game g){

        pl.moveLorenzo();
        pl.moveLorenzo();

    }

    /**
     * static method to read a file of tokens of type "Move forward twice" and create an ArrayList of SoloActionToken
     * @param path path of the file
     * @return ArrayList of SoloActionToken read from file
     */
    public static ArrayList<SoloActionToken> createMoveForward2TokenDeck(String path){
        Gson gson = new Gson();
        ArrayList<SoloActionToken> moveForward2Tokens = new ArrayList<>();
        BufferedReader buffReader = new BufferedReader(new InputStreamReader(MoveForward2Token.class.getResourceAsStream(path)));
        moveForward2Tokens=gson.fromJson(buffReader,new TypeToken<ArrayList<MoveForward2Token>>(){}.getType());
        return moveForward2Tokens;
    }

    public String toString(){
        return "LORENZO MOVES FORWARD TWICE";
    }
}
