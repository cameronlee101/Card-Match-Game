package main.java.game;

import main.java.entity.Card;

import javax.swing.*;
import java.awt.*;

/**
 * Each GamePanel is used to interact with one card
 */
// TODO: refactor
public class GamePanel extends JPanel {
    //******************************************************************************************************************
    //* variables
    //******************************************************************************************************************
    // Panel screen settings
    public static final int tileSize = 128; // 128x128 tile
    public static final int screenWidth = tileSize;  // 128 pixels
    public static final int screenHeight = tileSize; // 128 pixels

    // Card on the panel
    private Card card = null;

    //******************************************************************************************************************
    //* constructor
    //******************************************************************************************************************
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setMinimumSize(new Dimension(screenWidth, screenHeight));
        this.setMaximumSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.addMouseListener(new GPMouseHandler(this));
    }

    //******************************************************************************************************************
    //* getters and setters
    //******************************************************************************************************************
    /**
     * Sets this GamePanel's card to the given Card object
     * @param card the Card object to set this GamePanel object's card to
     */
    public void setCard(Card card) {
        this.card = card;
    }

    /**
     * Returns this GamePanel's card
     * @return this GamePanel's card
     */
    public Card getCard() {
        return this.card;
    }
}
