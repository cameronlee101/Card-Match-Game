package main.java.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Card {
    //******************************************************************************************************************
    //* variables
    //******************************************************************************************************************
    // Card size settings
    public final int cardWidth = 80;
    public final int cardLength = 100;

    // Sprite related variables
    private BufferedImage back, front;
    public boolean flipped = false;

    // Type of card
    public Symbol symbol;

    //******************************************************************************************************************
    //* constructor
    //******************************************************************************************************************
    public Card(Symbol symbol) {
        getCardImage(symbol);
    }

    //******************************************************************************************************************
    //* methods
    //******************************************************************************************************************
    /**
     * Retrieves the images for the card
     */
    private void getCardImage(Symbol symbol) {
        try {
            back = ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardBack.png")));
            switch (symbol) {
                case Diamond -> front = ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/tempCardDiamond.png")));
                case Club -> front = ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/tempCardClub.png")));
                case Heart -> front = ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/tempCardHeart.png")));
                case Spade -> front = ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/tempCardSpade.png")));
                case Circle -> front = ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardCircle.png")));
                case Triangle -> front = ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/tempCardTriangle.png")));
                case Square -> front = ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/tempCardSquare.png")));
                case Hexagon -> front = ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/tempCardHexagon.png")));
            }
        }
        catch (IOException e) {
            System.out.println("Card image loading not working");
            e.printStackTrace();
        }

        this.symbol = symbol;
    }

    /**
     * Flips the card so the displayed sprite is changed
     */
    public void flip() {
        flipped = !flipped;
    }

    /**
     * Draws the sprite for this card
     * @param g2 the Graphics2D object used to draw
     */
    public void draw(Graphics2D g2) {
        if (flipped) {
            g2.drawImage(front, 24, 14, cardWidth, cardLength, null);
        }
        else {
            g2.drawImage(back, 24, 14, cardWidth, cardLength, null);
        }
    }

}
