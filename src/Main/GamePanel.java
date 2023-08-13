package Main;

import Entity.Card;

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
    public final int originalTileSize = 128; // 128x128 tile
    public final int scale = 1;

    public final int tileSize = originalTileSize * scale; // 128x128 tile
    public final int maxScreenCol = 1;
    public final int maxScreenRow = 1;
    public final int screenWidth = tileSize * maxScreenCol;  // 128 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 128 pixels

    // User input objects
    GPMouseHandler mouseH = new GPMouseHandler(this);

    // Background image
    BufferedImage background = null;

    // Card on the panel
    protected Card card = null;

    //******************************************************************************************************************
    //* constructor
    //******************************************************************************************************************
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setMinimumSize(new Dimension(screenWidth, screenHeight));
        this.setMaximumSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addMouseListener(mouseH);
        this.setFocusable(true);

        getBackgroundImage();
    }

    //******************************************************************************************************************
    //* getters and setters
    //******************************************************************************************************************
    /**
     * Sets this GamePanel's card to the given card
     * @param card the Card to set this GamePanel's card to
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
            background = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Resources/tempBackground.png")));
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