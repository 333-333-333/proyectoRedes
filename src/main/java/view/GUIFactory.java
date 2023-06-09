package view;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.SwingConstants.CENTER;

public abstract class GUIFactory extends JFrame {

    protected GUIFactory(int width, int length) {
        dimention(width, length);
        decorate();
        launch();
    }

    protected void loadElements() {
        loadPanel();
        loadButton();
        loadText();
        loadTextField();
    }

    protected abstract void loadButton();

    protected abstract void loadText();

    protected abstract void loadPanel();

    protected abstract void loadTextField();

    protected void asignElements() {
        asignToFrame();
        asignToPanel();
        loadTextField();
    }

    protected abstract void asignToFrame();

    protected abstract void asignToPanel();

    protected abstract void viewPanel();

    protected abstract void setActionListener(ActionListener listener);

    private void launch() {
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void dimention(int width, int length) {
        this.setResizable(false);
        this.setSize(width, length);
    }

    private void decorate() {
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(220, 220, 220));
    }

    protected JTextField createTextField(int x, int y,
                                         int width, int length) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, width, length);
        return textField;
    }

    protected JButton createButton(int x, int y,
                                   int width, int length,
                                   String texto) {
        JButton button = new JButton(texto);
        button.setBounds(x, y, width, length);
        button.setBackground(new Color(200, 120, 100));
        button.setForeground(Color.WHITE);;
        return button;
    }

    protected JButton createButton(String text) {
        JButton button = new JButton(text);
        return button;
    }

    protected JButton createButton(int ancho, int largo,
                                   String texto) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(ancho, largo));
        boton.setBackground(new Color(200, 120, 100));
        boton.setForeground(Color.WHITE);
        return boton;
    }

    protected JLabel createTitle(String text,
                                 int x, int y,
                                 int width, int length) {
        JLabel title = new JLabel(text);
        title.setBounds(x, y, width, length);
        title.setHorizontalAlignment(CENTER);
        title.setForeground(Color.BLACK);
        Font usedFont = new Font("Arial", 1, 35);
        title.setFont(usedFont);
        return title;
    }

    protected JLabel createSubtitle(String text,
                                    int x, int y,
                                    int width, int length) {
        JLabel subtitle = new JLabel(text);
        subtitle.setBounds(x, y, width, length);
        subtitle.setHorizontalAlignment(CENTER);
        subtitle.setForeground(Color.BLACK);
        Font usedFont = new Font("Arial", 1, 20);
        subtitle.setFont(usedFont);
        return subtitle;
    }

    protected JPanel createPanel(int x, int y,
                                 int width, int length) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(200, 200, 200));
        panel.setBounds(x, y, width, length);
        panel.setVisible(false);
        return panel;
    }

    protected JTextPane createTextPane(String text,
                                       int x, int y,
                                       int width, int length) {
        JTextPane textPane = new JTextPane();
        textPane.setText(text);
        textPane.setEditable(false);
        textPane.setBounds(x, y,
                width, length);
        textPane.setOpaque(false);

        Font usedFont = new Font("Arial", 1, 20);
        textPane.setFont(usedFont);
        textPane.setForeground(Color.WHITE);

        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        return textPane;
    }

}