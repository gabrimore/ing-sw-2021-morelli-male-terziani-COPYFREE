package it.polimi.ingsw.view;

import it.polimi.ingsw.model.deck.carddecks.DevelopmentCard;
import it.polimi.ingsw.model.deck.carddecks.leadercards.AdditionalProduction;
import it.polimi.ingsw.model.deck.carddecks.leadercards.LeaderCard;
import it.polimi.ingsw.model.game.MarketBoard;
import it.polimi.ingsw.model.game.Double;
import it.polimi.ingsw.model.player.faithtrack.PopeSpaceTile;
import it.polimi.ingsw.model.player.warehouse.Resources;
import it.polimi.ingsw.model.player.warehouse.Shelf;
import it.polimi.ingsw.model.player.warehouse.Warehouse;
import it.polimi.ingsw.view.gui.ViewManager;

import java.util.ArrayList;
import java.util.Arrays;

public class LocalModel {

    private ArrayList<String> usedNames;

    private ArrayList<LeaderCard> initialLeaderCardChoice;
    private ArrayList<LeaderCard> activeLeaderCards, hiddenLeaderCards;
    private ArrayList<Resources> extraInput;

    private DevelopmentCard[][] deckField;
    private DevelopmentCard[][] devSpace;

    private ArrayList<DevelopmentCard> possibleDevCard;
    private ArrayList<Integer> possibleDevSpaceColumn;

    private ArrayList<Resources> strongBox;
    private Shelf[] shelves;

    private MarketBoard.Marble[][] marketBoard;
    private MarketBoard.Marble extraMarble;

    private ArrayList<Double<String, Integer>> positions;
    private PopeSpaceTile[] popeTiles;
    private int popePoint;

    private ArrayList<Resources> produced;
    private ArrayList<Resources> available;
    private int faithPointEarned;
    private ArrayList<Integer> devCardProdUsed;
    private ArrayList<Integer> leadCardProdUsed;

    private String nickname;
    private boolean isNotReady = true;
    private int myTurn;
    private String currentPlayer;

    private int cliOrGui;
    private ViewManager view;
    private boolean lobbySize;


    public LocalModel(){
        ArrayList<Resources.ResType> allowedResTypes = new ArrayList<>(Arrays.asList(Resources.ResType.COIN, Resources.ResType.SERVANT, Resources.ResType.SHIELD, Resources.ResType.STONE));

        shelves = new Shelf[5];
        shelves[0]= new Shelf(1, allowedResTypes);
        shelves[1]= new Shelf(2, allowedResTypes);
        shelves[2]= new Shelf(3, allowedResTypes);
        shelves[3] = null;
        shelves[4] = null;

        this.usedNames = new ArrayList<>() ;
        this.strongBox = new ArrayList<>();
        this.initialLeaderCardChoice = new ArrayList<>();
        this.activeLeaderCards = new ArrayList<>();
        this.hiddenLeaderCards = new ArrayList<>();
        this.possibleDevCard = new ArrayList<>();
        this.devSpace = new DevelopmentCard[3][3];
        this.positions = new ArrayList<>();
        this.popeTiles = new PopeSpaceTile[] {
                new PopeSpaceTile(2, 5),
                new PopeSpaceTile(3, 12),
                new PopeSpaceTile(4, 19)
        };

        this.produced = new ArrayList<>();
        this.available = new ArrayList<>();
        this.extraInput = new ArrayList<>();
        devCardProdUsed= new ArrayList<>();
        leadCardProdUsed= new ArrayList<>();
    }


    public boolean isPossible(String resource, int i){

        switch (resource) {

            case "STONE":
                return shelves[i].isPossible(new Resources(Resources.ResType.STONE, 1));

            case "COIN":
                return shelves[i].isPossible(new Resources(Resources.ResType.COIN, 1));

            case "SHIELD":
                return shelves[i].isPossible(new Resources(Resources.ResType.SHIELD, 1));

            case "SERVANT":
                return shelves[i].isPossible(new Resources(Resources.ResType.SERVANT, 1));

            default:
                return false;
        }
    }

    /////////// SETTERS

    public void setLobbySize(boolean chooseLobbySize){ lobbySize = chooseLobbySize; }

    public void setStrongBox(ArrayList<Resources> strongBox) { this.strongBox = strongBox; }

    public void setMarketBoard(MarketBoard.Marble[][] marketBoard) { this.marketBoard = marketBoard; }

    public void setExtraMarble(MarketBoard.Marble extraMarble) { this.extraMarble=extraMarble;}

    public void setShelves(ArrayList<Shelf> shelves) {
        this.shelves[0] = shelves.get(0);
        this.shelves[1] = shelves.get(1);
        this.shelves[2] = shelves.get(2);
        if(this.shelves[3] == null && shelves.get(3) != null){
            this.shelves[3] = shelves.get(3);
        }else if(this.shelves[3] != null && shelves.get(3) != null){
            this.shelves[3] = shelves.get(3);
        }
        if(this.shelves[4] == null && shelves.get(4) != null){
            this.shelves[4] = shelves.get(4);
        }else if(this.shelves[4] != null && shelves.get(4) != null){
            this.shelves[4] = shelves.get(4);
        }
    }

    public void setInitialLeaderCardChoice(ArrayList<LeaderCard> initialLeaderCardChoice) {
        this.initialLeaderCardChoice = initialLeaderCardChoice;
    }

    public void setActiveHiddenLeaderCards(ArrayList<LeaderCard> activeLeaderCards, ArrayList<LeaderCard> hiddenLeaderCards) {
        this.activeLeaderCards = activeLeaderCards;
        this.hiddenLeaderCards = hiddenLeaderCards;
    }

    public void setPosition(ArrayList<Double<String, Integer>> positions) { this.positions = positions; }

    public void setDeckField(DevelopmentCard[][] deckField) {this.deckField = deckField;}

    public void setDevSpace(DevelopmentCard[][] devSpace) { this.devSpace = devSpace; }

    public void setNotReady(boolean notReady) {
        isNotReady = notReady;
    }

    public void setMyTurn(int myTurn) {
        this.myTurn = myTurn;
    }

    public void setPopeTiles(PopeSpaceTile[] popeTiles) {
        this.popeTiles = popeTiles;
    }

    public void setPopePoint(int popePoint) {
        this.popePoint = popePoint;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPossibleDevSpaceColumn(ArrayList<Integer> possibleDevSpaceColumn) { this.possibleDevSpaceColumn = possibleDevSpaceColumn; }

    public void setPossibleDevCard(ArrayList<DevelopmentCard> possibleDevCard) { this.possibleDevCard = possibleDevCard; }

    public void setCurrentPlayer(String currentPlayer) { this.currentPlayer = currentPlayer; }

    public void setProductionResources(ArrayList<Resources> produced, ArrayList<Resources> available, int point){
        this.produced = produced;
        this.available = available;
        this.faithPointEarned = point;
    }

    public ArrayList<Integer> getDevCardProdUsed() {
        return devCardProdUsed;
    }

    public ArrayList<Integer> getLeadCardProdUsed() {
        return leadCardProdUsed;
    }

    public void setExtraInput (ArrayList<Resources> extra){
        this.extraInput = extra;
    }

    public void setCliOrGui(int cliOrGui) { this.cliOrGui = cliOrGui;}

    public void setView(ViewManager view) {
        this.view = view;
    }


    /////////// GETTERS////

    public ArrayList<String> getUsedNames() { return usedNames;}

     public ViewManager getView() { return view;}

    public int getCliOrGui() { return cliOrGui;}

    public ArrayList<Resources> getExtraInput(){ return extraInput;}

    public ArrayList<Resources> getProduced() { return produced;}

    public int getFaithPointEarned() { return faithPointEarned;}

    public ArrayList<Resources> getAvailable() { return available;}

    public ArrayList<Integer> getPossibleDevSpaceColumn() { return possibleDevSpaceColumn;}

    public String getNickname() { return nickname;}

    public PopeSpaceTile[] getPopeTiles() { return popeTiles;}

    public int getPopePoint() { return popePoint;}

    public int getMyTurn() { return myTurn;}

    public ArrayList<LeaderCard> getActiveLeaderCards() { return activeLeaderCards;}

    public ArrayList<LeaderCard> getExtraProductionLeaderCard(){
        ArrayList<LeaderCard> extra = new ArrayList<>();

        for (LeaderCard lc : activeLeaderCards){
            if(lc instanceof AdditionalProduction){
                extra.add(lc);
            }
        }
        return extra;
    }

    public boolean isNotReady() { return isNotReady;}

    public ArrayList<LeaderCard> getHiddenLeaderCards() { return hiddenLeaderCards; }

    public ArrayList<LeaderCard> getInitialLeaderCardChoice() { return initialLeaderCardChoice; }

    public Shelf[] getShelves(){ return this.shelves;}

    public ArrayList<Resources> getStrongBox() { return strongBox; }

    public MarketBoard.Marble[][] getMarketBoard() { return marketBoard; }

    public ArrayList<Double<String, Integer>> getPosition() { return positions; }

    public DevelopmentCard[][] getDeckField() { return deckField; }

    public MarketBoard.Marble getExtraMarble(){ return extraMarble;}

    public DevelopmentCard[][] getDevSpace() { return devSpace; }

    public ArrayList<DevelopmentCard> getPossibleDevCard() { return possibleDevCard; }

    public boolean getLobbySize() {
        return lobbySize;
    }

    public String getCurrentPlayer(){ return currentPlayer;}

    public void setDevCardProdUsed(ArrayList<Integer> devCardProdUsed) {
        this.devCardProdUsed = devCardProdUsed;
    }

    public void setLeadCardProdUsed(ArrayList<Integer> leadCardProdUsed) {
        this.leadCardProdUsed = leadCardProdUsed;
    }

    public void setUsedNames(ArrayList<String> usedNames) { this.usedNames = usedNames;}


}