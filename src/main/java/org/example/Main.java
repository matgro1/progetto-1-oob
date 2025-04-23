package org.example;

import org.example.model.*; // Importa tutte le classi dal tuo modello

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    // Usa le classi Utente e Bacheca del tuo modello
    private static List<Utente> utenti = new ArrayList<>();
    private static Utente utenteCorrente = null; // Inizializza a null
    private static JFrame frame;

    public static void main(String[] args) {
        // Esegui l'interfaccia grafica nel thread di Event Dispatching
        SwingUtilities.invokeLater(() -> {
            // Aggiungiamo qualche utente di esempio per testare (rimuovere se hai persistenza)
            utenti.add(new Utente("test", "test"));
            utenti.add(new Utente("user", "pass"));

            frame = new JFrame("Gestore ToDo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null); // Centra la finestra

            showLoginPanel(); // Mostra il pannello di login all'avvio

            frame.setVisible(true);
        });
    }

    private static void showLoginPanel() {
        // Logout utente precedente se si torna alla schermata di login
        if (utenteCorrente != null) {
            utenteCorrente.logout(); // Assicura che successoAccesso sia false
            utenteCorrente = null;
        }

        JPanel panelLogin = new JPanel();
        panelLogin.setLayout(new BoxLayout(panelLogin, BoxLayout.Y_AXIS));
        panelLogin.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Aggiunge più spazio esterno

        JTextField usernameField = new JTextField(15); // Dimensione suggerita
        JPasswordField passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        // Allineamento e spaziatura migliorati
        Dimension fieldMaxSize = new Dimension(Integer.MAX_VALUE, usernameField.getPreferredSize().height);
        usernameField.setMaximumSize(fieldMaxSize);
        passwordField.setMaximumSize(fieldMaxSize);

        // Centro i bottoni
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Crea un pannello per i campi per un miglior controllo del layout (opzionale ma utile)
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Allinea il pannello dei campi al centro
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

        // Rimuove i componenti precedenti e aggiunge il nuovo pannello
        updateFrameContent(panelLogin);

        registerButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Username e password non possono essere vuoti.", "Errore Registrazione", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Verifica se l'utente esiste già (Case-insensitive per username è spesso una buona idea)
            boolean esisteGia = false;
            for (Utente utente : utenti) {
                if (utente.getLogin().equalsIgnoreCase(username)) { // Usa equalsIgnoreCase
                    esisteGia = true;
                    break;
                }
            }

            if (esisteGia) {
                JOptionPane.showMessageDialog(frame, "L'utente '" + username + "' esiste già.", "Errore Registrazione", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Aggiungi un nuovo utente
            utenti.add(new Utente(username, password));
            JOptionPane.showMessageDialog(frame, "Registrazione riuscita per l'utente '" + username + "'!", "Registrazione Completata", JOptionPane.INFORMATION_MESSAGE);
            // Opzionale: pulisci i campi dopo la registrazione
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
                // --- CORREZIONE CHIAVE: Usa il metodo accesso() dell'utente ---
                utente.accesso(username, password); // Tenta l'accesso
                if (utente.isAccesso()) { // Verifica se l'accesso è riuscito (successoAccesso = true)
                    utenteTrovato = utente;
                    break; // Esci dal ciclo appena trovato l'utente
                }
                // --- FINE CORREZIONE ---
            }

            if (utenteTrovato != null) {
                utenteCorrente = utenteTrovato; // Imposta l'utente corrente globale
                showDashboard(); // Passa alla dashboard
            } else {
                JOptionPane.showMessageDialog(frame, "Username o password errati.", "Login Fallito", JOptionPane.ERROR_MESSAGE);
                // Assicurati che nessun utente rimanga "loggato" erroneamente se il ciclo finisce senza successo
                // (anche se il reset all'inizio di showLoginPanel dovrebbe già gestirlo)
                if (utenteCorrente != null) {
                    utenteCorrente.logout();
                    utenteCorrente = null;
                }
            }
        });
    }

    private static void showDashboard() {
        if (utenteCorrente == null || !utenteCorrente.isAccesso()) {
            // Sicurezza: non mostrare la dashboard se l'utente non è loggato correttamente
            showLoginPanel();
            JOptionPane.showMessageDialog(frame, "Errore: Utente non autenticato.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JPanel dashboardPanel = new JPanel(new BorderLayout(10, 10)); // Usa BorderLayout per una struttura migliore
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Pannello Superiore: Saluto e Logout ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel dashboardLabel = new JLabel("Benvenuto, " + utenteCorrente.getLogin() + "!");
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> showLoginPanel()); // Torna al login
        topPanel.add(dashboardLabel);
        topPanel.add(Box.createHorizontalStrut(20)); // Spazio
        topPanel.add(logoutButton);

        // --- Pannello Centrale: Lista Bacheche e Creazione ---
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JButton creaBachecaButton = new JButton("Crea Nuova Bacheca");
        creaBachecaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        creaBachecaButton.addActionListener(e -> showCreaBachecaPanel());

        JLabel bachecheLabel = new JLabel("Le tue bacheche:");
        bachecheLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- Lista Bacheche ---
        // Usa un DefaultListModel per poter aggiornare la lista dinamicamente più facilmente
        DefaultListModel<Bacheca> listModel = new DefaultListModel<>();
        utenteCorrente.getBacheche().forEach(listModel::addElement); // Popola il modello

        JList<Bacheca> bachecheList = new JList<>(listModel);
        bachecheList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Seleziona solo una bacheca alla volta


        bachecheList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Esegui solo quando la selezione è stabile
                Bacheca selectedBacheca = bachecheList.getSelectedValue();
                if (selectedBacheca != null) {
                    bachecaUi();
                }
            }
        });


        JScrollPane bachecheScrollPane = new JScrollPane(bachecheList);
        bachecheScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        bachecheScrollPane.setPreferredSize(new Dimension(300, 200)); // Dimensione esempio
        bachecheScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300)); // Limita altezza massima


        centerPanel.add(creaBachecaButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(bachecheLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        centerPanel.add(bachecheScrollPane);

        // --- Aggiunta Pannelli al Frame ---
        dashboardPanel.add(topPanel, BorderLayout.NORTH);
        dashboardPanel.add(centerPanel, BorderLayout.CENTER);

        updateFrameContent(dashboardPanel);
    }

    private static void showCreaBachecaPanel() {
        if (utenteCorrente == null || !utenteCorrente.isAccesso()) {
            // Sicurezza
            showLoginPanel();
            return;
        }

        JPanel creaBachecaPanel = new JPanel();
        creaBachecaPanel.setLayout(new BoxLayout(creaBachecaPanel, BoxLayout.Y_AXIS));
        creaBachecaPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JTextField nomeBField = new JTextField(20);
        // Usa JTextArea per la descrizione se può essere multiriga
        JTextArea descrizioneBField = new JTextArea(5, 20);
        descrizioneBField.setLineWrap(true); // Va a capo automaticamente
        descrizioneBField.setWrapStyleWord(true); // Va a capo per parole intere
        JScrollPane descScrollPane = new JScrollPane(descrizioneBField); // Scroll per descrizioni lunghe

        JButton creaButton = new JButton("Crea Bacheca");
        JButton annullaButton = new JButton("Annulla");

        // Allineamento
        Dimension fieldMaxSize = new Dimension(Integer.MAX_VALUE, nomeBField.getPreferredSize().height);
        nomeBField.setMaximumSize(fieldMaxSize);
        descScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150)); // Limita dimensione JTextArea
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
            // Torna alla dashboard senza creare la bacheca
            showDashboard();
        });

        creaButton.addActionListener(e -> {
            String titolo = nomeBField.getText().trim();
            String descrizione = descrizioneBField.getText().trim(); // Ottieni testo da JTextArea

            if (titolo.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Il titolo della bacheca non può essere vuoto.", "Errore Creazione", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Verifica se una bacheca con lo stesso nome esiste già per questo utente
            boolean nomeEsistente = utenteCorrente.getBacheche().stream()
                    .anyMatch(b -> b.getNome().equalsIgnoreCase(titolo));
            if(nomeEsistente) {
                JOptionPane.showMessageDialog(frame, "Esiste già una bacheca con il titolo '" + titolo + "'.", "Errore Creazione", JOptionPane.ERROR_MESSAGE);
                return;
            }


            // Utilizza il metodo creaBacheca() della tua classe Utente
            // Funzionerà perché ora il login imposta correttamente successoAccesso
            utenteCorrente.creaBacheca(titolo, descrizione);

            JOptionPane.showMessageDialog(frame, "Bacheca '" + titolo + "' creata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);

            // Torna alla dashboard dopo la creazione (la lista verrà rigenerata)
            showDashboard();
        });
    }

    // Metodo helper per aggiornare il contenuto del frame
    private static void updateFrameContent(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }



    private static void bachecaUi(){
        JPanel bachecaUiPanel= new JPanel();
        updateFrameContent(bachecaUiPanel);

    }



}