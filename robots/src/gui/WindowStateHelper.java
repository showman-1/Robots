package gui;

import java.util.Properties;
import javax.swing.JInternalFrame;

/**
 * Отвечает ТОЛЬКО за сохранение и восстановление состояния окон в Properties.
 * Ничего не знает о файлах, только преобразует окно в Properties и обратно.
 */
public class WindowStateHelper {

    /**
     * Сохраняет состояние одного окна в Properties
     * @param properties куда сохранять
     * @param frame окно, состояние которого сохраняем
     * @param windowKey идентификатор окна ("logWindow", "gameWindow" и т.д.)
     */
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

    /**
     * Восстанавливает состояние окна из Properties
     * @param properties откуда восстанавливать
     * @param frame окно, которому применяем настройки
     * @param windowKey идентификатор окна
     */
    public void applyWindowState(Properties properties, JInternalFrame frame, String windowKey) {
        try {
            // Восстанавливаем позицию
            String xStr = properties.getProperty(windowKey + ".x");
            String yStr = properties.getProperty(windowKey + ".y");
            if (xStr != null && yStr != null) {
                frame.setLocation(Integer.parseInt(xStr), Integer.parseInt(yStr));
            }

            // Восстанавливаем размеры
            String widthStr = properties.getProperty(windowKey + ".width");
            String heightStr = properties.getProperty(windowKey + ".height");
            if (widthStr != null && heightStr != null) {
                frame.setSize(Integer.parseInt(widthStr), Integer.parseInt(heightStr));
            }

            // Восстанавливаем состояние
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
                            // Некоторые LookAndFeel могут не поддерживать maximize
                        }
                        break;
                    // case "NORMAL" - ничего не делаем, это состояние по умолчанию
                }
            }

        } catch (Exception e) {
            System.err.println("Ошибка восстановления окна " + windowKey + ": " + e.getMessage());
        }
    }
}