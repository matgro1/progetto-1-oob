package org.example.controller.mainpagecontroller;

import org.example.Home;
import org.example.controller.ControllerFather;
import org.example.controller.SessionManager;
import org.example.database.DatabaseConnection;
import org.example.gui.BachecaMainPage;
import org.example.gui.CreaBachecaPage;
import org.example.gui.ModificaUserPage;
import org.example.model.Bacheca;
import org.example.model.Utente;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.util.List;

public class MainPageControllerImpl extends ControllerFather implements MainPageController {

    @Override
    public void returnHome() {
        SessionManager.getInstance().setCurrentUser(null); // Logout
        JFrame frame = SessionManager.getInstance().getMainFrame();
        frame.getContentPane().removeAll();
        frame.setContentPane(new Home().getLoginPanel());
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public DefaultListModel<Bacheca> creazioneLista() {
        DefaultListModel<Bacheca> bachecheModel = new DefaultListModel<>();
        Utente currentUser = SessionManager.getInstance().getCurrentUser();

        if (currentUser != null) {
            List<Bacheca> listaBacheche = DatabaseConnection.bachecaDB.findByUtenteId(currentUser.getId());

            for (Bacheca bacheca : listaBacheche) {
                bachecheModel.addElement(bacheca);
            }
        }
        return bachecheModel;
    }

    @Override
    public void goToCreaBachecaPage() {
        JFrame frame = SessionManager.getInstance().getMainFrame();
        frame.getContentPane().removeAll();
        frame.setContentPane(new CreaBachecaPage().getCreaBachecaPage());
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void goToDialogModificaUtente() {
        ModificaUserPage dialog = new ModificaUserPage();
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    @Override
    public void goToBachecaMainPage(ListSelectionEvent e, Bacheca bachecaSelezionata) {
        if (!e.getValueIsAdjusting() && bachecaSelezionata != null) {
            SessionManager.getInstance().setCurrentBacheca(bachecaSelezionata);

            JFrame frame = SessionManager.getInstance().getMainFrame();
            frame.getContentPane().removeAll();
            frame.setContentPane(new BachecaMainPage().getBachecaMainPage());
            frame.revalidate();
            frame.repaint();
        }
    }
}