package it.polimi.ingsw.model.game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.model.deck.Deck;
import it.polimi.ingsw.model.deck.carddecks.*;
import it.polimi.ingsw.model.deck.carddecks.leadercards.*;
import it.polimi.ingsw.model.deck.tokendeck.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.SlotDevelopment;
import it.polimi.ingsw.model.player.warehouse.Warehouse;
import it.polimi.ingsw.network.messages.DeckFieldMessage;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observer.Observable;

import java.io.*;
import java.util.ArrayList;


public class Game extends Observable<Message> {

    @Expose //@SerializedName("marketBoard")
    private final MarketBoard marketBoard;
    @Expose
    @SerializedName("players")
    private final ArrayList<Player> players;
    @Expose
    @SerializedName("soloActionDeck")
    private Deck soloActionDeck;
    @Expose
    @SerializedName("leaderDeck")
    private final Deck leaderDeck;

    private Deck[][] deckField;

    public Game() {
        this.marketBoard = new MarketBoard();
        this.players = new ArrayList<>();
        this.soloActionDeck = createSoloActionTokenDeck();
        createDeckField();
        this.leaderDeck = createLeaderDeck();
    }

    public void setDeckField(Deck[][] deckField) {
        this.deckField = deckField;
    }

    public int addNewPlayer(String namePlayer){
        if(players.isEmpty()) {
            players.add(new Player(namePlayer, true));
        } else if (players.size() < 4) {
            players.add(new Player(namePlayer, false));
        }
        return players.size();
    }


    private void createDeckField() {
        deckField = new Deck[3][4];
        deckField[0][0] = new Deck(DevelopmentCard.createDevelopmentDeck("/developmentdecks/green1Deck.json"));
        deckField[0][1] = new Deck(DevelopmentCard.createDevelopmentDeck("/developmentdecks/blue1Deck.json"));
        deckField[0][2] = new Deck(DevelopmentCard.createDevelopmentDeck("/developmentdecks/violet1Deck.json"));
        deckField[0][3] = new Deck(DevelopmentCard.createDevelopmentDeck("/developmentdecks/yellow1Deck.json"));
        deckField[1][0] = new Deck(DevelopmentCard.createDevelopmentDeck("/developmentdecks/green2Deck.json"));
        deckField[1][1] = new Deck(DevelopmentCard.createDevelopmentDeck("/developmentdecks/blue2Deck.json"));
        deckField[1][2] = new Deck(DevelopmentCard.createDevelopmentDeck("/developmentdecks/violet2Deck.json"));
        deckField[1][3] = new Deck(DevelopmentCard.createDevelopmentDeck("/developmentdecks/yellow2Deck.json"));
        deckField[2][0] = new Deck(DevelopmentCard.createDevelopmentDeck("/developmentdecks/green3Deck.json"));
        deckField[2][1] = new Deck(DevelopmentCard.createDevelopmentDeck("/developmentdecks/blue3Deck.json"));
        deckField[2][2] = new Deck(DevelopmentCard.createDevelopmentDeck("/developmentdecks/violet3Deck.json"));
        deckField[2][3] = new Deck(DevelopmentCard.createDevelopmentDeck("/developmentdecks/yellow3Deck.json"));

        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                deckField[i][j].shuffle();
            }
        }
    }

    public Deck createLeaderDeck(){

        ArrayList<LeaderCard> leaderCards = new ArrayList<>();

        leaderCards.addAll(Discount.createDiscountDeck("/leaderdecks/discountLeaderDeck.json"));
        leaderCards.addAll(ExtraSlot.createExtraSlotDeck("/leaderdecks/extraSlotLeaderDeck.json"));
        leaderCards.addAll(WhiteMarbleChanger.createWhiteMarbleChangerDeck("/leaderdecks/whiteMarbleChangerLeaderDeck.json"));
        leaderCards.addAll(AdditionalProduction.createAdditionalProdDeck("/leaderdecks/additionalProductionLeaderDeck.json"));

        Deck leaderDeck = new Deck(leaderCards);
        leaderDeck.shuffle();

        return leaderDeck;
    }

    private Deck createSoloActionTokenDeck() {

        ArrayList<SoloActionToken> tokens = new ArrayList<>();

        tokens.addAll(DiscardToken.createDiscardTokenDeck("/tokendecks/discardTokenDeck.json"));
        tokens.addAll(MoveForward2Token.createMoveForward2TokenDeck("/tokendecks/moveForward2TokenDeck.json"));
        tokens.addAll(MoveShuffleToken.createMoveShuffleTokenDeck("/tokendecks/moveShuffleTokenDeck.json"));

        Deck tokenDeck = new Deck(tokens);
        tokenDeck.shuffle();

        return tokenDeck;
    }

    public void remakeTokenDeck() {
        this.soloActionDeck = createSoloActionTokenDeck();
    }

    public void removeCard(int i, int j) throws EmptyDeckException {
        chooseDeck(i, j).draw();
        notify(new DeckFieldMessage(getTopCard()));
    }

    public Deck chooseDeck(int level, int color) {
        return deckField[level][color];
    }

    public Deck chooseDeck(int level, String color) {
        return chooseDeck(level, SlotDevelopment.colorToInt(color));
    }

    public Player getPlayer(int playerTurnIndicator) {
        return players.get(playerTurnIndicator);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public MarketBoard getMarketBoard() {
        return marketBoard;
    }

    public Deck getSoloActionDeck() {
        return soloActionDeck;
    }
    public Deck getLeaderDeck(){ return leaderDeck; }

    public Deck[][] getDeckField() {
        return deckField;
    }

    public void discardTokenEffect(int level, int color, int discardedCounter) throws EmptyDeckException {

        Deck deck = chooseDeck(level, color);

        while (discardedCounter < 2) {
            if (deck.getDeckSize() < 1) {
                throw new EmptyDeckException(level, color, discardedCounter);
            } else {
                deck.discardFromBottom();
                discardedCounter++;
            }
        }

    }

    public DevelopmentCard[][] getTopCard(){
        DevelopmentCard[][] topCardsDeckField = new DevelopmentCard[3][4];

        for(int level=0; level<3; level++ ) {
            for (int color = 0; color < 4; color++) {
                topCardsDeckField[level][color] = (DevelopmentCard) chooseDeck(level, color).getFirstCard();
            }
        }
        return topCardsDeckField;
    }

    /**
     * checks, for every first card of the deckField, if it is available sets the state to true
     * @param pl is a player
     */
    public ArrayList<Triple<DevelopmentCard, Integer, Integer>> checkDeckFieldTopCard(Player pl){
        ArrayList<Triple<DevelopmentCard, Integer, Integer>> topCards = new ArrayList<>();

        for(int level=0; level<3; level++ ){
            for(int color=0; color<4; color++){
                if(chooseDeck(level,color).getFirstCard() != null) {
                    chooseDeck(level, color).getFirstCard().checkRequirement(pl);

                    boolean set = false;

                    for (int k = 0; k < 3; k++) {
                        if (pl.getSlotDevelopment().checkIfCanSetCard((DevelopmentCard) chooseDeck(level, color).getFirstCard(), k)) {
                            set = true;
                        }
                    }

                    if (chooseDeck(level, color).getFirstCard().getState() && set) {
                        topCards.add(new Triple<>((DevelopmentCard) chooseDeck(level, color).getFirstCard(), level, color));
                    }
                }
            }
        }
        return topCards;
    }

    public ArrayList<DevelopmentCard> getPossibleDevCard(ArrayList<Triple<DevelopmentCard, Integer, Integer>> topCards){
        ArrayList<DevelopmentCard> possible = new ArrayList<>();

        for(Triple t : topCards){
            possible.add((DevelopmentCard) t.getCard());
        }

        return possible;
    }

    public ArrayList<Double<String, Integer>> playersPositions(){
       ArrayList<Double<String, Integer>> positions = new ArrayList<>();

       if(players.size() == 1){
           positions.add(new Double<>(players.get(0).getNickname(), players.get(0).getFaithTrack().getPosition()));
           positions.add(new Double<>("Lorenzo", players.get(0).getLorenzoPosition()));
       }else {
           for (Player pl : players) {
               positions.add(new Double<>(pl.getNickname(), pl.getFaithTrack().getPosition()));
           }
       }

       return positions;
    }


    public static void saveGame(Game game) throws IOException {


        ArrayList<String> names = new ArrayList<>();
        for (Player p : game.getPlayers()) names.add(p.getNickname());

        names.sort(String::compareToIgnoreCase);
        String namesPath = "";
        for (String name : names) {
            namesPath = namesPath + name;
        }

        File mastersOfRenaissanceDirectory = new File(getWorkingDirectory()+"\\MastersOfRenaissance");
        if(!mastersOfRenaissanceDirectory.exists()){
            mastersOfRenaissanceDirectory.mkdirs();
        }

        File gameDirectory = new File(mastersOfRenaissanceDirectory.getPath()+"\\" + namesPath);
        if (!gameDirectory.exists()) {
            gameDirectory.mkdirs();
        }

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Warehouse.class, new InterfaceAdapter());
        builder.registerTypeAdapter(LeaderCard.class, new AbstractLeaderCardAdapter());


        Gson gson = builder.excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();


        try (FileWriter writer = new FileWriter(gameDirectory.getPath() + "\\game.json")) {

            gson.toJson(game, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Game.saveDeckField(game, gameDirectory.getPath());

    }

    public static void saveDeckField(Game g, String gameDirectoryPath) throws IOException {

        File deckFieldDirectory = new File(gameDirectoryPath + "\\deckField");
        if (!deckFieldDirectory.exists()) {
            deckFieldDirectory.mkdirs();
        }

        for (int i = 0; i < g.deckField.length; i++) {
            for (int j = 0; j < g.deckField[i].length; j++) {
                ArrayList<DevelopmentCard> singleDeck = g.getDeckField()[i][j].getDeckItems();

                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                try (FileWriter writer = new FileWriter(deckFieldDirectory + "\\deck" + i + j + ".json")) {
                    gson.toJson(singleDeck, writer);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public static Game uploadGame(String namesPath) {

        Game uploadedGame = null;

        File mastersOfRenaissanceDirectory = new File(getWorkingDirectory()+"\\MastersOfRenaissance");
        if(!mastersOfRenaissanceDirectory.exists()){
            mastersOfRenaissanceDirectory.mkdirs();
        }

        File gameDirectory = new File(mastersOfRenaissanceDirectory.getPath()+"\\" + namesPath);
        if (!gameDirectory.exists()) {
            gameDirectory.mkdirs();
        }


            try (Reader reader = new FileReader(gameDirectory.getPath() + "\\game.json")) {


                GsonBuilder builder = new GsonBuilder();

                builder.registerTypeAdapter(Warehouse.class, new InterfaceAdapter());
                builder.registerTypeAdapter(LeaderCard.class, new AbstractLeaderCardAdapter());

                Gson gson = builder.create();

                uploadedGame = gson.fromJson(reader, Game.class);



            Deck[][] uploadedDeckField = new Deck[3][4];
            for (int i = 0; i < uploadedGame.getDeckField().length; i++) {
                for (int j = 0; j < uploadedGame.getDeckField()[i].length; j++) {
                    uploadedDeckField[i][j] = new Deck(DevelopmentCard.uploadDevelopmentDeck(gameDirectory.getPath() + "\\deckField\\deck" + i + j + ".json"));
                }
            }
            uploadedGame.setDeckField(uploadedDeckField);
            } catch (IOException e) {
                e.printStackTrace();
                return uploadedGame;
            }

        return uploadedGame;
    }

        public static String getWorkingDirectory(){
        String workingDirectory;

        String OS = (System.getProperty("os.name")).toUpperCase();

        if (OS.contains("WIN"))
        {

            workingDirectory = System.getenv("AppData");

        }

        else
        {

            workingDirectory = System.getProperty("user.home");

            workingDirectory += "/Library/Application Support/MastersOfRenaissance/";
        }

        return workingDirectory;
    }

    public static void deleteGameFile(String gameNamesPath) {

        String workingDirectory = getWorkingDirectory();
        File gameDirectory = new File(workingDirectory + "\\MastersOfRenaissance\\" + gameNamesPath);
        File deckDirectory= new File(gameDirectory.getPath()+"\\deckField");

        for (File file : deckDirectory.listFiles())
            if (!file.isDirectory()) {
                file.delete();
            }

        if(deckDirectory.delete())
        {
            System.out.println(" Deck Direct File deleted successfully");
        }
        else
        {
            System.out.println("Failed to delete the file deck direct");
        }

        File gameFile=new File(gameDirectory.getPath()+"\\game.json");

        if(gameFile.delete())
        {
            System.out.println("File deleted successfully game file");
        }
        else
        {
            System.out.println("Failed to delete the file game file");
        }



        if(gameDirectory.delete())
        {
            System.out.println("File deleted successfully game direct");
        }
        else
        {
            System.out.println("Failed to delete the file game direct");
        }
    }
}
