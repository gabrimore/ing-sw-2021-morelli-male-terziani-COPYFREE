package it.polimi.ingsw.model.player;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.deck.carddecks.DevelopmentCard;
import it.polimi.ingsw.model.deck.carddecks.leadercards.LeaderCard;
import it.polimi.ingsw.model.player.faithtrack.FaithTrack;
import it.polimi.ingsw.model.player.faithtrack.LorenzoIlMagnifico;
import it.polimi.ingsw.model.player.faithtrack.VaticanReportException;
import it.polimi.ingsw.model.player.warehouse.*;
import java.util.ArrayList;
import java.util.List;
import it.polimi.ingsw.model.player.warehouse.Resources.ResType;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.messages.ActiveHiddenLeaderCardMessage;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.StandardMessage;
import it.polimi.ingsw.observer.Observable;


public class Player extends Observable<Message> {

    @Expose
    private final String nickname;
    @Expose
    private final boolean inkwell;
    @Expose
    private Warehouse warehouse;
    @Expose
    private final SlotDevelopment slotDevelopment;
    @Expose
    private final FaithTrack faithTrack;

    @Expose
    private final ArrayList<LeaderCard> activeLeaderCards, hiddenLeaderCards;
    @Expose
    private final ArrayList<Resources> whiteMarble, extraProductionInput;
    @Expose
    private final ArrayList<ResType> discount;

    @Expose
    private LorenzoIlMagnifico lorenzo;

    public Player(String nickname, boolean inkwell) {
        this.nickname = nickname;
        this.inkwell = inkwell;
        this.warehouse= new SimpleWarehouse();
        this.faithTrack = new FaithTrack();
        this.slotDevelopment = new SlotDevelopment();
        this.whiteMarble = new ArrayList<>();
        this.discount = new ArrayList<>();
        this.extraProductionInput = new ArrayList<>();
        this.activeLeaderCards = new ArrayList<>();
        this.hiddenLeaderCards = new ArrayList<>();
    }

    public String getNickname() {return nickname;}
    public Warehouse getWarehouse() {return warehouse;}
    public SlotDevelopment getSlotDevelopment() {return slotDevelopment;}
    public ArrayList<Resources> getWhiteMarble(){return whiteMarble;}
    public ArrayList<Resources> getExtraProductionInput() {return extraProductionInput;}
    public ArrayList<LeaderCard> getHiddenLeaderCards() {return hiddenLeaderCards;}
    public ArrayList<LeaderCard> getActiveLeaderCards() {return activeLeaderCards;}
    public ArrayList<ResType> getDiscount() {return discount;}
    public FaithTrack getFaithTrack() {return faithTrack;}

    /**
     * @return points earned during the game
     */
    public int countVictoryPoints(){
        int point = 0;
        for(LeaderCard c : activeLeaderCards){
            point += c.getVictoryPoint();
        }
        point += faithTrack.getPoint() + slotDevelopment.getPoints();

        int count = 0;
        for(Resources res : warehouse.allResources()){
            count += res.getCounter();
        }
        point += (count / 5);

        return point;
    }

    /**
     * Set a Development Card in the chosen column
     * @param c is a Development Card
     * @param cardPosition index of the chosen column
     */
    public void setDevCard (DevelopmentCard c, int cardPosition){
        slotDevelopment.setCard(c, cardPosition);

        if(!discount.isEmpty()){
            for(Resources res : c.getCost()){
                if(discount.contains(res.getResourceType())) res.setCounter(res.getCounter() - 1);
            }
        }

        warehouse.useResources(c.getCost());
    }

    /**
     * Activate a development card production
     * @param column index of the chosen development card
     * @return output list of the production
     */
    public List<Resources> startProduction(int column) throws VaticanReportException {
        ArrayList<Resources> output = new ArrayList<>();

        DevelopmentCard c = slotDevelopment.getTopCard(column);

        if(c != null && c.getState()) {
            warehouse.useResources(c.getInputResources());
            if (c.getOutputFaithPoint() > 0) {
                moveFaithMarker(c.getOutputFaithPoint());
            }
            output = c.getOutputResources();
            return output;
        }
        return null;
    }

    /**
     * Player's base production
     * @param input two input resources of any kind, chosen by player
     * @param output one output resource of any kind, chosen by player
     * @return output if the production is doable
     */
    public Resources startBaseProduction (ArrayList<Resources> input, Resources output){
        if(warehouse.checkAvailabilityResources(input)){
            warehouse.useResources(input);
            return output;
        }else{
            return null;
        }
    }

    /**
     *
     * @param i index of extraProductionInput list
     * @param output one output resource of any kind, chosen by player
     * @return output if the production is doable
     */
    public Resources startExtraProduction (int i, Resources output) throws VaticanReportException {
        ArrayList<Resources> input = new ArrayList<Resources>();
        input.add(extraProductionInput.get(i));
        if (warehouse.checkAvailabilityResources(input)){
            warehouse.useResources(input);
            moveFaithMarker(1);
            return output;
        }else{
            return null;
        }
    }

    /**
     * Set a new Leader card at the beginning of the game
     * @param c Leader Card chosen
     */
    public void addLeaderCard(LeaderCard c){
        hiddenLeaderCards.add(c);
        notify(new ActiveHiddenLeaderCardMessage(hiddenLeaderCards, activeLeaderCards));
    }

    /**
     * Moves a Leader card from Hidden list to Active List
     * @param i index of a Leader Card in HiddenLeaderCard
     * @return return false if the Leader Card cannot be activated
     */
    public boolean setActiveLeaderCard(int i){
        LeaderCard c = hiddenLeaderCards.get(i);
        if(c.getState()) {
            activeLeaderCards.add(c);
            hiddenLeaderCards.remove(i);
            notify(new ActiveHiddenLeaderCardMessage(hiddenLeaderCards, activeLeaderCards));
            return true;
        }else{
            return false;
        }
    }

    /**
     * Activate a special ability of an active leader card
     //* @param i index of active leader card
     */
    public int activateSpecialAbility(){
        activeLeaderCards.get(activeLeaderCards.size()-1).specialAbility(this);

        if(warehouse instanceof ExtraSlotWarehouse){
            return 1;
        }else if(warehouse instanceof SecondExtraSlotWarehouse){
            return 2;
        }
        return 0;
    }

    /**
     * Discard an hidden Leader Card and give a FaithPoint
     * @param i index of Leader Card
     */
    public void discardLeaderCard (int i) throws VaticanReportException {
        hiddenLeaderCards.remove(i);
        moveFaithMarker(1);
        notify(new ActiveHiddenLeaderCardMessage(hiddenLeaderCards, activeLeaderCards));
    }

    //Methods for specialAbility in LeaderCard

    public void setWhiteMarble (ResType res){
        whiteMarble.add(new Resources(res, 1));
    }
    public void setDiscount (ResType res){
        discount.add(res);
    }
    public void setExtraProduction(ResType res){
        extraProductionInput.add(new Resources(res, 1));
    }
    public void setExtraShelf(ResType res){

        if (warehouse instanceof ExtraSlotWarehouse) {
            warehouse = new SecondExtraSlotWarehouse(warehouse, res, warehouse.getShelf(3) );
        }
        else {
            warehouse = new ExtraSlotWarehouse(warehouse, res);
        }

    }

    /**
     * Set a resource in the given shelf
     * @param i index of a shelf
     * @param res resource to be stored
     * @return true if the resource can be set, false otherwise
     */
    public boolean setResourceInShelf(int i, Resources res){
        return warehouse.setResourceInShelf(i,res); // i=3 extraShelf, i=4 second extraShelf
    }

    /**
     * Set the resources gained during production in the Strongbox
     * @param production list of resources
     */
    public void setResourcesInStrongbox(List<Resources> production){
        for (Resources res : production){
            warehouse.setResourceInStrongBox(res);
        }
    }

    /**
     * Checks if player is in the vatican report zone
     * @param pope index of PopeSpaceTile
     */
    public void vaticanReport(int pope){
        faithTrack.vaticanReport(pope);
        notify(new StandardMessage("The "+ (pope+1) +"° Vatican Report has occurred! You earned a total of " + faithTrack.getPopePoint() + " Pope Point"));
    }

    /**
     * Increase player position
     * @param faithPoint faithPoints earned
     * @throws VaticanReportException when player reaches a Pope Tile
     */
    public void moveFaithMarker(int faithPoint) throws VaticanReportException {
        for (int i = 1; i<= faithPoint; i++){
            try {
                faithTrack.setPosition();
            }catch (VaticanReportException e) {
                if (lorenzo != null){
                    lorenzo.vaticanReport(e.getPope());
                }else{
                    throw e;
                }
            }
        }
    }

    /**
     * Start a solo Game
     */
    public void soloGame(){
        lorenzo = new LorenzoIlMagnifico();
    }

    /**
     * Increase Lorenzo's position
     */
    public void moveLorenzo(){
        try {
            lorenzo.setPosition();
        }catch (VaticanReportException e){
            vaticanReport(e.getPope());
            if(e.getPope() == 2){
                Server.setTypeOfEndgame(1);
                Server.setEndGame();
            }
        }
    }

    public int getLorenzoPosition(){
        return lorenzo.getPosition();
    }

    /**
     * Checks if Lorenzo is in the vatican report zone
     * @param pope index of PopeSpaceTile
     */
    public void lorenzoVaticanReport(int pope){
        lorenzo.vaticanReport(pope);
    }
}