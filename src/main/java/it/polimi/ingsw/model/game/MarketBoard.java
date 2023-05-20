package it.polimi.ingsw.model.game;
import com.google.gson.annotations.Expose;
import it.polimi.ingsw.network.messages.MarketMessage;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observer.Observable;

import java.io.Serializable;
import java.util.*;

/**
 * Market Board class
 */

public class MarketBoard extends Observable<Message> implements Serializable {


    public enum Marble {
        @Expose
        SHIELD("/images/marbles/blueMrbl.png"),
        @Expose
        COIN("/images/marbles/yellowMrbl.png"),
        @Expose
        STONE("/images/marbles/blackMrbl.png"),
        @Expose
        SERVANT("/images/marbles/violetMrbl.png"),
        @Expose
        RED("/images/marbles/redMrbl.png"),
        @Expose
        WHITE("/images/marbles/whiteMrbl.png");

        private final String imageUrl;

        Marble(String imageUrl){
            this.imageUrl=imageUrl;
        }

        public String getImageUrl(){
            return this.imageUrl;
        }

        public String getType(){
            return this.toString();
        }


    }

    @Expose
    private final Marble[][] structure;
    @Expose
    private Marble extraMarble;

    public MarketBoard() {
        int k = 0;
        Marble[] arr = new Marble[]{Marble.WHITE,
                Marble.WHITE,Marble.WHITE,Marble.WHITE,Marble.SHIELD,
                Marble.SHIELD,Marble.STONE,Marble.STONE,Marble.COIN,
                Marble.COIN,Marble.SERVANT,Marble.SERVANT,Marble.RED,};
        List<Marble> stringList = Arrays.asList(arr);
        Collections.shuffle(stringList);
        stringList.toArray(arr);
        structure = new Marble[3][4];
        for(int i = 0; i<3; i++) {
            for (int j = 0; j<4; j++) {
                structure[i][j] = arr[k];
                k++;
            }
        }
        this.extraMarble = arr[12];
    }

    /**
     * Action performed by player. Return a list of marbles and update the market using the extramarble
     * @param i chosen row index
     * @return list of marbles contained in the chosen row
     */
    public List<Marble> chooseRow (int i){
        List<Marble> chosen = new ArrayList<Marble>(Arrays.asList(structure[i]));
        Marble[] updated = {structure[i][1], structure[i][2], structure[i][3], extraMarble};
        extraMarble = structure[i][0];
        structure[i] = updated.clone();

        notify(new MarketMessage(this));

        return chosen;
    }

    /**
     * Action performed by player. Return a list of marbles and update the market using the extramarble
     * @param i chosen column index
     * @return list of marbles contained in the chosen column
     */
    public List<Marble> chooseColumn (int i){
        Marble[] chosenMarbles = {structure[0][i], structure[1][i], structure[2][i]};
        List<Marble> chosen = new ArrayList<Marble>(Arrays.asList(chosenMarbles));
        Marble[] updated = {structure[1][i], structure[2][i], extraMarble};
        extraMarble = structure[0][i];
        for (int j = 0; j<3; j++){
            structure[j][i] = updated[j];
        }

        notify(new MarketMessage(this));

        return chosen;
    }

    /**
     * @return the extramarble
     */
    public Marble getExtraMarble(){
        return extraMarble;
    }

    public Marble getMarble(int i, int j){
        return structure[i][j];
    }

    public Marble[][] getStructure() { return structure; }

}
