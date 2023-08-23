package main.java.entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Math.max;

public class Card {
    //******************************************************************************************************************
    //* variables
    //******************************************************************************************************************
    public final int cardWidth = 80;
    public final int cardLength = 100;
    public static final int centerX = 24;
    public static final int centerY = 12;

    ArrayList<BufferedImage> sprites;   // BufferedImages are organized with flipping the card from back to front
                                        // corresponding to indexes 0 to maxAnimationNum
    public boolean flipped = false;

    protected final int minAnimationNum = 0;
    protected final int maxAnimationNum = 5;
    protected int curAnimationNum = 0;

    protected final int framesPerAnimation = 2;
    protected int animationTimer = 0;

    protected int flipState = 0;  // 0 = not flipping, 1 = flipping to front, 2 = flipping to back

    public boolean spriteVisible = false;

    // Type of card
    public Symbol symbol;

    //******************************************************************************************************************
    //* constructor
    //******************************************************************************************************************
    public Card(Symbol symbol) {
        sprites = new ArrayList<>();
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
            sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardBack.png"))));
            sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardBackFlipping50.png"))));
            sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardBackFlipping20.png"))));

            // needs refactoring somehow
            switch (symbol) {
                case Diamond -> {
                    sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardDiamondFrontFlipping20.png"))));
                    sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardDiamondFrontFlipping50.png"))));
                    sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardDiamond.png"))));
                }
                case Club -> {
                    sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardClubFrontFlipping20.png"))));
                    sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardClubFrontFlipping50.png"))));
                    sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardClub.png"))));
                }
                case Heart -> {
                    sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardHeartFrontFlipping20.png"))));
                    sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardHeartFrontFlipping50.png"))));
                    sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardHeart.png"))));
                }
                case Spade -> {
                    sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardSpadeFrontFlipping20.png"))));
                    sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardSpadeFrontFlipping50.png"))));
                    sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardSpade.png"))));
                }
                case Circle -> {
                    sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardCircleFrontFlipping20.png"))));
                    sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardCircleFrontFlipping50.png"))));
                    sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardCircle.png"))));
                }
                case Triangle -> {
                    sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardTriangleFrontFlipping20.png"))));
                    sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardTriangleFrontFlipping50.png"))));
                    sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardTriangle.png"))));
                }
                case Square -> {
                    sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardSquareFrontFlipping20.png"))));
                    sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardSquareFrontFlipping50.png"))));
                    sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardSquare.png"))));
                }
                case Star -> {
                    sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardStarFrontFlipping20.png"))));
                    sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardStarFrontFlipping50.png"))));
                    sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardStar.png"))));
                }
            }

            assert sprites.size() == maxAnimationNum + 1;
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
        if (curAnimationNum == minAnimationNum) {
            flipState = 1;
        }
        else if (curAnimationNum == maxAnimationNum) {
            flipState = 2;
        }
        else {
            System.out.println("Invalid logic, check Card.java flip()");
        }
    }

    /**
     * Updates this card's sprite, changing it if needed
     */
    private void updateCurSprite() {
        if (flipState != 0 && (animationTimer == framesPerAnimation)) {
            if (flipState == 1) {
                curAnimationNum++;
                if (curAnimationNum == maxAnimationNum) {
                    flipState = 0;
                }
            }
            else if (flipState == 2) {
                curAnimationNum--;
                if (curAnimationNum == minAnimationNum) {
                    flipState = 0;
                }
            }
            else {
                System.out.println("Invalid logic, check Card.java updateCurSprite()");
            }

            animationTimer = 0;
        }
    }

    /**
     * Is called every frame to update this card's sprite, changing it if needed
     */
    public void update() {
        updateCurSprite();
        if (flipState != 0) animationTimer++;
    }

    /**
     * Draws the current sprite for this card at its current position
     * @param g2 the Graphics2D object used to draw
     */
    public void draw(Graphics2D g2) {
        int xPos = centerX + ((cardWidth - sprites.get(curAnimationNum).getWidth()) / 2);
        int yPos = centerY + (max(minAnimationNum - curAnimationNum, curAnimationNum - maxAnimationNum) * 3);
        if (spriteVisible) {
            g2.drawImage(sprites.get(curAnimationNum), xPos, yPos,
                         sprites.get(curAnimationNum).getWidth(), sprites.get(curAnimationNum).getHeight(), null);
        }
    }
}
