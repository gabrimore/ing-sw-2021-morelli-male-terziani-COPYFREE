package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.view.gui.ViewManager;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ControllerChooseTurnType implements Initializable {


    private final ViewManager view;

    @FXML
    private Button takeResButton;
    @FXML
    private Button activeLeaderButton;
    @FXML
    private Button buyDevCard;
    @FXML
    private Button activateProduction;
    @FXML
    private Button showGameBoard;


    public ControllerChooseTurnType(ViewManager viewManager) {
        this.view = viewManager;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

       setTakeResButton();

       setActivateLeaderCardButton();

       setBuyDevCardButton();

       setActivateProduction();

       setShowGameBoard();

    }

    /**
     * this method set the listeners for the button to start the turn for taking resources from the market
     */
    private void setTakeResButton() {

        takeResButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {

                try {
                    view.sceneFloatingMenuMarket();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        takeResButton.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                takeResButton.setEffect(new DropShadow());
            }
        });

        takeResButton.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                takeResButton.setEffect(null);
            }
        });

    }

    /**
     * this method set the listeners for the button to activate or discard a leader card
     */
    private void setActivateLeaderCardButton() {

        activeLeaderButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {

                try {
                    view.sceneFloatingMenuActivateDiscardLeaderCard();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        activeLeaderButton.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                activeLeaderButton.setEffect(new DropShadow());
            }
        });

        activeLeaderButton.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                activeLeaderButton.setEffect(null);
            }
        });
    }

    /**
     * this method set the listeners and deals with the communication with the server for the button to start the turn to buy a development card from the deck field
     */
    private void setBuyDevCardButton() {

        buyDevCard.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                view.sendIntegerToServer(3);//buy dev card
                try {
                    view.sceneFloatingMenuBuyDevCard();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        buyDevCard.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                buyDevCard.setEffect(new DropShadow());
            }
        });

        buyDevCard.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                buyDevCard.setEffect(null);
            }
        });
    }

    /**
     * this method set the listeners and deals with the communication with the server for the button to start the turn for producing resources
     */
    private void setActivateProduction() {

        activateProduction.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {

                try {
                    view.sendIntegerToServer(4);
                    view.sceneFloatingChooseProduction();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        activateProduction.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                activateProduction.setEffect(new DropShadow());
            }
        });

        activateProduction.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                activateProduction.setEffect(null);
            }
        });
    }

    /**
     * this method set the listeners for the button to show the game board without concluding the turn
     */
    private void setShowGameBoard() {

        showGameBoard.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {

                try {
                    view.gameBoardScene();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        showGameBoard.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                showGameBoard.setEffect(new DropShadow());
            }
        });

        showGameBoard.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                showGameBoard.setEffect(null);
            }
        });
    }


}
