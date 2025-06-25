package ms.logic.status;

import java.time.Instant;

/**
 * A utility class for measuring elapsed time in milliseconds.
 * This timer can be started, stopped, and reset, and provides
 * the elapsed time between start and stop (or current time if still running).
 */
public class Timer {
    /** The instant when the timer was started. Null if not started. */
    private Instant startTime;

    /** The instant when the timer was stopped. Null if not stopped. */
    private Instant endTime;

    /** Flag indicating whether the timer is currently running. */
    private boolean running;

    /**
     * Constructs a new Timer in a reset state (not running, no time recorded).
     */
    public Timer() {
        this.startTime = null;
        this.endTime = null;
        this.running = false;
    }

    /**
     * Starts the timer if it's not already running.
     * Resets any previous end time and begins counting from the current instant.
     */
    public void start() {
        if (!running) {
            startTime = Instant.now();
            endTime = null;
            running = true;
        }
    }

    /**
     * Stops the timer if it's currently running.
     * Records the current instant as the end time.
     */
    public void stop() {
        if (running) {
            endTime = Instant.now();
            running = false;
        }
    }

    /**
     * Resets the timer to its initial state.
     * Clears both start and end times and stops the timer.
     */
    public void reset() {
        startTime = null;
        endTime = null;
        running = false;
    }

    /**
     * Gets the elapsed time in milliseconds.
     *
     * @return the elapsed time in milliseconds, or 0 if not started
     */
    public long getElapsedTime() {
        if (startTime == null) {
            return 0;
        }
        Instant end = endTime != null ? endTime : Instant.now();
        return end.toEpochMilli() - startTime.toEpochMilli();
    }
}