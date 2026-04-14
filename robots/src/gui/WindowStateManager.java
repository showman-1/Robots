package gui;

import java.util.Properties;
import javax.swing.JInternalFrame;

public class WindowStateManager {

    private Properties properties;
    private WindowStateSaver saver;
    private WindowStateLoader loader;

    public WindowStateManager() {
        this.properties = new Properties();
        this.saver = new WindowStateSaver();
        this.loader = new WindowStateLoader();
    }

    public void loadFromFile() {
        this.properties = loader.loadFromFile();
    }

    public void saveToFile() {
        saver.saveToFile(properties);
    }

    public void saveWindowState(JInternalFrame frame, String windowKey) {
        saver.saveWindowState(properties, frame, windowKey);
    }

    public void applyWindowState(JInternalFrame frame, String windowKey) {
        loader.applyWindowState(properties, frame, windowKey);
    }
}