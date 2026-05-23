package gui;

import config.WindowStateStore;
import model.RobotModel;
import log.Logger;

import java.awt.Frame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class RobotsProgram {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            // 1. Создаем независимые компоненты
            RobotModel robotModel = new RobotModel();
            WindowStateStore windowStateStore = new WindowStateStore();
            var logSource = Logger.getDefaultLogSource();

            // 2. Создаем главное окно, передавая зависимости
            MainApplicationFrame frame = new MainApplicationFrame(windowStateStore, robotModel, logSource);

            frame.pack();
            frame.setVisible(true);
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        });
    }
}