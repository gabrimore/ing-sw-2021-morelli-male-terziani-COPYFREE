package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.game.Double;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ControllerWinnerMenu implements Initializable {

    @FXML
    private Button goBackButton;

    @FXML
    private HBox hbox1Player;
    @FXML
    private Label player1Label;
    @FXML
    private Label pointPlayer1Label;

    @FXML
    private HBox hbox2Player;
    @FXML
    private Label player2Label;
    @FXML
    private Label pointPlayer2Label;

    @FXML
    private HBox hbox3Player;
    @FXML
    private Label player3Label;
    @FXML
    private Label pointPlayer3Label;

    @FXML
    private HBox hbox4Player;
    @FXML
    private Label player4Label;
    @FXML
    private Label pointPlayer4Label;

    @FXML
    private Label winnerLabel;
    @FXML
    private Label nameOfTheWinnerLabel;

    private ArrayList<Double<String, Integer>> victoryP;


    public ControllerWinnerMenu(ArrayList<Double<String, Integer>> victoryP) {

        this.victoryP= victoryP;



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        winnerLabel.setText("The winner is:");

        nameOfTheWinnerLabel.setText(victoryP.get(0).getName() );// the list is already ordered on the server side, so the winner is in position 0 of the list

        setRanking(victoryP);

    }

    /**
     * This method sets the ranking of the players ordered by number of points made
     * @param victoryP it is an array list which contains the couple (nickname of the player, number of points he made)
     */
    private void setRanking(ArrayList<Double<String, Integer>> victoryP) {

        switch(victoryP.size()){
            case 2:
                hbox3Player.setDisable(true);
                hbox4Player.setDisable(true);

                player1Label.setText("1)@"+victoryP.get(0).getName());
                if(victoryP.get(0).getPosition() != 999){
                    pointPlayer1Label.setText(""+victoryP.get(0).getPosition());
                }else{
                    pointPlayer1Label.setText("");
                }

                player2Label.setText("2)@"+victoryP.get(1).getName());
                if(victoryP.get(1).getPosition() != 999) {
                    pointPlayer2Label.setText("" + victoryP.get(1).getPosition());
                }else{
                    pointPlayer2Label.setText("");
                }

                break;

            case 3:
                hbox4Player.setDisable(true);

                player1Label.setText("1)@"+victoryP.get(0).getName());
                pointPlayer1Label.setText(""+victoryP.get(0).getPosition());

                player2Label.setText("2)@"+victoryP.get(1).getName());
                pointPlayer2Label.setText(""+victoryP.get(1).getPosition());

                player3Label.setText("3)@"+victoryP.get(2).getName());
                pointPlayer3Label.setText(""+victoryP.get(2).getPosition());

                break;

            case 4:
                player1Label.setText("1)@"+victoryP.get(0).getName());
                pointPlayer1Label.setText(""+victoryP.get(0).getPosition());

                player2Label.setText("2)@"+victoryP.get(1).getName());
                pointPlayer2Label.setText(""+victoryP.get(1).getPosition());

                player3Label.setText("3)@"+victoryP.get(2).getName());
                pointPlayer3Label.setText(""+victoryP.get(2).getPosition());

                player4Label.setText("4)@"+victoryP.get(3).getName());
                pointPlayer4Label.setText(""+victoryP.get(3).getPosition());
                break;

        }


    }

}
