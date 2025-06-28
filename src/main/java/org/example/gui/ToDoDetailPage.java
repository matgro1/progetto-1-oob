package org.example.gui;

import lombok.Getter;
import org.example.controller.tododetailpagecontroller.ToDoDetailPageController;
import org.example.controller.tododetailpagecontroller.ToDoDetailPageControllerImpl;
import org.example.model.ChecklistItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ToDoDetailPage extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    @Getter
    private JLabel nomeToDoLabel;
    private JList<ChecklistItem> list1;
    private JCheckBox completaCheckBox;
    private JLabel dataScadenza; // Dichiarato, ma non inizializzato
    private JLabel ultimaModifica; // Dichiarato, ma non inizializzato
    private JLabel utenteCodiviso; // Dichiarato, ma non inizializzato
    private ToDoDetailPageController controller;

    public ToDoDetailPage() {
        // Chiamata esplicita al costruttore di JDialog, anche se implicita funziona, è buona pratica
        super();
        this.controller = new ToDoDetailPageControllerImpl();

        // --- Inizializzazione e costruzione della GUI ---

        // Inizializzazione del contentPane principale con BorderLayout
        contentPane = new JPanel(new BorderLayout(5, 5));

        // Inizializzazione e configurazione di nomeToDoLabel
        nomeToDoLabel = new JLabel();
        nomeToDoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nomeToDoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        contentPane.add(nomeToDoLabel, BorderLayout.NORTH); // Aggiunto al NORTH del contentPane

        // Inizializzazione di JList e JCheckBox
        list1 = new JList<>();
        completaCheckBox = new JCheckBox("Completa ToDo");
        completaCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
        completaCheckBox.setFont(new Font("Arial", Font.PLAIN, 14));

        // --- Gestione dei JLabel aggiuntivi: Data Scadenza, Ultima Modifica, Utente Condiviso ---
        // Questi vanno inizializzati prima di essere passati al controller.
        // Li mettiamo in un pannello separato per organizzarli visivamente.
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 0, 5)); // GridLayout per allinearli in colonna
        dataScadenza = new JLabel("Data di Scadenza: N/A"); // Inizializzazione
        ultimaModifica = new JLabel("Ultima Modifica: N/A"); // Inizializzazione
        utenteCodiviso = new JLabel("Condiviso con: N/A");   // Inizializzazione

        infoPanel.add(dataScadenza);
        infoPanel.add(ultimaModifica);
        infoPanel.add(utenteCodiviso);

        // Creazione di un pannello centrale per la lista e le informazioni, usando BorderLayout
        // Così puoi mettere la lista al centro e le info magari sotto o a lato
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(list1), BorderLayout.CENTER); // La JList dovrebbe essere in una JScrollPane
        centerPanel.add(completaCheckBox, BorderLayout.SOUTH); // CheckBox sotto la lista

        contentPane.add(centerPanel, BorderLayout.CENTER); // Aggiunto il pannello centrale al contentPane
        contentPane.add(infoPanel, BorderLayout.EAST); // Posiziono le info a destra (o WEST, a seconda del layout desiderato)

        // --- Pannello per i bottoni OK/Annulla ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonOK = new JButton("OK");
        buttonCancel = new JButton("Annulla");
        buttonPanel.add(buttonOK);
        buttonPanel.add(buttonCancel);
        contentPane.add(buttonPanel, BorderLayout.SOUTH); // Aggiunto il pannello dei bottoni al SOUTH del contentPane

        // --- Configurazione generale del JDialog ---
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // --- Inizializzazione della GUI tramite controller ---
        // Ora tutti i componenti passati sono correttamente inizializzati.
        controller.initializeGui(
                list1,
                completaCheckBox,
                nomeToDoLabel,
                contentPane, // Passa il contentPane principale
                dataScadenza,
                ultimaModifica,
                utenteCodiviso
        );

        // --- Listener per i bottoni OK e Cancel ---
        buttonOK.addActionListener(e -> {
            controller.onOkAction();
            dispose();
        });
        buttonCancel.addActionListener(e -> {
            controller.onCancelAction();
            dispose();
        });

        // --- Gestione della chiusura della finestra ---
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // Impedisce la chiusura diretta dalla X
        addWindowListener(new WindowAdapter() { // Listener per la chiusura con la X
            @Override
            public void windowClosing(WindowEvent e) {
                controller.onCancelAction(); // Chiama l'azione di annullamento del controller
                dispose(); // Chiude il dialogo
            }
        });

        // Listener per il tasto ESC (Escape)
        contentPane.registerKeyboardAction(e -> {
            controller.onCancelAction(); // Chiama l'azione di annullamento del controller
            dispose(); // Chiude il dialogo
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        // --- Impostazioni finali del JDialog ---
        pack(); // Adatta la dimensione del dialogo ai suoi contenuti
        setLocationRelativeTo(null); // Centra il dialogo sullo schermo
    }

    // --- Metodi getter per i componenti (utili se il controller o altre parti devono accedervi) ---
    public JList<ChecklistItem> getChecklistJList() { return list1; }
    public JCheckBox getCompletaCheckBox() { return completaCheckBox; }
    public JLabel getDataScadenzaLabel() { return dataScadenza; } // Aggiunto getter per coerenza
    public JLabel getUltimaModificaLabel() { return ultimaModifica; } // Aggiunto getter per coerenza
    public JLabel getUtenteCondivisoLabel() { return utenteCodiviso; } // Aggiunto getter per coerenza


    public void closeDialog() {
        dispose();
    }
}