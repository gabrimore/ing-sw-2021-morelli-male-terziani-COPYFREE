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
import javafx.scene.layout.GridPane;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ControllerBuyDevCard implements Initializable {

    private final ViewManager view;

    private final int POSSIBLEDEVCARDDECK=6;


    @FXML
    private GridPane gridPaneDevCards;

    @FXML
    private Button goBackButton;

    @FXML
    private Label titleLabel;


    @FXML
    private ImageView image00;
    @FXML
    private ImageView image01;
    @FXML
    private ImageView image02;
    @FXML
    private ImageView image03;
    @FXML
    private ImageView image04;
    @FXML
    private ImageView image05;

    @FXML
    private ImageView image10;
    @FXML
    private ImageView image11;
    @FXML
    private ImageView image12;
    @FXML
    private ImageView image13;
    @FXML
    private ImageView image14;
    @FXML
    private ImageView image15;

    private ArrayList<String> possibleCardsUrl;


    public ControllerBuyDevCard(ViewManager viewManager)  {

        this.view = viewManager;

        // list of the potential development cards to be bought
        possibleCardsUrl = view.getGuiAdapter().getCardUrls(POSSIBLEDEVCARDDECK);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setGoBackButton();

        if( !(possibleCardsUrl.isEmpty()) ) {
            setDevCards(possibleCardsUrl);
        }
        else{
            gridPaneDevCards.setDisable(true);
            gridPaneDevCards.setOpacity(0);
            titleLabel.setText("There aren't development cards to be bought");
            titleLabel.setLayoutY(80.0);
        }


    }

    /**
     * this method set the images of the development cards that can be bought onto the scene and invoke the method to set the listeners for each
     * @param possibleCardsUrl list of the urls of the images of the development cards to be displayed
     */
    private void setDevCards(ArrayList<String> possibleCardsUrl ) {

        switch(possibleCardsUrl.size()){

            case 12:image15.setImage(new Image(possibleCardsUrl.get(11)));
                setListenerToDevCard(image15, 11);

            case 11:image14.setImage(new Image(possibleCardsUrl.get(10) ));
                setListenerToDevCard(image14, 10);

            case 10:image13.setImage(new Image(possibleCardsUrl.get(9) ));
                setListenerToDevCard(image13, 9);

            case 9:image12.setImage(new Image(possibleCardsUrl.get(8) ));
                setListenerToDevCard(image12, 8);

            case 8:image11.setImage(new Image(possibleCardsUrl.get(7) ));
                setListenerToDevCard(image11, 7);

            case 7:image10.setImage(new Image(possibleCardsUrl.get(6) ));
                setListenerToDevCard(image10, 6);

            case 6:image05.setImage(new Image(possibleCardsUrl.get(5) ));
                setListenerToDevCard(image05, 5);

            case 5:image04.setImage(new Image(possibleCardsUrl.get(4) ));
                setListenerToDevCard(image04, 4);

            case 4:image03.setImage(new Image(possibleCardsUrl.get(3) ));
                setListenerToDevCard(image03, 3);

            case 3:image02.setImage(new Image(possibleCardsUrl.get(2) ));
                setListenerToDevCard(image02, 2);

            case 2:image01.setImage(new Image(possibleCardsUrl.get(1) ));
                setListenerToDevCard(image01, 1);

            case 1:image00.setImage(new Image(possibleCardsUrl.get(0) ));
                setListenerToDevCard(image00, 0);

            default: break;
        }

    }

    /**
     * this method set the listeners and deals with the communication with the server for the development card to be bought
     * @param image image of the development card onto which to set the listeners
     * @param chosenDevCard index of the development card to pass at the server when chosen
     */
    private void setListenerToDevCard(ImageView image, int chosenDevCard){
        image.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { image.setEffect(new DropShadow());
            }
        });

        image.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                image.setEffect(null);
            }
        });

        image.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                    view.sendIntegerToServer(1);//choose to buy a card
                    view.sendIntegerToServer(chosenDevCard);//card chosen

                    try {
                    view.sceneFloatingMenuChooseColumnDevCards(true);
                    } catch (IOException e) {
                    e.printStackTrace();
                    }

            }
        });
    }

    /**
     * this method set the listeners and deals with the communication with the server for the go back button
     */
    private void setGoBackButton() {

        goBackButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {

                try {
                    if(view.getGuiAdapter().getCardUrls(POSSIBLEDEVCARDDECK).size()>0){ view.sendIntegerToServer(2);};//GO BACK
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