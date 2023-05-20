package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.view.gui.ViewManager;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ControllerActivateDiscardLeaderCard implements Initializable {

    private final ViewManager view;
    private int chosenLeader = -1;

    private final int HIDDENLEADERDECK=5;


    @FXML
    private VBox vboxFirst;
    @FXML
    private ImageView imageFirstLeaderCard;
    @FXML
    private CheckBox firstCheckBox;

    @FXML
    private VBox vboxSecond;
    @FXML
    private ImageView imageSecondLeaderCard;
    @FXML
    private CheckBox secondCheckBox;

    @FXML
    private Button activateButton;
    @FXML
    private Button discardButton;

    @FXML
    private Button goBackButton;

    @FXML
    private Label titleLabel;

    @FXML
    private Label cannotActivateLabel;


    public ControllerActivateDiscardLeaderCard(ViewManager viewManager) {
        this.view = viewManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        setGoBackButton();
        cannotActivateLabel.setOpacity(0);

        if(view.getGuiAdapter().getCardUrls(HIDDENLEADERDECK).size()>0){
            setLeaderCard1();
            setActivateButton();
            setDiscardButton();

            if(view.getGuiAdapter().getCardUrls(HIDDENLEADERDECK).size()==2){
                setLeaderCard2();
            }else{
                disableSecondLeaderComponents();
            }
        }else{
            titleLabel.setText("There aren't leader cards to be activated or discarded");
            titleLabel.setLayoutY(95.0);
            disableComponents();
        }

    }

    /**
     * this method set the listeners and deals with the communication with the server for the activate button
     */
    private void setActivateButton() {

        activateButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                if (chosenLeader != -1) {
                    if (view.getGuiAdapter().checkHiddenLeadCardState(chosenLeader)) {
                        view.sendIntegerToServer(2); //decide which turn between activate/discard leader card
                        view.sendIntegerToServer(1);//activate
                        view.sendIntegerToServer(chosenLeader);// 0 o 1

                    } else {
                        cannotActivateLabel.setOpacity(1);
                    }
                }
            }

        });

        activateButton.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                activateButton.setEffect(new DropShadow());
            }
        });

        activateButton.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                activateButton.setEffect(null);
            }
        });

    }

    /**
     * this method set the listeners and deals with the communication with the server for the discard button
     */
    private void setDiscardButton() {

        discardButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {

                if (chosenLeader != -1) {
                    view.sendIntegerToServer(2); //decide which turn between activate/discard leader card
                    view.sendIntegerToServer(2);//discard
                    view.sendIntegerToServer(chosenLeader);

                }
            }

        });

        discardButton.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                discardButton.setEffect(new DropShadow());
            }
        });

        discardButton.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                discardButton.setEffect(null);
            }
        });

    }

    /**
     * this method set the image and the listeners for the first leader card
     */
    private void setLeaderCard1() {

        imageFirstLeaderCard.setImage( new Image( view.getGuiAdapter().getCardUrls(HIDDENLEADERDECK).get(0)));

        vboxFirst.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                vboxFirst.setEffect(new DropShadow());
            }
        });

        vboxFirst.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                vboxFirst.setEffect(null);
            }
        });

        vboxFirst.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(firstCheckBox.isSelected()){
                    firstCheckBox.setSelected(false);
                    chosenLeader = -1;
                }
                else{
                    firstCheckBox.setSelected(true);
                    chosenLeader = 0;
                }

                secondCheckBox.setSelected(false);

            }
        });

    }

    /**
     * this method set the image and the listeners for the second leader card
     */
    private void setLeaderCard2() {

        imageSecondLeaderCard.setImage( new Image( view.getGuiAdapter().getCardUrls(HIDDENLEADERDECK).get(1)));

        vboxSecond.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                vboxSecond.setEffect(new DropShadow());
            }
        });

        vboxSecond.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                vboxSecond.setEffect(null);
            }
        });

        vboxSecond.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(secondCheckBox.isSelected()){
                    secondCheckBox.setSelected(false);
                    chosenLeader = -1;
                }
                else {
                    secondCheckBox.setSelected(true);
                    chosenLeader = 1;
                }

                firstCheckBox.setSelected(false);

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


    /**
     * this method disable all the components of the second leader card in the case it is not present
     */
    private void disableSecondLeaderComponents() {
        vboxSecond.setDisable(true);
        vboxSecond.setOpacity(0);
        secondCheckBox.setDisable(true);
        secondCheckBox.setOpacity(0);
    }

    /**
     * this method disable all the components that will be re enabled when needed
     */
    private void disableComponents() {
        activateButton.setDisable(true);
        activateButton.setOpacity(0);

        discardButton.setDisable(true);
        discardButton.setOpacity(0);

        vboxFirst.setDisable(true);
        vboxFirst.setOpacity(0);
        firstCheckBox.setDisable(true);
        firstCheckBox.setOpacity(0);

        disableSecondLeaderComponents();
    }


}
