package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.deck.carddecks.DevelopmentCard;
import it.polimi.ingsw.model.deck.carddecks.ColorLevel;
import it.polimi.ingsw.model.game.Double;
import it.polimi.ingsw.model.deck.carddecks.leadercards.*;
import it.polimi.ingsw.model.game.MarketBoard;
import it.polimi.ingsw.model.player.warehouse.Resources;
import it.polimi.ingsw.view.LocalModel;

import java.util.ArrayList;

public class CliAdapter {

    private final LocalModel localModel;

    public CliAdapter(LocalModel localModel){ this.localModel = localModel; }

    /**
     * writes the content of a shelf as an array of 3 characters
     * @param i index of the shelf
     * @return the array of characters representing the content of the shelf
     */
    public char[] getShelf(int i){

        if(localModel.getShelves()[i] == null){
            return new char[]{' ', ' ', ' '};
        }else {
            if(localModel.getShelves()[i].getShelfCurrentType() == null){
                return new char[]{' ', ' ', ' '};
            }else {
                return shelfContent(convertResTypeToSymbol(localModel.getShelves()[i].getShelfCurrentType()), localModel.getShelves()[i].getShelfNumberOfResources());
            }
        }
    }

    private char[] shelfContent(char type ,int count){
        char[] charray = new char[3];

        for (int i = 0; i < 3; i++) {
            if(count == 0){
                charray[i] = ' ';
            }else {
                charray[i] = type;
                count--;
            }
        }
        return charray;
    }

    /**
     * checks whether an extra shelf is null
     * @param i index of the extra shelf
     * @return true if extra shelf is not null
     */
    public boolean checkExtraShelf(int i){
        return localModel.getShelves()[i] != null;
    }

    /**
     * converts a resource type into a character symbol
     * @param type resource type
     * @return character representing the resource type
     */
    private char convertResTypeToSymbol(Resources.ResType type) {

        switch (type) {

            case STONE:
                return '⚱';

            case COIN:
                return '©';

            case SHIELD:
                return '♦';

            case SERVANT:
                return '۩';

            default:
                return 'e';
        }

    }

    /**
     *writes the marble of the market as an array of 3 characters
     * @param i row in the market (4 for the extraMarble)
     * @param j column in the market (4 for the extraMarble)
     * @return
     */
    public char[] getMarble(int i, int j){
        if(i==4 && j==4) {
            return new char[]{'(', convertMarbleToSymbol(localModel.getExtraMarble()), ')'};
        }else {
            return new char[]{'(', convertMarbleToSymbol(localModel.getMarketBoard()[i][j]), ')'};
        }
    }

    /**
     *converts a marble into a character symbol
     * @param marble marble
     * @return character representing the marble
     */
    private char convertMarbleToSymbol(MarketBoard.Marble marble){

        switch (marble) {

            case STONE:
                return '⚱';

            case COIN:
                return '©';

            case SHIELD:
                return '♦';

            case SERVANT:
                return '۩';

            case WHITE:
                return ' ';

            case RED:
                return '†';

            default:
                return 'e';
        }

    }

    /**
     * writes the content of a resource of the strongbox e as an array of 6 characters
     * @param i index of the resource inside the strongbox
     * @return array of character representing the resource in the strongbox
     */
    public char[] getResourceFromStrongbox(int i){
        char[] charray = new char[]{' ', ' ', ' ', ' ', ' ', ' '};
        Resources res;

        try{
            if(localModel.getStrongBox().isEmpty()) throw new IndexOutOfBoundsException();
            res = localModel.getStrongBox().get(i);

        }catch (IndexOutOfBoundsException e){
            return charray;
        }

            charray[0] = convertResTypeToSymbol(res.getResourceType());
            charray[2] = 'x';

            char[] local = Utilities.convertIntToCharArrayOfThree(res.getCounter());

            charray[3]=local[0];
            charray[4]=local[1];
            charray[5]=local[2];

       return charray;
    }



    public int getNumOfDevCard(int i){
        int count = 0;
        for(int k=0; k<3; k++){
            if(localModel.getDevSpace()[i][k] != null){
                count++;
            }
        }
        return count;
    }

    public int getNumOfPossibleDevCard(){ return localModel.getPossibleDevCard().size();}

    public int getNumActiveLeaderCard(){ return localModel.getActiveLeaderCards().size(); }

    public int getNumInactiveLeaderCard(){ return localModel.getHiddenLeaderCards().size();}

    public ArrayList<Double<String, Integer>> getPositions(){ return localModel.getPosition();}

    private ArrayList<char[]> emptyCard(){
        ArrayList<char[]> charray = new ArrayList<>();
        charray.add(new char[]{' ', ' ', ' '});
        charray.add(new char[]{' ', ' ', ' '});
        charray.add(new char[]{' ', ' ', ' '});
        charray.add(new char[]{' ', ' '});
        charray.add(new char[]{' ', ' ', ' '});
        charray.add(new char[]{' ', ' ', ' '});
        charray.add(new char[]{' ', ' ', ' '});
        charray.add(new char[]{' ', ' ', ' '});
        charray.add(new char[]{' ', ' ', ' '});
        charray.add(new char[]{' ', ' ', ' '});
        charray.add(new char[]{' ', ' ', ' '});
        return charray;
    }

    public ArrayList<char[]> getCard(int place, int i, int j){
        ArrayList<char[]> charray = new ArrayList<>();

        switch(place){

            case 1:
                DevelopmentCard fieldCard = localModel.getDeckField()[i][j];
                if (fieldCard == null){
                    charray = emptyCard();
                }else{
                    charray = createCharDevCard(fieldCard);
                }
                break;

            case 2:
                DevelopmentCard card = localModel.getDevSpace()[i][j];

                if(card == null){
                    charray = emptyCard();
                }else {
                    charray = createCharDevCard(card);
                }
                break;
        }

        return charray;
    }

    public ArrayList<char[]> getCard(int list, int i){
        ArrayList<char[]> charray = new ArrayList<>();

        switch(list){

            case 3:
                charray = createCharLeaderCard(localModel.getInitialLeaderCardChoice().get(i));
                return charray;

            case 4:
                charray = createCharLeaderCard(localModel.getActiveLeaderCards().get(i));
                return charray;

            case 5:
                charray = createCharLeaderCard(localModel.getHiddenLeaderCards().get(i));
                return charray;

            case 6:  //PossibleDevCards
                charray = createCharDevCard(localModel.getPossibleDevCard().get(i));
                return charray;
        }

        return charray;
    }

    private ArrayList<char[]> createCharDevCard(DevelopmentCard devCard){

        ArrayList<char[]> listOfCharray = new ArrayList<>();
        char[] charray = new char[]{' ', ' ', ' '};

        ////COST
        for (int i=0; i<3; i++){
            try {
                listOfCharray.add(getCharResource(devCard.getCost().get(i)));
            } catch (IndexOutOfBoundsException e){
                    listOfCharray.add(charray);
                }
        }

        ////VICTORY POINT
        char[] charrayDue = new char[]{' ', ' '};
        if( ((Integer)devCard.getVictoryPoint()).toString().length()==2) {
            charrayDue[0]= ((Integer)devCard.getVictoryPoint()).toString().charAt(0);
            charrayDue[1]= ((Integer)devCard.getVictoryPoint()).toString().charAt(1);
        }else{
            charrayDue[0]= ((Integer)devCard.getVictoryPoint()).toString().charAt(0);
        }
        listOfCharray.add(charrayDue);

        ////INPUT RESOURCES
        for (int i=0; i<2; i++){
            try {
                listOfCharray.add(getCharResource(devCard.getInputResources().get(i)));
            } catch (IndexOutOfBoundsException e){
                listOfCharray.add(charray);
            }
        }

        ////OUTPUT RESOURCES
        for (int i=0; i<3; i++){
            try {
                listOfCharray.add(getCharResource(devCard.getOutputResources().get(i)));
            } catch (IndexOutOfBoundsException e){
                listOfCharray.add(charray);
            }
        }

        ////FAITH POINT
            if(devCard.getOutputFaithPoint()==0) listOfCharray.add( new char[]{' '});
            else{
                listOfCharray.add(new char[]{( (Integer) devCard.getOutputFaithPoint() ).toString().charAt(0)});
            }

         ////COLOR LEVEL
        char[] colorLevel = getCharFromColorLevel(devCard.getColorLevel());

        if(colorLevel[3] == ' ') {
            colorLevel[3] = ((Integer)devCard.getColorLevel().getLevel()).toString().charAt(0);
        }

        listOfCharray.add(colorLevel);

        return listOfCharray;
    }

    private char[] getCharResource(Resources res){
        char[] charray = new char[]{' ', ' ', ' '};

            charray[0]= convertResTypeToSymbol(res.getResourceType());
            charray[1]='x';
            charray[2]= ( (Integer) res.getCounter() ).toString().charAt(0);

        return charray;
    }

    private ArrayList<char[]> createCharLeaderCard(LeaderCard lc){
        ArrayList<char[]> charray = new ArrayList<>();

        if(lc instanceof AdditionalProduction){

            charray.add( getCharFromColorLevel(lc.getRequirement().getColorLevelRequirement().get(0)));
            charray.add(new char[]{' ', ' ', ' ',' '});
            charray.add(new char[]{' ', ' ', ' ',' '});
            charray.add(Utilities.convertIntToCharArrayOfThree(4));
            charray.add(new char[]{convertResTypeToSymbol(((AdditionalProduction) lc).getAddProdResource()),' ','=','?',' ','†'});

        }else if(lc instanceof Discount){

            charray.add( getCharFromColorLevel(lc.getRequirement().getColorLevelRequirement().get(0)));
            charray.add( getCharFromColorLevel(lc.getRequirement().getColorLevelRequirement().get(1)));
            charray.add(new char[]{' ', ' ', ' ',' '});
            charray.add(Utilities.convertIntToCharArrayOfThree(2));
            charray.add(new char[]{'-','1',' ',convertResTypeToSymbol(((Discount) lc).getDiscountResource()),' ',' '});

        }else if(lc instanceof ExtraSlot){

            char type = convertResTypeToSymbol( (lc.getRequirement().getResourcesRequirement().get(0)).getResourceType());
            int counter = (lc.getRequirement().getResourcesRequirement().get(0)).getCounter();
            char resType = convertResTypeToSymbol(((ExtraSlot) lc).getExtraSlotResource());

            charray.add(new char[]{type, ' ', 'x', (char) (counter +'0')});
            charray.add(new char[]{' ', ' ', ' ',' '});
            charray.add(new char[]{' ', ' ', ' ',' '});
            charray.add(Utilities.convertIntToCharArrayOfThree(3));
            charray.add(new char[]{'|',resType,'|','|',resType,'|'});

        }else if(lc instanceof WhiteMarbleChanger){

            charray.add( getCharFromColorLevel(lc.getRequirement().getColorLevelRequirement().get(0)));
            charray.add( getCharFromColorLevel(lc.getRequirement().getColorLevelRequirement().get(1)));
            charray.add( getCharFromColorLevel(lc.getRequirement().getColorLevelRequirement().get(2)));
            charray.add(Utilities.convertIntToCharArrayOfThree(5));
            charray.add(new char[]{'(',' ',')',' ','=',convertResTypeToSymbol(((WhiteMarbleChanger) lc).getOutputResource())});

        }

        return charray;
    }

    private char[] getCharFromColorLevel(ColorLevel cl){
        char[] charray = new char[4];

        switch(cl.getColor()){

            case "GRN" :
                charray[0] = 'G';
                charray[1] = 'R';
                charray[2] = 'N';
                break;

            case "VLT" :
                charray[0] = 'V';
                charray[1] = 'L';
                charray[2] = 'T';
                break;

            case "BLU" :
                charray[0] = 'B';
                charray[1] = 'L';
                charray[2] = 'U';
                break;

            case "YLW" :
                charray[0] = 'Y';
                charray[1] = 'L';
                charray[2] = 'W';
                break;
            default:
                System.out.println("Default\n");
        }

        if(cl.getLevel() == 2) charray[3] = (char) (2 + '0');
        else charray[3] = ' ';

        return charray;
    }


}