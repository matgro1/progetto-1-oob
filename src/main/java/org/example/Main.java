package org.example;

import org.example.model.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<Utente> utenti = new ArrayList<>();
    private static Utente utenteCorrente = null; // Inizializza a null
    private static JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            utenti.add(new Utente("test", "test"));
            utenti.add(new Utente("user", "pass"));

            frame = new JFrame("Gestore ToDo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);

            showLoginPanel();

            frame.setVisible(true);
        });
    }

    private static void showLoginPanel() {
        if (utenteCorrente != null) {
            utenteCorrente.logout();
            utenteCorrente = null;
        }

        JPanel panelLogin = new JPanel();
        panelLogin.setLayout(new BoxLayout(panelLogin, BoxLayout.Y_AXIS));
        panelLogin.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        Dimension fieldMaxSize = new Dimension(Integer.MAX_VALUE, usernameField.getPreferredSize().height);
        usernameField.setMaximumSize(fieldMaxSize);
        passwordField.setMaximumSize(fieldMaxSize);

        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        fieldsPanel.add(new JLabel("Username:"));
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldsPanel.add(usernameField);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        fieldsPanel.add(new JLabel("Password:"));
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldsPanel.add(passwordField);

        panelLogin.add(fieldsPanel);
        panelLogin.add(Box.createRigidArea(new Dimension(0, 20)));
        panelLogin.add(loginButton);
        panelLogin.add(Box.createRigidArea(new Dimension(0, 10)));
        panelLogin.add(registerButton);

        updateFrameContent(panelLogin);

        registerButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Username e password non possono essere vuoti.", "Errore Registrazione", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean esisteGia = false;
            for (Utente utente : utenti) {
                if (utente.getLogin().equalsIgnoreCase(username)) {
                    esisteGia = true;
                    break;
                }
            }

            if (esisteGia) {
                JOptionPane.showMessageDialog(frame, "L'utente '" + username + "' esiste già.", "Errore Registrazione", JOptionPane.ERROR_MESSAGE);
                return;
            }

            utenti.add(new Utente(username, password));
            JOptionPane.showMessageDialog(frame, "Registrazione riuscita per l'utente '" + username + "'!", "Registrazione Completata", JOptionPane.INFORMATION_MESSAGE);
            usernameField.setText("");
            passwordField.setText("");
        });

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Username e password non possono essere vuoti.", "Errore Login", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Utente utenteTrovato = null;
            for (Utente utente : utenti) {
                utente.accesso(username, password);
                if (utente.isAccesso()) {
                    utenteTrovato = utente;
                    break;
                }
            }

            if (utenteTrovato != null) {
                utenteCorrente = utenteTrovato;
                showDashboard();
            } else {
                JOptionPane.showMessageDialog(frame, "Username o password errati.", "Login Fallito", JOptionPane.ERROR_MESSAGE);
                if (utenteCorrente != null) {
                    utenteCorrente.logout();
                    utenteCorrente = null;
                }
            }
        });
    }

    private static void showDashboard() {
        if (utenteCorrente == null || !utenteCorrente.isAccesso()) {
            showLoginPanel();
            JOptionPane.showMessageDialog(frame, "Errore: Utente non autenticato.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JPanel dashboardPanel = new JPanel(new BorderLayout(10, 10));
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel dashboardLabel = new JLabel("Benvenuto, " + utenteCorrente.getLogin() + "!");
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> showLoginPanel());
        topPanel.add(dashboardLabel);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(logoutButton);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JButton creaBachecaButton = new JButton("Crea Nuova Bacheca");
        creaBachecaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        creaBachecaButton.addActionListener(e -> showCreaBachecaPanel());

        JLabel bachecheLabel = new JLabel("Le tue bacheche:");
        bachecheLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        DefaultListModel<Bacheca> listModel = new DefaultListModel<>();
        utenteCorrente.getBacheche().forEach(listModel::addElement);

        JList<Bacheca> bachecheList = new JList<>(listModel);
        bachecheList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bachecheList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Bacheca selectedBacheca = bachecheList.getSelectedValue();
                if (selectedBacheca != null) {
                    bachecaUi(selectedBacheca);
                }
            }
        });


        JScrollPane bachecheScrollPane = new JScrollPane(bachecheList);
        bachecheScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        bachecheScrollPane.setPreferredSize(new Dimension(300, 200));
        bachecheScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));


        centerPanel.add(creaBachecaButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(bachecheLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        centerPanel.add(bachecheScrollPane);

        dashboardPanel.add(topPanel, BorderLayout.NORTH);
        dashboardPanel.add(centerPanel, BorderLayout.CENTER);

        updateFrameContent(dashboardPanel);
    }

    private static void showCreaBachecaPanel() {
        if (utenteCorrente == null || !utenteCorrente.isAccesso()) {
            showLoginPanel();
            return;
        }

        JPanel creaBachecaPanel = new JPanel();
        creaBachecaPanel.setLayout(new BoxLayout(creaBachecaPanel, BoxLayout.Y_AXIS));
        creaBachecaPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JTextField nomeBField = new JTextField(20);
        JTextArea descrizioneBField = new JTextArea(5, 20);
        descrizioneBField.setLineWrap(true);
        descrizioneBField.setWrapStyleWord(true);
        JScrollPane descScrollPane = new JScrollPane(descrizioneBField);

        JButton creaButton = new JButton("Crea Bacheca");
        JButton annullaButton = new JButton("Annulla");

        Dimension fieldMaxSize = new Dimension(Integer.MAX_VALUE, nomeBField.getPreferredSize().height);
        nomeBField.setMaximumSize(fieldMaxSize);
        descScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        creaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        annullaButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        creaBachecaPanel.add(new JLabel("Titolo Bacheca:"));
        creaBachecaPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        creaBachecaPanel.add(nomeBField);
        creaBachecaPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        creaBachecaPanel.add(new JLabel("Descrizione Bacheca (opzionale):"));
        creaBachecaPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        creaBachecaPanel.add(descScrollPane); // Aggiungi lo JScrollPane
        creaBachecaPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        creaBachecaPanel.add(creaButton);
        creaBachecaPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        creaBachecaPanel.add(annullaButton);

        updateFrameContent(creaBachecaPanel);

        annullaButton.addActionListener(e -> {
            showDashboard();
        });

        creaButton.addActionListener(e -> {
            String titolo = nomeBField.getText().trim();
            String descrizione = descrizioneBField.getText().trim();

            if (titolo.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Il titolo della bacheca non può essere vuoto.", "Errore Creazione", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean nomeEsistente = utenteCorrente.getBacheche().stream()
                    .anyMatch(b -> b.getNome().equalsIgnoreCase(titolo));
            if(nomeEsistente) {
                JOptionPane.showMessageDialog(frame, "Esiste già una bacheca con il titolo '" + titolo + "'.", "Errore Creazione", JOptionPane.ERROR_MESSAGE);
                return;
            }


            utenteCorrente.creaBacheca(titolo, descrizione);

            JOptionPane.showMessageDialog(frame, "Bacheca '" + titolo + "' creata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);

            showDashboard();
        });
    }

    private static void updateFrameContent(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }



    private static void bachecaUi(Bacheca selectedBacheca){
        JPanel bachecaUiPanel= new JPanel();
        bachecaUiPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> showDashboard());
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(logoutButton);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JButton creaBachecaButton = new JButton("Crea Nuovo ToDo");
        creaBachecaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        creaBachecaButton.addActionListener(e -> showCreaToDo(selectedBacheca));

        JLabel bachecheLabel = new JLabel("I tuoi ToDo:");
        bachecheLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        DefaultListModel<ToDo> listModel = new DefaultListModel<>();
        selectedBacheca.getToDo().forEach(listModel::addElement);



        JList<ToDo> ToDoList = new JList<>(listModel);
        ToDoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ToDoList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
            }
        });


        JScrollPane ToDoScrollPane = new JScrollPane(ToDoList);
        ToDoScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        ToDoScrollPane.setPreferredSize(new Dimension(300, 200));
        ToDoScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));


        centerPanel.add(creaBachecaButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(bachecheLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        centerPanel.add(ToDoScrollPane);

        bachecaUiPanel.add(topPanel, BorderLayout.NORTH);
        bachecaUiPanel.add(centerPanel, BorderLayout.CENTER);

        updateFrameContent(bachecaUiPanel);

    }

    private static void showCreaToDo(Bacheca selectedBacheca) {
        JPanel creaToDoPanel = new JPanel();
        creaToDoPanel.setLayout(new BoxLayout(creaToDoPanel, BoxLayout.Y_AXIS));
        creaToDoPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JTextField nomeTField = new JTextField(20);
        JTextField dataTField = new JTextField(20);

        JButton creaButton = new JButton("Crea ToDo");
        JButton annullaButton = new JButton("Annulla");

        Dimension fieldMaxSize = new Dimension(Integer.MAX_VALUE, nomeTField.getPreferredSize().height);
        nomeTField.setMaximumSize(fieldMaxSize);
        dataTField.setMaximumSize(fieldMaxSize);
        creaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        annullaButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        creaToDoPanel.add(new JLabel("Titolo ToDo:"));
        creaToDoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        creaToDoPanel.add(nomeTField);
        creaToDoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        creaToDoPanel.add(new JLabel("Data Scadenza (dd/MM/yyyy):"));
        creaToDoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        creaToDoPanel.add(dataTField);
        creaToDoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        creaToDoPanel.add(creaButton);
        creaToDoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        creaToDoPanel.add(annullaButton);

        updateFrameContent(creaToDoPanel);

        annullaButton.addActionListener(e -> bachecaUi(selectedBacheca));

        creaButton.addActionListener(e -> {
            String titolo = nomeTField.getText().trim();
            String dataTesto = dataTField.getText().trim();

            if (titolo.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Il titolo del ToDo non può essere vuoto.", "Errore Creazione", JOptionPane.WARNING_MESSAGE);
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate data;

            try {
                data = LocalDate.parse(dataTesto, formatter);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(frame, "Formato data non valido! Usa il formato dd/MM/yyyy.", "Errore Creazione", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean nomeEsistente = selectedBacheca.getToDo().stream()
                    .anyMatch(todo -> todo.getTitolo().equalsIgnoreCase(titolo));
            if (nomeEsistente) {
                JOptionPane.showMessageDialog(frame, "Esiste già un ToDo con il titolo '" + titolo + "'.", "Errore Creazione", JOptionPane.ERROR_MESSAGE);
                return;
            }

            selectedBacheca.aggiungiToDo(new ToDo(titolo, data));
            JOptionPane.showMessageDialog(frame, "ToDo '" + titolo + "' creata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);

            bachecaUi(selectedBacheca);
        });
    }



}