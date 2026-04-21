package gui;

import java.util.Properties;
import javax.swing.JInternalFrame;

public class WindowStateManager {

    private Properties properties;
    private ConfigFileManager fileManager;
    private WindowStateHelper windowHelper;

    public WindowStateManager() {
        this.properties = new Properties();
        this.fileManager = new ConfigFileManager();
        this.windowHelper = new WindowStateHelper();
    }


    public void loadFromFile() {
        this.properties = fileManager.loadFromFile();
    }


    public void saveToFile() {
        fileManager.saveToFile(properties);
    }


    public void saveWindowState(JInternalFrame frame, String windowKey) {
        windowHelper.saveWindowState(properties, frame, windowKey);
    }


    public void applyWindowState(JInternalFrame frame, String windowKey) {
        windowHelper.applyWindowState(properties, frame, windowKey);
    }
}