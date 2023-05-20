package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.view.gui.ViewManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Screen;

import java.net.URL;
import java.util.ResourceBundle;


public class ControllerScene1 implements Initializable {


    @FXML
    private TextField firstTextField;
    @FXML
    private TextField secondTextField;
    @FXML
    private AnchorPane backgroundPane;

    private static final double WIDTH=Screen.getPrimary().getVisualBounds().getWidth();  //1024;
    private static final double HEIGHT=Screen.getPrimary().getVisualBounds().getHeight();   //768;
    @FXML
    private StackPane stackPane;
    @FXML
    private AnchorPane scrollPane;

    @FXML
    private Label labelFirst;
    @FXML
    private Label labelSecond;
    private boolean chooseLobby = true; //this value will be retrieved from the server
    @FXML
    private GridPane paneOnTheRoll;

    @FXML
    private Label nickTakenLabel;


    private final ViewManager view;
    private static String ipString=null;
    private static Integer portString=0;
    private static Integer size=0;
    private static String nickName=null;


    public ControllerScene1(ViewManager view) {
        this.view=view;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Image image = new Image("/images/graphicstuff/background.png");//, WIDTH /*this is the horizontal position*/, HEIGHT/*this is the vertical position*/, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(WIDTH,HEIGHT, false, false, false, false/*this is the parameter to adjust if we don't want that it resize with the window*/));


        stackPane.setPrefSize(WIDTH,HEIGHT-35);
        stackPane.setBackground(new Background(backgroundImage) );

        scrollPane.setLayoutX(WIDTH/2-250);
        scrollPane.setLayoutY(HEIGHT/4);

        startNicknameLobbySizeScene();

    }

    /**
     * this method sets the components on the scene first scene for asking the nickname of the player and in case the size of the lobby
     */
    public void startNicknameLobbySizeScene() {

        nickTakenLabel.setOpacity(0);

        paneOnTheRoll.add(createStartButton(), 2, 2);


        labelFirst.setText("Nickname: ");
        firstTextField.clear();
        firstTextField.setPromptText("Your Nickname");


         secondTextField.setOpacity(0);
         labelSecond.setOpacity(0);

         chooseLobby = view.getGuiAdapter().getLobbySize();

         if(chooseLobby){
                labelSecond.setText("Lobby size: ");
                labelSecond.setOpacity(1);
                secondTextField.clear();
                secondTextField.setOpacity(0.7);
                secondTextField.setPromptText("Number of Players");
         }


    }


    /**
     * creates a button, adds all the listeners and then returns it to be displayed in the scene
     * @return the button created
     */
    private Button createStartButton(){
        Button startButton = new Button("Start");
        startButton.setOpacity(0.7);
        startButton.setFont(Font.font("Algerian", 12));
        startButton.setPrefSize(169,22);
        startButton.setStyle("-fx-text-base-color: #ff9901;");

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                nickName = firstTextField.getCharacters().toString();
                if (chooseLobby) {
                    if (!secondTextField.getCharacters().toString().equals("")){
                        size = Integer.parseInt(secondTextField.getCharacters().toString());
                    if (!nickName.equals("")) {

                        if( !(view.getGuiAdapter().getListOfPlayers().contains(nickName)) ) {

                        if ((!secondTextField.getCharacters().toString().equals("")) && (size < 5 && size > 0)) {
                            view.sendIntegerToServer(size);

                            view.sendNameToServer(nickName);

                            //here happens the change of scene
                            if (size != 1) {
                                waitingFullLobby();
                            }

                        }
                        } else{
                            nickTakenLabel.setDisable(false);
                            nickTakenLabel.setOpacity(1);
                        }

                        }

                    }
                }
                else{
                    if(!nickName.equals("")) {
                        if( !(view.getGuiAdapter().getListOfPlayers().contains(nickName)) ) {
                            view.sendNameToServer(nickName);
                            //here happens the change of scene
                            waitingFullLobby();
                        }else{
                               nickTakenLabel.setOpacity(1);
                             }

                    }
                }



            }
        });

        return startButton;
    }

    /**
     * this method loads the scene for waiting the other players to join the lobby
     */
    private void waitingFullLobby(){

        paneOnTheRoll.getChildren().clear();

        Label waitinglabel = new Label("Waiting for other players . . .");
        waitinglabel.setFont(Font.font("Algerian", 22));

        waitinglabel.setLayoutX(65.0);
        waitinglabel.setLayoutY(150.0);


        scrollPane.getChildren().add(waitinglabel);


    }


}
