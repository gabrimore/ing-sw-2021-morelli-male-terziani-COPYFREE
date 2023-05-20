package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.player.warehouse.Resources;
import it.polimi.ingsw.view.gui.ViewManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static it.polimi.ingsw.view.gui.ViewManager.fromResourceToIndex;


public class ControllerProductionLeaderCard implements Initializable {

    private final ViewManager view;

    private final int ACTIVEEXTRAPRODUCTIONLEADCARDS=9;

    @FXML
    private Button goBackButton;

    @FXML
    private VBox firstLeaderVbox;
    @FXML
    private ImageView firstLeaderCardImg;
    @FXML
    private RadioButton firstLeaderButton;

    @FXML
    private VBox secondLeaderVbox;
    @FXML
    private ImageView secondLeaderCardImg;
    @FXML
    private RadioButton secondLeaderButton;

    @FXML
    private RadioButton coinButton;
    @FXML
    private RadioButton servantButton;
    @FXML
    private RadioButton shieldButton;
    @FXML
    private RadioButton stoneButton;

    @FXML
    private Button produceButton;
    @FXML
    private Label productionDeniedLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private HBox thirdResHbox;
    @FXML
    private Label chooseOutputLabel;


    private String leaderChoice="first";
    private String resourcerChoice="COIN";

    public ControllerProductionLeaderCard(ViewManager view)  {
        this.view=view;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        productionDeniedLabel.setOpacity(0);

        setGoBackButton();

        if(view.getGuiAdapter().getCardUrls(ACTIVEEXTRAPRODUCTIONLEADCARDS).size()>1) {
            setSecondLeaderCard();
            secondLeaderButton.setDisable(false);
            secondLeaderButton.setOpacity(1);

            if(view.getGuiAdapter().getLeadCardProdUsed().contains(0)) {firstLeaderVbox.setDisable(true);}
            else {firstLeaderVbox.setDisable(false);}

        }
        if(view.getGuiAdapter().getCardUrls(ACTIVEEXTRAPRODUCTIONLEADCARDS).size()>0){
            setFirstLeaderCard();
            createToogleOfLeaders();
            createToogleOfOutputResources();
            setProduceButton();

            if(view.getGuiAdapter().getLeadCardProdUsed().contains(1)) {secondLeaderVbox.setDisable(true);}
            else {secondLeaderVbox.setDisable(false);}

        }else{
            disableComponents();
        }

    }


    /**
     * this method set the listeners and deals with the communication with the server for the produce button
     */
    private void setProduceButton() {

        produceButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {

                view.sendIntegerToServer(3);
                view.sendIntegerToServer(fromLeaderChoosenToIndex(leaderChoice));
                view.sendIntegerToServer(fromResourceToIndex(resourcerChoice));

                ArrayList<Resources> temp = new ArrayList<>();
                temp.add(view.getGuiAdapter().getExtraInput().get((fromLeaderChoosenToIndex(leaderChoice))) );
/*
                if (view.getGuiAdapter().checkAvailability( temp)) {
                    try {
                        view.sceneFloatingChooseProduction();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    productionDeniedLabel.setOpacity(1);
                }


 */

            }
        });

        produceButton.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                produceButton.setEffect(new DropShadow());
            }
        });

        produceButton.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                produceButton.setEffect(null);
            }
        });

    }

    /**
     * this method set the image and the listeners for the first leader card
     */
    private void setFirstLeaderCard() {

        firstLeaderCardImg.setImage( new Image(  view.getGuiAdapter().getCardUrls(ACTIVEEXTRAPRODUCTIONLEADCARDS).get(0)));

        firstLeaderVbox.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                firstLeaderVbox.setEffect(new DropShadow());
            }
        });

        firstLeaderVbox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                firstLeaderVbox.setEffect(null);
            }
        });

        firstLeaderVbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                firstLeaderButton.setSelected(true);
            }
        });

    }

    /**
     * this method set the image and the listeners for the second leader card
     */
    private void setSecondLeaderCard() {
        secondLeaderCardImg.setImage( new Image( view.getGuiAdapter().getCardUrls(ACTIVEEXTRAPRODUCTIONLEADCARDS).get(1)));

        secondLeaderVbox.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                secondLeaderVbox.setEffect(new DropShadow());
            }
        });

        secondLeaderVbox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                secondLeaderVbox.setEffect(null);
            }
        });

        secondLeaderVbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                secondLeaderButton.setSelected(true);
            }
        });
    }

    /**
     * this method creates the toogle of radio buttons to choose the leader card to produce from
     */
    private void createToogleOfLeaders(){
        ToggleGroup groupLeaders = new ToggleGroup();

        firstLeaderButton.setText("first");
        firstLeaderButton.setFont(Font.font("Verdana"));
        firstLeaderButton.setToggleGroup(groupLeaders);
        firstLeaderButton.setSelected(true);

        if(view.getGuiAdapter().getCardUrls(ACTIVEEXTRAPRODUCTIONLEADCARDS).size()>1){

            secondLeaderButton.setText("second");
            secondLeaderButton.setFont(Font.font("Verdana"));
            secondLeaderButton.setToggleGroup(groupLeaders);
        }


        groupLeaders.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) {

                RadioButton rb = (RadioButton)groupLeaders.getSelectedToggle();

                leaderChoice = rb.getText();

            }

        });


    }

    /**
     * this method creates the toogle of radio buttons to choose the output resource
     */
    private void createToogleOfOutputResources(){
        ToggleGroup groupResources = new ToggleGroup();

        coinButton.setText("COIN");
        coinButton.setFont(Font.font("Verdana"));
        coinButton.setToggleGroup(groupResources);
        coinButton.setSelected(true);

        servantButton.setText("SERVANT");
        servantButton.setFont(Font.font("Verdana"));
        servantButton.setToggleGroup(groupResources);

        shieldButton.setText("SHIELD");
        shieldButton.setFont(Font.font("Verdana"));
        shieldButton.setToggleGroup(groupResources);

        stoneButton.setText("STONE");
        stoneButton.setFont(Font.font("Verdana"));
        stoneButton.setToggleGroup(groupResources);


        groupResources.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) {

                RadioButton rb = (RadioButton)groupResources.getSelectedToggle();

                resourcerChoice = rb.getText();


            }

        });


    }

    /**
     * this method transforms the name of the leader card chosen in an index(int)
     */
    private int fromLeaderChoosenToIndex(String choice){
        return choice.equals("first") ? 0 : 1;
    }

    /**
     * this method set the listeners and deals with the communication with the server for the go back button
     */
    private void setGoBackButton() {

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

    /**
     * this method disable all the components that will we enabled when needed
     */
    private void disableComponents(){

        chooseOutputLabel.setDisable(true);
        chooseOutputLabel.setOpacity(0);

        thirdResHbox.setDisable(true);
        thirdResHbox.setOpacity(0);

        firstLeaderVbox.setDisable(true);
        firstLeaderVbox.setOpacity(0);

        secondLeaderVbox.setDisable(true);
        secondLeaderVbox.setOpacity(0);
        secondLeaderButton.setDisable(true);
        secondLeaderButton.setOpacity(0);

        produceButton.setDisable(true);
        produceButton.setOpacity(0);

        titleLabel.setText("No leader cards to produce from");

    }


}
