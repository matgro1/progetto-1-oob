package org.example.controller.modificabachecacontroller;

import javax.swing.*;

public interface ModificaBachecaController {
    void inizializzazione(JTextField titoloField, JTextField descrizioneField);
    void ok(String titoloModificato, String descrizioneModificata);
}
