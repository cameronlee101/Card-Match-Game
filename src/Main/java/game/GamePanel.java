package main.java.game;

import main.java.entity.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Each GamePanel is used to interact with one card
 */
public class GamePanel extends JPanel {
    //******************************************************************************************************************
    //* variables
    //******************************************************************************************************************
    // Panel screen settings
    public static final int originalTileSize = 128; // 128x128 tile
    public static final int scale = 1;

    public static final int tileSize = originalTileSize * scale; // 128x128 tile
    public static final int maxScreenCol = 1;
    public static final int maxScreenRow = 1;
    public static final int screenWidth = tileSize * maxScreenCol;  // 128 pixels
    public static final int screenHeight = tileSize * maxScreenRow; // 128 pixels

    // Background image
    private BufferedImage background = null;

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

        getBackgroundImage();
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

    //******************************************************************************************************************
    //* methods
    //******************************************************************************************************************
    /**
     * Retrieves the background image for this panel
     */
    private void getBackgroundImage() {
        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/Background.png")));
        }
        catch (IOException e) {
            System.out.println("Background image loading not working");
            e.printStackTrace();
        }
    }

    /**
     * Is called every frame to repaint all the sprites on this panel
     */
    public void update() {
        card.update();
        repaint();
    }

    /**
     * Draws the background for this panel
     * @param g2 the Graphics2D object used to draw
     */
    public void draw(Graphics2D g2) {
        g2.drawImage(background, 0, 0, tileSize, tileSize, null);
    }

    /**
     * Uses a Graphics2D object to draw all the sprites on this panel
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        this.draw(g2);
        if (card != null) card.draw(g2);

        g2.dispose();
    }
}
