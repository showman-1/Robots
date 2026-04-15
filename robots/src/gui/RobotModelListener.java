package gui;

public interface RobotModelListener {

    void onRobotStateChanged(double x, double y, double direction, double targetX, double targetY);
}