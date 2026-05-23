package log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final LogWindowSource logSource = new LogWindowSource();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static LogWindowSource getDefaultLogSource() {
        return logSource;
    }

    public static void debug(String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        LogEntry entry = new LogEntry(timestamp + " [DEBUG] " + message);
        logSource.add(entry);
    }

    public static void info(String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        LogEntry entry = new LogEntry(timestamp + " [INFO] " + message);
        logSource.add(entry);
    }

    public static void error(String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        LogEntry entry = new LogEntry(timestamp + " [ERROR] " + message);
        logSource.add(entry);
    }
}