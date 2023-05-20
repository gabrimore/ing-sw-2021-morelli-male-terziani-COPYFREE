package it.polimi.ingsw.view.gui.controllers;


import it.polimi.ingsw.view.gui.ViewManager;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ControllerMarket implements Initializable {


    private final ViewManager view;

    @FXML
    private ImageView extraMarbleImg;
    @FXML
    private ImageView image00;
    @FXML
    private ImageView image10;
    @FXML
    private ImageView image20;
    @FXML
    private ImageView image01;
    @FXML
    private ImageView image11;
    @FXML
    private ImageView image21;
    @FXML
    private ImageView image02;
    @FXML
    private ImageView image12;
    @FXML
    private ImageView image22;
    @FXML
    private ImageView image03;
    @FXML
    private ImageView image13;
    @FXML
    private ImageView image23;

    @FXML
    private Button goBackButton;

    @FXML
    private Button row1Button;
    @FXML
    private Rectangle row1Img;

    @FXML
    private Button row2Button;
    @FXML
    private Rectangle row2Img;

    @FXML
    private Button row3Button;
    @FXML
    private Rectangle row3Img;

    @FXML
    private Button col1Button;
    @FXML
    private Rectangle col1Img;
    @FXML
    private Button col2Button;
    @FXML
    private Rectangle col2Img;
    @FXML
    private Button col3Button;
    @FXML
    private Rectangle col3Img;
    @FXML
    private Button col4Button;
    @FXML
    private Rectangle col4Img;



    public ControllerMarket(ViewManager viewManager) {
        this.view = viewManager;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setGoBackButton();

        setMarbles();

        setChooseButtons();

    }

    /**
     * this method invokes all the methods to set the listeners for each row and column
     */
    private void setChooseButtons() {
        setRow1();
        setRow2();
        setRow3();

        setColumn1();
        setColumn2();
        setColumn3();
        setColumn4();

    }

    /**
     * this method set the listeners and deals with the communication with the server for the button to start choose the first row
     */
    private void setRow1() {


        row1Button.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {

                view.sendIntegerToServer(1);//sceglie il turno qui!!

                view.sendIntegerToServer(1); // 1= row
                view.sendIntegerToServer(1); // 1 = first row

            }
        });

        row1Button.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                row1Img.setOpacity(0.7);
            }
        });

        row1Button.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                row1Img.setOpacity(0.3);
            }
        });
    }

    /**
     * this method set the listeners and deals with the communication with the server for the button to start choose the second row
     */
    private void setRow2() {

        row2Button.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {

                view.sendIntegerToServer(1);//sceglie il turno qui!!

                view.sendIntegerToServer(1); // 1= row
                view.sendIntegerToServer(2); // 2 = second row

            }
        });

        row2Button.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                row2Img.setOpacity(0.7);
            }
        });

        row2Button.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                row2Img.setOpacity(0.3);
            }
        });
    }

    /**
     * this method set the listeners and deals with the communication with the server for the button to start choose the third row
     */
    private void setRow3() {

        row3Button.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {

                view.sendIntegerToServer(1);//sceglie il turno qui!!

                view.sendIntegerToServer(1); // 1= row
                view.sendIntegerToServer(3); // 3 = third row

            }
        });

        row3Button.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                row3Img.setOpacity(0.7);
            }
        });

        row3Button.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                row3Img.setOpacity(0.3);
            }
        });
    }


    /**
     * this method set the listeners and deals with the communication with the server for the button to start choose the first column
     */
    private void setColumn1() {

        col1Button.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {

                view.sendIntegerToServer(1);//sceglie il turno qui!!

                view.sendIntegerToServer(2); // 2= column
                view.sendIntegerToServer(1); // 1= first column

            }
        });

        col1Button.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                col1Img.setOpacity(0.7);
            }
        });

        col1Button.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                col1Img.setOpacity(0.3);
            }
        });
    }

    /**
     * this method set the listeners and deals with the communication with the server for the button to start choose the second column
     */
    private void setColumn2() {

        col2Button.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {

                view.sendIntegerToServer(1);//sceglie il turno qui!!

                view.sendIntegerToServer(2); // 2= column
                view.sendIntegerToServer(2); // 2= second column

            }
        });

        col2Button.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                col2Img.setOpacity(0.7);
            }
        });

        col2Button.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                col2Img.setOpacity(0.3);
            }
        });

    }

    /**
     * this method set the listeners and deals with the communication with the server for the button to start choose the third column
     */
    private void setColumn3() {

        col3Button.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {

                view.sendIntegerToServer(1);//sceglie il turno qui!!

                view.sendIntegerToServer(2); // 2= column
                view.sendIntegerToServer(3); // 3= third column

            }
        });

        col3Button.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                col3Img.setOpacity(0.7);
            }
        });

        col3Button.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                col3Img.setOpacity(0.3);
            }
        });

    }

    /**
     * this method set the listeners and deals with the communication with the server for the button to start choose the fourth column
     */
    private void setColumn4() {

        col4Button.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {

                view.sendIntegerToServer(1);//sceglie il turno qui!!

                view.sendIntegerToServer(2); // 2= column
                view.sendIntegerToServer(4); // 4= fourth column

            }
        });

        col4Button.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                col4Img.setOpacity(0.7);
            }
        });

        col4Button.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                col4Img.setOpacity(0.3);
            }
        });

    }


    /**
     * this method set the images for all the marbles of the market
     */
    private void setMarbles() {

        image00.setImage(new Image(view.getGuiAdapter().getMarble(0,0).getImageUrl()));
        image10.setImage(new Image(view.getGuiAdapter().getMarble(1,0).getImageUrl()));
        image20.setImage(new Image(view.getGuiAdapter().getMarble(2,0).getImageUrl()));

        image01.setImage(new Image(view.getGuiAdapter().getMarble(0,1).getImageUrl()));
        image11.setImage(new Image(view.getGuiAdapter().getMarble(1,1).getImageUrl()));
        image21.setImage(new Image(view.getGuiAdapter().getMarble(2,1).getImageUrl()));

        image02.setImage(new Image(view.getGuiAdapter().getMarble(0,2).getImageUrl()));
        image12.setImage(new Image(view.getGuiAdapter().getMarble(1,2).getImageUrl()));
        image22.setImage(new Image(view.getGuiAdapter().getMarble(2,2).getImageUrl()));

        image03.setImage(new Image(view.getGuiAdapter().getMarble(0,3).getImageUrl()));
        image13.setImage(new Image(view.getGuiAdapter().getMarble(1,3).getImageUrl()));
        image23.setImage(new Image(view.getGuiAdapter().getMarble(2,3).getImageUrl()));

        extraMarbleImg.setImage(new Image((view.getGuiAdapter().getMarble(4,4).getImageUrl())));

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


}
