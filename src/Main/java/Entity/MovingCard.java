package main.java.entity;

import main.java.game.GameLogicDriver;
import main.java.game.GamePanel;

import java.awt.*;

public class MovingCard extends Card{
    //******************************************************************************************************************
    //* variables
    //******************************************************************************************************************
    private boolean activated;

    private int posIncrementIndex = 0;
    private final int maxPosIncrementIndex = GameLogicDriver.singleCardDistributionTime;
    private final int XPosIncrementVal, YPosIncrementVal;
    private int curPosX, curPosY;

    //******************************************************************************************************************
    //* constructor
    //******************************************************************************************************************
    public MovingCard(Symbol symbol, int rowNum, int colNum, int maxRow, int maxCol) {
        super(symbol);

        curPosX = ((maxCol * GamePanel.screenWidth) / 2) - (Card.cardStandardWidth / 2);
        curPosY = maxRow * GamePanel.screenHeight;
        int finalPosX = centerX + (colNum * GamePanel.screenWidth);
        int finalPosY = centerY + (rowNum * GamePanel.screenHeight);
        XPosIncrementVal = (finalPosX - curPosX) / maxPosIncrementIndex;
        YPosIncrementVal = (finalPosY - curPosY) / maxPosIncrementIndex;
    }

    //******************************************************************************************************************
    //* methods
    //******************************************************************************************************************
    /**
     * Activates this MovingCard so that it starts moving towards it's intended position
     */
    public void activate() {
        this.activated = true;
    }

    /**
     * Once the card is activated, shifts it every frame from the default position to where the card is going
     */
    public void update() {
        super.update();
        if (activated) {
            while (posIncrementIndex < maxPosIncrementIndex) {
                curPosX += XPosIncrementVal;
                curPosY += YPosIncrementVal;
                posIncrementIndex++;
            }
        }
    }

    /**
     * Draws the current sprite for this card at its current position
     * @param g2 the Graphics2D object used to draw
     */
    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(sprites.get(curAnimationNum), curPosX, curPosY,
                     sprites.get(curAnimationNum).getWidth(), sprites.get(curAnimationNum).getHeight(), null);
    }

}
