package it.polimi.ingsw.model.player;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.deck.carddecks.ColorLevel;
import it.polimi.ingsw.model.deck.carddecks.DevelopmentCard;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.SlotDevMessage;
import it.polimi.ingsw.observer.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SlotDevelopment extends Observable<Message> {

    @Expose
    private final DevelopmentCard[][] devSpace;
    @Expose
    private int numOfCards = 0;

    public SlotDevelopment() {
        this.devSpace = new DevelopmentCard[3][3];
    }

    /**
     * Set a Development Card in the chosen column
     * @param devCard is a Development Card
     * @param column index of the chosen column
     */
    public void setCard (DevelopmentCard devCard, int column){
        int level = devCard.getColorLevel().getLevel() - 1;
        if(level>1) devSpace[column][level-1].setState(false);
        devSpace[column][level] = devCard;
        devSpace[column][level].setState(false);
        numOfCards++;
        notify(new SlotDevMessage(devSpace));
    }

    /**
     * Checks if Player can set a Development Card in the chosen column
     * @param devCard is a Development Card
     * @param column index of the chosen column
     * @return true if the card can be set, false if not
     */
    public boolean checkIfCanSetCard (DevelopmentCard devCard, int column){
        int level = devCard.getColorLevel().getLevel();

        switch (level) {
            case 1:
                if (devSpace[column][0] == null) return true;
                break;
            case 2:
                if (devSpace[column][0] != null && devSpace[column][1] == null) return true;
                break;
            case 3:
                if (devSpace[column][0] != null && devSpace[column][1] != null && devSpace[column][2] == null) return true;
                break;
        }
        return false;
    }


    /**
     * @param column index of the chosen column
     * @return the top card of the column if it exist, null if not
     */
    public DevelopmentCard getTopCard(int column) {
        for (int i = 2; i >= 0; i--) {
            if (devSpace[column][i] != null) {
                return devSpace[column][i];
            }
        }
        return null;
    }

    /**
     * Check which production can be activated
     * @param p Player to be checked
     */
    public void checkSlotDevTopCards(Player p){
        for(int i = 0; i<3; i++){
            if(getTopCard(i) != null){
                getTopCard(i).checkProduction(p);
            }
        }
    }

    /**
     * @return all the Victory Points in the slotDevelopment
     */
    public int getPoints(){
        int point = 0;
        for (int i = 0; i<= 2; i++){
            for (int j = 0; j<= 2; j++){
                if (devSpace[i][j] != null) {
                    point += devSpace[i][j].getVictoryPoint();
                }
            }
        }
        return point;
    }

    /**
     * Method used by Leader Card to check a requirement for activation
     * @param colorLevels is a list of ColorLevels required
     * @return true if the requirements are fulfilled, false if not
     */
    public boolean checkCard(ArrayList<ColorLevel> colorLevels){
        if(colorLevels.get(0).getLevel() > 1){
            for(int i = 0; i<=2; i++){
                if(devSpace[i][1]!= null && devSpace[i][1].getColorLevel().getColor().equals(colorLevels.get(0).getColor())){
                    return true;
                }
            }
            return false;
        }else{
            List<String> colors = colorLevels.stream().map(ColorLevel::getColor).collect(Collectors.toList());
            for(String s : colors){
                long k = colorLevels.stream().filter(c -> c.getColor().equals(s)).count();
                if(k > getColors(s)){
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Method used by checkCard
     * @param color is a Development Card color
     * @return the number of Development Cards of that color
     */
    private int getColors(String color){
        int k = 0;
        for (int i = 0; i<= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                if (devSpace[i][j] != null && devSpace[i][j].getColorLevel().getColor().equals(color)){
                    k++;
                }
            }
        }
        return k;
    }

    public DevelopmentCard[][] getDevSpace() {
        return devSpace;
    }

    public int getNumOfCards() {
        return numOfCards;
    }

    /**
     * Convert a Color to the corresponding Int.
     * The order is GREEN, BLUE, VIOLET, YELLOW as in the rules
     * @param color the color
     * @return the int associated to the color
     */
    public static int colorToInt(String color){
        switch (color)
        {
            case "GREEN":
                return 0;

            case "BLUE":
                return 1;

            case "VIOLET":
                return 2;

            case "YELLOW":
                return 3;

            default: return -3;
        }
    }
}
