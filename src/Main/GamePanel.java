package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GamePanel extends JPanel {
    //******************************************************************************************************************
    //* variables
    //******************************************************************************************************************
    // PANEL SCREEN SETTINGS
    final int originalTileSize = 128; // 128x128 tile
    final int scale = 1;

    public final int tileSize = originalTileSize * scale; // 128x128 tile
    public final int maxScreenCol = 1;
    public final int maxScreenRow = 1;
    public final int screenWidth = tileSize * maxScreenCol;  // 128 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 128 pixels

    // USER INPUT OBJECTS
    KeyHandler keyH = new KeyHandler();
    MouseHandler mouseH = new MouseHandler();

    // BACKGROUND IMAGE
    BufferedImage background;

    //******************************************************************************************************************
    //* constructor
    //******************************************************************************************************************
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setMinimumSize(new Dimension(screenWidth, screenHeight));
        this.setMaximumSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(mouseH);
        this.setFocusable(true);

        getBackgroundImage();
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
     * Is called every frame to update the background sprite
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
     * Generates the Graphics2D object used to draw the background
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        this.draw(g2);

        g2.dispose();
    }
}
