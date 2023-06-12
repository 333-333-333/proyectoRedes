package view;



import javax.swing.*;
import java.awt.event.ActionListener;

public class MainGUI extends GUIFactory {


    private JPanel MainPane;
    private JButton Login, Register;
    private JTextField UserField, PasswordField;

    public MainGUI() {
        super(1280, 720);
        setVisible(true);
        loadElements();
        asignElements();
        viewPanel();
    }

    protected void loadButton() {
        cargarBotonesPanelPrincipal();
    }

    protected void loadTextField() {
        UserField = createTextField(200, 200,
                325, 25);

        PasswordField = createTextField(200, 300,
                325, 25);
    }

    private void cargarBotonesPanelPrincipal() {
        Login = createButton(200, 500,
                150, 25,
                "Iniciar sesión");

        Register = createButton(375, 500,
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
        MainPane = createPanel(0, 100,
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
        MainPane.add(this.UserField);
        MainPane.add(this.PasswordField);

        MainPane.add(this.Login);
        MainPane.add(this.Register);

    }

    protected void viewPanel() {
        this.MainPane.setVisible(true);
    }

    public void setActionListener(ActionListener a) {
        Login.addActionListener(a);
        Register.addActionListener(a);
    }

    public JPanel getMainPane() {
        return MainPane;
    }

    public void setMainPane(JPanel mainPane) {
        MainPane = mainPane;
    }

    public JButton getLogin() {
        return Login;
    }

    public void setLogin(JButton login) {
        Login = login;
    }

    public JButton getRegister() {
        return Register;
    }

    public void setRegister(JButton register) {
        Register = register;
    }

    public JTextField getUserField() {
        return UserField;
    }

    public void setUserField(JTextField userField) {
        UserField = userField;
    }

    public JTextField getPasswordField() {
        return PasswordField;
    }

    public void setPasswordField(JTextField passwordField) {
        PasswordField = passwordField;
    }
}