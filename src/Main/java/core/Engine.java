package main.java.core;

import main.java.game.GameLogicDriver;

/**
 * Runs the game
 */
public class Engine implements Runnable {
    //******************************************************************************************************************
    //* variables
    //******************************************************************************************************************
    protected final int FPS = 60;

    // Thread that runs the game
    public Thread gameThread;

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
        if (gameThread == null) {
            System.out.println("Starting thread");
            gameThread = new Thread(this);
            gameThread.start();
        }
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
    @SuppressWarnings("all")
    public void run() {
        long targetFrameTime = 1000000000 / FPS; // Desired frame time for 60 FPS
        long lastFrameTime = System.nanoTime();
        long frameCount = 0;
        long lastFPSTime = System.nanoTime();

        while (gameThread != null) {
            long currentTime = System.nanoTime();
            long elapsedTime = currentTime - lastFrameTime;

            if (elapsedTime < targetFrameTime) {
                // Sleep to cap the frame rate
                try {
                    Thread.sleep((targetFrameTime - elapsedTime) / 1000000); // Convert nanoseconds to milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            lastFrameTime = System.nanoTime();

            if (GameLogicDriver.threadRunning()) {
                update();
                frameCount++;
            }

            // Calculate and print actual frame rate every second
            if (currentTime - lastFPSTime >= 1000000000) {
                //System.out.println("FPS: " + frameCount);
                frameCount = 0;
                lastFPSTime = currentTime;
            }
        }
        System.out.println("Thread terminating");
    }
}
