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
    protected int gamePanelsWidth;
    protected int gamePanelsHeight;

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
     * Returns the GameBoard's 2D array of game panels' width
     * @return the GameBoard's 2D array of game panels' width
     */
    public int getGamePanelsWidth() {
        return gamePanelsWidth;
    }

    /**
     * Returns the GameBoard's 2D array of game panels' height
     * @return the GameBoard's 2D array of game panels' height
     */
    public int getGamePanelsHeight() {
        return gamePanelsHeight;
    }

    //******************************************************************************************************************
    //* constructor
    //******************************************************************************************************************
    public GameBoard(int x, int y) {
        gamePanelsWidth = x;
        gamePanelsHeight = y;
        gamePanels = new GamePanel[gamePanelsWidth][gamePanelsHeight];
        this.setLayout(new GridLayout(gamePanelsWidth, gamePanelsHeight));

        for (int i = 0; i < gamePanelsWidth; i++) {
            for (int j = 0; j < gamePanelsHeight; j++) {
                gamePanels[i][j] = new GamePanel();
                this.add(gamePanels[i][j]);
            }
        }
    }
}
