package main.java.game;

import main.java.entity.MovingCard;
import main.java.entity.Symbol;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * JPanel that holds all the game panels
 */
public class GameBoard extends JPanel {
    //******************************************************************************************************************
    //* variables
    //******************************************************************************************************************
    protected GamePanel[][] gamePanels;
    protected int gamePanelsRows;
    protected int gamePanelsCols;

    // Variables related to opening animation
    ArrayList<MovingCard> openAnimationCardDeck = new ArrayList<>();

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

    //******************************************************************************************************************
    //* constructor
    //******************************************************************************************************************
    public GameBoard(int rows, int cols) {
        gamePanelsRows = rows;
        gamePanelsCols = cols;
        gamePanels = new GamePanel[gamePanelsRows][gamePanelsCols];
        this.setLayout(new GridLayout(gamePanelsRows, gamePanelsCols));

        for (int i = 0; i < gamePanelsRows; i++) {
            for (int j = 0; j < gamePanelsCols; j++) {
                gamePanels[i][j] = new GamePanel();
                this.add(gamePanels[i][j]);
            }
        }

        this.setPreferredSize(new Dimension(GamePanel.screenWidth * cols, GamePanel.screenHeight * rows));
        this.setBackground(Color.WHITE);
    }

    //******************************************************************************************************************
    //* methods
    //******************************************************************************************************************
    /**
     * TODO: figure out how to store card coordinates/offset for opening animation
     */
    public void startOpeningAnimation() {
        for (int i = 0; i < (gamePanelsCols * gamePanelsRows); i++) {
            openAnimationCardDeck.add(new MovingCard(Symbol.Diamond));
        }
    }

    /**
     * Draws the sprites contained on this JPanel object
     * @param g2 the Graphics2D object used to draw
     */
    public void draw(Graphics2D g2) {
        for (MovingCard card : openAnimationCardDeck) {
            card.draw(g2);
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
