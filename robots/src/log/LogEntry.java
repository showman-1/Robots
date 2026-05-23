package log;

public class LogEntry {
    private final String message;
    private final long timestamp;

    public LogEntry(String message) {
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}