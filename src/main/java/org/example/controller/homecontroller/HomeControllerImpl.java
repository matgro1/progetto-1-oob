package org.example.controller.homecontroller;

import org.example.Home;
import org.example.controller.ControllerFather;
import org.example.controller.SessionManager;
import org.example.dao.UtenteDAO.UtenteDAO;
import org.example.database.DatabaseConnection;
import org.example.gui.MainPage;
import org.example.model.Utente;

import javax.swing.*;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * The type Home controller.
 */
public class HomeControllerImpl extends ControllerFather implements HomeController {
    @Override
    public void inizializzazione() {
        JFrame frame = new JFrame("Progetto ToDo List");
        SessionManager.getInstance().setMainFrame(frame);

        frame.setSize(800, 600);
        frame.setContentPane(new Home().getLoginPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void goToMainPage(JTextField username, JPasswordField password, JPanel login) {
        String inputUsername = username.getText();
        String inputPassword = new String(password.getPassword());

        boolean loggedIn = false;

        ArrayList<Utente> utentiDalDb = UtenteDAO.findAll();

        for (Utente u : utentiDalDb) {
            if (u.getLogin().equals(inputUsername) && u.getPassword().equals(inputPassword)) {

                SessionManager.getInstance().setCurrentUser(u);
                loggedIn = true;

                JFrame frame = SessionManager.getInstance().getMainFrame();
                frame.setVisible(true);
                frame.getContentPane().removeAll();
                frame.setContentPane(new MainPage().getMainPage());
                frame.revalidate();
                frame.repaint();
                break;
            }
        }

        if (!loggedIn) {
            JOptionPane.showMessageDialog(login, "Nome utente o password non validi!", "Errore di Login", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(getClass().getName()).info("Credenziali non valide o utente non trovato.");
        }
    }

    @Override
    public void addUser(JTextField username, JPasswordField password, JPanel login) {
        String inputUsername = username.getText();
        String inputPassword = new String(password.getPassword());

        if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
            JOptionPane.showMessageDialog(login, "Nome utente o password non validi!", "Errore di registrazione", JOptionPane.ERROR_MESSAGE);
        } else {
            Utente nuovoUtente = new Utente(inputUsername, inputPassword);
            DatabaseConnection.utenteDB.save(nuovoUtente);
            JOptionPane.showMessageDialog(login, "Registrazione avvenuta con successo!");
        }
    }
}