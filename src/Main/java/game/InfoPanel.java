package game;

import javax.swing.*;
import java.awt.*;

import static java.lang.Math.max;

public class InfoPanel extends JPanel {
    //******************************************************************************************************************
    //* variables
    //******************************************************************************************************************
    int infoRows = 1;
    int infoCols = 3;

    private int matchAttempts = 0;
    private final JLabel matchAttemptsLabel = new JLabel();

    //******************************************************************************************************************
    //* constructor
    //******************************************************************************************************************
    public InfoPanel(int cardCols) {
        this.setLayout(new GridLayout(infoRows, infoCols));
        this.setPreferredSize(new Dimension(max(GameBoard.panelScreenWidth * 4, GameBoard.panelScreenWidth * cardCols), GameBoard.panelScreenHeight));
        this.setBackground(Color.WHITE);

        updateMatchAttempts();
        matchAttemptsLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(matchAttemptsLabel);

        // placeholder labels
        this.add(new JLabel());
        this.add(new JLabel());
    }

    //******************************************************************************************************************
    //* methods
    //******************************************************************************************************************
    /**
     * Increments the number of match attempts and updates the JLabel that displays that number
     */
    public void incrementMatchAttempts() {
        matchAttempts++;
        updateMatchAttempts();
    }

    /**
     * Updates the JLabel that displays the number of match attempts
     */
    private void updateMatchAttempts() {
        matchAttemptsLabel.setText("<html>Total Match Attempts: " + matchAttempts + "</html>");
    }
}
