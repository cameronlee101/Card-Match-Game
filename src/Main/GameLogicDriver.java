package Main;

import Entity.Card;
import Entity.Symbol;

import java.util.ArrayList;
import java.util.Random;

/**
 * Handles all the game's logic
 */
public class GameLogicDriver {
    //******************************************************************************************************************
    //* variables
    //******************************************************************************************************************
    private static GameBoard gameBoard;
    private static int gamePanelsWidth, gamePanelsHeight;

    private static Card firstCard = null;
    private static Card secondCard = null;

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
     * Sets the GameLogicDriver's gameBoard variable, as well as retrieves the width and height of the GameBoard
     * @param gameBoard the GameBoard to set this GameLogicDriver's gameBoard to
     */
    public static void setGameBoard(GameBoard gameBoard) {
        GameLogicDriver.gameBoard = gameBoard;
        gamePanelsWidth = gameBoard.getGamePanelsWidth();
        gamePanelsHeight = gameBoard.getGamePanelsHeight();
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
     * Creates and randomly distributes the cards for each game panel
     */
    private static void setupGame() {
        ArrayList<Card> cardDeck = makeCardDeck();
        Random randNumGen = new Random();
        GamePanel[][] gamePanels = gameBoard.getGamePanels();

        for (int i = 0; i < gamePanelsWidth; i++) {
            for (int j = 0; j < gamePanelsHeight; j++) {
                int randNum = randNumGen.nextInt(cardDeck.size());
                gamePanels[i][j].setCard(cardDeck.remove(randNum));
            }
        }
    }

    /**
     * Creates an arraylist of two of each type of card to be distributed onto each game panel
     */
    private static ArrayList<Card> makeCardDeck() {
        ArrayList<Card> cardDeck = new ArrayList<>();
        Symbol curSymbol = Symbol.Diamond;

        for (int i = 0; i < (gamePanelsWidth * gamePanelsHeight) / 2; i++) {
            cardDeck.add(new Card(curSymbol));
            cardDeck.add(new Card(curSymbol));
            curSymbol = curSymbol.next();
        }

        return cardDeck;
    }

    /**
     * TODO: implement properly
     */
    public static void checkCard(Card card) {
        card.flip();
    }

    /**
     * Calls update() on the appropriate objects
     */
    public static void update() {
        GamePanel[][] gamePanels = gameBoard.getGamePanels();

        for (int i = 0; i < gamePanelsWidth; i++) {
            for (int j = 0; j < gamePanelsHeight; j++) {
                gamePanels[i][j].update();
            }
        }
    }
}
