package gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import model.RobotModel;
import model.RobotModelListener;

public class RobotCoordinatesWindow extends JInternalFrame implements RobotModelListener {

    private JTextArea coordinatesArea;
    private java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
    private RobotModel model;

    public RobotCoordinatesWindow(RobotModel model) {
        super("Координаты робота", true, true, true, true);
        this.model = model;

        setSize(250, 200);
        setLocation(320, 10);

        model.addListener(this);

        createUI();

        updateDisplay(model.getX(), model.getY(), model.getDirection(),
                model.getTargetX(), model.getTargetY());
    }

    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        coordinatesArea = new JTextArea();
        coordinatesArea.setEditable(false);          // Нельзя редактировать
        coordinatesArea.setFocusable(false);         // Нельзя получить фокус (убирает курсор)
        coordinatesArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        coordinatesArea.setBackground(null);          // Прозрачный фон (как у панели)

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(new TitledBorder("Текущее положение"));
        infoPanel.add(coordinatesArea, BorderLayout.CENTER);

        JLabel hintLabel = new JLabel("Данные обновляются автоматически");
        hintLabel.setFont(new Font("Dialog", Font.ITALIC, 10));

        mainPanel.add(infoPanel, BorderLayout.CENTER);
        mainPanel.add(hintLabel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        pack();
        setSize(250, 180);
    }

    private void updateDisplay(double x, double y, double direction, double targetX, double targetY) {
        StringBuilder sb = new StringBuilder();

        sb.append("Робот:\n");
        sb.append("  X: ").append(df.format(x)).append("\n");
        sb.append("  Y: ").append(df.format(y)).append("\n");

        double degrees = Math.toDegrees(direction);
        sb.append("  Угол: ").append(df.format(degrees)).append("°\n\n");

        sb.append("Цель:\n");
        sb.append("  X: ").append(df.format(targetX)).append("\n");
        sb.append("  Y: ").append(df.format(targetY)).append("\n\n");

        double distance = Math.hypot(x - targetX, y - targetY);
        sb.append("Расстояние: ").append(df.format(distance));

        coordinatesArea.setText(sb.toString());
    }

    @Override
    public void onRobotStateChanged(double x, double y, double direction, double targetX, double targetY) {
        java.awt.EventQueue.invokeLater(() -> {
            updateDisplay(x, y, direction, targetX, targetY);
        });
    }
}