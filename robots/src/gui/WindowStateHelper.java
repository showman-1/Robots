package gui;

import java.util.Properties;
import javax.swing.JInternalFrame;

public class WindowStateHelper {

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