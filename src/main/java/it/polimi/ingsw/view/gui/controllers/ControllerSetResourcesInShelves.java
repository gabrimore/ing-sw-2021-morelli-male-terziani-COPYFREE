package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.player.warehouse.Resources;
import it.polimi.ingsw.view.gui.ViewManager;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static it.polimi.ingsw.view.gui.ViewManager.fromResourceToIndex;


public class ControllerSetResourcesInShelves implements Initializable {


    private ArrayList<Resources.ResType> resourcesList = new ArrayList<>();
    
    private final ViewManager view;
    private String resourceChosen;

    private int indexOfTheChosenResource = -1;
    private int chosenCheckFourthPlayer = 0;


    @FXML
    private Button discardButton;
    
    @FXML
    private ImageView leaderCardImage1;
    @FXML
    private ImageView leaderCardImage2;
    @FXML
    private VBox listOfResourcesVertical;
    @FXML
    private AnchorPane paneOnScroll;

    @FXML
    private Button buttonToChooseFirstShelf;
    @FXML
    private Rectangle firstShelfBlurImage;
    @FXML
    private ImageView imageOfTheResourceFirstShelf;

    @FXML
    private HBox hbox2;
    @FXML
    private Button buttonToChooseSecondShelf;
    @FXML
    private Rectangle secondShelfBlurImage;
    @FXML
    private ImageView imageOfTheResourceSecondShelf1;
    @FXML
    private ImageView imageOfTheResourceSecondShelf2;

    @FXML
    private HBox hbox3;
    @FXML
    private Button buttonToChooseThirdShelf;
    @FXML
    private Rectangle thirdShelfBlurImage;
    @FXML
    private ImageView imageOfTheResourceThirdShelf1;
    @FXML
    private ImageView imageOfTheResourceThirdShelf2;
    @FXML
    private ImageView imageOfTheResourceThirdShelf3;

    @FXML
    private ImageView switchArrow12;
    @FXML
    private ImageView switchArrow23;
    @FXML
    private ImageView switchArrow13;

    @FXML
    private Button buttonToChooseLeadeCard1;
    @FXML
    private Rectangle leaderCardBlur1;
    @FXML
    private ImageView res1Leader1;
    @FXML
    private ImageView res2Leader1;

    @FXML
    private Button buttonToChooseLeadeCard2;
    @FXML
    private Rectangle leaderCardBlur2;
    @FXML
    private ImageView res1Leader2;
    @FXML
    private ImageView res2Leader2;

    @FXML
    private HBox hboxLead1;
    @FXML
    private HBox hboxLead2;

    private boolean isStartGame=false;

    public ControllerSetResourcesInShelves(ViewManager view, ArrayList<Resources.ResType> resourcesList, boolean isStartGame) {
        this.isStartGame=isStartGame;
        this.view= view;
        this.resourcesList= resourcesList;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // sets the list of resource which has to be allocated or discarded
        setResourceListToAllocate(resourcesList);

        //SHELVES: sets the resource image and disable the button or give the effect to the button
        setFirstShelf();
        setSecondShelf();
        setThirdShelf();

        //discard button
        if(!isStartGame){setListenersDiscardButton();}
        else{ discardButton.setDisable(true);
              discardButton.setOpacity(0);
            }
        
        //switch Shelves
        setListenersSwitchShelves();

        // LeaderCards
        setLeaderCards();
    }

    /**
     * this method set the listeners and deals with the communication with the server for leader cards that enables the extra shelves
     */
    private void setLeaderCards() {

        // first leader card
        buttonToChooseLeadeCard1.setDisable(true);
        leaderCardBlur1.setDisable(true);
        res1Leader1.setDisable(true);
        res2Leader1.setDisable(true);
        leaderCardBlur1.setOpacity(0);

        //second leader card
        buttonToChooseLeadeCard2.setDisable(true);
        leaderCardBlur2.setDisable(true);
        res1Leader2.setDisable(true);
        res2Leader2.setDisable(true);
        leaderCardBlur2.setOpacity(0);

        if(view.getGuiAdapter().getShelf(3) != null){

            hboxLead1.toBack();
            leaderCardImage1.setImage(new Image(view.getGuiAdapter().getCardUrls(7).get(0)));

            buttonToChooseLeadeCard1.setDisable(false);
            leaderCardBlur1.setDisable(false);
            leaderCardBlur1.setOpacity(0.3);

            buttonToChooseLeadeCard1.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    if (resourceChosen != null && indexOfTheChosenResource != -1) {
                    if (view.getGuiAdapter().isPossible(resourceChosen, 3)) {
                        view.sendIntegerToServer(indexOfTheChosenResource); // index of resource choosen
                        view.sendIntegerToServer(3); // 3 = set in shelf
                        view.sendIntegerToServer(4); // 3 = index of shelf

                        listOfResourcesVertical.getChildren().remove(indexOfTheChosenResource);
                        resourcesList.remove(indexOfTheChosenResource);

                        indexOfTheChosenResource = -1;
                        resourceChosen = null;

                        try {
                            if (resourcesList.isEmpty()) {
                                view.gameBoardScene();
                            } else {
                                view.sceneFloatingMenuPutResInShelves(resourcesList, false);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
            });

            buttonToChooseLeadeCard1.setOnMouseEntered(new EventHandler() {
                @Override
                public void handle(Event event) {
                    leaderCardBlur1.setOpacity(0.8);
                }
            });

            buttonToChooseLeadeCard1.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    leaderCardBlur1.setOpacity(0.3);
                }
            });

            if( view.getGuiAdapter().getShelf(3).getShelfNumberOfResources()>0) {
                res1Leader1.setDisable(false);
                res1Leader1.setImage(new Image(view.getGuiAdapter().getShelf(3).getShelfCurrentType().getImageUrl()));

                if (view.getGuiAdapter().getShelf(3).getShelfNumberOfResources()>1){
                    res2Leader1.setDisable(false);
                    res2Leader1.setImage(new Image(view.getGuiAdapter().getShelf(3).getShelfCurrentType().getImageUrl()));

                    leaderCardBlur1.setDisable(true);
                    buttonToChooseLeadeCard1.setDisable(true);

                } }

        }


        // second leader card

        if(view.getGuiAdapter().getShelf(4) != null){
            hboxLead2.toBack();
            leaderCardImage2.setImage(new Image(view.getGuiAdapter().getCardUrls(7).get(1)));
            buttonToChooseLeadeCard2.setDisable(false);
            leaderCardBlur2.setDisable(false);
            leaderCardBlur2.setOpacity(0.3);

            buttonToChooseLeadeCard2.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    if (resourceChosen != null && indexOfTheChosenResource != -1) {
                        if (view.getGuiAdapter().isPossible(resourceChosen, 4)) {
                            view.sendIntegerToServer(indexOfTheChosenResource); // index of resource choosen
                            view.sendIntegerToServer(3); // 3 = set in shelf
                            view.sendIntegerToServer(5); // 3 = index of shelf

                            listOfResourcesVertical.getChildren().remove(indexOfTheChosenResource);
                            resourcesList.remove(indexOfTheChosenResource);

                            indexOfTheChosenResource = -1;
                            resourceChosen = null;

                            try {
                                if (resourcesList.isEmpty()) {
                                    view.gameBoardScene();
                                } else {
                                    view.sceneFloatingMenuPutResInShelves(resourcesList, false);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            });

            buttonToChooseLeadeCard2.setOnMouseEntered(new EventHandler() {
                @Override
                public void handle(Event event) {
                    leaderCardBlur2.setOpacity(0.8);
                }
            });

            buttonToChooseLeadeCard2.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    leaderCardBlur2.setOpacity(0.3);
                }
            });

            if( view.getGuiAdapter().getShelf(4).getShelfNumberOfResources()>0) {
                res1Leader2.setDisable(false);
                res1Leader2.setImage(new Image(view.getGuiAdapter().getShelf(4).getShelfCurrentType().getImageUrl()));

                if (view.getGuiAdapter().getShelf(4).getShelfNumberOfResources()>1){
                    res2Leader2.setDisable(false);
                    res2Leader2.setImage(new Image(view.getGuiAdapter().getShelf(4).getShelfCurrentType().getImageUrl()));

                    leaderCardBlur2.setDisable(true);
                    buttonToChooseLeadeCard2.setDisable(true);
                } }

        }


    }

    /**
     * this method invokes all the methods to set the listeners for each arrow that allows to switch the content between two shelves
     */
    private void setListenersSwitchShelves() {

        setListenersArrowsImg(switchArrow12, 0,1);
        setListenersArrowsImg(switchArrow23, 1,2);
        setListenersArrowsImg(switchArrow13, 0,2);

    }

    /**
     * this method set the listeners and deals with the communication with the server for the button to start choose the first row
     * @param arrow imageview of the arrow on which the listeners will be applied
     * @param firstShelf index of the first chosen shelf to switch
     * @param secondShelf index of the second chosen shelf to switch
     */
    private void setListenersArrowsImg(ImageView arrow, int firstShelf, int secondShelf){

        arrow.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if(  !(view.getGuiAdapter().getShelf(firstShelf).getShelfCurrentType()==null && view.getGuiAdapter().getShelf(secondShelf).getShelfCurrentType()==null) ){

                view.sendIntegerToServer(0); // index of resource choosen, we send 0 because the request of the shelves comes immediately after so we need to choose a resource before doing anything
                view.sendIntegerToServer(1); // 1 = switch shelves
                view.sendIntegerToServer(firstShelf+1);
                view.sendIntegerToServer(secondShelf+1);

                try {
                    view.sceneFloatingMenuPutResInShelves(resourcesList, isStartGame);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            }

        });

        arrow.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                arrow.setEffect(new DropShadow());
            }
        });

        arrow.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                arrow.setEffect(null);
                arrow.setEffect(new InnerShadow());
            }
        });
    }

    /**
     * this method set the listeners and deals with the communication with the server for the button to discard a resource
     */
    private void setListenersDiscardButton() {

        discardButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                if (resourceChosen != null && indexOfTheChosenResource != -1) {
                view.sendIntegerToServer(indexOfTheChosenResource); // index of resource choosen
                view.sendIntegerToServer(2); // 2 = discard Resource

                listOfResourcesVertical.getChildren().remove(indexOfTheChosenResource);
                resourcesList.remove(indexOfTheChosenResource);
                indexOfTheChosenResource = -1;
                resourceChosen = null;


                try {
                    if (resourcesList.isEmpty()) {
                        view.gameBoardScene();
                    } else {
                        view.sceneFloatingMenuPutResInShelves(resourcesList, false);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }}
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
     * this method set the listeners and deals with the communication with the server for the first shelf
     * it sets two overlapped nodes: a button and an image view, in the case there's already the resource, the button is disabled and the image is displayed.
     * Otherwise the image view is disabled and the button activated
     */
    private void setFirstShelf() {
        //resource present in the model so upload the image
        if( view.getGuiAdapter().getShelf(0).getShelfCurrentType() != null){

            imageOfTheResourceFirstShelf.setImage(new Image(view.getGuiAdapter().getShelf(0).getShelfCurrentType().getImageUrl()));
            firstShelfBlurImage.setDisable(true);
            firstShelfBlurImage.setOpacity(0);
            imageOfTheResourceFirstShelf.setEffect(null);
            buttonToChooseFirstShelf.setDisable(true);
        }
        else{
            // there is no res so activate the button to choose
            imageOfTheResourceFirstShelf.setDisable(true);

            buttonToChooseFirstShelf.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {

                    if (resourceChosen != null) {

                        if (view.getGuiAdapter().isPossible(resourceChosen, 0)) {
                            view.sendIntegerToServer(indexOfTheChosenResource); // index of resource choosen
                            if (!isStartGame) {
                                view.sendIntegerToServer(3);
                            } // 3 = set in shelf
                            view.sendIntegerToServer(1); // 1 = index of shelf


                            if (isStartGame) {
                                if (chosenCheckFourthPlayer != 0) {
                                    listOfResourcesVertical.getChildren().remove(chosenCheckFourthPlayer);
                                    resourcesList.remove(chosenCheckFourthPlayer);
                                } else {
                                    listOfResourcesVertical.getChildren().remove(0);
                                    resourcesList.remove(0);
                                }

                                indexOfTheChosenResource = -1;
                                resourceChosen = null;
                            } else {
                                listOfResourcesVertical.getChildren().remove(indexOfTheChosenResource);
                                resourcesList.remove(indexOfTheChosenResource);

                                indexOfTheChosenResource = -1;
                                resourceChosen = null;
                            }
                            try {
                                if (resourcesList.isEmpty()) {
                                    view.gameBoardScene();
                                } else {
                                    view.sceneFloatingMenuPutResInShelves(resourcesList, false);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                }
            });

            buttonToChooseFirstShelf.setOnMouseEntered(new EventHandler() {
                @Override
                public void handle(Event event) {
                    firstShelfBlurImage.setOpacity(0.8);
                }
            });

            buttonToChooseFirstShelf.setOnMouseExited(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event){
                    firstShelfBlurImage.setOpacity(0.3);
                }
            });
        }

    }

    /**
     * this method set the listeners and deals with the communication with the server for the second shelf
     * it sets two overlapped nodes: a button and an image view, in the case there are already the resources, the button is disabled and the image is displayed.
     * Otherwise the image view are disabled and the button activated
     */
    private void setSecondShelf() {

        hbox2.toBack();
        imageOfTheResourceSecondShelf1.setDisable(true);
        imageOfTheResourceSecondShelf2.setDisable(true);

        buttonToChooseSecondShelf.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {

                if (resourceChosen != null) {

                    if (view.getGuiAdapter().isPossible(resourceChosen, 1)) {
                        view.sendIntegerToServer(indexOfTheChosenResource); // index of resource choosen

                        if (!isStartGame) {
                            view.sendIntegerToServer(3);  // 3 = set in shelf
                        }

                        view.sendIntegerToServer(2); // 2 = index of shelf

                        if(isStartGame) {
                            if (chosenCheckFourthPlayer != 0) {
                                listOfResourcesVertical.getChildren().remove(chosenCheckFourthPlayer);
                                resourcesList.remove(chosenCheckFourthPlayer);
                            } else {
                                listOfResourcesVertical.getChildren().remove(0);
                                resourcesList.remove(0);
                            }

                            indexOfTheChosenResource = -1;
                            resourceChosen = null;
                        } else {
                            listOfResourcesVertical.getChildren().remove(indexOfTheChosenResource);
                            resourcesList.remove(indexOfTheChosenResource);

                            indexOfTheChosenResource = -1;
                            resourceChosen = null;
                        }

                        try {
                            if (resourcesList.isEmpty()) {
                                view.gameBoardScene();
                            } else {
                                view.sceneFloatingMenuPutResInShelves(resourcesList, false);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }
            }
        });

        buttonToChooseSecondShelf.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                secondShelfBlurImage.setOpacity(0.8);
            }
        });

        buttonToChooseSecondShelf.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                secondShelfBlurImage.setOpacity(0.3);
            }
        });

        if(view.getGuiAdapter().getShelf(1).getShelfNumberOfResources() > 0 ) {
            imageOfTheResourceSecondShelf1.setDisable(false);
            imageOfTheResourceSecondShelf1.setEffect(null);
            imageOfTheResourceSecondShelf1.setImage(new Image(view.getGuiAdapter().getShelf(1).getShelfCurrentType().getImageUrl()));

            if (view.getGuiAdapter().getShelf(1).getShelfNumberOfResources() > 1){
                imageOfTheResourceSecondShelf2.setDisable(false);
                imageOfTheResourceSecondShelf2.setEffect(null);
                imageOfTheResourceSecondShelf2.setImage(new Image(view.getGuiAdapter().getShelf(1).getShelfCurrentType().getImageUrl()));
                secondShelfBlurImage.setDisable(true);
                secondShelfBlurImage.setOpacity(0);
                buttonToChooseSecondShelf.setDisable(true);
            } }


    }

    /**
     * this method set the listeners and deals with the communication with the server for the third shelf
     * it sets two overlapped nodes: a button and an image view, in the case there are already the resources, the button is disabled and the image is displayed.
     * Otherwise the image view are disabled and the button activated
     */
    private void setThirdShelf() {

        hbox3.toBack();
        imageOfTheResourceThirdShelf1.setDisable(true);
        imageOfTheResourceThirdShelf2.setDisable(true);
        imageOfTheResourceThirdShelf3.setDisable(true);

        buttonToChooseThirdShelf.setOnAction(new EventHandler() {

            @Override
            public void handle(Event event) {

                if (resourceChosen != null) {
                    if (view.getGuiAdapter().isPossible(resourceChosen, 2)) {
                        view.sendIntegerToServer(indexOfTheChosenResource); // index of resource choosen
                        if (!isStartGame) {
                            view.sendIntegerToServer(3);
                        } // 3 = set in shelf
                        view.sendIntegerToServer(3); // 3 = index of shelf

                        if (isStartGame) {
                            if (chosenCheckFourthPlayer != 0) {
                                listOfResourcesVertical.getChildren().remove(chosenCheckFourthPlayer);
                                resourcesList.remove(chosenCheckFourthPlayer);
                            } else {
                                listOfResourcesVertical.getChildren().remove(0);
                                resourcesList.remove(0);
                            }

                            indexOfTheChosenResource = -1;
                            resourceChosen = null;
                        } else {
                            listOfResourcesVertical.getChildren().remove(indexOfTheChosenResource);
                            resourcesList.remove(indexOfTheChosenResource);

                            indexOfTheChosenResource = -1;
                            resourceChosen = null;
                        }

                        try {
                            if (resourcesList.isEmpty()) {
                                view.gameBoardScene();
                            } else {
                                view.sceneFloatingMenuPutResInShelves(resourcesList, false);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }
            }
        });

        buttonToChooseThirdShelf.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                thirdShelfBlurImage.setOpacity(0.8);
            }
        });

        buttonToChooseThirdShelf.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                thirdShelfBlurImage.setOpacity(0.3);
            }
        });

        if( view.getGuiAdapter().getShelf(2).getShelfNumberOfResources() > 0) {
            imageOfTheResourceThirdShelf1.setDisable(false);
            imageOfTheResourceThirdShelf1.setEffect(null);
            imageOfTheResourceThirdShelf1.setImage(new Image(view.getGuiAdapter().getShelf(2).getShelfCurrentType().getImageUrl()));

            if (view.getGuiAdapter().getShelf(2).getShelfNumberOfResources() > 1){
                imageOfTheResourceThirdShelf2.setDisable(false);
                imageOfTheResourceThirdShelf2.setEffect(null);
                imageOfTheResourceThirdShelf2.setImage(new Image(view.getGuiAdapter().getShelf(2).getShelfCurrentType().getImageUrl()));

                if (view.getGuiAdapter().getShelf(2).getShelfNumberOfResources() > 2){
                    imageOfTheResourceThirdShelf3.setDisable(false);
                    imageOfTheResourceThirdShelf3.setEffect(null);
                    imageOfTheResourceThirdShelf3.setImage(new Image(view.getGuiAdapter().getShelf(2).getShelfCurrentType().getImageUrl()));
                    thirdShelfBlurImage.setDisable(true);
                    buttonToChooseThirdShelf.setDisable(true);
                }
            }

        }


    }

    /**
     * this method invokes all the methods to set the listeners for each resource of the list: creates a button for each resource and sets the image
     * @param resourcesList it is the array list of res type that contains the resources to be allocated in the shelves or to be discarded
     */
    private void setResourceListToAllocate(ArrayList<Resources.ResType> resourcesList){
       Image image1, image2, image3, image4;
       ImageView imageView1, imageView2, imageView3, imageView4;
       Button b1,b2,b3,b4;

        switch (resourcesList.size()){
           case 1:
               //BUTTON 1
               image1 = new Image(resourcesList.get(0).getImageUrl());
               imageView1 = new ImageView();
               imageView1.setImage(image1);
               imageView1.setFitHeight(63.0);
               imageView1.setFitWidth(55.0);
               imageView1.setPreserveRatio(true);

               b1 = new Button("",imageView1);
               b1.setPrefSize(60.0, 87.0);
               b1.setStyle("-fx-background-color: trasparent;");

               addListenersToResourceButton(b1, resourcesList.get(0).getType( resourcesList.get(0).toString() ));

               listOfResourcesVertical.getChildren().add(b1);
               break;

           case 2:
               //BUTTON 1
               image1 = new Image(resourcesList.get(0).getImageUrl());
               imageView1 = new ImageView();
               imageView1.setImage(image1);
               imageView1.setFitHeight(63.0);
               imageView1.setFitWidth(55.0);
               imageView1.setPreserveRatio(true);

               b1 = new Button("",imageView1);
               b1.setPrefSize(60.0, 87.0);
               b1.setStyle("-fx-background-color: trasparent;");

               addListenersToResourceButton(b1,  resourcesList.get(0).getType( resourcesList.get(0).toString()));

               listOfResourcesVertical.getChildren().add(b1);

               //BUTTON 2
               image2 = new Image(resourcesList.get(1).getImageUrl());
               imageView2 = new ImageView();
               imageView2.setImage(image2);
               imageView2.setFitHeight(63.0);
               imageView2.setFitWidth(55.0);
               imageView2.setPreserveRatio(true);

               b2 = new Button("",imageView2);
               b2.setPrefSize(60.0, 87.0);
               b2.setStyle("-fx-background-color: trasparent;");

               addListenersToResourceButton(b2,  resourcesList.get(1).getType( resourcesList.get(1).toString()));

               listOfResourcesVertical.getChildren().add(b2);
               break;

            case 3:
                //BUTTON 1
                image1 = new Image(resourcesList.get(0).getImageUrl());
                imageView1 = new ImageView();
                imageView1.setImage(image1);
                imageView1.setFitHeight(63.0);
                imageView1.setFitWidth(55.0);
                imageView1.setPreserveRatio(true);

                b1 = new Button("",imageView1);
                b1.setPrefSize(60.0, 87.0);
                b1.setStyle("-fx-background-color: trasparent;");

                addListenersToResourceButton(b1,  resourcesList.get(0).getType( resourcesList.get(0).toString()));

                listOfResourcesVertical.getChildren().add(b1);

                //BUTTON 2
                image2 = new Image(resourcesList.get(1).getImageUrl());
                imageView2 = new ImageView();
                imageView2.setImage(image2);
                imageView2.setFitHeight(63.0);
                imageView2.setFitWidth(55.0);
                imageView2.setPreserveRatio(true);

                b2 = new Button("",imageView2);
                b2.setPrefSize(60.0, 87.0);
                b2.setStyle("-fx-background-color: trasparent;");

                addListenersToResourceButton(b2,  resourcesList.get(1).getType( resourcesList.get(1).toString()));

                listOfResourcesVertical.getChildren().add(b2);

                //BUTTON 3
                image3 = new Image(resourcesList.get(2).getImageUrl());
                imageView3 = new ImageView();
                imageView3.setImage(image3);
                imageView3.setFitHeight(63.0);
                imageView3.setFitWidth(55.0);
                imageView3.setPreserveRatio(true);

                b3 = new Button("",imageView3);
                b3.setPrefSize(60.0, 87.0);
                b3.setStyle("-fx-background-color: trasparent;");

                addListenersToResourceButton(b3,  resourcesList.get(2).getType( resourcesList.get(2).toString()));

                listOfResourcesVertical.getChildren().add(b3);
                break;

            case 4:
                //BUTTON 1
                image1 = new Image(resourcesList.get(0).getImageUrl());
                imageView1 = new ImageView();
                imageView1.setImage(image1);
                imageView1.setFitHeight(63.0);
                imageView1.setFitWidth(55.0);
                imageView1.setPreserveRatio(true);

                b1 = new Button("",imageView1);
                b1.setPrefSize(60.0, 87.0);
                b1.setStyle("-fx-background-color: trasparent;");

                addListenersToResourceButton(b1,  resourcesList.get(0).getType( resourcesList.get(0).toString()));

                listOfResourcesVertical.getChildren().add(b1);

                //BUTTON 2
                image2 = new Image(resourcesList.get(1).getImageUrl());
                imageView2 = new ImageView();
                imageView2.setImage(image2);
                imageView2.setFitHeight(63.0);
                imageView2.setFitWidth(55.0);
                imageView2.setPreserveRatio(true);

                b2 = new Button("",imageView2);
                b2.setPrefSize(60.0, 87.0);
                b2.setStyle("-fx-background-color: trasparent;");

                addListenersToResourceButton(b2,  resourcesList.get(1).getType( resourcesList.get(1).toString()));

                listOfResourcesVertical.getChildren().add(b2);

                //BUTTON 3
                image3 = new Image(resourcesList.get(2).getImageUrl());
                imageView3 = new ImageView();
                imageView3.setImage(image3);
                imageView3.setFitHeight(63.0);
                imageView3.setFitWidth(55.0);
                imageView3.setPreserveRatio(true);

                b3 = new Button("",imageView3);
                b3.setPrefSize(60.0, 87.0);
                b3.setStyle("-fx-background-color: trasparent;");

                addListenersToResourceButton(b3,  resourcesList.get(2).getType( resourcesList.get(2).toString()));

                listOfResourcesVertical.getChildren().add(b3);

                //BUTTON 4
                image4 = new Image(resourcesList.get(3).getImageUrl());
                imageView4 = new ImageView();
                imageView4.setImage(image4);
                imageView4.setFitHeight(63.0);
                imageView4.setFitWidth(55.0);
                imageView4.setPreserveRatio(true);

                b4 = new Button("",imageView4);
                b4.setPrefSize(60.0, 87.0);
                b4.setStyle("-fx-background-color: trasparent;");

                addListenersToResourceButton(b4,  resourcesList.get(3).getType( resourcesList.get(3).toString()));

                listOfResourcesVertical.getChildren().add(b4);
                break;

       }

    }

    /**
     * this method set the listeners for the buttons to choose a resource
     * @param button it is the node( in this case the button) on which to set the listeners
     * @param res it is the string containing the name of the resource that is associated with the button
     */
    private void addListenersToResourceButton(Button button, String res){

        button.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {

                if(isStartGame){
                    resourceChosen = res;
                    indexOfTheChosenResource = fromResourceToIndex(resourceChosen);
                    chosenCheckFourthPlayer = listOfResourcesVertical.getChildren().indexOf(button);
                   }
                else {
                    indexOfTheChosenResource = listOfResourcesVertical.getChildren().indexOf(button);
                    resourceChosen = res;
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


}
