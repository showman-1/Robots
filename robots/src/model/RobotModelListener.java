package model;

/**
 * Слушатель изменений модели робота
 */
public interface RobotModelListener {

    /**
     * Вызывается при изменении состояния робота (движение или смена цели)
     */
    void onRobotStateChanged(double x, double y, double direction, double targetX, double targetY);
}