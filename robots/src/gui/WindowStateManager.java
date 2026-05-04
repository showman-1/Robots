package gui;

import java.util.Properties;
import javax.swing.JInternalFrame;

import model.RobotModel;  // если нужно

public class WindowStateManager {

    private Properties properties;
    private final ConfigFileManager fileManager = new ConfigFileManager();
    private final WindowStateHelper windowHelper = new WindowStateHelper();

    public WindowStateManager() {
        this.properties = new Properties();
        loadFromFile();  // сразу загружаем при создании
    }

    /**
     * Загружает настройки из файла
     */
    public void loadFromFile() {
        this.properties = fileManager.load();
    }

    /**
     * Сохраняет настройки в файл
     */
    public void saveToFile() {
        fileManager.save(properties);
    }

    /**
     * Сохраняет состояние окна (без автоматического сохранения в файл)
     */
    public void saveWindowState(JInternalFrame frame, String windowKey) {
        windowHelper.saveWindowState(properties, frame, windowKey);
    }

    /**
     * Сохраняет состояние окна и сразу записывает в файл
     */
    public void saveWindowStateAndFlush(JInternalFrame frame, String windowKey) {
        windowHelper.saveWindowState(properties, frame, windowKey);
        saveToFile();
    }

    /**
     * Восстанавливает состояние окна
     */
    public void applyWindowState(JInternalFrame frame, String windowKey) {
        windowHelper.applyWindowState(properties, frame, windowKey);
    }
}