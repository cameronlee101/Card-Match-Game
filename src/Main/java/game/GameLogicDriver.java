package main.java.game;

import main.java.core.Engine;
import main.java.entity.Card;
import main.java.entity.Symbol;

import java.util.ArrayList;
import java.util.Random;

/**
 * Handles most of the game's logic
 */
public final class GameLogicDriver {
    //******************************************************************************************************************
    //* variables
    //******************************************************************************************************************
    // General game variables
    private static GameBoard gameBoard;
    private static InfoPanel infoPanel;
    private static int gamePanelsCols, gamePanelsRows;

    // Used to keep track of which cards were flipped
    private static Card firstCard = null;
    private static Card secondCard = null;

    // Variables related to delaying accepting user input
    private static InputDelayTracker inputDelayTracker = new InputDelayTracker();
    private static boolean toStartHoldTimer = false;

    // Variables related to the opening animation of distribution cards onto each game panel
    static boolean inOpeningAnimation = true;
    public static final int singleCardDistributionTime = 15;

    //******************************************************************************************************************
    //* setters and getters
    //******************************************************************************************************************
    /**
     * Sets the GameLogicDriver's 'gameBoard' variable, as well as retrieves the width and height of the GameBoard in
     * number of game panels
     * @param gameBoard the GameBoard to set this GameLogicDriver's 'gameBoard' variable to
     */
    public static void setGameBoard(GameBoard gameBoard) {
        GameLogicDriver.gameBoard = gameBoard;
        gamePanelsCols = gameBoard.getGamePanelsRows();
        gamePanelsRows = gameBoard.getGamePanelsCols();
    }

    /**
     * Sets this GameLogicDriver's 'infoPanel' variable
     * @param infoPanel the InfoPanel to set this GameLogicDriver's 'infoPanel' to
     */
    public static void setInfoPanel(InfoPanel infoPanel) {
        GameLogicDriver.infoPanel = infoPanel;
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
        startOpeningAnimation();
    }

    /**
     * Creates and randomly distributes the cards in the game board
     */
    private static void setupGame() {
        ArrayList<Card> cardDeck = makeCardDeck();
        Random randNumGen = new Random();

        for (int i = 0; i < gamePanelsCols; i++) {
            for (int j = 0; j < gamePanelsRows; j++) {
                int randNum = randNumGen.nextInt(cardDeck.size());
                gameBoard.setCard(i, j, cardDeck.remove(randNum));
            }
        }
    }

    /**
     * Creates an arraylist of two of each type of card to be distributed onto the game board
     */
    private static ArrayList<Card> makeCardDeck() {
        ArrayList<Card> cardDeck = new ArrayList<>();
        Symbol curSymbol = Symbol.Diamond;

        for (int i = 0; i < (gamePanelsCols * gamePanelsRows) / 2; i++) {
            cardDeck.add(new Card(curSymbol));
            cardDeck.add(new Card(curSymbol));
            curSymbol = curSymbol.next();
        }

        return cardDeck;
    }

    /**
     * Calls methods on relevant objects to start the opening card distribution animation
     */
    private static void startOpeningAnimation() {
        gameBoard.startOpeningAnimation();
        inputDelayTracker.startOpeningAnimationDelay(gamePanelsRows * gamePanelsCols * singleCardDistributionTime);
    }

    /**
     * Is called when the user clicks on a game panel with a card. Checks to see if this is the first or second card
     * that the user has selected and runs logic depending on the types of the two cards
     * @param card The current card that the user selected
     */
    // TODO: bug when clicking the same card too fast
    public static void checkCard(Card card) {
        if (!card.flipped && !inputDelayTracker.delayingInput() && !inOpeningAnimation) {
            if (firstCard == null) {
                firstCard = card;
                card.flip();
                inputDelayTracker.startFlipTimer();
            }
            else {
                if (firstCard.getSymbol() == card.getSymbol()) {
                    System.out.println("Pair found");
                    firstCard = null;
                } else {
                    System.out.println("No pair found");
                    toStartHoldTimer = true;
                    secondCard = card;
                }

                card.flip();
                inputDelayTracker.startFlipTimer();

                infoPanel.incrementMatchAttempts();
            }
        }
    }

    /**
     * Is called by a InputDelayTracker when it's holdDelayTimer has reached its limit to reset the two selected cards
     */
    static void unFlipCards() {
        firstCard.flip();
        secondCard.flip();
        firstCard = null;
        secondCard = null;
    }

    /**
     * Is called by a InputDelayTracker to see if it needs to start the holdDelayTimer after its flipDelayTimer finishes
     */
    static void needToStartHold() {
        if (toStartHoldTimer) {
            toStartHoldTimer = false;
            inputDelayTracker.startHoldTimer();
        }
    }

    /**
     * Is called by a InputDelayTracker to make the cards visible and clickable after the starting distribution animation
     */
    static void beginCardInteraction() {
        inOpeningAnimation = false;
    }

    /**
     * Makes all of GameLogicDriver's variables null to reset and prepare for a new game
     */
    @SuppressWarnings("unused")
    public static void reset() {
        gameBoard = null;
        infoPanel = null;
        gamePanelsCols = 0;
        gamePanelsRows = 0;
        firstCard = null;
        secondCard = null;
        inputDelayTracker = new InputDelayTracker();
        toStartHoldTimer = false;
    }

    //******************************************************************************************************************
    //* methods called every frame
    //******************************************************************************************************************
    /**
     * Calls update() on the appropriate objects
     */
    public static void update() {
        gameBoard.update();
        inputDelayTracker.update();
    }
}
