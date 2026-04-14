package gui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JInternalFrame;

public class WindowStateSaver {

    private static final String CONFIG_FILE = System.getProperty("user.home") +
            File.separator +
            ".robot_game_config.properties";

    public void saveWindowState(Properties properties, JInternalFrame frame, String windowKey) {
        try {
            java.awt.Point location = frame.getLocation();
            properties.setProperty(windowKey + ".x", String.valueOf(location.x));
            properties.setProperty(windowKey + ".y", String.valueOf(location.y));

            properties.setProperty(windowKey + ".width", String.valueOf(frame.getWidth()));
            properties.setProperty(windowKey + ".height", String.valueOf(frame.getHeight()));

            if (frame.isIcon()) {
                properties.setProperty(windowKey + ".state", "ICONIFIED");
            } else if (frame.isMaximum()) {
                properties.setProperty(windowKey + ".state", "MAXIMIZED");
            } else {
                properties.setProperty(windowKey + ".state", "NORMAL");
            }

        } catch (Exception e) {
            System.err.println("Ошибка сохранения состояния окна " + windowKey + ": " + e.getMessage());
        }
    }

    public void saveToFile(Properties properties) {
        try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
            properties.store(out, "Robot Game Configuration");
        } catch (IOException e) {
            System.err.println("Ошибка сохранения конфигурации: " + e.getMessage());
        }
    }
}