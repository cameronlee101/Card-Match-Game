package main.java.core;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Variables/Objects
        JFrame window = new JFrame();
        GameBoard GB = new GameBoard(4, 4);

        // Creating the info panel that goes on the bottom (rn is just a button)
        JPanel bottomPanel = new JPanel();
        JButton button1 = new JButton("temp button");
        button1.setPreferredSize(new Dimension(512, 128));
        button1.setFocusable(true);
        bottomPanel.setLayout(new GridLayout(1,1));
        bottomPanel.add(button1);

        // Adding in the two main panels to the window
        GridBagConstraints c = new GridBagConstraints();
        window.setLayout(new GridBagLayout());
        c.fill = GridBagConstraints.BOTH;

        c.gridy = 0;
        window.add(GB, c);

        c.weighty = 1.0;
        c.gridy = 1;
        window.add(bottomPanel, c);

        applyWindowSettings(window);

        // Adding required attributes to the GameLogicDriver
        GameLogicDriver.setGameBoard(GB);
        // Start the game
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