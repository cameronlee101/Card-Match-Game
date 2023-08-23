package main.java.game;

import main.java.entity.Card;
import main.java.entity.MovingCard;
import main.java.entity.Symbol;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * JPanel that holds all the game panels
 */
// TODO: refactor
public class GameBoard extends JPanel {
    //******************************************************************************************************************
    //* variables
    //******************************************************************************************************************
    protected GamePanel[][] gamePanels;
    protected int gamePanelsRows;
    protected int gamePanelsCols;

    // Variables related to opening animation
    ArrayList<MovingCard> openAnimationCardDeck = new ArrayList<>();
    int openingCardIndex = 0;
    int numOfCards;
    int singleCardMoveTimer = 0;

    protected Card[][] cards;
    private BufferedImage panelBackground;

    //******************************************************************************************************************
    //* setters and getters
    //******************************************************************************************************************
    /**
     * Returns the GameBoard's 2D array of game panels
     * @return the GameBoard's 2D array of game panels
     */
    public GamePanel[][] getGamePanels() {
        return gamePanels;
    }

    /**
     * Returns the GameBoard's 2D array of game panels' rows
     * @return the GameBoard's 2D array of game panels' rows
     */
    public int getGamePanelsRows() {
        return gamePanelsRows;
    }

    /**
     * Returns the GameBoard's 2D array of game panels' columns
     * @return the GameBoard's 2D array of game panels' columns
     */
    public int getGamePanelsCols() {
        return gamePanelsCols;
    }

    public void setCard(int i, int j, Card card) {
        cards[i][j] = card;
        card.setCords(i, j);
        gamePanels[i][j].setCard(card);
    }

    //******************************************************************************************************************
    //* constructor
    //******************************************************************************************************************
    public GameBoard(int rows, int cols) {
        gamePanelsRows = rows;
        gamePanelsCols = cols;
        numOfCards = rows * cols;
        gamePanels = new GamePanel[gamePanelsRows][gamePanelsCols];
        cards = new Card[gamePanelsRows][gamePanelsCols];
        this.setLayout(new GridLayout(gamePanelsRows, gamePanelsCols));

        for (int i = 0; i < gamePanelsRows; i++) {
            for (int j = 0; j < gamePanelsCols; j++) {
                // Creating row x col GamePanels and adding it into this GameBoard (JPanel subclass)
                gamePanels[i][j] = new GamePanel();
                this.add(gamePanels[i][j]);
                // Creating the MovingCard objects for the opening card distributing animation
                openAnimationCardDeck.add(new MovingCard(Symbol.Diamond, i, j, rows, cols));
                openAnimationCardDeck.get((i * gamePanelsCols) + j).spriteVisible = true;
            }
        }

        getBackgroundImage();

        this.setPreferredSize(new Dimension(GamePanel.screenWidth * cols, GamePanel.screenHeight * rows));
        this.setOpaque(false);
    }

    /**
     * Retrieves the background image for this panel
     */
    private void getBackgroundImage() {
        try {
            panelBackground = ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/Background.png")));
        }
        catch (IOException e) {
            System.out.println("Background image loading not working");
            e.printStackTrace();
        }
    }
    //******************************************************************************************************************
    //* methods
    //******************************************************************************************************************
    /**
     * Activates each MovingCard object so that they move towards their intended position in set time intervals
     */
    public void startOpeningAnimation() {
        openAnimationCardDeck.get(openingCardIndex).activate();
    }

    /**
     * Calls update() on appropriate objects
     */
    public void update() {
        for (int i = 0; i < gamePanelsRows; i++) {
            for (int j = 0; j < gamePanelsCols; j++) {
                cards[i][j].update();
            }
        }
        for (MovingCard card : openAnimationCardDeck) {
            card.update();
        }

        if (singleCardMoveTimer == GameLogicDriver.singleCardDistributionTime) {
            singleCardMoveTimer = 0;
            openingCardIndex++;
            if (openingCardIndex <= openAnimationCardDeck.size() - 1) {
                openAnimationCardDeck.get(openingCardIndex).activate();
            }
        }
        singleCardMoveTimer++;

        repaint();
    }

    /**
     * Draws the sprites contained on this JPanel object
     * @param g2 the Graphics2D object used to draw
     */
    public void draw(Graphics2D g2) {
        for (int i = 0; i < gamePanelsRows; i++) {
            for (int j = 0; j < gamePanelsCols; j++) {
                g2.drawImage(panelBackground, j * GamePanel.tileSize, i * GamePanel.tileSize,
                        GamePanel.tileSize, GamePanel.tileSize, null);
                if (cards[i][j] != null) cards[i][j].draw(g2);
            }
        }

        if (GameLogicDriver.inOpeningAnimation) {
            for (MovingCard card : openAnimationCardDeck) {
                card.draw(g2);
            }
        }
    }

    /**
     * Uses a Graphics2D object to draw all the sprites on this panel (primarily the opening animation card sprites)
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        this.draw(g2);

        g2.dispose();
    }
}
