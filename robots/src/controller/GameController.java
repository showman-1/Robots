package controller;

import java.util.Timer;
import java.util.TimerTask;

import model.RobotModel;

public class GameController {

    private final Timer timer = new Timer("GameTimer", true);
    private RobotModel model;
    private Runnable redrawCallback;

    public GameController(RobotModel model, Runnable redrawCallback) {
        this.model = model;
        this.redrawCallback = redrawCallback;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (redrawCallback != null) {
                    java.awt.EventQueue.invokeLater(redrawCallback);
                }
            }
        }, 0, 50);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                model.update();
            }
        }, 0, 10);
    }

    public void stop() {
        timer.cancel();
    }
}