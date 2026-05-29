package gui;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import controller.GameController;
import model.RobotModel;

public class GameWindow extends JInternalFrame {

    private final GameVisualizer visualizer;
    private final GameController controller;

    public GameWindow(RobotModel model) {
        super("Игровое поле", true, true, true, true);

        this.visualizer = new GameVisualizer(null, model);

        this.controller = new GameController(model, () -> visualizer.requestRedraw());

        this.visualizer.setController(this.controller);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        setSize(600, 500);
    }
}