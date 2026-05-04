package gui;

import java.util.Properties;
import javax.swing.JInternalFrame;

public class WindowStateManager {

    private Properties properties = new Properties();
    private final ConfigFileManager fileManager = new ConfigFileManager();
    private final WindowStateHelper windowHelper = new WindowStateHelper();

    public WindowStateManager() {

    }


    public void saveWindowState(JInternalFrame frame, String windowKey) {
        windowHelper.saveWindowState(properties, frame, windowKey);
        fileManager.save(properties);
    }


    public void applyWindowState(JInternalFrame frame, String windowKey) {
        this.properties = fileManager.load();
        windowHelper.applyWindowState(properties, frame, windowKey);
    }
}