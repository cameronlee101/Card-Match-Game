package main.java.entity;

import java.awt.*;

import static java.lang.Math.max;

public class MovingCard extends Card{
    //******************************************************************************************************************
    //* variables
    //******************************************************************************************************************


    //******************************************************************************************************************
    //* constructor
    //******************************************************************************************************************
    public MovingCard(Symbol symbol) {
        super(symbol);
    }

    /**
     * Once the card is activated, shifts it every frame from the default position to where the card is going
     */
    public void update() {
        super.update();

    }

    /**
     * Draws the current sprite for this card at its current position
     * @param g2 the Graphics2D object used to draw
     */
    @Override
    public void draw(Graphics2D g2) {
        // TODO: figure out math for card position
        int xPos = centerX + ((cardWidth - sprites.get(curAnimationNum).getWidth()) / 2);
        int yPos = centerY + (max(minAnimationNum - curAnimationNum, curAnimationNum - maxAnimationNum) * 3);
        if (spriteVisible) {
            g2.drawImage(sprites.get(curAnimationNum), xPos, yPos,
                    sprites.get(curAnimationNum).getWidth(), sprites.get(curAnimationNum).getHeight(), null);
        }
    }

}
