package controller;

import java.util.Timer;
import java.util.TimerTask;

import model.RobotModel;

/**
 * Контроллер игры - управляет таймерами и связывает модель с визуализацией
 */
public class GameController {

    private final Timer timer = new Timer("GameTimer", true);
    private RobotModel model;
    private Runnable redrawCallback;  // callback для перерисовки

    public GameController(RobotModel model, Runnable redrawCallback) {
        this.model = model;
        this.redrawCallback = redrawCallback;

        // Таймер для перерисовки (каждые 50 мс)
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (redrawCallback != null) {
                    java.awt.EventQueue.invokeLater(redrawCallback);
                }
            }
        }, 0, 50);

        // Таймер для обновления модели (каждые 10 мс)
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                model.update();
            }
        }, 0, 10);
    }

    /**
     * Останавливает таймеры (при необходимости)
     */
    public void stop() {
        timer.cancel();
    }
}