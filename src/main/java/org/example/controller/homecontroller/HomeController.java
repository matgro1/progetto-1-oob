package org.example.controller.homecontroller;

import javax.swing.*;

public interface HomeController {
    void inizializzazione();

    void goToMainPage(JTextField username, JPasswordField password, JPanel login);
    void addUser(JTextField username, JPasswordField password, JPanel login);
}
