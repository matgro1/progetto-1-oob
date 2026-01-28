package org.example.controller.modificauserpagecontroller;

import org.example.controller.ControllerFather;
import org.example.controller.SessionManager;
import org.example.database.DatabaseConnection;
import org.example.model.Utente;

import javax.swing.*;

public class ModificaUserPageControllerImpl extends ControllerFather implements ModificaUserPageController {
    @Override
    public void inizializazione(JTextField username, JTextField password) {
        Utente u = SessionManager.getInstance().getCurrentUser();
        if (u != null) {
            username.setText(u.getLogin());
            password.setText(u.getPassword());
        }
    }

    @Override
    public void modificaUtente(JTextField username, JTextField password) {
        Utente u = SessionManager.getInstance().getCurrentUser();
        if (u != null) {
            String usernameS = username.getText();
            String passwordS = password.getText();
            u.changeUser(usernameS, passwordS);

            DatabaseConnection.utenteDB.update(u);
        }
    }
}