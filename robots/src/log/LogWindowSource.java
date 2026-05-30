package log;

import java.util.*;

public class LogWindowSource {

    private final ConcurrentCircularBuffer<LogEntry> logEntries;
    private final List<LogChangeListener> listeners = new ArrayList<>();

    private volatile LogChangeListener[] activeListeners = new LogChangeListener[0];
    public LogWindowSource(int queueLength) {
        this.logEntries = new ConcurrentCircularBuffer<>(queueLength);
    }

    public void registerListener(LogChangeListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
            activeListeners = listeners.toArray(new LogChangeListener[0]);
        }
    }

    public void unregisterListener(LogChangeListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
            activeListeners = listeners.toArray(new LogChangeListener[0]);
        }
    }

    public void append(LogLevel logLevel, String strMessage) {
        LogEntry entry = new LogEntry(logLevel, strMessage);
        logEntries.add(entry);
        notifyListeners();
    }

    private void notifyListeners() {
        LogChangeListener[] snapshot = activeListeners;
        for (LogChangeListener listener : snapshot) {
            try {
                listener.onLogChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int size() {
        return logEntries.size();
    }

    public int getMaxSize() {
        return logEntries.getMaxSize();
    }

    public Iterable<LogEntry> range(int startFrom, int count) {
        if (startFrom < 0 || startFrom >= logEntries.size()) {
            return Collections.emptyList();
        }
        int end = Math.min(startFrom + count, logEntries.size());
        return logEntries.getRange(startFrom, end);
    }

    public Iterable<LogEntry> all() {
        return logEntries.getAll();
    }

    public Iterable<LogEntry> last(int count) {
        return logEntries.getLast(count);
    }

    public void clear() {
        logEntries.clear();
        notifyListeners();
    }
}