package utils;

public class PerformanceTimer {
    private long startTime;
    private long endTime;
    private boolean running;
    
    public void start() {
        startTime = System.currentTimeMillis();
        running = true;
    }
    
    public void stop() {
        endTime = System.currentTimeMillis();
        running = false;
    }
    
    public void reset() {
        startTime = 0;
        endTime = 0;
        running = false;
    }
    
    public long getElapsedTimeMillis() {
        return running ? System.currentTimeMillis() - startTime : endTime - startTime;
    }
    
    public String getElapsedTimeFormatted() {
        double seconds = getElapsedTimeMillis() / 1000.0;
        return String.format("%.2f seconds", seconds);
    }
} 