package gui;

import java.util.Properties;
import javax.swing.JInternalFrame;

/**
 * Управляет сохранением и восстановлением состояния окон.
 * Использует ConfigFileManager для работы с файлом и WindowStateHelper для работы с окнами.
 */
public class WindowStateManager {

    private Properties properties;
    private ConfigFileManager fileManager;
    private WindowStateHelper windowHelper;

    public WindowStateManager() {
        this.properties = new Properties();
        this.fileManager = new ConfigFileManager();
        this.windowHelper = new WindowStateHelper();
    }

    /**
     * Загружает настройки из файла
     */
    public void loadFromFile() {
        this.properties = fileManager.loadFromFile();
    }

    /**
     * Сохраняет настройки в файл
     */
    public void saveToFile() {
        fileManager.saveToFile(properties);
    }

    /**
     * Сохраняет состояние одного окна
     */
    public void saveWindowState(JInternalFrame frame, String windowKey) {
        windowHelper.saveWindowState(properties, frame, windowKey);
    }

    /**
     * Восстанавливает состояние окна
     */
    public void applyWindowState(JInternalFrame frame, String windowKey) {
        windowHelper.applyWindowState(properties, frame, windowKey);
    }
}