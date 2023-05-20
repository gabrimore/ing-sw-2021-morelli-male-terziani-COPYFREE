package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.player.warehouse.Resources;
import it.polimi.ingsw.view.gui.ViewManager;
import it.polimi.ingsw.view.gui.customizedcomponents.ResourcePicker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Screen;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerSceneChooseInitialLeadersAndResources implements Initializable {

    private static final double WIDTH= Screen.getPrimary().getVisualBounds().getWidth();  //1024;
    private static final double HEIGHT=Screen.getPrimary().getVisualBounds().getHeight();   //768;

    private static final int INITIALLEADERDECKCHOICE=3;

    @FXML
    private StackPane stackPane;
    @FXML
    private AnchorPane  scrollPane;
    @FXML
    private AnchorPane paneOnTheScroll;
    @FXML
    private HBox leaderHorizontalPane;
    @FXML
    private VBox vbox1, vbox2, vbox3, vbox4;
    @FXML
    private Button startButton;

    @FXML
    private Label labelChoose;
    @FXML
    private ImageView leaderCard1;
    @FXML
    private CheckBox firstCheck;
    @FXML
    private ImageView leaderCard2;
    @FXML
    private CheckBox secondCheck;
    @FXML
    private ImageView leaderCard3;
    @FXML
    private CheckBox thirdCheck;
    @FXML
    private ImageView leaderCard4;
    @FXML
    private CheckBox fourthCheck;

    private VBox firstChoice, secondChoice;
    private String firstRes, secondRes=null;
    int i=1;
    int lc1=0, lc2=0;
    private final ViewManager view;


    public ControllerSceneChooseInitialLeadersAndResources(ViewManager view) {
        this.view=view;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Image image = new Image("/images/graphicstuff/background.png");//, WIDTH /*this is the horizontal position*/, HEIGHT/*this is the vertical position*/, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(WIDTH,HEIGHT, false, false, false, false/*this is the parameter to adjust if we don't want that it resize with the window*/));


        stackPane.setPrefSize(WIDTH,HEIGHT-35);
        stackPane.setBackground(new Background(backgroundImage) );

        scrollPane.setLayoutX(WIDTH/4.3);
        scrollPane.setLayoutY(HEIGHT/6.5);

        labelChoose.setLayoutX(WIDTH/2 - 450);
        labelChoose.setLayoutY(HEIGHT/6.5 + 15);


        leaderHorizontalPane.setLayoutX(WIDTH/12 - 40);
        leaderHorizontalPane.setLayoutY(HEIGHT/6.5+55);
        leaderHorizontalPane.setPrefSize(750,250);

        setLeaderCards();

        startButton.setLayoutX(WIDTH/4.3- 40);
        startButton.setLayoutY(HEIGHT/6.5+310);
        startButton.setPrefSize(140 , 30);
        startButton.setOpacity(0.7);

        chooseStartupLeaderCards();
    }

    /**
     * this method sets the images of the four leader cards that can be chosen at the start of the game
     */
    private void setLeaderCards() {
        ArrayList<String> initialLeaderUrls=view.getGuiAdapter().getCardUrls(INITIALLEADERDECKCHOICE);

        leaderCard1.setImage(new Image(initialLeaderUrls.get(0)));
        leaderCard2.setImage(new Image(initialLeaderUrls.get(1)));
        leaderCard3.setImage(new Image(initialLeaderUrls.get(2)));
        leaderCard4.setImage(new Image(initialLeaderUrls.get(3)));

    }

    /**
     * this method sets the listeners for the leader cards and the checkbox, besides it handles the variables that contain the leaders chosen
     */
    private void chooseStartupLeaderCards() {

        ArrayList<VBox> listOfLeaders = new ArrayList<>();
        listOfLeaders.add(vbox1);
        listOfLeaders.add(vbox2);
        listOfLeaders.add(vbox3);
        listOfLeaders.add(vbox4);

        for (VBox leaderCard : listOfLeaders) {

            leaderCard.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    leaderCard.setEffect(new DropShadow());
                }
            });

            leaderCard.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    leaderCard.setEffect(null);
                }
            });

            leaderCard.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    setOnlyTwoChoosen(leaderCard, (CheckBox) leaderCard.getChildren().get(1));
                }
            });
        }

        ///BUTTON
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //if(player turn !=0){
                // here we switch from choosing the leader cards to choosing the resources

                if(firstCheck.isSelected()){lc1=1;}
                if(secondCheck.isSelected()){
                    if(lc1==0) lc1=2;
                        else lc2=2;}
                if(thirdCheck.isSelected()){
                    if(lc1==0) lc1=3;
                    else lc2=3;}
                if(fourthCheck.isSelected()){
                    if(lc1==0) lc1=4;
                    else lc2=4;}

                if( lc1 !=0 && lc2!=0){
                    view.sendIntegerToServer(lc1);
                    view.sendIntegerToServer(lc2);
                    chooseStartupResources(); // here happens the change
                }else{
                    lc1=0; lc2=0;
                }
            }

        });

    }


    /**
     * this method is used in "chooseStartupLeaderCards()"
     * it oblige to choose only two of the four leaders
     */
    private void setOnlyTwoChoosen( VBox choice, CheckBox checkBox) {

        if(checkBox.isSelected()) {
            checkBox.setSelected(false);

            if(firstChoice!=null) {
                if (firstChoice.equals(choice)) firstChoice = null;
            }
            else {
                secondChoice=null;
            }


        }else{
            if(firstChoice!=null && secondChoice!=null) {
                if(i==1){
                    ((CheckBox)firstChoice.getChildren().get(1)).setSelected(false);
                    firstChoice=choice;
                    i=2;
                }else{
                    ((CheckBox)secondChoice.getChildren().get(1)).setSelected(false);
                    secondChoice=choice;
                    i=1;
                }

            }
            if(firstChoice!=null && secondChoice==null) {
                secondChoice=choice;
            }
            if(firstChoice==null && secondChoice!=null) {
                firstChoice = choice;
            }
            if(firstChoice==null && secondChoice==null) {
                firstChoice = choice;
            }
            checkBox.setSelected(true);
        }

    }

    /**
     * this method sets the listeners for the leader cards and the checkbox, besides it handles the variables that contain the leaders chosen
     */
    private void chooseStartupResources() {
        paneOnTheScroll.getChildren().clear();

        labelChoose.setText("Choose a resource: ");

        Button startupButton = new Button("Startup");
        startupButton.setLayoutX(WIDTH/4.3+180);
        startupButton.setLayoutY(HEIGHT/6.5+310);
        startupButton.setPrefSize(140 , 30);
        startupButton.setOpacity(0.7);
        startupButton.setFont(Font.font("Algerian"));
        startupButton.setStyle("-fx-text-base-color: #ff9901;");


        VBox vertical = new VBox();
        vertical.setSpacing(20);
        vertical.setLayoutX(WIDTH/12 - 40);
        vertical.setLayoutY(HEIGHT/6.5+8);

        int turn = view.getGuiAdapter().getMyTurn();

        HBox faithBox = new HBox();
        Label faithLabel = new Label(" + ");
        faithLabel.setFont(Font.font("Verdana", 35));
        ImageView faitImg = new ImageView("/images/resourcesgame/faithImg.png");
        faitImg.setPreserveRatio(true);
        faitImg.setFitWidth(40);

        switch (turn){
            case 1://this case goes directly to the plancia di gioco
                try {
                    view.gameBoardScene();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            case 2:
                labelChoose.setText("Choose a resource: ");
                vertical.getChildren().add(labelChoose);
                vertical.getChildren().add(createToogleOfResourcepickers(true));
                break;

            case 3:
                labelChoose.setText("Choose a resource: ");
                vertical.getChildren().add(labelChoose);
                vertical.getChildren().add(createToogleOfResourcepickers(true));

                faithBox.getChildren().add(faithLabel);
                faithBox.getChildren().add(faitImg );
                vertical.getChildren().add(faithBox);
                break;

            case 4:
                labelChoose.setText("Choose a resource: ");
                vertical.getChildren().add(labelChoose);
                vertical.getChildren().add(createToogleOfResourcepickers(true));
                Label secondLabel = new Label(" Choose a second resource:  ");
                secondLabel.setFont(Font.font("Algerian", 15));
                vertical.getChildren().add(secondLabel);
                vertical.getChildren().add(createToogleOfResourcepickers(false));


                faithBox.getChildren().add(faithLabel);
                faithBox.getChildren().add(faitImg );
                vertical.getChildren().add(faithBox);
                break;

        }

        paneOnTheScroll.getChildren().add(startupButton);
        paneOnTheScroll.getChildren().add(vertical);

        ///BUTTON
        startupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                try {
                    // here we change to the third scene
                    view.gameBoardScene();

                    ArrayList<Resources.ResType> resourcesList = new ArrayList<>();
                    resourcesList.add( fromStringToResType(firstRes) );
                    if(secondRes!=null)resourcesList.add(fromStringToResType(secondRes));
                   view.sceneFloatingMenuPutResInShelves(resourcesList, true);

                } catch (IOException e) {e.printStackTrace();}

            }
        });

    }

    // isFirst variable is useful to initialize the resource picker depending on which turn is
    // because only the forth player can choose between two resources while the remaining can choose
    // only 1 resource. isFrist= true sets the first variable and isSecond sets the second
    /**
     * this method creates a toogle of resources with their radio button.
     * @param isFirst if true sets only the first toggle of resources, otherwise creates two toggle(4th player has two!)
     */
    private HBox createToogleOfResourcepickers(Boolean isFirst){
        ToggleGroup group = new ToggleGroup();

        ResourcePicker coin = new ResourcePicker(Resources.ResType.COIN);
        coin.getRadioButton().setToggleGroup(group);
        coin.getRadioButton().setSelected(true);

        ResourcePicker servant = new ResourcePicker(Resources.ResType.SERVANT);
        servant.getRadioButton().setToggleGroup(group);

        ResourcePicker shield = new ResourcePicker(Resources.ResType.SHIELD);
        shield.getRadioButton().setToggleGroup(group);

        ResourcePicker stone = new ResourcePicker(Resources.ResType.STONE);
        stone.getRadioButton().setToggleGroup(group);


        if (isFirst) {
            firstRes = "COIN";
        }
        else{
            secondRes = "COIN";
        }

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) {

                RadioButton rb = (RadioButton)group.getSelectedToggle();

                if (isFirst) {
                    firstRes = rb.getText();
                }else{
                    secondRes = rb.getText();
                }

            }

        });


        HBox horizontal = new HBox();
        horizontal.setSpacing(40);
        horizontal.getChildren().add(coin);
        horizontal.getChildren().add(servant);
        horizontal.getChildren().add(shield);
        horizontal.getChildren().add(stone);


        return horizontal;
    }

    /**
     * this method receive a string with the name of the resource and returns the equivalent res type object
     * @param resString the type of resource
     * @return res type equivalent of the string received
     */
    private Resources.ResType fromStringToResType(String resString){

        switch(resString){
            case "COIN": return Resources.ResType.COIN;
            case "SERVANT": return Resources.ResType.SERVANT;
            case "SHIELD": return Resources.ResType.SHIELD;
        }
        return Resources.ResType.STONE;
    }


}