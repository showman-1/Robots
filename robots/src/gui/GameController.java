package gui;

import model.RobotModel;
import java.awt.Point;
import javax.swing.Timer;

public class GameController {
    private final RobotModel model;
    private final GameVisualizer view;
    private final Timer timer;

    public GameController(RobotModel model, GameVisualizer view) {
        this.model = model;
        this.view = view;

        // Подписываем view на обновления модели
        model.addListener(view);

        // Создаем таймер для обновления модели
        this.timer = new Timer(50, (e) -> {
            model.updateModel();
        });
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void handleMouseClick(Point point) {
        model.setTargetPosition(point.x, point.y);
    }
}