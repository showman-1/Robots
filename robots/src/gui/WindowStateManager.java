package gui;

import java.awt.Point;
import java.io.*;
import java.util.Properties;
import javax.swing.JInternalFrame;

public class WindowStateManager {

    private static final String CONFIG_FILE = System.getProperty("user.home") +
            File.separator +
            ".robot_game_config.properties";

    private Properties properties;

    public WindowStateManager() {
        properties = new Properties();
    }

    public void saveWindowState(JInternalFrame frame, String windowKey) {
        try {
            Point location = frame.getLocation();
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

    public void saveToFile() {
        try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
            properties.store(out, "Robot Game Configuration");
        } catch (IOException e) {
            System.err.println("Ошибка сохранения конфигурации: " + e.getMessage());
        }
    }

    public boolean loadFromFile() {
        File configFile = new File(CONFIG_FILE);
        if (!configFile.exists()) {
            return false;
        }

        try (FileInputStream in = new FileInputStream(configFile)) {
            properties.load(in);
            return true;
        } catch (IOException e) {
            System.err.println("Ошибка загрузки конфигурации: " + e.getMessage());
            return false;
        }
    }

    public void applyWindowState(JInternalFrame frame, String windowKey) {
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