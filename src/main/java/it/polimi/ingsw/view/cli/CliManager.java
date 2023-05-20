package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.view.cli.complete.InitialLogoDrawing;
import it.polimi.ingsw.view.cli.basic.MarketBoardDrawing;
import it.polimi.ingsw.view.cli.basic.WarehouseDrawing;
import it.polimi.ingsw.view.cli.complete.BuyableCardsDrawing;
import it.polimi.ingsw.view.cli.complete.DeckFieldDrawing;
import it.polimi.ingsw.view.cli.complete.InitialLeaderCardDrawing;
import it.polimi.ingsw.view.cli.complete.PersonalBoardDrawing;

public class CliManager {

    private final PersonalBoardDrawing personalBoardDrawing;
    private final InitialLeaderCardDrawing initialLeaderCardChoiceDrawing;
    private final DeckFieldDrawing deckFieldDrawing;
    private final MarketBoardDrawing marketBoardDrawing;
    private final BuyableCardsDrawing buyableCardsDrawing;
    private final InitialLogoDrawing logoDrawing;
    private final WarehouseDrawing warehouseDrawing;


    public CliManager(CliAdapter cliAdapter){
        this.personalBoardDrawing = new PersonalBoardDrawing(cliAdapter);
        this.deckFieldDrawing = new DeckFieldDrawing(cliAdapter);
        this.initialLeaderCardChoiceDrawing = new InitialLeaderCardDrawing(cliAdapter);
        this.marketBoardDrawing = new MarketBoardDrawing(cliAdapter);
        this.buyableCardsDrawing = new BuyableCardsDrawing(cliAdapter);
        this.logoDrawing = new InitialLogoDrawing(cliAdapter);
        this.warehouseDrawing = new WarehouseDrawing(cliAdapter);
    }

    /**
     * draws an element o a 2dimensional array and prints it
     * @param choice the element to be drawn and printed
     */
    public void draw(int choice){

        switch(choice){

            case 0:
                personalBoardDrawing.draw();
                personalBoardDrawing.printElement();
                break;
            case 1:
                initialLeaderCardChoiceDrawing.draw();
                initialLeaderCardChoiceDrawing.printElement();
                break;
            case 2:
                marketBoardDrawing.draw();
                marketBoardDrawing.printElement();
                break;

            case 3:
                deckFieldDrawing.draw();
                deckFieldDrawing.printElement();
                break;

            case 4:
                buyableCardsDrawing.draw();
                buyableCardsDrawing.printElement();
                break;

            case 5:
                logoDrawing.draw();
                logoDrawing.printElement();
                break;

            case 6:
                warehouseDrawing.draw();
                warehouseDrawing.printElement();
                break;

        }

    }


}
