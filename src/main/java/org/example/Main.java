package org.example;

import org.example.model.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    private static ArrayList<Utente> utenti = new ArrayList<>();
    private static Utente utenteCorrente;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Gestore ToDo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panelLogin = new JPanel();
        panelLogin.setLayout(new BoxLayout(panelLogin, BoxLayout.Y_AXIS));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        panelLogin.add(new JLabel("Username:"));
        panelLogin.add(usernameField);
        panelLogin.add(new JLabel("Password:"));
        panelLogin.add(passwordField);
        panelLogin.add(new JLabel());
        panelLogin.add(loginButton);
        panelLogin.add(registerButton);

        frame.add(panelLogin);
        frame.setVisible(true);

        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Verifica se l'utente esiste già
            for (Utente utente : utenti) {
                if (utente.getLogin().equals(username)) {
                    JOptionPane.showMessageDialog(frame, "L'utente esiste già.");
                    return;
                }
            }

            // Aggiungi un nuovo utente
            utenti.add(new Utente(username, password));
            JOptionPane.showMessageDialog(frame, "Registrazione riuscita!");
        });

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            boolean trovato = false;
            for (Utente utente : utenti) {
                if (utente.getLogin().equals(username) && utente.getPassword().equals(password)) {
                    utenteCorrente = utente;
                    trovato = true;
                    break;
                }
            }

            if (trovato) {
                JOptionPane.showMessageDialog(frame, "Login riuscito!");
                frame.getContentPane().removeAll();
                showDashboard(frame);
            } else {
                JOptionPane.showMessageDialog(frame, "Login fallito, riprova.");
            }
        });
    }

    private static void showDashboard(JFrame frame) {
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new BorderLayout());

        JLabel dashboardLabel = new JLabel("Benvenuto nella tua dashboard, " + utenteCorrente.getLogin() + "!");
        dashboardPanel.add(dashboardLabel, BorderLayout.NORTH);


        frame.add(dashboardPanel);
        frame.revalidate();
        frame.repaint();
    }
}
