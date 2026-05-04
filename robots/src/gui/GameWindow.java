package gui;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import controller.GameController;
import model.RobotModel;

public class GameWindow extends JInternalFrame {

    private final GameVisualizer visualizer;
    private GameController controller;

    public GameWindow(RobotModel model) {
        super("Игровое поле", true, true, true, true);
        visualizer = new GameVisualizer(model);

        // Создаем контроллер, который будет управлять таймерами
        controller = new GameController(model, () -> visualizer.requestRedraw());

        // Сообщаем визуализатору, как запрашивать перерисовку
        visualizer.setUpdateRequest(() -> visualizer.requestRedraw());

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        setSize(600, 500);
    }
}