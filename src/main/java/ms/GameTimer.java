package ms;

import java.time.Instant;
public class GameTimer {
    private Instant startTime;
    private Instant endTime;
    private boolean running;

    public GameTimer() {
        this.startTime = null;
        this.endTime = null;
        this.running = false;
    }

    public void start() {
        if (!running) {
            startTime = Instant.now();
            endTime = null;
            running = true;
        }
    }

    public void stop() {
        if (running) {
            endTime = Instant.now();
            running = false;
        }
    }

    public void reset() {
        startTime = null;
        endTime = null;
        running = false;
    }

    public long getElapsedTime() {
        if (startTime == null) {
            return 0;
        }
        Instant end = endTime != null ? endTime : Instant.now();
        return end.toEpochMilli() - startTime.toEpochMilli();
    }

    public boolean isRunning() {
        return running;
    }
}