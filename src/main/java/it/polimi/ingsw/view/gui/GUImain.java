package it.polimi.ingsw.view.gui;

import javafx.application.Application;
import javafx.stage.Stage;


public class GUImain extends Application {

    private ViewManager view;
    private static GuiAdapter guiAdapter;

    public static void main(String[] args, GuiAdapter adapter) {
        guiAdapter=adapter;
        launch();
    }

    /**
     * this method is invoked by the launch() in the static main method.
     * it initialize the view manager and prepare thee stage to be shown for the GUI
     * @param primaryStage it is the stage on which the scene will be uploaded
     */

    @Override
    public void start(Stage primaryStage) throws Exception{

        view = new ViewManager(guiAdapter);

        guiAdapter.setView(view);

        primaryStage = view.getMainStage(); //to obtain the stage
        primaryStage.setTitle("Masters of Renaissance: Lorenzo il Magnifico");
        primaryStage.show(); //to show the stage
    }


}
