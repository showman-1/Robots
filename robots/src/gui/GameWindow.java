package gui;

import model.RobotModel;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame {
    private final GameVisualizer visualizer;
    private GameController controller; // <-- добавили поле

    public GameWindow() {
        super("Игровое поле", true, true, true, true);
        this.visualizer = new GameVisualizer(); // <-- без параметров!
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        setSize(600, 500);

        // Добавляем слушатель мыши, который будет вызывать контроллер
        visualizer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (controller != null) {
                    controller.handleMouseClick(e.getPoint());
                }
            }
        });
    }

    // Метод для внедрения контроллера
    public void setController(GameController controller) {
        this.controller = controller;
    }

    public GameVisualizer getVisualizer() {
        return visualizer;
    }
}