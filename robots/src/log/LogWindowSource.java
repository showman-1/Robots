package log;

import collections.BoundedCircularList;
import java.util.List;

public class LogWindowSource {
    private final List<LogEntry> entries;
    private final List<LogChangeListener> listeners = new java.util.ArrayList<>();

    public LogWindowSource() {
        this.entries = new BoundedCircularList<>(5);
    }

    public void add(LogEntry entry) {
        entries.add(entry);
        notifyListeners();
    }

    public List<LogEntry> all() {
        return entries;
    }

    public void registerListener(LogChangeListener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(LogChangeListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (LogChangeListener listener : listeners) {
            listener.onLogChanged();
        }
    }
}