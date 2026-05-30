package log;

public final class Logger {

    private static final int DEFAULT_LOG_SIZE = 500;

    private static final LogWindowSource defaultLogSource;

    static {
        defaultLogSource = new LogWindowSource(DEFAULT_LOG_SIZE);
    }

    private Logger() {
    }

    public static void debug(String strMessage) {
        defaultLogSource.append(LogLevel.Debug, strMessage);
    }

    public static void error(String strMessage) {
        defaultLogSource.append(LogLevel.Error, strMessage);
    }

    public static void info(String strMessage) {
        defaultLogSource.append(LogLevel.Info, strMessage);
    }

    public static void warning(String strMessage) {
        defaultLogSource.append(LogLevel.Warning, strMessage);
    }

    public static LogWindowSource getDefaultLogSource() {
        return defaultLogSource;
    }
}