package org.example;

import org.example.model.Utente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class home {

    private static ArrayList<Utente> utenti = new ArrayList<>();
    private static Utente utenteCorrente = null;


    private JPanel login;
    private JTextField username;
    private JPasswordField password;
    private JButton loginButton;
    private JButton registerButton;
    private static JFrame frame;
    public static void main(String[] args) {

        utenti.add(new Utente("user", "pass"));
        frame = new JFrame("login");
        frame.setContentPane(new home().login);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(800, 600);
    }
    public home() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputUsername = username.getText();
                char[] inputPasswordChars = password.getPassword();

                String inputPassword = new String(inputPasswordChars);

                boolean loggedIn = false;
                for (Utente u : utenti) {
                    // Usa .equals() per confrontare il contenuto delle stringhe
                    if (u.getLogin().equals(inputUsername) && u.getPassword().equals(inputPassword)) {
                        System.out.println("sgrodolix");
                        loggedIn = true;
                        utenteCorrente = u;
                        break;
                    }
                }

                if (!loggedIn) {
                    JOptionPane.showMessageDialog(login, "Nome utente o password non validi!", "Errore di Login", JOptionPane.ERROR_MESSAGE);
                    System.out.println("Credenziali non valide o utente non trovato.");
                }

            }

        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        });

    }

}
