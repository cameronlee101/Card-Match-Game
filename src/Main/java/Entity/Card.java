package entity;

import game.GameBoard;
import game.GameLogicDriver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static java.lang.Math.max;

public class Card {
    //******************************************************************************************************************
    //* variables
    //******************************************************************************************************************
    // Card constants
    public static final int cardStandardWidth = 80;
    public static final int cardStandardHeight = 100;
    public static final int centerX = (GameBoard.panelScreenWidth - cardStandardWidth) / 2;
    public static final int centerY = (GameBoard.panelScreenHeight - cardStandardHeight) / 2;

    // Type of card
    protected Symbol cardSymbol;

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
    public Card(Symbol cardSymbol) {
        try {
            ClassLoader classLoader = Card.class.getClassLoader();

            sprites.add(ImageIO.read(Objects.requireNonNull(classLoader.getResourceAsStream("images/CardBack.png"))));
            sprites.add(ImageIO.read(Objects.requireNonNull(classLoader.getResourceAsStream("images/CardBackFlipping50.png"))));
            sprites.add(ImageIO.read(Objects.requireNonNull(classLoader.getResourceAsStream("images/CardBackFlipping20.png"))));

            Map<Symbol, String> symbolImagePaths = Map.of(
                Symbol.Diamond, "CardDiamond",
                Symbol.Club, "CardClub",
                Symbol.Heart, "CardHeart",
                Symbol.Spade, "CardSpade",
                Symbol.Circle, "CardCircle",
                Symbol.Triangle, "CardTriangle",
                Symbol.Square, "CardSquare",
                Symbol.Star, "CardStar"
            );

            String symbolName = symbolImagePaths.get(cardSymbol);
            if (symbolName != null) {
                for (int i : new int[]{20, 50}) {
                    String imagePath = String.format("images/%sFrontFlipping%d.png", symbolName, i);
                    sprites.add(ImageIO.read(Objects.requireNonNull(classLoader.getResourceAsStream(imagePath))));
                }
                sprites.add(ImageIO.read(Objects.requireNonNull(classLoader.getResourceAsStream("images/" + symbolName + ".png"))));
            }

            assert sprites.size() == maxAnimationNum + 1;
        }
        catch (Exception e) {
            System.out.println("Card image loading not working");
            e.printStackTrace();
        }

        this.cardSymbol = cardSymbol;
    }

    //******************************************************************************************************************
    //* getters and setters
    //******************************************************************************************************************
    /**
     * Returns this Card object's cardSymbol attribute
     * @return this Card object's cardSymbol attribute
     */
    public Symbol getCardSymbol() {
        return cardSymbol;
    }

    /**
     * Returns this Card object's flipState attribute
     * @return this Card object's flipState attribute
     */
    public int getFlipState() {
        return flipState;
    }

    //******************************************************************************************************************
    //* general methods
    //******************************************************************************************************************
    /**
     * Set various variables so that this card can calculate where it should be and where it needs to go
     * @param rowNum 0-based numbered row that this card belongs in
     * @param colNum 0-based numbered column that this card belongs in
     * @param maxRow the number of rows present in the game
     * @param maxCol the number of columns present in the game
     */
    public void initialize(int rowNum, int colNum, int maxRow, int maxCol) {
        curPosX = ((maxCol * GameBoard.panelScreenWidth) / 2) - (Card.cardStandardWidth / 2);
        curPosY = maxRow * GameBoard.panelScreenHeight;
        finalPosX = centerX + (colNum * GameBoard.panelScreenWidth);
        finalPosY = centerY + (rowNum * GameBoard.panelScreenHeight);
        XPosIncrementVal = (finalPosX - curPosX) / maxPosIncrementIndex;
        YPosIncrementVal = (finalPosY - curPosY) / maxPosIncrementIndex;
    }

    /**
     * Activates this Card so that it starts moving from the start position towards it's row x col position
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
     * Given an x and y coordinate, returns true if that coordinate is within the range of this Card object
     * @param x the x portion of the position to check (x increases from left to right)
     * @param y the y portion of the position to check (y increases from top to down)
     * @return true if x y coordinate pair is within range of the Card object, false if not
     */
    public boolean pointInRange(int x, int y) {
        return x >= curPosX && x <= curPosX + cardStandardWidth && y >= curPosY && y <= curPosY + cardStandardHeight;
    }

    //******************************************************************************************************************
    //* methods called every frame
    //******************************************************************************************************************
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
                curPosX = finalPosX + ((cardStandardWidth - sprites.get(curAnimationNum).getWidth()) / 2) + (GameBoard.panelScreenWidth * col);
                curPosY = finalPosY + (max(minAnimationNum - curAnimationNum, curAnimationNum - maxAnimationNum) * 3) + (GameBoard.panelScreenHeight * row);
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
