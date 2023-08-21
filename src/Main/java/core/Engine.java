package main.java.core;

import main.java.game.GameLogicDriver;

/**
 * Runs the game
 */
public class Engine implements Runnable {
    //******************************************************************************************************************
    //* variables
    //******************************************************************************************************************
    protected int FPS = 60;

    // Thread that runs the game
    protected Thread gameThread;

    //******************************************************************************************************************
    //* singleton constructor and methods
    //******************************************************************************************************************
    private static Engine engine = null;

    private Engine() {}

    /**
     * Checks if an instance of Engine exists yet. If it does, returns that instance. If not, creates and
     * returns a new instance
     * @return the Engine object if it exists already, new Engine object if it doesn't yet
     */
    public static Engine getInstance() {
        if (engine == null) {
            engine = new Engine();
        }
        return engine;
    }

    //******************************************************************************************************************
    //* methods
    //******************************************************************************************************************
    /**
     * Starts the thread that runs the game
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Calls update on the appropriate objects every frame
     */
    public void update() {
        GameLogicDriver.update();
    }

    /**
     * Is the main loop that runs the game. Calls update() every frame.
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
}
