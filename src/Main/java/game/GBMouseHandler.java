package main.java.game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GBMouseHandler implements MouseListener {
    //******************************************************************************************************************
    //* variables
    //******************************************************************************************************************
    GameBoard gameBoard;

    //******************************************************************************************************************
    //* constructor
    //******************************************************************************************************************
    public GBMouseHandler(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    //******************************************************************************************************************
    //* overrides
    //******************************************************************************************************************
    @Override
    public void mouseClicked(MouseEvent e) {
        // Unused
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse pressed");
        gameBoard.checkCard(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Unused
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Unused
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Unused
    }
}
