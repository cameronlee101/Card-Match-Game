package Main;

import Entity.Card;
import Entity.Symbol;

/**
 * Handles all the game's logic
 */
public class GameLogicDriver {
    //******************************************************************************************************************
    //* variables
    //******************************************************************************************************************
    private static GamePanel[][] gamePanels;
    private static int gamePanelsDimension;

    //******************************************************************************************************************
    //* singleton constructor and methods
    //******************************************************************************************************************
    private static GameLogicDriver theGameLogicDriver = null;

    private GameLogicDriver() {}

    /**
     * Checks if an instance of GameLogicDriver exists yet. If it does, returns that instance. If not, creates and
     * returns a new instance
     * @return the GameLogicDriver object if it exists already, new GameLogicDriver object if it doesn't yet
     */
    public static GameLogicDriver getInstance() {
        if (theGameLogicDriver == null) {
            theGameLogicDriver = new GameLogicDriver();
        }
        return theGameLogicDriver;
    }

    //******************************************************************************************************************
    //* setters and getters
    //******************************************************************************************************************
    /**
     * Sets the GameLogicDriver's gamePanels variable
     * @param gamePanels the GamePanel to set this GameLogicDriver's gamePanels to
     */
    public static void setGamePanels(GamePanel[][] gamePanels) {
        GameLogicDriver.gamePanels = gamePanels;
    }

    /**
     * Sets the GameLogicDriver's gamePanelsDimension variable
     * @param gamePanelsDimension the int to set this GameLogicDriver'S gamePanelsDimension to
     */
    public static void setGamePanelsDimension(int gamePanelsDimension) {
        GameLogicDriver.gamePanelsDimension = gamePanelsDimension;
    }

    //******************************************************************************************************************
    //* methods
    //******************************************************************************************************************
    /**
     * Runs the appropriate methods to start the game
     */
    public static void startGame() {
        setupGame();
        Engine.getInstance().startGameThread();
    }

    /**
     * Creates and distributes the cards for each game panel
     */
    private static void setupGame() {
        for (int i = 0; i < gamePanelsDimension; i++) {
            for (int j = 0; j < gamePanelsDimension; j++) {
                gamePanels[i][j].setCard(new Card(Symbol.Heart));
            }
        }
    }

    /**
     * Calls update() on the appropriate objects
     */
    public static void update() {
        for (int i = 0; i < gamePanelsDimension; i++) {
            for (int j = 0; j < gamePanelsDimension; j++) {
                gamePanels[i][j].update();
            }
        }
    }
}
