package org.example.controller.creabachecapagecontroller;

import org.example.controller.ControllerFather;
import org.example.controller.SessionManager;
import org.example.database.DatabaseConnection;
import org.example.gui.MainPage;
import org.example.model.Bacheca;
import org.example.model.Utente;

import javax.swing.*;

/**
 * The type Crea bacheca page controller.
 */
public class CreaBachecaPageControllerImpl extends ControllerFather implements CreaBachecaPageController {
    @Override
    public void returnMainPage() {
        JFrame frame = SessionManager.getInstance().getMainFrame();
        frame.setVisible(true);
        frame.getContentPane().removeAll();
        frame.setContentPane(new MainPage().getMainPage());
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void creaBacheca(JPanel creaBachecaPagePanel, JTextField textField1, JTextField textField2) {
        String inputTitolo = textField1.getText();
        String inputDescrizione = textField2.getText();

        Utente currentUser = SessionManager.getInstance().getCurrentUser();

        if (inputTitolo.isEmpty()) {
            JOptionPane.showMessageDialog(creaBachecaPagePanel, "Titolo non valido!", "Errore", JOptionPane.ERROR_MESSAGE);
        } else if (currentUser != null) {
            DatabaseConnection.bachecaDB.save(new Bacheca(inputTitolo, inputDescrizione, currentUser.getId()));

            returnMainPage();
        }
    }
}