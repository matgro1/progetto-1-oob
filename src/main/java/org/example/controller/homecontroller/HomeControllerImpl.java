package org.example.controller.homecontroller;

import org.example.Home;
import org.example.gui.MainPage;
import org.example.model.Utente;
import org.example.controller.ControllerFather;

import javax.swing.*;
import java.util.logging.Logger;

public class HomeControllerImpl extends ControllerFather implements HomeController{
    @Override
    public void inizializzazione() {
        utenti.add(new Utente("user", "pass"));
        Utente utenteProva= utenti.getFirst();
        utenteProva.accesso("user","pass");
        utenteProva.creaBacheca("prova","lorem ipsum");
        utenteProva.logout();
        solarLintMerda2IlRitorno();
        frame.setSize(800, 600);
        frame.setContentPane(new Home().getLoginPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    @Override
    public void goToMainPage(JTextField username, JPasswordField password, JPanel login){
        String inputUsername = username.getText();
        char[] inputPasswordChars = password.getPassword();

        String inputPassword = new String(inputPasswordChars);

        boolean loggedIn = false;
        for (Utente u : utenti) {
            if (u.getLogin().equals(inputUsername) && u.getPassword().equals(inputPassword)) {

                u.accesso(inputUsername, inputPassword);
                loggedIn = true;
                solarLintMerda(u);
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
            Logger logger = Logger.getLogger(getClass().getName());
            logger.info("Credenziali non valide o utente non trovato.");
        }
    }
    @Override
    public void addUser(JTextField username, JPasswordField password, JPanel login){

        String inputUsername = username.getText();
        char[] inputPasswordChars = password.getPassword();

        String inputPassword = new String(inputPasswordChars);
        if (inputUsername.isEmpty()||inputPassword.isEmpty()){

            JOptionPane.showMessageDialog(login, "Nome utente o password non validi!", "Errore di registrazione", JOptionPane.ERROR_MESSAGE);
        }
        else{
            utenti.add(new Utente(inputUsername,inputPassword));
        }
    }
    private static void solarLintMerda(Utente u){
        utente = u;
    }
    private static void solarLintMerda2IlRitorno(){
        frame = new JFrame("login");
    }
}
