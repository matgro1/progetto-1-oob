package org.example.controller.modificabachecacontroller;

import javax.swing.*;

/**
 * The interface Modifica bacheca controller.
 */
public interface ModificaBachecaController {


    /**
     * Ok.
     *
     * @param titoloModificato      the titolo modificato
     * @param descrizioneModificata the descrizione modificata
     */
    void ok(String titoloModificato, String descrizioneModificata);


    boolean cancellaBacheca();
}
