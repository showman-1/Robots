package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;

public class LogWindow extends JInternalFrame implements LogChangeListener {

    private LogWindowSource logSource;
    private TextArea logContent;

    private String cachedContent = "";

    public LogWindow(LogWindowSource logSource) {
        super("Протокол работы", true, true, true, true);
        this.logSource = logSource;
        this.logSource.registerListener(this);

        logContent = new TextArea("");
        logContent.setEditable(false);
        logContent.setSize(200, 500);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();

        updateLogContent();
    }

    private void updateLogContent() {
        StringBuilder content = new StringBuilder();

        for (LogEntry entry : logSource.all()) {
            content.append(entry.getMessage()).append("\n");
        }

        String newContent = content.toString();

        if (!newContent.equals(cachedContent)) {
            logContent.setText(newContent);
            cachedContent = newContent;
        }

        logContent.invalidate();
    }

    @Override
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
    }
}