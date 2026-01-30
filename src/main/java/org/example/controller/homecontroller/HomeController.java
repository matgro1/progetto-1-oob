package org.example.controller.homecontroller;

import javax.swing.*;

/**
 * The interface Home controller.
 */
public interface HomeController {
    /**
     * Inizializzazione.
     */
    void inizializzazione();

    /**
     * Go to main page.
     *
     * @param username the username
     * @param password the password
     * @param login    the login
     */
    void goToMainPage(JTextField username, JPasswordField password, JPanel login);

    /**
     * Add user.
     *
     * @param username the username
     * @param password the password
     * @param login    the login
     */
    void addUser(JTextField username, JPasswordField password, JPanel login);
}
