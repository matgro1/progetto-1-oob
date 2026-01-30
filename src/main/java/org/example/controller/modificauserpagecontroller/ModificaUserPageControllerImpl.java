package org.example.controller.modificauserpagecontroller;

import org.example.Home;
import org.example.controller.ControllerFather;
import org.example.controller.SessionManager;
import org.example.database.DatabaseConnection;
import org.example.model.Utente;

import javax.swing.*;

/**
 * The type Modifica user page controller.
 */
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

    @Override
    public void cancellaUtente() {
        Utente u = SessionManager.getInstance().getCurrentUser();
        int confirm = JOptionPane.showConfirmDialog(null,
                "Sei sicuro di voler eliminare il tuo account? Questa azione è irreversibile.",
                "Conferma eliminazione",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            DatabaseConnection.utenteDB.delete(u.getId());

            SessionManager.getInstance().setCurrentUser(null);
            JFrame frame = SessionManager.getInstance().getMainFrame();
            frame.getContentPane().removeAll();
            frame.setContentPane(new Home().getLoginPanel());
            frame.revalidate();
            frame.repaint();
        }
    }
}