package org.example.controller.modificabachecacontroller;

import javax.swing.*;

/**
 * The interface Modifica bacheca controller.
 */
public interface ModificaBachecaController {
    /**
     * Inizializzazione.
     *
     * @param titoloField      the titolo field
     * @param descrizioneField the descrizione field
     */
    void inizializzazione(JTextField titoloField, JTextField descrizioneField);

    /**
     * Ok.
     *
     * @param titoloModificato      the titolo modificato
     * @param descrizioneModificata the descrizione modificata
     */
    void ok(String titoloModificato, String descrizioneModificata);


    boolean cancellaBacheca();
}
