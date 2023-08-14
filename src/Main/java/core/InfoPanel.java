package main.java.core;

import javax.swing.*;
import java.awt.*;

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
        this.setPreferredSize(new Dimension(GamePanel.screenWidth * cardCols, GamePanel.screenHeight));

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
    public void incrementMatchAttempts() {
        matchAttempts++;
        updateMatchAttempts();
    }

    private void updateMatchAttempts() {
        matchAttemptsLabel.setText("<html>Total Match Attempts: " + matchAttempts + "</html>");
    }
}
