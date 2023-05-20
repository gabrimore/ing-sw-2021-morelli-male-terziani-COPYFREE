package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.player.warehouse.Resources;
import it.polimi.ingsw.view.gui.ViewManager;
import it.polimi.ingsw.view.gui.customizedcomponents.ResourcePicker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static it.polimi.ingsw.view.gui.ViewManager.fromResourceToIndex;

public class ControllerBaseProduction implements Initializable {

    private ViewManager view;

    private String input1 ="COIN", input2="COIN", output="COIN";

    @FXML
    private Button goBackButton;

    @FXML
    private RadioButton coinFirstButton;
    @FXML
    private RadioButton servantFirstButton;
    @FXML
    private RadioButton shieldFirstButton;
    @FXML
    private RadioButton stoneFirstButton;


    @FXML
    private RadioButton coinSecondButton;
    @FXML
    private RadioButton servantSecondButton;
    @FXML
    private RadioButton shieldSecondButton;
    @FXML
    private RadioButton stoneSecondButton;


    @FXML
    private RadioButton coinThirdButton;
    @FXML
    private RadioButton servantThirdButton;
    @FXML
    private RadioButton shieldThirdButton;
    @FXML
    private RadioButton stoneThirdButton;

    @FXML
    private Button produceButton;
    @FXML
    private Label productionDeniedLabel;

    @FXML
    private Label chooseInput1Label;
    @FXML
    private Label chooseInput2Label;
    @FXML
    private Label chooseOutputLabel;

    @FXML
    private VBox verticalBoxWithResourcePickers;



    public ControllerBaseProduction(ViewManager viewManager) {
        this.view=viewManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        setGoBackButton();


        verticalBoxWithResourcePickers.getChildren().add(createLabel("choose first input resource :"));
        verticalBoxWithResourcePickers.getChildren().add(setFirstInputResourcesToogle());

        verticalBoxWithResourcePickers.getChildren().add(createLabel("choose second input resource :"));
        verticalBoxWithResourcePickers.getChildren().add(setSecondInputResourcesToogle());

        verticalBoxWithResourcePickers.getChildren().add(createLabel("choose output resource:"));
        verticalBoxWithResourcePickers.getChildren().add(setOutputResourcesToogle());


        productionDeniedLabel.setOpacity(0);

        setProduceButton();

    }


    /**
     * This method creates a label with the text passed as argument and returns the object just created
     * @param text what the label will display
     * @return the label itself
     */
    private Label createLabel(String text){
        Label label = new Label();

        label.setText(text);
        label.setLayoutX(10.0);
        label.setLayoutY(132.0);
        label.setPrefSize(323.0, 26.0);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);
        label.setFont(new Font("Algerian", 15.0));

        return label;
    }

    /**
     * this method set the listeners and deals with the communication with the server for the produce button
     */
    private void setProduceButton() {

        produceButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {

                ArrayList<Resources> resourcesList = createListOfResources(fromResourceToIndex(input1), fromResourceToIndex(input2));

               try {

                    if (view.getGuiAdapter().checkAvailability(resourcesList)) {
                        view.sendIntegerToServer(2);
                        view.sendIntegerToServer(fromResourceToIndex(input1));
                        view.sendIntegerToServer(fromResourceToIndex(input2));
                        view.sendIntegerToServer(fromResourceToIndex(output));

                        view.getGuiAdapter().setBaseProduction(false);
                        view.sceneFloatingChooseProduction();
                    } else {
                        productionDeniedLabel.setOpacity(1);
                    }



                } catch (IOException e) {
                    e.printStackTrace();
                }

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
     * this method creates a list of resources from the indexes(int) of the resources chosen in order to exploit the "checkAvailability(listOfResources)" that tells whether the production can be done
     * @param in1 index of the first input resource
     * @param in2 index of the second input resource
     * @return an array list containing the resources created starting from the indexes passed as argument in this method
     */
    private ArrayList<Resources> createListOfResources(int in1, int in2){

        ArrayList<Resources> resourcesList = new ArrayList<>();

        Resources res1 =new Resources(Resources.RES_TYPES.get(in1-1),1 );
        Resources res2 =new Resources(Resources.RES_TYPES.get(in2-1),1 );

        if(res1.getResourceType().equals(res2.getResourceType())) {
            resourcesList.add( new Resources(Resources.RES_TYPES.get(in1-1),2 ) );
        }
        else{
            resourcesList.add(res1);
            resourcesList.add(res2);
        }

        return resourcesList;

    }

    /**
     * this method creates the toogle of radio buttons to choose the first input resource
     */
    /*
    private void setFirstInputResourcesToogle() {

        ToggleGroup groupFirstResources = new ToggleGroup();

        coinFirstButton.setText("COIN");
        coinFirstButton.setFont(Font.font("Verdana"));
        coinFirstButton.setToggleGroup(groupFirstResources);
        coinFirstButton.setSelected(true);

        servantFirstButton.setText("SERVANT");
        servantFirstButton.setFont(Font.font("Verdana"));
        servantFirstButton.setToggleGroup(groupFirstResources);

        shieldFirstButton.setText("SHIELD");
        shieldFirstButton.setFont(Font.font("Verdana"));
        shieldFirstButton.setToggleGroup(groupFirstResources);

        stoneFirstButton.setText("STONE");
        stoneFirstButton.setFont(Font.font("Verdana"));
        stoneFirstButton.setToggleGroup(groupFirstResources);


        groupFirstResources.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) {

                RadioButton rb = (RadioButton)groupFirstResources.getSelectedToggle();

                input1 = rb.getText();
            }
        });


    }
*/

    private HBox setFirstInputResourcesToogle(){
        ToggleGroup groupFirstResources = new ToggleGroup();

        ResourcePicker coin1 = new ResourcePicker(Resources.ResType.COIN);
        coin1.getRadioButton().setToggleGroup(groupFirstResources);
        coin1.getRadioButton().setSelected(true);

        ResourcePicker servant1 = new ResourcePicker(Resources.ResType.SERVANT);
        servant1.getRadioButton().setToggleGroup(groupFirstResources);

        ResourcePicker shield1 = new ResourcePicker(Resources.ResType.SHIELD);
        shield1.getRadioButton().setToggleGroup(groupFirstResources);

        ResourcePicker stone1 = new ResourcePicker(Resources.ResType.STONE);
        stone1.getRadioButton().setToggleGroup(groupFirstResources);


        groupFirstResources.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) {

                RadioButton rb = (RadioButton)groupFirstResources.getSelectedToggle();

                input1 = rb.getText();
            }
        });

        HBox horizontal = new HBox();
        horizontal.setSpacing(20);
        horizontal.getChildren().add(coin1);
        horizontal.getChildren().add(servant1);
        horizontal.getChildren().add(shield1);
        horizontal.getChildren().add(stone1);


        return horizontal;
    }

    /**
     * this method creates the toogle of radio buttons to choose the second input resource
     */
    /*
    private void setSecondInputResourcesToogle() {
        ToggleGroup groupSecondResources = new ToggleGroup();

        coinSecondButton.setText("COIN");
        coinSecondButton.setFont(Font.font("Verdana"));
        coinSecondButton.setToggleGroup(groupSecondResources);
        coinSecondButton.setSelected(true);

        servantSecondButton.setText("SERVANT");
        servantSecondButton.setFont(Font.font("Verdana"));
        servantSecondButton.setToggleGroup(groupSecondResources);

        shieldSecondButton.setText("SHIELD");
        shieldSecondButton.setFont(Font.font("Verdana"));
        shieldSecondButton.setToggleGroup(groupSecondResources);

        stoneSecondButton.setText("STONE");
        stoneSecondButton.setFont(Font.font("Verdana"));
        stoneSecondButton.setToggleGroup(groupSecondResources);


        groupSecondResources.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) {

                RadioButton rb = (RadioButton)groupSecondResources.getSelectedToggle();

                input2 = rb.getText();

            }

        });

    }
     */
    private HBox setSecondInputResourcesToogle(){
        ToggleGroup groupSecondResources = new ToggleGroup();

        ResourcePicker coin2 = new ResourcePicker(Resources.ResType.COIN);
        coin2.getRadioButton().setToggleGroup(groupSecondResources);
        coin2.getRadioButton().setSelected(true);

        ResourcePicker servant2 = new ResourcePicker(Resources.ResType.SERVANT);
        servant2.getRadioButton().setToggleGroup(groupSecondResources);

        ResourcePicker shield2 = new ResourcePicker(Resources.ResType.SHIELD);
        shield2.getRadioButton().setToggleGroup(groupSecondResources);

        ResourcePicker stone2 = new ResourcePicker(Resources.ResType.STONE);
        stone2.getRadioButton().setToggleGroup(groupSecondResources);


        groupSecondResources.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) {

                RadioButton rb = (RadioButton)groupSecondResources.getSelectedToggle();

                input2 = rb.getText();

            }

        });

        HBox horizontal = new HBox();
        horizontal.setSpacing(20);
        horizontal.getChildren().add(coin2);
        horizontal.getChildren().add(servant2);
        horizontal.getChildren().add(shield2);
        horizontal.getChildren().add(stone2);


        return horizontal;

    }

    /**
     * this method creates the toogle of radio buttons to choose the output resource
     */
    /*
    private void setOutputResourcesToogle() {

        ToggleGroup groupOutputResources = new ToggleGroup();

        coinThirdButton.setText("COIN");
        coinThirdButton.setFont(Font.font("Verdana"));
        coinThirdButton.setToggleGroup(groupOutputResources);
        coinThirdButton.setSelected(true);

        servantThirdButton.setText("SERVANT");
        servantThirdButton.setFont(Font.font("Verdana"));
        servantThirdButton.setToggleGroup(groupOutputResources);

        shieldThirdButton.setText("SHIELD");
        shieldThirdButton.setFont(Font.font("Verdana"));
        shieldThirdButton.setToggleGroup(groupOutputResources);

        stoneThirdButton.setText("STONE");
        stoneThirdButton.setFont(Font.font("Verdana"));
        stoneThirdButton.setToggleGroup(groupOutputResources);


        groupOutputResources.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) {

                RadioButton rb = (RadioButton)groupOutputResources.getSelectedToggle();

                output = rb.getText();

            }

        });


    }
     */
    private HBox setOutputResourcesToogle(){
        ToggleGroup groupOutputResources = new ToggleGroup();

        ResourcePicker coin3 = new ResourcePicker(Resources.ResType.COIN);
        coin3.getRadioButton().setToggleGroup(groupOutputResources);
        coin3.getRadioButton().setSelected(true);

        ResourcePicker servant3 = new ResourcePicker(Resources.ResType.SERVANT);
        servant3.getRadioButton().setToggleGroup(groupOutputResources);

        ResourcePicker shield3 = new ResourcePicker(Resources.ResType.SHIELD);
        shield3.getRadioButton().setToggleGroup(groupOutputResources);

        ResourcePicker stone3 = new ResourcePicker(Resources.ResType.STONE);
        stone3.getRadioButton().setToggleGroup(groupOutputResources);


        groupOutputResources.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) {

                RadioButton rb = (RadioButton)groupOutputResources.getSelectedToggle();

                output = rb.getText();

            }

        });

        HBox horizontal = new HBox();
        horizontal.setSpacing(20);
        horizontal.getChildren().add(coin3);
        horizontal.getChildren().add(servant3);
        horizontal.getChildren().add(shield3);
        horizontal.getChildren().add(stone3);


        return horizontal;

    }

    /**
     * this method set the listeners and deals with the communication with the server for the go back button
     */
    private void setGoBackButton(){

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

}
