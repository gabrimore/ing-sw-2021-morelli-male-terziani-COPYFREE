package it.polimi.ingsw.view.gui.customizedcomponents;

import it.polimi.ingsw.model.player.warehouse.Resources;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class ResourcePicker extends VBox {


    private final ImageView resImage;
    private final RadioButton radioButton;

    public ResourcePicker(Resources.ResType resource){
        this.radioButton= new RadioButton();
        radioButton.setText(resource.getType(resource.toString()));
        radioButton.setFont(Font.font("Verdana"));

        resImage = new ImageView(resource.getImageUrl());
        resImage.setFitWidth(50);
        if(resource.equals(Resources.ResType.SHIELD))resImage.setFitWidth(40);
        resImage.setPreserveRatio(true);


        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
        this.getChildren().add(resImage);
        this.getChildren().add(radioButton);

        setListenersToImage();
    }

    /**
     * this method sets the listeners for the image of the resource
     */
    private void setListenersToImage() {

        resImage.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                resImage.setEffect(new DropShadow());
            }
        });

        resImage.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                resImage.setEffect(null);
            }
        });

        resImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                radioButton.setSelected(true);
            }
        });

    }

    /**
     * this method return the radio button associated to this picker
     */
    public RadioButton getRadioButton(){
        return radioButton;
    }

}
