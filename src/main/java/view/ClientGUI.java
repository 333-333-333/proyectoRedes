package view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ClientGUI extends GUIFactory {

    private JPanel VideoPanel;
    private JLabel VideoLabel;
    private JTextArea LoggerDescription;
    private JScrollPane LoggerArea;

    public ClientGUI() {
        super(1080, 480);
        this.setVisible(true);
        loadElements();
        asignElements();
        viewPanel();
    }

    @Override
    protected void loadButton() {
        // No buttons required.
    }

    @Override
    protected void loadText() {
        VideoLabel = createSubtitle(null,
                0, 0,
                720, 480);
        LoggerDescription = createTextArea("Test",
                0, 0,
                360, 480);
    }

    @Override
    protected void loadPanel() {
        loadVideoPanel();
        loadLoggerArea();
    }

    private void loadVideoPanel() {
        this.VideoPanel = createPanel(0,0,720,480);
    }

    private void loadLoggerArea() {
        LoggerArea = createScrollPane(720, 0,
                360, 480);
    }
    @Override
    protected void loadTextField() {

    }

    @Override
    protected void asignToFrame() {
        add(VideoPanel);
        add(LoggerArea);
    }

    @Override
    protected void asignToPanel() {
        asignToVideoPanel();
        asignToLoggerArea();
    }

    protected void asignToVideoPanel() {
        this.VideoPanel.add(this.VideoLabel);
    }

    protected void asignToLoggerArea() {
        LoggerArea.add(LoggerDescription);
    }

    @Override
    protected void viewPanel() {
        this.VideoPanel.setVisible(true);
    }

    @Override
    protected void setActionListener(ActionListener listener) {

    }

    public JLabel getVideoLabel() {
        return VideoLabel;
    }


    public JTextArea getLoggerDescription() {
        return LoggerDescription;
    }

}