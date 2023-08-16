package main.java.core;

import javax.swing.*;
import java.awt.*;

public class Main {
    private static int rows = 0;
    private static int cols = 0;

    public static void main(String[] args) {
        getUserDimensionsInput();

        if ((rows * cols) % 2 == 1 && rows > 0 && cols >0) {
            System.out.println("Number of cards in rows * columns must be even and both must be positive");
        }
        else {
            startCardGame(rows, cols);
        }
    }

    /**
     * Creates a window that takes user input as to how many rows and columns of cards they want the game to have
     */
    private static void getUserDimensionsInput() {
        rows = 4;
        cols = 4;
    }

    /**
     * Creates the window that contains the card matching game
     * @param cardRows the number of rows of cards that the game will have
     * @param cardCols the number of columns of cards that the game will have
     */
    private static void startCardGame(int cardRows, int cardCols) {
        // Creates the window that this game is contained in
        JFrame window = new JFrame();

        // Creates the cardRows by cardCols large grid of GamePanels in one JPanel
        GameBoard GB = new GameBoard(cardRows, cardCols);

        // Creating the match attempts info panel that goes on the bottom of the window
        InfoPanel IP = new InfoPanel(cardCols);

        // Adding in the two main panels to the window
        GridBagConstraints c = new GridBagConstraints();
        window.setLayout(new GridBagLayout());
        c.fill = GridBagConstraints.BOTH;

        c.gridy = 0;
        window.add(GB, c);

        c.weighty = 1.0;
        c.gridy = 1;
        window.add(IP, c);

        applyWindowSettings(window);

        // Adding required attributes to the GameLogicDriver
        GameLogicDriver.setGameBoard(GB);
        GameLogicDriver.setInfoPanel(IP);

        // Starts the game
        GameLogicDriver.startGame();
    }

    /**
     * Applies this game's default window settings to the given JFrame object
     * @param window the JFrame object to apply this game's default settings to
     */
    private static void applyWindowSettings(JFrame window) {
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("Memory Game");
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setBackground(Color.black);
    }
}