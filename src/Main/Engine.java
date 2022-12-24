package Main;

import java.awt.*;
import java.util.ArrayList;

public class Engine implements Runnable {
    //******************************************************************************************************************
    //* variables
    //******************************************************************************************************************
    int FPS = 60;

    // Thread that runs the game
    Thread gameThread;

    //
    GamePanel[][] gamePanels;
    int gamePanelsDimension;

    //******************************************************************************************************************
    //* constructor
    //******************************************************************************************************************
    public Engine() {

    }

    //******************************************************************************************************************
    //* setters and getters
    //******************************************************************************************************************
    /**
     *
     */
    public void setGamePanels(GamePanel[][] gamePanels) {
        this.gamePanels = gamePanels;
    }

    /**
     *
     */
    public void setGamePanelsDimension(int gamePanelsDimension) {
        this.gamePanelsDimension = gamePanelsDimension;
    }

    //******************************************************************************************************************
    //* methods
    //******************************************************************************************************************
    /**
     *
     */
    public void setupGame() {

    }

    /**
     *
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     *
     */
    // TODO: create new class that handles updating all the sprites
    public void update() {
        for (int i = 0; i < gamePanelsDimension; i++) {
            for (int j = 0; j < gamePanelsDimension; j++) {
                gamePanels[i][j].update();
            }
        }
    }

    /**
     *
     */
    @Override
    public void run() {

        double drawInterval = (double)1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                delta--;
                drawCount++;
            }

            /*
            // FPS counter
            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
            */
        }
    }

    /**
     *
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {

    }
}
