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

import java.net.URL;
import java.util.ResourceBundle;


public class ControllerTokenSoloGame implements Initializable {


    private final ViewManager view;
    private String urlImgToken;
    private String text;

    @FXML
    private Button okButton;

    @FXML
    private ImageView tokenImg;

    @FXML
    private Label tokenText;


    public ControllerTokenSoloGame(ViewManager viewManager, String urlImgToken, String text) {
        this.view = viewManager;
        this.urlImgToken=urlImgToken;
        this.text= text;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setOkButton();

        setTokenImgAndDescription(urlImgToken, text);

    }


    /**
     * this method set the image and the description, of the token extracted, on the floating menu that appears at the end of the turn in the solo game
     * @param urlImgToken it's the url of the image of the token extracted
     * @param text it's the textual desception of the token extracted
     */
    private void setTokenImgAndDescription(String urlImgToken, String text) {

        tokenImg.setImage(new Image( urlImgToken));

        tokenText.setText(text);

    }

    /**
     * this method set the listeners and deals with the communication with the server for the button to accept the effect of the token extracted at tthe end of the turn
     */
    private void setOkButton() {

        okButton.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    view.sendNameToServer("f"); // f: it's a character to trigger the server to send the message to start the turn
                }
            });

        okButton.setOnMouseEntered(new EventHandler() {
                @Override
                public void handle(Event event) {
                    okButton.setEffect(new DropShadow());
                }
            });

        okButton.setOnMouseExited(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event){
                    okButton.setEffect(null);
                }
            });


    }


}
