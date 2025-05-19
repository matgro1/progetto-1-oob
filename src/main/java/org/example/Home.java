package org.example;

import org.example.model.MainPage;
import org.example.model.Utente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Logger;

// todo si deve gestire con un controller non direttamente
// todo esportarlo come jar
public class Home {

    private static ArrayList<Utente> utenti = new ArrayList<>();


    private JPanel login;
    private JTextField username;
    private JPasswordField password;
    private JButton loginButton;
    private JButton registerButton;
    private static JFrame frame;
    public static void main(String[] args) {


        utenti.add(new Utente("user", "pass"));
        Utente utenteProva= utenti.get(0);
        utenteProva.accesso("user","pass");
        utenteProva.creaBacheca("prova","lorem ipsum");
        utenteProva.logout();
        frame = new JFrame("login");
        frame.setSize(800, 600);
        frame.setContentPane(new Home().login);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public Home() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputUsername = username.getText();
                char[] inputPasswordChars = password.getPassword();

                String inputPassword = new String(inputPasswordChars);

                boolean loggedIn = false;
                for (Utente u : utenti) {
                    if (u.getLogin().equals(inputUsername) && u.getPassword().equals(inputPassword)) {

                        u.accesso(inputUsername, inputPassword);
                        loggedIn = true;
                        frame.setVisible(true);
                        frame.getContentPane().removeAll();
                        frame.setContentPane(new MainPage(frame, u).getMainPage());
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

        });
        registerButton.addActionListener(e-> {
                String inputUsername = username.getText();
                char[] inputPasswordChars = password.getPassword();

                String inputPassword = new String(inputPasswordChars);
                if (inputUsername.isEmpty()||inputPassword.isEmpty()){

                    JOptionPane.showMessageDialog(login, "Nome utente o password non validi!", "Errore di registrazione", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    utenti.add(new Utente(inputUsername,inputPassword));
                }
        });

    }
    // Da aggiungere nella classe Home
    public JPanel getLoginPanel() {
        return login;
    }

}
