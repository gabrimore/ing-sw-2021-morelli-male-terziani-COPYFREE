package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.view.gui.ViewManager;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;


public class ControllerWhiteMarble implements Initializable {

    private final ViewManager view;

    private final int ACTIVELEADERCARD=4;

    @FXML
    private Label titleLabel;
    @FXML
    private ImageView imageFirstLeaderCard;
    @FXML
    private ImageView imageSecondLeaderCard;

    int whiteMarble;


    public ControllerWhiteMarble(ViewManager view, int whiteMarble) {
        this.view = view;
        this.whiteMarble = whiteMarble;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        titleLabel.setText("Choose In which resource you want to change the " + convertFromIntToString(whiteMarble) +" white marble");

        setFirstLeaderCard();

        setSecondLeaderCard();

    }


    /**
     * this method set the image, handles the communication with the server and set the listeners for the first leader card
     */
    private void setFirstLeaderCard() {

        imageFirstLeaderCard.setImage(new Image(view.getGuiAdapter().getCardUrls(ACTIVELEADERCARD).get(0)));



        imageFirstLeaderCard.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imageFirstLeaderCard.setEffect(new DropShadow());
            }
        });

        imageFirstLeaderCard.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imageFirstLeaderCard.setEffect(null);
            }
        });

        imageFirstLeaderCard.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               view.sendIntegerToServer(0);
            }
        });

    }

    /**
     * this method set the image, handles the communication with the server and set the listeners for the second leader card
     */
    private void setSecondLeaderCard() {

        imageSecondLeaderCard.setImage(new Image(view.getGuiAdapter().getCardUrls(ACTIVELEADERCARD).get(1)));



        imageSecondLeaderCard.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imageSecondLeaderCard.setEffect(new DropShadow());
            }
        });

        imageSecondLeaderCard.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imageSecondLeaderCard.setEffect(null);
            }
        });

        imageSecondLeaderCard.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.sendIntegerToServer(1);
            }
        });

    }

    /**
     * this method converts the int passed in a string with the english word in order to display the right text on the floating menu
     */
    private String convertFromIntToString (int whiteMarble){

        String write= null;

        switch (whiteMarble) {
            case 1:  write = "first";
                break;
            case 2:  write = "second";
                break;
            case 3:  write = "third";
                break;
            case 4:  write = "fourth";
                break;
            default: write = "";
                break;
        }

        return  write;

    }


}
