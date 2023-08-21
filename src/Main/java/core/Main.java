package main.java.core;

import main.java.game.GameBoard;
import main.java.game.GameLogicDriver;
import main.java.game.InfoPanel;
import main.java.startingInput.InputTextBox;

import javax.swing.*;
import java.awt.*;

public class Main {
    private static int cardRows = 0;
    private static int cardCols = 0;
    private static volatile boolean validInput = false;

    public static void main(String[] args) {
        JFrame window = new JFrame();

        getUserDimensionsInput(window);

        while (!validInput) Thread.onSpinWait();

        startCardGame(window);
    }

    /**
     * Creates a window that takes user input as to how many rows and columns of cards they want the game to have
     */
    private static void getUserDimensionsInput(JFrame window) {
        // Components contained in the panel that goes in the window
        JLabel label1 = new JLabel("Enter number of rows (min. 1):");
        JLabel label2 = new JLabel("Enter number of columns (min. 4):");
        InputTextBox inputBox1 = new InputTextBox();
        InputTextBox inputBox2 = new InputTextBox();

        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(e -> checkUserInput(inputBox1, inputBox2, label1, label2));

        // Setting up the panel
        GridBagConstraints c = new GridBagConstraints();

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setPreferredSize(new Dimension(400, 400));

        // TODO: fix layout stuff
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        c.weightx = 1;
        c.ipady = 40;
        inputPanel.add(label1, c);

        c.gridy = 1;
        c.weightx = 0;
        c.ipady = 0;
        inputPanel.add(inputBox1, c);

        c.gridy = 2;
        c.ipady = 40;
        inputPanel.add(label2, c);

        c.gridy = 3;
        c.ipady = 0;
        inputPanel.add(inputBox2, c);

        c.gridy = 4;
        inputPanel.add(doneButton, c);

        window.add(inputPanel);

        applyWindowSettings(window);
        window.revalidate();
        window.repaint();
    }

    /**
     * Is called when the user clicks the "Done" button. Converts the user's input into ints and validates those numbers
     * @param textBox1 the text box where user enters the number of rows for the game
     * @param textBox2 the text box where user enters the number of columns for the game
     * @param label1 the text that asks the user for the number of rows, as well as describes any errors in input
     * @param label2 the text that asks the user for the number of columns, as well as describes any errors in input
     */
    private static void checkUserInput(InputTextBox textBox1, InputTextBox textBox2, JLabel label1, JLabel label2) {
        try {
            cardRows = Integer.parseInt(textBox1.getUserInput());
            cardCols = Integer.parseInt(textBox2.getUserInput());

            if ((cardRows * cardCols) % 2 == 1 && cardRows > 0 && cardCols > 0) {
                System.out.println("Number of cards in rows * columns must be even and both must be positive");
            }
            else if (cardRows < 1 || cardCols < 4) {
                System.out.println("Must have at least 1 row and 4 columns of cards");
            }
            else {
                validInput = true;
            }
        }
        catch (Exception e) {
            System.out.println("error in input");
        }
    }

    /**
     * Creates the window that contains the card matching game
     */
    private static void startCardGame(JFrame window) {
        // Resets the window
        window.getContentPane().removeAll();

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

        c.gridy = 1;
        window.add(IP, c);

        // Adding required attributes to the GameLogicDriver
        GameLogicDriver.setGameBoard(GB);
        GameLogicDriver.setInfoPanel(IP);

        applyWindowSettings(window);
        window.revalidate();
        window.repaint();

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
        window.setResizable(false);
        window.setTitle("Memory Game");
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setBackground(Color.WHITE);
    }
}