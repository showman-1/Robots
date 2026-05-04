package gui;

import java.util.Properties;
import javax.swing.JInternalFrame;



public class WindowStateManager {

    private Properties properties;
    private final ConfigFileManager fileManager = new ConfigFileManager();
    private final WindowStateHelper windowHelper = new WindowStateHelper();

    public WindowStateManager() {
        this.properties = new Properties();
        loadFromFile();
    }

    public void loadFromFile() {
        this.properties = fileManager.load();
    }

    public void saveToFile() {
        fileManager.save(properties);
    }
    public void saveWindowState(JInternalFrame frame, String windowKey) {
        windowHelper.saveWindowState(properties, frame, windowKey);
    }

    public void saveWindowStateAndFlush(JInternalFrame frame, String windowKey) {
        windowHelper.saveWindowState(properties, frame, windowKey);
        saveToFile();
    }


    public void applyWindowState(JInternalFrame frame, String windowKey) {
        windowHelper.applyWindowState(properties, frame, windowKey);
    }
}