package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.game.Double;
import it.polimi.ingsw.model.player.warehouse.Resources;
import it.polimi.ingsw.view.gui.ViewManager;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class GameBoardController implements Initializable {


    private final ViewManager view;

    private final int DECKFIELDCARDSCHOICE=1;
    private final int DEVSPACEDECKCHOICE=2;
    private final int ACTIVELEADERCARDSDECKCHOICE=4;
    private final int HIDDENLEADERCARDSDECKCHOICE=5;
    private final int EXTRASHELFLEADERCARDS=7;

    @FXML
    private StackPane floatingMenuPane;

    @FXML
    private ImageView resFirstShelfImg;
    @FXML
    private ImageView res1SecondShelfImg;
    @FXML
    private ImageView res2SecondShelfImg;
    @FXML
    private ImageView res1ThirdShelfImg;
    @FXML
    private ImageView res2ThirdShelfImg;
    @FXML
    private ImageView res3ThirdShelfImg;

    @FXML
    private Label quantityCoinStrongBox;
    @FXML
    private Label quantityServantStrongBox;
    @FXML
    private Label quantityShieldStrongBox;
    @FXML
    private Label quantityStoneStrongBox;

    @FXML
    private ImageView col1Img1; //lower
    @FXML
    private ImageView col1Img2;
    @FXML
    private ImageView col1Img3; //topper

    @FXML
    private ImageView col2Img1; //lower
    @FXML
    private ImageView col2Img2;
    @FXML
    private ImageView col2Img3; //topper

    @FXML
    private ImageView col3Img1; //lower
    @FXML
    private ImageView col3Img2;
    @FXML
    private ImageView col3Img3; //topper

    @FXML
    private GridPane faithTrack;
    @FXML
    private ImageView imageFaithMarker;

    @FXML
    private ImageView faithTile1Img;
    @FXML
    private ImageView faithTile2Img;
    @FXML
    private ImageView faithTile3Img;

    @FXML
    private ImageView leaderCardImg1Active;
    @FXML
    private ImageView lc1res1;
    @FXML
    private ImageView lc1res2;
    @FXML
    private ImageView leaderCardImg2Active;
    @FXML
    private ImageView lc2res1;
    @FXML
    private ImageView lc2res2;

    @FXML
    private ImageView leaderCardImg1Hidden;
    @FXML
    private ImageView leaderCardImg2Hidden;

    @FXML
    private ImageView extraMarbleImg;
    @FXML
    private ImageView marble00;
    @FXML
    private ImageView marble10;
    @FXML
    private ImageView marble20;
    @FXML
    private ImageView marble01;
    @FXML
    private ImageView marble11;
    @FXML
    private ImageView marble21;
    @FXML
    private ImageView marble02;
    @FXML
    private ImageView marble12;
    @FXML
    private ImageView marble22;
    @FXML
    private ImageView marble03;
    @FXML
    private ImageView marble13;
    @FXML
    private ImageView marble23;

    @FXML
    private Label turnIndicatorLabel;
    @FXML
    private Label playerPosition1;
    @FXML
    private Label playerPosition2;
    @FXML
    private Label playerPosition3;
    @FXML
    private Label playerPosition4;

    @FXML
    private Button goBackToChooseTurnButton;

    @FXML
    private ImageView deckField00;
    @FXML
    private ImageView deckField10;
    @FXML
    private ImageView deckField20;
    @FXML
    private ImageView deckField01;
    @FXML
    private ImageView deckField11;
    @FXML
    private ImageView deckField21;
    @FXML
    private ImageView deckField02;
    @FXML
    private ImageView deckField12;
    @FXML
    private ImageView deckField22;
    @FXML
    private ImageView deckField03;
    @FXML
    private ImageView deckField13;
    @FXML
    private ImageView deckField23;



    public GameBoardController(ViewManager view) {
        this.view=view;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        disableComponents();

        setResourcesInShelves();

        setResourcesInStrongBox();

        setDevCardInDevelopmentSlot();

        setPositionOnTheFaithTrack();

        setFaithPointTiles();

        setActiveLeaderCards();

        setHiddenLeaderCards();

        setMarketView();

        setOpponentsNameAndPosition();

        setDeckFieldOfDevelopmentCards();

        if(view.getIfIsMyTurn()) {
            setGoBackToChooseTurnButton();
        }

    }

    /**
     * this method set the images for the development cards on the deck field
     */
    private void setDeckFieldOfDevelopmentCards() {

        ArrayList<String> deckFieldImageUrl=view.getGuiAdapter().getCardUrls(DECKFIELDCARDSCHOICE,0);

        if(deckFieldImageUrl.size()>0){deckField00.setImage(new Image(deckFieldImageUrl.get(0)));}else{deckField00.setDisable(true);}
        if(deckFieldImageUrl.size()>1){deckField10.setImage(new Image(deckFieldImageUrl.get(1)));}else{deckField10.setDisable(true);}
        if(deckFieldImageUrl.size()>2){deckField20.setImage(new Image(deckFieldImageUrl.get(2)));}else{deckField20.setDisable(true);}

        deckFieldImageUrl=view.getGuiAdapter().getCardUrls(DECKFIELDCARDSCHOICE,1);


        if(deckFieldImageUrl.size()>0){deckField01.setImage(new Image(deckFieldImageUrl.get(0)));}else{deckField01.setDisable(true);}
        if(deckFieldImageUrl.size()>1){deckField11.setImage(new Image(deckFieldImageUrl.get(1)));}else{deckField11.setDisable(true);}
        if(deckFieldImageUrl.size()>2){deckField21.setImage(new Image(deckFieldImageUrl.get(2)));}else{deckField21.setDisable(true);}

        deckFieldImageUrl=view.getGuiAdapter().getCardUrls(DECKFIELDCARDSCHOICE,2);

        if(deckFieldImageUrl.size()>0){deckField02.setImage(new Image(deckFieldImageUrl.get(0)));}else{deckField02.setDisable(true);}
        if(deckFieldImageUrl.size()>1){deckField12.setImage(new Image(deckFieldImageUrl.get(1)));}else{deckField12.setDisable(true);}
        if(deckFieldImageUrl.size()>2){deckField22.setImage(new Image(deckFieldImageUrl.get(2)));}else{deckField22.setDisable(true);}

        deckFieldImageUrl=view.getGuiAdapter().getCardUrls(DECKFIELDCARDSCHOICE,3);

        if(deckFieldImageUrl.size()>0){deckField03.setImage(new Image(deckFieldImageUrl.get(0)));}else{deckField03.setDisable(true);}
        if(deckFieldImageUrl.size()>1){deckField13.setImage(new Image(deckFieldImageUrl.get(1)));}else{deckField13.setDisable(true);}
        if(deckFieldImageUrl.size()>2){deckField23.setImage(new Image(deckFieldImageUrl.get(2)));}else{deckField23.setDisable(true);}


    }

    /**
     * this method set the listeners and deals with the button that allow to go back to the floating menu that allows to choose a turn.
     * this method is invoked, and so the button is created, only if it's actually the turn of such player
     */
    private void setGoBackToChooseTurnButton() {
        goBackToChooseTurnButton.setDisable(false);
        goBackToChooseTurnButton.setOpacity(1);

        goBackToChooseTurnButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {

                try {
                    view.sceneFloatingMenuChooseTurnType();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        goBackToChooseTurnButton.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                goBackToChooseTurnButton.setEffect(new DropShadow());
            }
        });

        goBackToChooseTurnButton.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                goBackToChooseTurnButton.setEffect(null);
            }
        });

    }

    /**
     * this method set the texts ,on the game board menu, that tell whose turn is, the name and the points on the other opponents
     */
    private void setOpponentsNameAndPosition() {

        turnIndicatorLabel.setText("It's the turn of : " + (view.getGuiAdapter().getCurrentPlayer() == null ? "" : view.getGuiAdapter().getCurrentPlayer()));

        ArrayList<Double<String, Integer>> namePosition=view.getGuiAdapter().getPositions();

        switch(namePosition.size()){
            case 1:
                playerPosition1.setDisable(false);
                playerPosition1.setText("@"+namePosition.get(0).getName()+": "+namePosition.get(0).getPosition());
                break;

            case 2:
                playerPosition1.setDisable(false);
                playerPosition1.setText("@"+namePosition.get(0).getName()+": "+namePosition.get(0).getPosition());

                playerPosition2.setDisable(false);
                playerPosition2.setText("@"+namePosition.get(1).getName()+": "+namePosition.get(1).getPosition());
                break;

            case 3:
                playerPosition1.setDisable(false);
                playerPosition1.setText("@"+namePosition.get(0).getName()+": "+namePosition.get(0).getPosition());

                playerPosition2.setDisable(false);
                playerPosition2.setText("@"+namePosition.get(1).getName()+": "+namePosition.get(1).getPosition());

                playerPosition3.setDisable(false);
                playerPosition3.setText("@"+namePosition.get(2).getName()+": "+namePosition.get(2).getPosition());

                break;

            case 4:
                playerPosition1.setDisable(false);
                playerPosition1.setText("@"+namePosition.get(0).getName()+": "+namePosition.get(0).getPosition());

                playerPosition2.setDisable(false);
                playerPosition2.setText("@"+namePosition.get(1).getName()+": "+namePosition.get(1).getPosition());

                playerPosition3.setDisable(false);
                playerPosition3.setText("@"+namePosition.get(2).getName()+": "+namePosition.get(2).getPosition());

                playerPosition4.setDisable(false);
                playerPosition4.setText("@"+namePosition.get(3).getName()+": "+namePosition.get(3).getPosition());

                break;

        }

    }

    /**
     * this method set the images for the market, of the current state, on the game board
     */
    private void setMarketView() {

        marble00.setImage(new Image(view.getGuiAdapter().getMarble(0, 0).getImageUrl()));
        marble10.setImage(new Image(view.getGuiAdapter().getMarble(1, 0).getImageUrl()));
        marble20.setImage(new Image(view.getGuiAdapter().getMarble(2, 0).getImageUrl()));

        marble01.setImage(new Image(view.getGuiAdapter().getMarble(0, 1).getImageUrl()));
        marble11.setImage(new Image(view.getGuiAdapter().getMarble(1, 1).getImageUrl()));
        marble21.setImage(new Image(view.getGuiAdapter().getMarble(2, 1).getImageUrl()));

        marble02.setImage(new Image(view.getGuiAdapter().getMarble(0, 2).getImageUrl()));
        marble12.setImage(new Image(view.getGuiAdapter().getMarble(1, 2).getImageUrl()));
        marble22.setImage(new Image(view.getGuiAdapter().getMarble(2, 2).getImageUrl()));

        marble03.setImage(new Image(view.getGuiAdapter().getMarble(0, 3).getImageUrl()));
        marble13.setImage(new Image(view.getGuiAdapter().getMarble(1, 3).getImageUrl()));
        marble23.setImage(new Image(view.getGuiAdapter().getMarble(2, 3).getImageUrl()));

        extraMarbleImg.setImage(new Image(view.getGuiAdapter().getMarble(4, 4).getImageUrl()));

    }

    /**
     * this method set the images of the hidden leader cards that belongs to the player
     */
    private void setHiddenLeaderCards() {

        ArrayList<String> hiddenLeaderCardsUrl=view.getGuiAdapter().getCardUrls(HIDDENLEADERCARDSDECKCHOICE);

        if(hiddenLeaderCardsUrl.size()>0){
            leaderCardImg1Hidden.setDisable(false);
            leaderCardImg1Hidden.setImage(new Image(hiddenLeaderCardsUrl.get(0)));
        }
        if(hiddenLeaderCardsUrl.size()==2){
            leaderCardImg2Hidden.setDisable(false);
            leaderCardImg2Hidden.setImage(new Image(hiddenLeaderCardsUrl.get(1)));
        }

    }

    /**
     * this method set the images of the active leader cards that belongs to the player
     */
    private void setActiveLeaderCards() {


        if (view.getGuiAdapter().getCardUrls(ACTIVELEADERCARDSDECKCHOICE).size() > 0) {
            // set the first leader card
            leaderCardImg1Active.setDisable(false);
            leaderCardImg1Active.setImage(new Image(view.getGuiAdapter().getCardUrls(ACTIVELEADERCARDSDECKCHOICE).get(0)));

            // set the second leader card
            if (view.getGuiAdapter().getCardUrls(ACTIVELEADERCARDSDECKCHOICE).size() > 1) {
                leaderCardImg2Active.setDisable(false);
                leaderCardImg2Active.setImage(new Image(view.getGuiAdapter().getCardUrls(ACTIVELEADERCARDSDECKCHOICE).get(1)));
            }

        }


        //now I handle the images in case of extra shelves
        if (view.getGuiAdapter().getCardUrls(EXTRASHELFLEADERCARDS).size() >0) {

        if (view.getGuiAdapter().getCardUrls(EXTRASHELFLEADERCARDS).size() == 1) {

            if (view.getGuiAdapter().indexOfExtraSlot(view.getGuiAdapter().getCardUrls(EXTRASHELFLEADERCARDS).get(0)) == 0) {

                // the extra slot leader is the first of the active leader cards
                if (view.getGuiAdapter().getShelf(3).getShelfNumberOfResources() > 0) {
                    lc1res1.setDisable(false);
                    lc1res1.setImage(new Image(view.getGuiAdapter().getShelf(3).getShelfCurrentType().getImageUrl()));
                }
                if (view.getGuiAdapter().getShelf(3).getShelfNumberOfResources() == 2) {
                    lc1res2.setDisable(false);
                    lc1res2.setImage(new Image(view.getGuiAdapter().getShelf(3).getShelfCurrentType().getImageUrl()));
                }
            } else {
                // the extra slot leader is the second of the active leader cards
                if (view.getGuiAdapter().getShelf(3).getShelfNumberOfResources() > 0) {
                    lc2res1.setDisable(false);
                    lc2res1.setImage(new Image(view.getGuiAdapter().getShelf(3).getShelfCurrentType().getImageUrl()));
                }
                if (view.getGuiAdapter().getShelf(3).getShelfNumberOfResources() == 2) {
                    lc2res2.setDisable(false);
                    lc2res2.setImage(new Image(view.getGuiAdapter().getShelf(3).getShelfCurrentType().getImageUrl()));
                }
            }

        }
        if (view.getGuiAdapter().getCardUrls(EXTRASHELFLEADERCARDS).size() == 2) {

            if (view.getGuiAdapter().getShelf(3).getShelfNumberOfResources() > 0) {
                lc1res1.setDisable(false);
                lc1res1.setImage(new Image(view.getGuiAdapter().getShelf(3).getShelfCurrentType().getImageUrl()));
            }
            if (view.getGuiAdapter().getShelf(3).getShelfNumberOfResources() == 2) {
                lc1res2.setDisable(false);
                lc1res2.setImage(new Image(view.getGuiAdapter().getShelf(3).getShelfCurrentType().getImageUrl()));
            }


            if (view.getGuiAdapter().getShelf(4).getShelfNumberOfResources() > 0) {
                lc2res1.setDisable(false);
                lc2res1.setImage(new Image(view.getGuiAdapter().getShelf(4).getShelfCurrentType().getImageUrl()));
            }
            if (view.getGuiAdapter().getShelf(4).getShelfNumberOfResources() == 2) {
                lc2res2.setDisable(false);
                lc2res2.setImage(new Image(view.getGuiAdapter().getShelf(4).getShelfCurrentType().getImageUrl()));
            }
        }

    }

        }

    /**
     * this method set the images of the faith point tiles, besides it changes the image, displaying the ones crossed, in case of vatican report
     */
    private void setFaithPointTiles() {

        if( !(view.getGuiAdapter().checkPopeTile(0)) ) faithTile1Img.setImage( new Image( "/images/graphicstuff/popeFavor1Cross.png"));
        if( !(view.getGuiAdapter().checkPopeTile(1)) ) faithTile2Img.setImage( new Image( "/images/graphicstuff/popeFavor2Cross.png"));
        if( !(view.getGuiAdapter().checkPopeTile(2)) ) faithTile3Img.setImage( new Image( "/images/graphicstuff/popeFavor3Cross.png"));

    }

    /**
     * this method set the images of the faith marker of the player, on the faith track, showing the current position
     */
    private void setPositionOnTheFaithTrack() {

        switch(view.getGuiAdapter().getMyPosition()){

            case 0:GridPane.setRowIndex(imageFaithMarker, 3);
                GridPane.setColumnIndex(imageFaithMarker, 1);
                break;

            case 1:GridPane.setRowIndex(imageFaithMarker, 3);
                GridPane.setColumnIndex(imageFaithMarker, 2);
                break;

            case 2:GridPane.setRowIndex(imageFaithMarker, 3);
                GridPane.setColumnIndex(imageFaithMarker, 3);
                break;

            case 3:GridPane.setRowIndex(imageFaithMarker, 2);
                GridPane.setColumnIndex(imageFaithMarker, 3);
                break;

            case 4:GridPane.setRowIndex(imageFaithMarker, 1);
                GridPane.setColumnIndex(imageFaithMarker, 3);
                break;

            case 5:GridPane.setRowIndex(imageFaithMarker, 1);
                GridPane.setColumnIndex(imageFaithMarker, 4);
                break;

            case 6:GridPane.setRowIndex(imageFaithMarker, 1);
                GridPane.setColumnIndex(imageFaithMarker, 5);
                break;

            case 7:GridPane.setRowIndex(imageFaithMarker, 1);
                GridPane.setColumnIndex(imageFaithMarker, 6);
                break;

            case 8:GridPane.setRowIndex(imageFaithMarker, 1);
                GridPane.setColumnIndex(imageFaithMarker, 7);
                break;

            case 9:GridPane.setRowIndex(imageFaithMarker, 1);
                GridPane.setColumnIndex(imageFaithMarker, 8);
                break;

            case 10:GridPane.setRowIndex(imageFaithMarker, 2);
                GridPane.setColumnIndex(imageFaithMarker, 8);
                break;

            case 11:GridPane.setRowIndex(imageFaithMarker, 3);
                GridPane.setColumnIndex(imageFaithMarker, 8);
                break;

            case 12:GridPane.setRowIndex(imageFaithMarker, 3);
                GridPane.setColumnIndex(imageFaithMarker, 9);
                break;

            case 13:GridPane.setRowIndex(imageFaithMarker, 3);
                GridPane.setColumnIndex(imageFaithMarker, 10);
                break;

            case 14:GridPane.setRowIndex(imageFaithMarker, 3);
                GridPane.setColumnIndex(imageFaithMarker, 11);
                break;

            case 15:GridPane.setRowIndex(imageFaithMarker, 3);
                GridPane.setColumnIndex(imageFaithMarker, 12);
                break;

            case 16:GridPane.setRowIndex(imageFaithMarker, 3);
                GridPane.setColumnIndex(imageFaithMarker, 13);
                break;

            case 17:GridPane.setRowIndex(imageFaithMarker, 2);
                GridPane.setColumnIndex(imageFaithMarker, 13);
                break;

            case 18:GridPane.setRowIndex(imageFaithMarker, 1);
                GridPane.setColumnIndex(imageFaithMarker, 13);
                break;

            case 19:GridPane.setRowIndex(imageFaithMarker, 1);
                GridPane.setColumnIndex(imageFaithMarker, 14);
                break;

            case 20:GridPane.setRowIndex(imageFaithMarker, 1);
                GridPane.setColumnIndex(imageFaithMarker, 15);
                break;

            case 21:GridPane.setRowIndex(imageFaithMarker, 1);
                GridPane.setColumnIndex(imageFaithMarker, 16);
                break;

            case 22:GridPane.setRowIndex(imageFaithMarker, 1);
                GridPane.setColumnIndex(imageFaithMarker, 17);
                break;

            case 23:GridPane.setRowIndex(imageFaithMarker, 1);
                GridPane.setColumnIndex(imageFaithMarker, 18);
                break;

            case 24:GridPane.setRowIndex(imageFaithMarker, 1);
                GridPane.setColumnIndex(imageFaithMarker, 19);
                break;

        }

    }

    /**
     * this method set the images of the development cards that the player has, on the development slot
     */
    private void setDevCardInDevelopmentSlot() {

        ArrayList<String> firstColumnImageUrls=view.getGuiAdapter().getCardUrls(DEVSPACEDECKCHOICE, 0);

        switch(firstColumnImageUrls.size()){//sostituire con firstcolumnimageurl.size()??
            case 3:
                col1Img3.setDisable(false);
                col1Img3.setImage(new Image(firstColumnImageUrls.get(2)));

            case 2:
                col1Img2.setDisable(false);
                col1Img2.setImage(new Image(firstColumnImageUrls.get(1)));

            case 1:
                col1Img1.setDisable(false);
                col1Img1.setImage(new Image(firstColumnImageUrls.get(0)));

            default: break;
        }

        ArrayList<String> secondColumnImageUrls=view.getGuiAdapter().getCardUrls(DEVSPACEDECKCHOICE, 1);

        switch(secondColumnImageUrls.size()){
            case 3:
                col2Img3.setDisable(false);
                col2Img3.setImage(new Image(secondColumnImageUrls.get(2)));

            case 2:
                col2Img2.setDisable(false);
                col2Img2.setImage(new Image(secondColumnImageUrls.get(1)));

            case 1:
                col2Img1.setDisable(false);
                col2Img1.setImage(new Image(secondColumnImageUrls.get(0)));

            default: break;
        }

        ArrayList<String> thirdColumnImageUrls=view.getGuiAdapter().getCardUrls(DEVSPACEDECKCHOICE, 2);

        switch(thirdColumnImageUrls.size()){
            case 3:
                col3Img3.setDisable(false);
                col3Img3.setImage(new Image(thirdColumnImageUrls.get(2)));

            case 2:
                col3Img2.setDisable(false);
                col3Img2.setImage(new Image(thirdColumnImageUrls.get(1)));

            case 1:
                col3Img1.setDisable(false);
                col3Img1.setImage(new Image(thirdColumnImageUrls.get(0)));

            default: break;
        }


    }

    /**
     * this method set the quantity of the resources currently present in the strongbox
     */
    private void setResourcesInStrongBox() {

        String coinQnt = String.valueOf(view.getGuiAdapter().getStrongboxQuantities(Resources.ResType.COIN));
        quantityCoinStrongBox.setText("x "+coinQnt);

        String servantQnt = String.valueOf(view.getGuiAdapter().getStrongboxQuantities(Resources.ResType.SERVANT));
        quantityServantStrongBox.setText("x "+servantQnt);

        String shieldQnt = String.valueOf(view.getGuiAdapter().getStrongboxQuantities(Resources.ResType.SHIELD));
        quantityShieldStrongBox.setText("x "+shieldQnt);

        String stoneQnt = String.valueOf(view.getGuiAdapter().getStrongboxQuantities(Resources.ResType.STONE));
        quantityStoneStrongBox.setText("x "+stoneQnt);

    }

    /**
     * this method set the images of the resources currently present in the shelves
     */
    private void setResourcesInShelves() {

        if( view.getGuiAdapter().getShelf(0)!=null && view.getGuiAdapter().getShelf(0).getShelfCurrentType() != null )  {
            resFirstShelfImg.setDisable(false);
            resFirstShelfImg.setImage(new Image(view.getGuiAdapter().getShelf(0).getShelfCurrentType().getImageUrl()));
        }


        if(view.getGuiAdapter().getShelf(1)!=null &&view.getGuiAdapter().getShelf(1).getShelfCurrentType() != null) {
            res1SecondShelfImg.setDisable(false);
            res1SecondShelfImg.setImage(new Image(view.getGuiAdapter().getShelf(1).getShelfCurrentType().getImageUrl()));

            if(view.getGuiAdapter().getShelf(1).getShelfNumberOfResources() > 1 ) {
                res2SecondShelfImg.setDisable(false);
                res2SecondShelfImg.setImage(new Image(view.getGuiAdapter().getShelf(1).getShelfCurrentType().getImageUrl()));
            }
        }


        if(view.getGuiAdapter().getShelf(2)!=null &&view.getGuiAdapter().getShelf(2).getShelfCurrentType() != null) {
            res1ThirdShelfImg.setDisable(false);
            res1ThirdShelfImg.setImage(new Image(view.getGuiAdapter().getShelf(2).getShelfCurrentType().getImageUrl()));

            if(view.getGuiAdapter().getShelf(2).getShelfNumberOfResources() > 1 ) {
                res2ThirdShelfImg.setDisable(false);
                res2ThirdShelfImg.setImage(new Image(view.getGuiAdapter().getShelf(2).getShelfCurrentType().getImageUrl()));
                if(view.getGuiAdapter().getShelf(2).getShelfNumberOfResources() > 2 ){
                    res3ThirdShelfImg.setDisable(false);
                    res3ThirdShelfImg.setImage(new Image(view.getGuiAdapter().getShelf(2).getShelfCurrentType().getImageUrl()));
                }
            }
        }


    }

    /**
     * this method disable all the components that will we re enabled when needed
     */
    private void disableComponents() {
        //shelves images
        resFirstShelfImg.setDisable(true);
        res1SecondShelfImg.setDisable(true);
        res2SecondShelfImg.setDisable(true);
        res1ThirdShelfImg.setDisable(true);
        res2ThirdShelfImg.setDisable(true);
        res3ThirdShelfImg.setDisable(true);

        //slot development
        col1Img1.setDisable(true);
        col1Img2.setDisable(true);
        col1Img3.setDisable(true);
        col2Img1.setDisable(true);
        col2Img2.setDisable(true);
        col2Img3.setDisable(true);
        col3Img1.setDisable(true);
        col3Img2.setDisable(true);
        col3Img3.setDisable(true);

        //active leader card stuff
        leaderCardImg1Active.setDisable(true);
        lc1res1.setDisable(true);
        lc1res2.setDisable(true);
        leaderCardImg2Active.setDisable(true);
        lc2res1.setDisable(true);
        lc2res2.setDisable(true);

        //hidden leader card
        leaderCardImg1Hidden.setDisable(true);
        leaderCardImg2Hidden.setDisable(true);

        // label players
        playerPosition1.setDisable(true);
        playerPosition2.setDisable(true);
        playerPosition3.setDisable(true);
        playerPosition4.setDisable(true);

        // goBackToChooseTurnButton
        goBackToChooseTurnButton.setDisable(true);
        goBackToChooseTurnButton.setOpacity(0);

    }

    /**
     * This method returns the stack pane on which the right floating menu is displayed when needed
     * @return the reference to the stack pane on which to display the floating menu
     */
    public StackPane getFloatingMenuPane(){
        return this.floatingMenuPane;
    }


}
