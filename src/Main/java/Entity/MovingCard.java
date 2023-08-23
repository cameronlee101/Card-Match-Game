package main.java.entity;

import main.java.game.GamePanel;

import java.awt.*;

public class MovingCard extends Card{
    //******************************************************************************************************************
    //* variables
    //******************************************************************************************************************
    public boolean activated;

    private int positionIncrement = 0;
    private int maxPositionIncrement = 10;
    private int finalPosX, finalPosY, startingPosX, startingPosY;

    //******************************************************************************************************************
    //* constructor
    //******************************************************************************************************************
    public MovingCard(Symbol symbol, int rowNum, int colNum) {
        super(symbol);

        finalPosX = centerX + (colNum * GamePanel.screenWidth);
        finalPosY = centerY;
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

        }
    }

    /**
     * Draws the current sprite for this card at its current position
     * @param g2 the Graphics2D object used to draw
     */
    @Override
    public void draw(Graphics2D g2) {
        // TODO: figure out math for card position
        int xPos = centerX;
        int yPos = centerY;
        g2.drawImage(sprites.get(curAnimationNum), xPos, yPos,
                sprites.get(curAnimationNum).getWidth(), sprites.get(curAnimationNum).getHeight(), null);
    }

}
