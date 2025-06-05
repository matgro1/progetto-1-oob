package org.example.controller.modificauserpagecontroller;

import org.example.controller.ControllerFather;

import javax.swing.*;
public class ModificaUserPageControllerImpl extends ControllerFather implements ModificaUserPageController {
    @Override
    public void inizializazione(JTextField username, JTextField password) {
        username.setText(utente.getLogin());
        password.setText(utente.getPassword());
    }

    @Override
    public void modificaUtente(JTextField username, JTextField password) {
        String usernameS = username.getText();
        String passwordS= password.getText();
        utente.changeUser(usernameS, passwordS);
    }
}
