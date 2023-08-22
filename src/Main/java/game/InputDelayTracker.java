package main.java.game;

public class InputDelayTracker {
    //******************************************************************************************************************
    //* variables
    //******************************************************************************************************************
    // Various timers and the length of time those timers go for (in frames)
    // note: current implementation makes it so the number of frames the timer goes for is duration-2
    private static final int flipDelayDuration = 10;
    private static final int holdDelayDuration = 40;
    private static int openingAnimationDelay = 0;
    private static int flipDelayTimer = 0;
    private static int holdDelayTimer = 0;
    private static int openingAnimationDelayTimer = 0;

    //******************************************************************************************************************
    //* constructor
    //******************************************************************************************************************
    public InputDelayTracker() {
    }

    //******************************************************************************************************************
    //* methods
    //******************************************************************************************************************
    /**
     * Activates the flipDelayTimer (for use to stop input when card flip animation happens)
     */
    public void startFlipTimer() {
        flipDelayTimer++;
    }

    /**
     * Activates the holdDelayTimer (for use when showing two incorrect cards briefly before flipping them back over)
     */
    public void startHoldTimer() {
        holdDelayTimer++;
    }

    /**
     * Activates the openingAnimationDelayTimer with the given upper limit
     * @param openingAnimationDelay the upper limit for this timer
     */
    public void startOpeningAnimationDelay(int openingAnimationDelay) {
        InputDelayTracker.openingAnimationDelay = openingAnimationDelay;
        openingAnimationDelayTimer++;
    }

    /**
     * Returns true if one or more of the delay timers have been activated, false if not
     * @return true if one or more of the delay timers have been activated, false if not
     */
    public boolean delayingInput() {
        return flipDelayTimer != 0 || holdDelayTimer != 0 || openingAnimationDelayTimer != 0;
    }

    /**
     * Is called every frame to increment the delay timers if any of them are activated
     * If a timer finishes, calls additional methods depending on the timer
     */
    public void update() {
        if (flipDelayTimer != 0) {
            flipDelayTimer++;
            if (flipDelayTimer == flipDelayDuration) {
                flipDelayTimer = 0;
                GameLogicDriver.needToStartHold();
            }
        }
        if (holdDelayTimer != 0) {
            holdDelayTimer++;
            if (holdDelayTimer == holdDelayDuration) {
                holdDelayTimer = 0;
                GameLogicDriver.unFlipCards();
            }
        }
        if (openingAnimationDelayTimer != 0) {
            openingAnimationDelayTimer++;
            if (openingAnimationDelayTimer == openingAnimationDelay) {
                openingAnimationDelayTimer = 0;
                GameLogicDriver.beginCardInteraction();
            }
        }
    }
}
