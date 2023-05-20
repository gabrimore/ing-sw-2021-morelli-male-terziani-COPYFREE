package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.deck.carddecks.DevelopmentCard;
import it.polimi.ingsw.model.deck.carddecks.leadercards.ExtraSlot;
import it.polimi.ingsw.model.deck.carddecks.leadercards.LeaderCard;
import it.polimi.ingsw.model.deck.carddecks.leadercards.WhiteMarbleChanger;
import it.polimi.ingsw.model.game.Double;
import it.polimi.ingsw.model.game.MarketBoard;
import it.polimi.ingsw.model.player.warehouse.Resources;
import it.polimi.ingsw.model.player.warehouse.Shelf;
import it.polimi.ingsw.model.player.warehouse.Warehouse;
import it.polimi.ingsw.view.LocalModel;

import java.util.ArrayList;

public class GuiAdapter {

    private LocalModel localModel;
    private ViewManager view;
    private boolean baseProduction = true;


    public GuiAdapter(LocalModel localModel) {
        this.localModel = localModel;
    }


    public boolean getLobbySize() {
        return localModel.getLobbySize();
    }

    public String getCurrentPlayer() {
        return localModel.getCurrentPlayer();
    }

    public ArrayList<String> getCardUrls(int deckChoice, int column) {
        ArrayList<String> urls = new ArrayList<>();

        switch (deckChoice) {
            case 1://fatto
                for (int i = 0; i < localModel.getDeckField().length; i++) {
                    if (localModel.getDeckField()[i][column] != null) {
                        urls.add(localModel.getDeckField()[i][column].getImageURL());
                    }
                }
                break;

            case 2://fatto
                for (int i = 0; i < localModel.getDevSpace().length && localModel.getDevSpace()[column][i] != null; i++) {
                    urls.add(localModel.getDevSpace()[column][i].getImageURL());
                }
                break;

        }
        return urls;
    }

    public ArrayList<String> getCardUrls(int deckChoice) {
        ArrayList<String> urls = new ArrayList<>();

        switch (deckChoice) {

            case 3://fatto
                for (LeaderCard lc : localModel.getInitialLeaderCardChoice()) {
                    urls.add(lc.getImageURL());
                }
                break;

            case 4://fatto, manca parte extraShelf
                for (LeaderCard lc : localModel.getActiveLeaderCards()) {
                    urls.add(lc.getImageURL());
                }
                break;

            case 5://fatto
                for (LeaderCard lc : localModel.getHiddenLeaderCards()) {
                    urls.add(lc.getImageURL());
                }
                break;

            case 6://fatto
                for (DevelopmentCard dc : localModel.getPossibleDevCard()) {
                    urls.add(dc.getImageURL());
                }
                break;

            case 7:
                for (LeaderCard lc : localModel.getActiveLeaderCards()) {
                    if (lc instanceof ExtraSlot) {
                        urls.add(lc.getImageURL());
                    }
                }
                break;

            case 8:
                for (LeaderCard lc : localModel.getActiveLeaderCards()) {
                    if (lc instanceof WhiteMarbleChanger) {
                        urls.add(lc.getImageURL());
                    }
                }
                break;

            case 9:
                for (LeaderCard lc : localModel.getExtraProductionLeaderCard()) {
                    urls.add(lc.getImageURL());
                }
                break;

        }

        return urls;
    }

    public int indexOfExtraSlot(String url) {
        for (int i = 0; i < localModel.getActiveLeaderCards().size(); i++) {

            LeaderCard lc = localModel.getActiveLeaderCards().get(i);

            if (lc instanceof ExtraSlot && lc.getImageURL().equals(url)) {
                return i;
            }
        }
        return -1;
    }

    public boolean checkHiddenLeadCardState(int index) {
        if (index < localModel.getHiddenLeaderCards().size()) {
            return localModel.getHiddenLeaderCards().get(index).getState();
        } else return false;
    }

    public boolean isNotReady() {
        return localModel.isNotReady();
    }

    public int getMyTurn() {
        return localModel.getMyTurn();
    }

    public MarketBoard.Marble getMarble(int i, int j) {
        if (i == 4 && j == 4) {
            return (localModel.getExtraMarble());
        } else {
            return (localModel.getMarketBoard()[i][j]);
        }
    }


    //////////PRODUCTION///////////

    public ArrayList<Resources> getProduced() {
        return localModel.getProduced();
    }

    public ArrayList<Resources> getAvailable() {
        return localModel.getAvailable();
    }

    public boolean getIsBaseProduction() {
        return baseProduction;
    }

    public void setBaseProduction(boolean baseProduction) {
        this.baseProduction = baseProduction;
    }

    public ArrayList<Resources> getExtraInput() {
        return localModel.getExtraInput();
    }

    public ArrayList<Integer> getLeadCardProdUsed() {
        return localModel.getLeadCardProdUsed();
    }

    public ArrayList<Integer> getDevCardProdUsed() {
        return localModel.getDevCardProdUsed();
    }

    public int getFaithPointEarned() {
        return localModel.getFaithPointEarned();
    }


    //////////DEVSPACE/////////////

    public ArrayList<Integer> getDevSpaceColumnsWithCards() {
        ArrayList<Integer> notEmptyColumns = new ArrayList<>();
        for (int columnIndex = 0; columnIndex < 3; columnIndex++) {
            if (localModel.getDevSpace()[columnIndex][0] != null)
                notEmptyColumns.add(columnIndex);

        }
        return notEmptyColumns;
    }

    public boolean checkLastCardColumnProdState(int column) {

        if (getDevSpaceColumnsWithCards().contains(column)) {
            for (int i = 2; i >= 0; i--) {
                if (localModel.getDevSpace()[column][i] != null) {
                    return localModel.getDevSpace()[column][i].getState();
                }
            }
        }
        return false;
    }

    public int devSpaceNumberOfCards(int column) {
        return localModel.getDevSpace()[column].length;
    }

    public ArrayList<Integer> getPossibleDevSpaceColumns() {
        return localModel.getPossibleDevSpaceColumn();
    }


    /////////SHELF////////////////

    public Shelf getShelf(int i) {
        return localModel.getShelves()[i];
    }

    public boolean isPossible(String resource, int i) {
        return localModel.isPossible(resource, i);
    }


    //////////STRONGBOX/////////////////////

    public int getStrongboxQuantities(Resources.ResType resType) {
        int quantity = 0;
        if (localModel.getStrongBox().size() != 0) {
            for (int i = 0; /*localModel.getStrongBox().get(i).getResourceType() != resType && */ i < localModel.getStrongBox().size(); i++) {
                if (localModel.getStrongBox().get(i).getResourceType() == resType) {
                    quantity = localModel.getStrongBox().get(i).getCounter();
                }
            }
        }
        return quantity;
    }

    public boolean checkAvailability(ArrayList<Resources> input) {

        int check = 0;

        ArrayList<Resources> allResources = getAvailable();
        int indexOfResource;


        for (Resources resUse : input) {
            indexOfResource = Warehouse.extractIndex(resUse.getResourceType(), allResources);
            if (indexOfResource != -1)
                if (resUse.getCounter() <= allResources.get(indexOfResource).getCounter()) check++;
        }

        return check == input.size();

    }


    /////////SHELF////////////////

    public ArrayList<Double<String, Integer>> getPositions() {
        return localModel.getPosition();
    }

    public int getMyPosition() {

        int position = 0;

        for (Double d : getPositions()) {
            if (d.getName().equals(localModel.getNickname())) {
                position = (int) d.getPosition();
            }
        }

        return position;
    }


    public void setView(ViewManager viewManager) {
        this.view = viewManager;
    }

    public ViewManager getView() {
        return this.view;
    }


    //////POPETILES////////

    public boolean checkPopeTile(int i) {

        int point = localModel.getPopePoint();

        if (point == 9) {
            return true;
        }

        if (point == 0 && localModel.getPopeTiles()[i].isDiscarded()) {
            return false;
        } else if (point == 0 && !(localModel.getPopeTiles()[i].isDiscarded())) {
            return true;
        }

        switch (i) {

            case 0:
                if ((point == 2 || point == 5 || point == 6)) {
                    return true;
                }

            case 1:
                if ((point == 3 || point == 5 || point == 7)) {
                    return true;
                }

            case 2:
                if ((point == 4 || point == 6 || point == 7)) {
                    return true;
                }
        }
        return true;
    }

    public ArrayList<String> getListOfPlayers() { return localModel.getUsedNames();}


}