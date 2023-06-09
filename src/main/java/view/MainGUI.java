package view;



import javax.swing.*;
import java.awt.event.ActionListener;

public class MainGUI extends GUIFactory {


    private JPanel MainPane;
    private JButton Login, Register;
    private JTextField UserField, PasswordField;

    public MainGUI() {
        super(1280, 720);
        this.setVisible(true);
        loadElements();
        asignElements();
        viewPanel();

    }

    protected void loadButton() {
        cargarBotonesPanelPrincipal();
    }

    protected void loadTextField() {
        this.UserField = createTextField(200, 200,
                325, 25);
        this.PasswordField = createTextField(200, 300,
                325, 25);
    }

    private void cargarBotonesPanelPrincipal() {
        this.Login = createButton(200, 500,
                150, 25,
                "Iniciar sesión");

        this.Register = createButton(375, 500,
                150, 25,
                "Registrarse");
    }

    @Override
    protected void loadText() {
    }

    @Override
    protected void loadPanel() {
        cargarPanelPrincipal();
    }

    private void cargarPanelPrincipal() {
        this.MainPane = createPanel(0, 100,
                1280, 620);
    }

    @Override
    protected void asignToFrame() {
        this.add(this.MainPane);
    }


    @Override
    protected void asignToPanel() {
        asignarAPanelPrincipal();
    }

    private void asignarAPanelPrincipal() {
        this.MainPane.add(this.UserField);
        this.MainPane.add(this.PasswordField);

        this.MainPane.add(this.Login);
        this.MainPane.add(this.Register);


    }

    protected void viewPanel() {
        this.MainPane.setVisible(true);
    }

    public void setActionListener(ActionListener a) {
        this.Login.addActionListener(a);
        this.Register.addActionListener(a);
    }

    public JButton getRegister() {
        return this.Register;
    }

    public JButton getLogin() {
        return this.Login;
    }

}