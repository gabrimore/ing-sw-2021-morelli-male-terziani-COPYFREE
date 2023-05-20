package it.polimi.ingsw.view.gui;


import it.polimi.ingsw.model.game.Double;
import it.polimi.ingsw.model.player.warehouse.Resources;
import it.polimi.ingsw.network.ServerHandler;
import it.polimi.ingsw.view.gui.controllers.*;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;



public class    ViewManager {

    private boolean isMyTurn;

    private GameBoardController controllerGameBoard;
    private final GuiAdapter guiAdapter;

    private static final double WIDTH= Screen.getPrimary().getVisualBounds().getWidth();      //1024;
    private static final double HEIGHT= Screen.getPrimary().getVisualBounds().getHeight();   //768;

    // The STAGE contains only one SCENE and a scene can contain multiple PANEs.
    private final Stage mainStage;
    private final Scene mainScene;
    private final AnchorPane mainPane;


    public ViewManager(GuiAdapter adapter) throws IOException {

        guiAdapter = adapter;

        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT-35 );
        mainStage= new Stage();
        mainStage.setScene(mainScene);


        //Once the gui has started, the view manages is istantiated and so the first scene can be loaded.
        // The first scene is the one which asks for the nickName of the player and, in case of the first client which connects, the Lobby size
        firstScene();

    }


    /////////////////////////// SCENES \\\\\\\\\\\\\\\\\\\\\\\\\\

    /**
     * uploads onto the stage the first scene in which we ask for the nick name and the lobby size
     * @throws IOException in case the scene loaded from the FXML specified is not found
     */
    private void firstScene() throws IOException {
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/scenesFXML/initialScene.fxml"));
        loader1.setController(new ControllerScene1(this));
        Parent root = loader1.load();

        mainStage.setScene(new Scene(root));

    }

    /**
     * uploads onto the stage the scene in which we ask for the initial leader cards to be chosen and in case, the initial resources
     * @throws IOException in case the scene loaded from the FXML specified is not found
     */
    public void secondSceneChooseInitialLeadersAndResources() throws IOException {
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/scenesFXML/chooseInitialLeaderCards.fxml"));
        loader2.setController(new ControllerSceneChooseInitialLeadersAndResources(this));
        Parent root = loader2.load();

        mainStage.setScene(new Scene(root));

    }

    /**
     * uploads the most important scene of the game, the game board, the one in which everything is displayed
     * @throws IOException in case the scene loaded from the FXML specified is not found
     */
    public void gameBoardScene() throws IOException {
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/scenesFXML/gameScene.fxml"));
        controllerGameBoard = new GameBoardController(this);
        loader3.setController(controllerGameBoard);

        Parent root = loader3.load();

        mainStage.setScene(new Scene(root));

    }

    /**
     * uploads onto the stackPane of the gameBoardScene the scene in which we give the possibility to store the resources in the shelves
     * @param resourcesList it is the list of the resources that has to be settled in the shelves
     * @param isStartGame if it is true it allows to disable the option to discard the resource
     * @throws IOException in case the scene loaded from the FXML specified is not found
     */
    public void sceneFloatingMenuPutResInShelves(ArrayList<Resources.ResType> resourcesList, boolean isStartGame) throws IOException{

        FXMLLoader loaderShelves = new FXMLLoader();
        loaderShelves.setLocation(GameBoardController.class.getResource("/scenesFXML/floatingMenuShelvesRes.fxml"));
        loaderShelves.setController(new ControllerSetResourcesInShelves(this, resourcesList, isStartGame) );
        Parent stackPaneLoadedFXML = loaderShelves.load();


        controllerGameBoard.getFloatingMenuPane().toFront();
        controllerGameBoard.getFloatingMenuPane().getChildren().setAll(stackPaneLoadedFXML);

    }

    /**
     * uploads onto the stackPane of the gameBoardScene the scene in which we give the possibility to choose the turn the player wants to perform
     * @throws IOException in case the scene loaded from the FXML specified is not found
     */
    public void sceneFloatingMenuChooseTurnType() throws IOException{

        FXMLLoader loaderTurn = new FXMLLoader();
        loaderTurn.setLocation(GameBoardController.class.getResource("/scenesFXML/floatingMenuChooseTypeOfTurn.fxml"));
        loaderTurn.setController(new ControllerChooseTurnType(this) );
        Parent stackPaneLoadedFXML = loaderTurn.load();

        controllerGameBoard.getFloatingMenuPane().toFront();
        controllerGameBoard.getFloatingMenuPane().getChildren().setAll(stackPaneLoadedFXML);

    }

    /**
     * uploads onto the stackPane of the gameBoardScene the scene in which we allow the player to take resources from the market
     * @throws IOException in case the scene loaded from the FXML specified is not found
     */
    public void sceneFloatingMenuMarket() throws IOException{

        FXMLLoader loaderMarket = new FXMLLoader();
        loaderMarket.setLocation(GameBoardController.class.getResource("/scenesFXML/floatingMenuMarket.fxml"));
        loaderMarket.setController(new ControllerMarket(this) );
        Parent stackPaneLoadedFXML = loaderMarket.load();

        controllerGameBoard.getFloatingMenuPane().toFront();
        controllerGameBoard.getFloatingMenuPane().getChildren().setAll(stackPaneLoadedFXML);

    }

    /**
     * uploads onto the stackPane of the gameBoardScene the scene in which we give the possibility to choose activate or discard the leader cards
     * @throws IOException in case the scene loaded from the FXML specified is not found
     */
    public void sceneFloatingMenuActivateDiscardLeaderCard() throws IOException{

        FXMLLoader loaderLeaderCard = new FXMLLoader();
        loaderLeaderCard.setLocation(GameBoardController.class.getResource("/scenesFXML/floatingMenuActivateDiscardLeaderCard.fxml"));
        loaderLeaderCard.setController(new ControllerActivateDiscardLeaderCard(this) );
        Parent stackPaneLoadedFXML = loaderLeaderCard.load();

        controllerGameBoard.getFloatingMenuPane().toFront();
        controllerGameBoard.getFloatingMenuPane().getChildren().setAll(stackPaneLoadedFXML);

    }

    /**
     * uploads onto the stackPane of the gameBoardScene the scene in which we give the possibility to buy the development cards of which the player has the resources
     * @throws IOException in case the scene loaded from the FXML specified is not found
     */
    public void sceneFloatingMenuBuyDevCard() throws IOException{

        FXMLLoader loaderDevCard = new FXMLLoader();
        loaderDevCard.setLocation(GameBoardController.class.getResource("/scenesFXML/floatingMenuBuyDevCard.fxml"));
        loaderDevCard.setController(new ControllerBuyDevCard(this) );
        Parent stackPaneLoadedFXML = loaderDevCard.load();

        controllerGameBoard.getFloatingMenuPane().toFront();
        controllerGameBoard.getFloatingMenuPane().getChildren().setAll(stackPaneLoadedFXML);

    }

    /**
     * uploads onto the stackPane of the gameBoardScene the scene in which we give the possibility to choose a column of the develpment space
     * This scene is utilized in two case dependig on the value of isBuyDev.
     * @param isBuyDev  if it's true: allows to choose the column where you can place the development card after having bought it
     *                  if it's false: allows to choose the column(actually the top card of such column) from where you want produce during the production turn
     * @throws IOException in case the scene loaded from the FXML specified is not found
     */
    public void sceneFloatingMenuChooseColumnDevCards(boolean isBuyDev) throws IOException{

        FXMLLoader loaderChooseDevCard = new FXMLLoader();
        loaderChooseDevCard.setLocation(GameBoardController.class.getResource("/scenesFXML/floatingMenuChooseColumnDevCard.fxml"));
        loaderChooseDevCard.setController(new ControllerChooseColumnDevCard(this, isBuyDev) );
        Parent stackPaneLoadedFXML = loaderChooseDevCard.load();

        controllerGameBoard.getFloatingMenuPane().toFront();
        controllerGameBoard.getFloatingMenuPane().getChildren().setAll(stackPaneLoadedFXML);

    }

    /**
     * uploads onto the stackPane of the gameBoardScene the scene in which we give the possibility to choose which kind of production the player wants to perform
     * @throws IOException in case the scene loaded from the FXML specified is not found
     */
    public void sceneFloatingChooseProduction() throws IOException{

        FXMLLoader loaderProduction = new FXMLLoader();
        loaderProduction.setLocation(GameBoardController.class.getResource("/scenesFXML/floatingMenuChooseProduction.fxml"));
        loaderProduction.setController(new ControllerChooseProduction(this) );
        Parent stackPaneLoadedFXML = loaderProduction.load();

        controllerGameBoard.getFloatingMenuPane().toFront();
        controllerGameBoard.getFloatingMenuPane().getChildren().setAll(stackPaneLoadedFXML);

    }

    /**
     * uploads onto the stackPane of the gameBoardScene the scene in which we give the possibility to choose in which resource to convert the marble in the case two leader cards, with this effect, are active at the same time
     * @throws IOException in case the scene loaded from the FXML specified is not found
     */
    private void switchToWhiteMarbleChangerScene(int whiteMarble) throws IOException {
    FXMLLoader loaderWhiteMarble = new FXMLLoader();
    loaderWhiteMarble.setLocation(GameBoardController.class.getResource("/scenesFXML/floatingMenuWhiteMarbleChanger.fxml"));
    loaderWhiteMarble.setController(new ControllerWhiteMarble(this, whiteMarble) );
    Parent stackPaneLoadedFXML = loaderWhiteMarble.load();

    controllerGameBoard.getFloatingMenuPane().toFront();
    controllerGameBoard.getFloatingMenuPane().getChildren().setAll(stackPaneLoadedFXML);
}

    /**
     * uploads onto the stackPane of the gameBoardScene the scene in which we show the ranking of the players highlighting who is the winner
     * @param victoryP it is the list of the players, containing the nick name and the points reached by each of them, ordered by points
     * @throws IOException in case the scene loaded from the FXML specified is not found
     */
    public void sceneFloatingMenuWinner(ArrayList<Double<String, Integer>> victoryP) throws IOException{

        FXMLLoader loaderProduction = new FXMLLoader();
        loaderProduction.setLocation(GameBoardController.class.getResource("/scenesFXML/floatingMenuWinner.fxml"));
        loaderProduction.setController(new ControllerWinnerMenu(victoryP) );
        Parent stackPaneLoadedFXML = loaderProduction.load();

        controllerGameBoard.getFloatingMenuPane().toFront();
        controllerGameBoard.getFloatingMenuPane().getChildren().setAll(stackPaneLoadedFXML);

    }

    /**
     * uploads onto the stackPane of the gameBoardScene the scene in which we show which is the token that has been extracted with it's effect
     * @param urlImgToken it is the url of the image of the token that has been extracted
     * @param text it is the textual description of the token that has been extracted
     * @throws IOException in case the scene loaded from the FXML specified is not found
     */
    public void sceneFloatingTokenSoloGame(String urlImgToken, String text) throws IOException{

        FXMLLoader loaderProduction = new FXMLLoader();
        loaderProduction.setLocation(GameBoardController.class.getResource("/scenesFXML/floatingTokenSoloGame.fxml"));
        loaderProduction.setController(new ControllerTokenSoloGame(this, urlImgToken, text) );
        Parent stackPaneLoadedFXML = loaderProduction.load();

        controllerGameBoard.getFloatingMenuPane().toFront();
        controllerGameBoard.getFloatingMenuPane().getChildren().setAll(stackPaneLoadedFXML);

    }



    ////////////////////////////// GETTERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    /**
     * it is used by the controllers of the different scenes to communicate with the server by invoking viewManager.getGuiAdapter().*
     * @return the instance of the gui adapter that can communicate with the local model of the client
     */
    public GuiAdapter getGuiAdapter(){
        return this.guiAdapter;
    }

    /**
     * it is used by the controllers of the different scenes to set the scene on the stackPane of the gameboard
     * @return the instance of the gui adapter that can communicate with the local model of the client
     */
    public GameBoardController getControllerGameBoard(){
        return controllerGameBoard;
    }

    /**
     * it is used by the GUI main to show the stage
     * @return the instance of the gui adapter that can communicate with the local model of the client
     */
    public Stage getMainStage() {
        return this.mainStage;
    }

    /**
     * it is used by the GameBoardController to set the button that allows to return to the floating menu in which the player can choose which type of turn he wants to perform
     * @return the instance of the gui adapter that can communicate with the local model of the client
     */
    public boolean getIfIsMyTurn() {
        return isMyTurn;
    }



    ////////////////////////////// GETTERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    /**
     * This method is used to communicate the choices made by the user through the gui to the server
     * @param i is the int that has to be sent to the server
     */
    public void sendIntegerToServer(Integer i){
        ServerHandler.sendToServer(i);
    }

    /** This method is used to communicate the nickname choosen by the user through the gui to the server
     * @param name is the nick name choosen by the player
     */
    public void sendNameToServer(String name){
        ServerHandler.sendNameToServer(name);
    }



    //////////////////// invocations from the server side \\\\\\\\\\\\\\\\\\\\\\
    /** This method is invoked by the server to start the game once the lobby size is reached
     */
    public void startMatch(){

        Platform.runLater( () -> {
        try {
            secondSceneChooseInitialLeadersAndResources(); //this is what will happen once the size of the lobby is reached
        } catch (Exception e) {
            e.printStackTrace();
        }

        });

    }

    /** This method is invoked by the server to start the turn of the player
     */
    public void startTurn(){

        isMyTurn = true;



        Platform.runLater( () -> {

            try {
                gameBoardScene(); //this is what will happen because has to update the game board
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                sceneFloatingMenuChooseTurnType(); //this is what will happen once the size of the lobby is reached
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }

    /** This method is invoked by the server to update the game board
     */
    public void updateGameBoard(){

        isMyTurn = false;

        Platform.runLater( () -> {
            try {
                gameBoardScene(); //this is what will happen once the size of the lobby is reached
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }

    /** This method is invoked by the server to set the resources in the shelves. It is needed since we can't pass from the scene of the market ot the scene of set resources in shelves the list of the resources to set
     * @param resourcesList is the list of resources to store in the shelves
     */
    public void setResourcesInShelves(ArrayList<Resources.ResType> resourcesList){

        Platform.runLater( () -> {
            try {
                sceneFloatingMenuPutResInShelves(resourcesList, false); //this is what will happen once the size of the lobby is reached
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }

    /**
     * This method is invoked by the server to choose in which resource to convert the marble in the case two leader cards, with this effect, are active at the same time
     * @param whiteMarble index of the resource in the list of resources taken from the market
     */
    public void chooseWhiteMarbleEffect(int whiteMarble){

        Platform.runLater( () -> {
            try {
                switchToWhiteMarbleChangerScene(whiteMarble);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }

    /**
     * This method is invoked by the server to set the end of the game and so the scene with the rankings
     * @param victoryP it is the list of the players, containing the nick name and the points reached by each of them, ordered by points
     */
    public void winnerEndGame(ArrayList<Double<String, Integer>> victoryP){

        Platform.runLater( () -> {
            try {
                sceneFloatingMenuWinner(victoryP);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }

    /**
     * This method is invoked by the server to set the end of display which is the token that has been extracted with it's effect
     * @param urlImgToken it is the url of the image of the token that has been extracted
     * @param text it is the textual description of the token that has been extracted
     * */
    public void tokenExtracted(String urlImgToken, String text){

        Platform.runLater( () -> {
            try {
                sceneFloatingTokenSoloGame(urlImgToken,text );
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }



    /////////////////// UTILITIES

    /**
     * this method transforms the name of the resource in an index(int) which is the conventional representation for the resources in this program
     * @param res name of the resource to be converted in index
     * @return an int which is the conventional representation for the resources in this program
     */
    public static int fromResourceToIndex(String res){

        switch(res){
            case "COIN": return 1;
            case "SERVANT": return 2;
            case "SHIELD": return 3;
        }

        return 4;

    }

}
