package org.example.controller.modificauserpagecontroller;

import javax.swing.*;

/**
 * The interface Modifica user page controller.
 */
public interface ModificaUserPageController {
    /**
     * Inizializazione.
     *
     * @param username the username
     * @param password the password
     */
    void inizializazione(JTextField username, JTextField password);

    /**
     * Modifica utente.
     *
     * @param username the username
     * @param password the password
     */
    void modificaUtente(JTextField username, JTextField password);
}
