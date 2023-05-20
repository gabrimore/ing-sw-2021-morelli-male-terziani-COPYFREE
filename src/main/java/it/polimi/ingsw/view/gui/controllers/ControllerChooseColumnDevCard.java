package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.view.gui.ViewManager;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ControllerChooseColumnDevCard implements Initializable {

    private ViewManager view;
    private boolean isBuyDev;

    private final int DEVSPACEDECKCHOICE=2;

    @FXML
    private HBox horizontalBox;

    @FXML
    private Button buttonCol1;
    @FXML
    private Button buttonCol2;
    @FXML
    private Button buttonCol3;

    @FXML
    private Label chooseRCLabel;

    @FXML
    private Button goBackButton;



    public ControllerChooseColumnDevCard(ViewManager viewManager, boolean isBuyDev) {

        this.view=viewManager;
        this.isBuyDev=isBuyDev;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        buttonCol1.setDisable(true);
        buttonCol2.setDisable(true);
        buttonCol3.setDisable(true);

        buttonCol1.setOpacity(0);
        buttonCol2.setOpacity(0);
        buttonCol3.setOpacity(0);

        if(isBuyDev){
            chooseRCLabel.setText("Place the development card in a column between :");
            setButtonsBuyDev();
            goBackButton.setDisable(true);
            goBackButton.setOpacity(0);
        }
        else{ //case produce from devCard
            chooseRCLabel.setText("Choose the card from which to produce between :");
            setButtonsProduce();
            setGoBackButton();
            horizontalBox.setPrefSize(566.0, 182.0);
            horizontalBox.setLayoutX(84);
            horizontalBox.setLayoutY(150);
        }


    }


    ////////////////////////////////////////// case: isBuyDev = false, is the case in which the column(actually the image of the card) to choose is from which development card you want to produce\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

        //produce from a column: show the images of the developments card you can use

    /**
     * this method set the buttons for choosing the column of the development card to produce from and invoke the method to set the listeners for each
     * it actually sets the images of the development cards instead of the button, to choose from which development card you want to produce
     */
    private void setButtonsProduce() {
        if(view.getGuiAdapter().checkLastCardColumnProdState(0)){ // checks whether the top card in the column 0 can be activated

            ArrayList<String> firstColumnDevSpaceUrls = view.getGuiAdapter().getCardUrls(DEVSPACEDECKCHOICE, 0);
            ImageView imgCol1 = new ImageView(new Image(firstColumnDevSpaceUrls.get(firstColumnDevSpaceUrls.size()-1)) );
            imgCol1.setFitWidth(121);
            imgCol1.setFitHeight(182);
            imgCol1.setPreserveRatio(true);
            buttonCol1.setGraphic( imgCol1);
            buttonCol1.setText("");
            buttonCol1.setPrefSize(121, 182);
            buttonCol1.setStyle("-fx-background-color: transparent;");
            buttonCol1.setOpacity(1);

            if(view.getGuiAdapter().getDevCardProdUsed().contains(0)) {buttonCol1.setDisable(true);}
            else {buttonCol1.setDisable(false);}

            setListenerProduceDevCardButton(buttonCol1, 0);
        }

        //second button
        if(view.getGuiAdapter().checkLastCardColumnProdState(1)){ // checks whether the top card in the column 1 can be activated
            ArrayList<String> secondColumnDevSpaceUrls=view.getGuiAdapter().getCardUrls(DEVSPACEDECKCHOICE, 1);

            ImageView imgCol2 = new ImageView(new Image(secondColumnDevSpaceUrls.get(secondColumnDevSpaceUrls.size()-1)) );
            imgCol2.setFitWidth(121);
            imgCol2.setFitHeight(182);
            imgCol2.setPreserveRatio(true);
            buttonCol2.setGraphic( imgCol2);
            buttonCol2.setText("");
            buttonCol2.setPrefSize(121, 182);
            buttonCol2.setStyle("-fx-background-color: transparent;");
            buttonCol2.setOpacity(1);

            if(view.getGuiAdapter().getDevCardProdUsed().contains(1)) {buttonCol2.setDisable(true);}
            else {buttonCol2.setDisable(false);}

            setListenerProduceDevCardButton(buttonCol2, 1);
        }

        //third button
        if(view.getGuiAdapter().checkLastCardColumnProdState(2)){ // checks whether the top card in the column 2 can be activated
            ArrayList<String> thirdColumnDevSpaceUrls=view.getGuiAdapter().getCardUrls(DEVSPACEDECKCHOICE, 2);
            ImageView imgCol3 = new ImageView(new Image(thirdColumnDevSpaceUrls.get(thirdColumnDevSpaceUrls.size()-1)) );
            imgCol3.setFitWidth(121);
            imgCol3.setFitHeight(182);
            imgCol3.setPreserveRatio(true);
            buttonCol3.setGraphic( imgCol3);
            buttonCol3.setText("");
            buttonCol3.setPrefSize(121, 182);
            buttonCol3.setStyle("-fx-background-color: transparent;");
            buttonCol3.setOpacity(1);

            if(view.getGuiAdapter().getDevCardProdUsed().contains(2)) {buttonCol3.setDisable(true);}
            else {buttonCol3.setDisable(false);}

            setListenerProduceDevCardButton(buttonCol3, 2);
        }

        // case in which there are no development card to produce from
        if( !(view.getGuiAdapter().checkLastCardColumnProdState(0)) && !(view.getGuiAdapter().checkLastCardColumnProdState(1)) && !(view.getGuiAdapter().checkLastCardColumnProdState(2)) ){
            horizontalBox.setDisable(true);
            chooseRCLabel.setText("There are no development cards to produce from");
            chooseRCLabel.setLayoutY(85.0);

        }


    }

    /**
     * this method set the listeners and deals with the communication with the server for the development card to produce from
     * @param button to choose from where to produce
     * @param columnIndex index of the button clicked to communicate which column has been chosen
     */
    private void setListenerProduceDevCardButton(Button button, int columnIndex) {

        button.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                view.sendIntegerToServer(1); // choose dev card production
                view.sendIntegerToServer(columnIndex);
                try {
                    view.sceneFloatingChooseProduction();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        button.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                button.setEffect(new DropShadow());
            }
        });

        button.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                button.setEffect(null);
            }
        });

    }



    ////////////////////////////////////////// case: isBuyDev = true, is the case in which the column to choose is where to place the development card in the development field\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    /**
     * this method set the buttons for choosing the column of the development field to place the development card and invoke the method to set the listeners for each
     */
    private void setButtonsBuyDev() {


        if( view.getGuiAdapter().getPossibleDevSpaceColumns().contains(0)){
            buttonCol1.setDisable(false);
            buttonCol1.setOpacity(1);
            setListenerBuyDevCardButton(buttonCol1, 0);
        }
        if( view.getGuiAdapter().getPossibleDevSpaceColumns().contains(1)){
            buttonCol2.setDisable(false);
            buttonCol2.setOpacity(1);
            setListenerBuyDevCardButton(buttonCol2, 1);
        }
        if( view.getGuiAdapter().getPossibleDevSpaceColumns().contains(2)){
            buttonCol3.setDisable(false);
            buttonCol3.setOpacity(1);
            setListenerBuyDevCardButton(buttonCol3, 2);
        }


    }

    /**
     *  this method set the listeners and deals with the communication with the server for the development card to be bought
     * @param button to choose where to put the development card
     * @param chosenColumn index of the button clicked to communicate which column has been chosen
     */
    private void setListenerBuyDevCardButton(Button button, int chosenColumn) {

        button.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {


                view.sendIntegerToServer(chosenColumn);//
                try {
                    view.gameBoardScene();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        button.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                button.setEffect(new DropShadow());
            }
        });

        button.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                button.setEffect(null);
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
                    view.sceneFloatingChooseProduction();
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
