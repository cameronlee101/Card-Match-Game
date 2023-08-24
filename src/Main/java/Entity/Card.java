package main.java.entity;

import main.java.game.GameLogicDriver;
import main.java.game.GamePanel;

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
    // Card constants
    public static final int cardStandardWidth = 80;
    public static final int cardStandardHeight = 100;
    public static final int centerX = (GamePanel.screenWidth - cardStandardWidth) / 2;
    public static final int centerY = (GamePanel.screenHeight - cardStandardHeight) / 2;

    // Type of card
    protected Symbol symbol;

    // Variables related to card flipping animation
    ArrayList<BufferedImage> sprites = new ArrayList<>();   // BufferedImages are organized with flipping the card from
                                                            // back to front corresponding to indices 0 to maxAnimationNum
    public boolean flipped = false;

    protected final int minAnimationNum = 0;
    protected final int maxAnimationNum = 5;
    protected int curAnimationNum = 0;

    protected final int framesPerAnimation = 2;
    protected int animationTimer = 0;

    protected int flipState = 0;  // 0 = not flipping, 1 = flipping to front, 2 = flipping to back

    // Variables related to card position and moving animations
    public int row, col;

    private boolean activated;
    private int posIncrementIndex = 0;
    private final int maxPosIncrementIndex = GameLogicDriver.singleCardDistributionTime;
    private int XPosIncrementVal, YPosIncrementVal, finalPosX, finalPosY, curPosX, curPosY;

    //******************************************************************************************************************
    //* constructor
    //******************************************************************************************************************
    public Card(Symbol symbol) {
        try {
            sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardBack.png"))));
            sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardBackFlipping50.png"))));
            sprites.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/main/resources/CardBackFlipping20.png"))));

            // TODO: needs refactoring somehow
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

    //******************************************************************************************************************
    //* getters and setters
    //******************************************************************************************************************
    /**
     * Returns this Card object's symbol attribute
     * @return this Card object's symbol attribute
     */
    public Symbol getSymbol() {
        return symbol;
    }

    //******************************************************************************************************************
    //* methods
    //******************************************************************************************************************
    /**
     * Set various variables so that this card can calculate where it should be and where it needs to go
     * @param rowNum 0-based numbered row that this card belongs in
     * @param colNum 0-based numbered column that this card belongs in
     * @param maxRow the number of rows present in the game
     * @param maxCol the number of columns present in the game
     */
    public void initialize(int rowNum, int colNum, int maxRow, int maxCol) {
        curPosX = ((maxCol * GamePanel.screenWidth) / 2) - (Card.cardStandardWidth / 2);
        curPosY = maxRow * GamePanel.screenHeight;
        finalPosX = centerX + (colNum * GamePanel.screenWidth);
        finalPosY = centerY + (rowNum * GamePanel.screenHeight);
        XPosIncrementVal = (finalPosX - curPosX) / maxPosIncrementIndex;
        YPosIncrementVal = (finalPosY - curPosY) / maxPosIncrementIndex;
    }

    /**
     * Activates this Card so that it starts moving from the start position towards it's intended position
     */
    public void activate() {
        this.activated = true;
    }

    /**
     * Begins the process of flipping this card so that the displayed sprite changes
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
     * Is called every frame to update this card's sprite and position
     */
    public void update() {
        // Updating this card's sprite if needed
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

        // If in the process of flipping, increments animationTimer (tracks frames for each flipping animation sprite)
        if (flipState != 0) animationTimer++;

        // If activated, moves this Card's position towards its proper position if needed, or keeps it at its proper
        // position if it is already there
        if (activated) {
            if (posIncrementIndex < maxPosIncrementIndex) {
                curPosX += XPosIncrementVal;
                curPosY += YPosIncrementVal;
                posIncrementIndex++;
            }
            else {
                curPosX = finalPosX + ((cardStandardWidth - sprites.get(curAnimationNum).getWidth()) / 2) + (GamePanel.screenWidth * col);
                curPosY = finalPosY + (max(minAnimationNum - curAnimationNum, curAnimationNum - maxAnimationNum) * 3) + (GamePanel.screenHeight * row);
            }
        }
    }

    /**
     * Draws the current sprite for this card at its current position
     * @param g2 the Graphics2D object used to draw
     */
    public void draw(Graphics2D g2) {
        g2.drawImage(sprites.get(curAnimationNum), curPosX, curPosY,
                     sprites.get(curAnimationNum).getWidth(), sprites.get(curAnimationNum).getHeight(), null);
    }
}
