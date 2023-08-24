package main.java.game;

import main.java.entity.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * JPanel that contains all the sprites and some logic needed for the game
 */
public class GameBoard extends JPanel {
    //******************************************************************************************************************
    //* variables
    //******************************************************************************************************************
    // Game panel constants
    public static final int panelTileSize = 128; // 128x128 tile
    public static final int panelScreenWidth = panelTileSize;  // 128 pixels
    public static final int panelScreenHeight = panelTileSize; // 128 pixels

    // Game panel variables
    private BufferedImage panelBackground;
    protected int gamePanelsRows;
    protected int gamePanelsCols;

    // Game board variables
    private final int gameBoardWidth;
    private final int gameBoardHeight;

    // Variables related to opening animation
    int openingCardIndex = 0;
    int numOfCards;
    int singleCardMoveTimer = 0;

    // Stores the Card objects used in the game
    protected Card[][] cards;

    //******************************************************************************************************************
    //* constructor
    //******************************************************************************************************************
    public GameBoard(int rows, int cols) {
        gamePanelsRows = rows;
        gamePanelsCols = cols;
        numOfCards = rows * cols;
        gameBoardWidth = cols * panelScreenWidth;
        gameBoardHeight = rows * panelScreenHeight;

        cards = new Card[gamePanelsRows][gamePanelsCols];

        getPanelBackgroundImage();

        this.setLayout(new GridLayout(gamePanelsRows, gamePanelsCols));
        this.addMouseListener(new GBMouseHandler(this));
        this.setPreferredSize(new Dimension(panelScreenWidth * cols, panelScreenHeight * rows));
    }

    /**
     * Retrieves the background image used for each game panel in this GameBoard
     */
    private void getPanelBackgroundImage() {
        try {
            panelBackground = ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/Background.png")));
        }
        catch (IOException e) {
            System.out.println("Background image loading not working");
            e.printStackTrace();
        }
    }

    //******************************************************************************************************************
    //* setters and getters
    //******************************************************************************************************************
    /**
     * Returns the GameBoard's number of rows of game panels
     * @return the GameBoard's number of rows of game panels
     */
    public int getGamePanelsRows() {
        return gamePanelsRows;
    }

    /**
     * Returns the GameBoard's number of columns of game panels
     * @return the GameBoard's number of columns of game panels
     */
    public int getGamePanelsCols() {
        return gamePanelsCols;
    }

    /**
     * Sets this GameBoard's 2D array 'cards' at [x][y] to the specified Card object
     * @param x first index of the 2D array to set the Card object in
     * @param y second index of the 2D array to set the Card object in
     * @param card the Card object to set in this GameBoard's 'cards' 2D array
     */
    public void setCard(int x, int y, Card card) {
        cards[x][y] = card;
        card.initialize(x, y, gamePanelsRows, gamePanelsCols);
    }

    /**
     * Returns the number of pixels width this GameBoard object occupies
     * @return the number of pixels width this GameBoard object occupies
     */
    @SuppressWarnings("unused")
    public int getGameBoardWidth() {
        return this.gameBoardWidth;
    }

    /**
     * Returns the number of pixels height this GameBoard object occupies
     * @return the number of pixels height this GameBoard object occupies
     */
    @SuppressWarnings("unused")
    public int getGameBoardHeight() {
        return this.gameBoardHeight;
    }

    //******************************************************************************************************************
    //* general methods
    //******************************************************************************************************************
    /**
     * Activates each Card object so that they start moving towards their row x col position
     */
    public void startOpeningAnimation() {
        cards[openingCardIndex / gamePanelsCols][openingCardIndex % gamePanelsCols].activate();
    }

    /**
     * Given an x and y coordinate, loops through this GameBoard's 2D array of Card objects to see if any of them
     * occupy that coordinate. If so, calls GameLogicDriver.checkCard() on that card
     * @param x the x portion of the position to check (x increases from left to right)
     * @param y the y portion of the position to check (y increases from top to down)
     */
    public void checkCard(int x, int y) {
        for (int i = 0; i < gamePanelsRows; i++) {
            for (int j = 0; j < gamePanelsCols; j++) {
                if (cards[i][j].pointInRange(x, y)) {
                    GameLogicDriver.checkCard(cards[i][j]);
                }
            }
        }
    }

    //******************************************************************************************************************
    //* methods called every frame
    //******************************************************************************************************************
    /**
     * Calls update() on appropriate objects, as well calling activate() on cards during the opening animation if needed
     */
    public void update() {
        for (int i = 0; i < gamePanelsRows; i++) {
            for (int j = 0; j < gamePanelsCols; j++) {
                cards[i][j].update();
            }
        }

        if (GameLogicDriver.inOpeningAnimation) {
            if (singleCardMoveTimer == GameLogicDriver.singleCardDistributionTime) {
                singleCardMoveTimer = 0;
                openingCardIndex++;
                if (openingCardIndex <= gamePanelsRows * gamePanelsCols - 1) {
                    cards[openingCardIndex / gamePanelsCols][openingCardIndex % gamePanelsCols].activate();
                }
            }
            singleCardMoveTimer++;
        }

        repaint();
    }

    /**
     * Draws the sprites contained in this GameBoard object
     * @param g2 the Graphics2D object used to draw
     */
    public void draw(Graphics2D g2) {
        for (int i = 0; i < gamePanelsRows; i++) {
            for (int j = 0; j < gamePanelsCols; j++) {
                g2.drawImage(panelBackground, j * panelTileSize, i * panelTileSize,
                        panelTileSize, panelTileSize, null);
            }
        }

        for (int i = 0; i < gamePanelsRows; i++) {
            for (int j = 0; j < gamePanelsCols; j++) {
                if (cards[i][j] != null) cards[i][j].draw(g2);
            }
        }
    }

    /**
     * Uses a Graphics2D object to draw all the sprites on this panel
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        this.draw(g2);

        g2.dispose();
    }
}
