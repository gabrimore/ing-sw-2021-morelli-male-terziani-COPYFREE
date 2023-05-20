package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.player.warehouse.Resources;
import it.polimi.ingsw.view.gui.ViewManager;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ControllerChooseProduction implements Initializable {


    private final ViewManager view;

    @FXML
    private Button goBackButton;

    @FXML
    private Button devCardButton;
    @FXML
    private Button baseProdButton;
    @FXML
    private Button leaderCardButton;
    @FXML
    private Button endProductionButton;

    @FXML
    private Label quantityCoinAvaLabel;
    @FXML
    private Label quantityServantAvaLabel;
    @FXML
    private Label quantityShieldAvaLabel;
    @FXML
    private Label quantityStoneAvaLabel;


    @FXML
    private Label quantityCoinProdLabel;
    @FXML
    private Label quantityServantProdLabel;
    @FXML
    private Label quantityShieldProdLabel;
    @FXML
    private Label quantityStoneProdLabel;



    public ControllerChooseProduction(ViewManager viewManager){
        this.view = viewManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setQuantityAvailable();

        setQuantityProduced();

        // the go back button is available only if the production is not started yet
        if( (view.getGuiAdapter().getProduced().isEmpty()) && (view.getGuiAdapter().getFaithPointEarned()==0) ) {
            setGoBackButton();
        }else{
            goBackButton.setDisable(true);
            goBackButton.setOpacity(0);
        }

        setDevCardButton();

        if(view.getGuiAdapter().getIsBaseProduction()) {
            setBaseProductionButton();
        }else{
            baseProdButton.setDisable(true);
        }

        setLeaderCardButton();

        setEndProductionButton();

    }

    /**
     * this method set the quantity of resources available to be utilized during the production
     */
    private void setQuantityAvailable() {

        ArrayList<Resources> availableResources = view.getGuiAdapter().getAvailable();

        for(Resources res : availableResources){

            switch(res.getResourceType().toString()){
                case "CNS": quantityCoinAvaLabel.setText("X "+ res.getCounter());
                    break;
                case "SRV": quantityServantAvaLabel.setText("X "+ res.getCounter());
                    break;
                case "SHD": quantityShieldAvaLabel.setText("X "+ res.getCounter());
                    break;
                case "STN": quantityStoneAvaLabel.setText("X "+ res.getCounter());
                    break;
            }

        }


    }

    /**
     * this method set the quantity of resources that has been produced during the production
     */
    private void setQuantityProduced() {

        ArrayList<Resources> producedResources = view.getGuiAdapter().getProduced();

        for(Resources res : producedResources){

            switch(res.getResourceType().toString()){

                case "CNS": quantityCoinProdLabel.setText("X "+ res.getCounter());
                    break;
                case "SRV": quantityServantProdLabel.setText("X "+ res.getCounter());
                    break;
                case "SHD": quantityShieldProdLabel.setText("X "+ res.getCounter());
                    break;
                case "STN": quantityStoneProdLabel.setText("X "+ res.getCounter());
                    break;

            }

        }

    }

    /**
     * this method set the listeners and deals with the communication with the server for the button to end the turn
     */
    private void setEndProductionButton() {
        endProductionButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {

                try {
                    if( (view.getGuiAdapter().getProduced().isEmpty()) && (view.getGuiAdapter().getFaithPointEarned()==0) ){
                        view.sendIntegerToServer(4);
                        view.sceneFloatingMenuChooseTurnType();
                    }
                    else{
                        view.sendIntegerToServer(4);
                        view.getGuiAdapter().setBaseProduction(true);
                        view.gameBoardScene();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        endProductionButton.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                endProductionButton.setEffect(new DropShadow());
            }
        });

        endProductionButton.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                endProductionButton.setEffect(null);
            }
        });

    }

    /**
     * this method set the listeners and deals with the communication with the server for the button to produce from a leader card
     */
    private void setLeaderCardButton() {
        leaderCardButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {

                try {
                    sceneFloatingProductionLeaderCard();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        leaderCardButton.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                leaderCardButton.setEffect(new DropShadow());
            }
        });

        leaderCardButton.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                leaderCardButton.setEffect(null);
            }
        });


    }

    /**
     * this method set the listeners and deals with the communication with the server for the button to activate the base production
     */
    private void setBaseProductionButton() {

        baseProdButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {


                try {
                    sceneFloatingBaseProduction();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        baseProdButton.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                baseProdButton.setEffect(new DropShadow());
            }
        });

        baseProdButton.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                baseProdButton.setEffect(null);
            }
        });

    }

    /**
     * uploads onto the stackPane of the gameBoardScene the scene in which we handle the base production
     * @throws IOException in case the scene loaded from the FXML specified is not found
     */
    private void sceneFloatingBaseProduction() throws IOException{

        FXMLLoader loaderBaseProduction = new FXMLLoader();
        loaderBaseProduction.setLocation(GameBoardController.class.getResource("/scenesFXML/floatingMenuProductionBase.fxml"));
        loaderBaseProduction.setController(new ControllerBaseProduction(view) );
        Parent stackPaneLoadedFXML = (StackPane) loaderBaseProduction.load();

        view.getControllerGameBoard().getFloatingMenuPane().toFront();
        view.getControllerGameBoard().getFloatingMenuPane().getChildren().setAll(stackPaneLoadedFXML);

    }

    /**
     * uploads onto the stackPane of the gameBoardScene the scene in which we handle the production from the leader card
     * @throws IOException in case the scene loaded from the FXML specified is not found
     */
    private void sceneFloatingProductionLeaderCard() throws IOException{

        FXMLLoader loaderBaseProduction = new FXMLLoader();
        loaderBaseProduction.setLocation(GameBoardController.class.getResource("/scenesFXML/floatingMenuProductionLeaderCard.fxml"));
        loaderBaseProduction.setController(new ControllerProductionLeaderCard(view) );
        Parent stackPaneLoadedFXML = (StackPane) loaderBaseProduction.load();

        view.getControllerGameBoard().getFloatingMenuPane().toFront();
        view.getControllerGameBoard().getFloatingMenuPane().getChildren().setAll(stackPaneLoadedFXML);

    }

    /**
     * this method set the listeners and deals with the communication with the server for the button to activate the production from the development cards
     */
    private void setDevCardButton() {

        devCardButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {

                try {
                    view.sceneFloatingMenuChooseColumnDevCards(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        devCardButton.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                devCardButton.setEffect(new DropShadow());
            }
        });

        devCardButton.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                devCardButton.setEffect(null);
            }
        });

    }

    /**
     * this method set the listeners and deals with the communication with the server for the go back button
     */
    private void setGoBackButton(){

        goBackButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {

                try {
                        view.sendIntegerToServer(4);
                        view.sceneFloatingMenuChooseTurnType();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        goBackButton.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                goBackButton.setEffect(new DropShadow());
            }
        });

        goBackButton.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                goBackButton.setEffect(null);
            }
        });
    }





}
