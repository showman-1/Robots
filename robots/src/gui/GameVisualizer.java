package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import model.RobotModel;

/**
 * Визуализатор игры - только отображение, без логики движения
 */
public class GameVisualizer extends JPanel {

    private RobotModel model;
    private Runnable updateRequest;  // для запроса перерисовки от контроллера

    public GameVisualizer(RobotModel model) {
        this.model = model;

        // Обработчик кликов мыши
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setTargetPosition(e.getPoint());
            }
        });

        setDoubleBuffered(true);
    }

    /**
     * Устанавливает callback для запроса перерисовки (вызывается контроллером)
     */
    public void setUpdateRequest(Runnable updateRequest) {
        this.updateRequest = updateRequest;
    }

    /**
     * Запрашивает перерисовку (вызывается из контроллера)
     */
    public void requestRedraw() {
        repaint();
    }

    private void setTargetPosition(Point p) {
        model.setTarget(p.x, p.y);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        int robotX = round(model.getX());
        int robotY = round(model.getY());
        double direction = model.getDirection();

        drawRobot(g2d, robotX, robotY, direction);
        drawTarget(g2d, round(model.getTargetX()), round(model.getTargetY()));
    }

    private static int round(double value) {
        return (int) (value + 0.5);
    }

    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private void drawRobot(Graphics2D g, int x, int y, double direction) {
        AffineTransform t = AffineTransform.getRotateInstance(direction, x, y);
        g.setTransform(t);

        g.setColor(Color.MAGENTA);
        fillOval(g, x, y, 30, 10);

        g.setColor(Color.BLACK);
        drawOval(g, x, y, 30, 10);

        g.setColor(Color.WHITE);
        fillOval(g, x + 10, y, 5, 5);

        g.setColor(Color.BLACK);
        drawOval(g, x + 10, y, 5, 5);

        g.setTransform(new AffineTransform());
    }

    private void drawTarget(Graphics2D g, int x, int y) {
        g.setTransform(new AffineTransform());
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }
}