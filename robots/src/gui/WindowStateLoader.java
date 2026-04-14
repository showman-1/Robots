package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JInternalFrame;

public class WindowStateLoader {

    private static final String CONFIG_FILE = System.getProperty("user.home") +
            File.separator +
            ".robot_game_config.properties";

    public Properties loadFromFile() {
        Properties properties = new Properties();
        File configFile = new File(CONFIG_FILE);

        if (!configFile.exists()) {
            return properties;
        }

        try (FileInputStream in = new FileInputStream(configFile)) {
            properties.load(in);
        } catch (IOException e) {
            System.err.println("Ошибка загрузки конфигурации: " + e.getMessage());
        }

        return properties;
    }

    public void applyWindowState(Properties properties, JInternalFrame frame, String windowKey) {
        try {
            String xStr = properties.getProperty(windowKey + ".x");
            String yStr = properties.getProperty(windowKey + ".y");
            if (xStr != null && yStr != null) {
                frame.setLocation(Integer.parseInt(xStr), Integer.parseInt(yStr));
            }

            String widthStr = properties.getProperty(windowKey + ".width");
            String heightStr = properties.getProperty(windowKey + ".height");
            if (widthStr != null && heightStr != null) {
                frame.setSize(Integer.parseInt(widthStr), Integer.parseInt(heightStr));
            }

            String state = properties.getProperty(windowKey + ".state");
            if (state != null) {
                switch (state) {
                    case "ICONIFIED":
                        frame.setIcon(true);
                        break;
                    case "MAXIMIZED":
                        try {
                            frame.setMaximum(true);
                        } catch (Exception e) {
                        }
                        break;
                }
            }

        } catch (Exception e) {
            System.err.println("Ошибка восстановления окна " + windowKey + ": " + e.getMessage());
        }
    }
}