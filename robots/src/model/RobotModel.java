package model;

import java.util.ArrayList;
import java.util.List;

public class RobotModel {

    private List<RobotModelListener> listeners = new ArrayList<>();

    private double x = 100;
    private double y = 100;
    private double direction = 0; // в радианах, 0 = вправо

    private double targetX = 150;
    private double targetY = 100;

    private static final double MAX_VELOCITY = 0.1;
    private static final double MAX_ANGULAR_VELOCITY = 0.001;

    public void addListener(RobotModelListener listener) {
        listeners.add(listener);
    }

    public void removeListener(RobotModelListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (RobotModelListener listener : listeners) {
            listener.onRobotStateChanged(x, y, direction, targetX, targetY);
        }
    }

    public void setTarget(double targetX, double targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
        notifyListeners();  // Оповещаем об изменении цели!
    }

    public boolean update() {
        double distance = distanceToTarget();

        if (distance < 0.5) {
            return true;
        }

        double angleToTarget = angleTo(targetX, targetY);
        double angularVelocity = calculateOptimalAngularVelocity(angleToTarget);

        moveRobot(MAX_VELOCITY, angularVelocity, 10);

        notifyListeners();

        return false;
    }

    private double calculateOptimalAngularVelocity(double targetAngle) {
        double angleDiff = targetAngle - direction;

        while (angleDiff > Math.PI) {
            angleDiff -= 2 * Math.PI;
        }
        while (angleDiff < -Math.PI) {
            angleDiff += 2 * Math.PI;
        }

        if (Math.abs(angleDiff) < 0.001) {
            return 0;
        }

        if (angleDiff > 0) {
            return MAX_ANGULAR_VELOCITY;
        } else {
            return -MAX_ANGULAR_VELOCITY;
        }
    }

    private double distanceToTarget() {
        double diffX = x - targetX;
        double diffY = y - targetY;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private double angleTo(double toX, double toY) {
        double diffX = toX - x;
        double diffY = toY - y;
        return normalizeAngle(Math.atan2(diffY, diffX));
    }

    private double normalizeAngle(double angle) {
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }

    private void moveRobot(double velocity, double angularVelocity, double duration) {
        velocity = applyLimits(velocity, 0, MAX_VELOCITY);
        angularVelocity = applyLimits(angularVelocity, -MAX_ANGULAR_VELOCITY, MAX_ANGULAR_VELOCITY);

        if (Math.abs(angularVelocity) < 0.00001) {
            x += velocity * duration * Math.cos(direction);
            y += velocity * duration * Math.sin(direction);
        } else {
            double newX = x + velocity / angularVelocity *
                    (Math.sin(direction + angularVelocity * duration) - Math.sin(direction));
            double newY = y - velocity / angularVelocity *
                    (Math.cos(direction + angularVelocity * duration) - Math.cos(direction));

            if (Double.isFinite(newX) && Double.isFinite(newY)) {
                x = newX;
                y = newY;
            }
        }

        direction = normalizeAngle(direction + angularVelocity * duration);
    }

    private double applyLimits(double value, double min, double max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getDirection() { return direction; }
    public double getTargetX() { return targetX; }
    public double getTargetY() { return targetY; }
}