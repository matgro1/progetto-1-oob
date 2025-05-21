package org.example.model;

import javax.swing.*;

public class ModificaBacheca extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField titoloField;
    private JTextField descrizioneField;

    // Variabili per conservare i valori modificati
    private String titoloModificato;
    private String descrizioneModificata;
    private boolean modificaConfermata = false;

    private final transient Bacheca bacheca;
    //todo come cazzo ho fatto a programmarlo dopo aver fatto i controller e NON aver usato un controller(top 3 mongoloidi? capiamo)

    public ModificaBacheca(Bacheca bacheca, JFrame frame) {
        super(frame, "Modifica Bacheca", true); // Imposta il frame padre e il titolo
        this.bacheca = bacheca;

        titoloField.setText(bacheca.getNome());
        descrizioneField.setText(bacheca.getDescrizione());
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> {
            titoloModificato = titoloField.getText();
            descrizioneModificata = descrizioneField.getText();
            onOK();
        });

        buttonCancel.addActionListener(e -> onCancel());

        // Configura dimensioni e posizione
        pack();
        setLocationRelativeTo(frame);
    }

    private void onOK() {
        // Modifica il titolo e la descrizione della bacheca
        if (bacheca != null) {
            bacheca.setTitolo(titoloModificato);
            bacheca.setDescrizione(descrizioneModificata);
            modificaConfermata = true;
        }
        dispose();
    }

    private void onCancel() {
        // Imposta i valori modificati a null o ai valori originali
        titoloModificato = null;
        descrizioneModificata = null;
        modificaConfermata = false;
        dispose();
    }

    public boolean isModificaConfermata() {
        return modificaConfermata;
    }
}