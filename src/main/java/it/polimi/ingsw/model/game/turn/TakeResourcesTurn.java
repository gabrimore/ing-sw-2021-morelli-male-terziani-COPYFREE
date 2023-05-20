package it.polimi.ingsw.model.game.turn;

import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.controller.TakeResourcesController;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.MarketBoard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.faithtrack.VaticanReportException;
import it.polimi.ingsw.model.player.warehouse.Resources;
import static it.polimi.ingsw.model.player.warehouse.Resources.ResType.*;

import java.util.ArrayList;
import java.util.List;

public class TakeResourcesTurn implements Turn{

    private final TakeResourcesController takeResourcesController;
    private final Player player;
    private final MarketBoard marketBoard;


    public TakeResourcesTurn(TakeResourcesController takeResourcesController, Player player, Game game){
        this.takeResourcesController = takeResourcesController;
        this.player = player;
        this.marketBoard = game.getMarketBoard();
        begin();
    }

    /**
     * handle the communication between the controller and the part of the model involved with taking resources from the market
     */
    @Override
    public void begin(){

        ArrayList<Resources> resources = (ArrayList<Resources>) convertResourceFromMarket(retrieveRowColumn() == 1 ? marketBoard.chooseRow( takeResourcesController.chooseIndex())
                                                              : marketBoard.chooseColumn( takeResourcesController.chooseIndex() ) );

        takeResourcesController.queryPlayer(resources);

    }

    /**
     * invokes the method on the controller that ask to the to choose between row or column
     * @return an int that stands for: 1= row, 2= column
     */
    private int retrieveRowColumn(){
        //row =1, column =2
        return takeResourcesController.chooseRowColumn();
    }

    /**
     * Convert a list of Marbles in Resources or FaithPoint, depending on the color
     * @param marbles list of marbles
     * @return list of resources. Might be empty
     */
    private List<Resources> convertResourceFromMarket (List<MarketBoard.Marble> marbles){
        List<Resources> converted = new ArrayList<>();

        int white = 0;

        for(MarketBoard.Marble m : marbles) {
            switch (m) {
                case SHIELD:
                    converted.add(new Resources(SHIELD, 1));
                    break;
                case SERVANT:
                    converted.add(new Resources(SERVANT, 1));
                    break;
                case COIN:
                    converted.add(new Resources(COIN, 1));
                    break;
                case STONE:
                    converted.add(new Resources(STONE, 1));
                    break;
                case RED:
                    try {
                        player.moveFaithMarker(1);
                    } catch (VaticanReportException e) {
                        MainController.vaticanReport(player, e);
                    }
                    break;
                case WHITE:
                    if(!player.getWhiteMarble().isEmpty()){
                        if(player.getWhiteMarble().size() > 1){

                            white++;

                            takeResourcesController.sendNumOfWhiteMarble(white);

                            converted.add(player.getWhiteMarble().get(takeResourcesController.chooseWhiteMarble()));
                        }else {
                            converted.add(player.getWhiteMarble().get(0));
                        }
                    }
            }
        }
        return converted;
    }


}