package Main;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Variables/Objects
        JFrame window = setupWindow(new JFrame());
        GameBoard GB = new GameBoard(4, 4);

        // Creating the info panel that goes on the bottom (rn is just a button)
        JPanel bottomPanel = new JPanel();
        JButton button1 = new JButton();
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

        window.pack();

        // Adding required attributes to the GameLogicDriver
        GameLogicDriver.setGameBoard(GB);
        // Start the game
        GameLogicDriver.startGame();
    }

    /**
     * Sets up the given window
     * @param window The window to set the settings for
     * @return the window with all the correct settings
     */
    private static JFrame setupWindow(JFrame window) {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("Memory Game");
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setBackground(Color.black);

        return window;
    }
}