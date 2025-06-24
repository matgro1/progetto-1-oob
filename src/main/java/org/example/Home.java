package org.example;

import org.example.controller.homecontroller.HomeController;
import org.example.controller.homecontroller.HomeControllerImpl;

import javax.swing.*;

public class Home {



    private JPanel login;
    private JTextField username;
    private JPasswordField password;
    private JButton loginButton;
    private JButton registerButton;
    private static final HomeController controller = new HomeControllerImpl();
    public static void main(String[] args) {
        controller.inizializzazione();
    }
    public Home() {

        loginButton.addActionListener(e->controller.goToMainPage(username,password,login));
        registerButton.addActionListener(e-> controller.addUser(username,password,login));

    }
    // Da aggiungere nella classe Home
    public JPanel getLoginPanel() {
        return login;
    }

}
