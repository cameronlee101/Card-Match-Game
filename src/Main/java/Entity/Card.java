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
                case Diamond -> front = ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardDiamond.png")));
                case Club -> front = ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardClub.png")));
                case Heart -> front = ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardHeart.png")));
                case Spade -> front = ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardSpade.png")));
                case Circle -> front = ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardCircle.png")));
                case Triangle -> front = ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardTriangle.png")));
                case Square -> front = ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardSquare.png")));
                case Star -> front = ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardStar.png")));
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
