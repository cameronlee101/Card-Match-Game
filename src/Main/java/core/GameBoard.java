package main.java.core;

import javax.swing.*;
import java.awt.*;

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
        this.setMaximumSize(new Dimension(GamePanel.screenWidth * cols, GamePanel.screenHeight * rows));
        this.setMinimumSize(new Dimension(GamePanel.screenWidth * cols, GamePanel.screenHeight * rows));
    }
}
