package main.java.core;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Used by a GamePanel to see when the player clicks on that panel
 */
public class GPMouseHandler implements MouseListener {
    //******************************************************************************************************************
    //* variables
    //******************************************************************************************************************
    GamePanel gamePanel;

    //******************************************************************************************************************
    //* constructor
    //******************************************************************************************************************
    public GPMouseHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    //******************************************************************************************************************
    //* overrides
    //******************************************************************************************************************
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse pressed");
        GameLogicDriver.getInstance();
        GameLogicDriver.checkCard(gamePanel.card);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
