package org.example.controller.modificabachecacontroller;

import org.example.controller.ControllerFather;
import org.example.controller.SessionManager;
import org.example.database.DatabaseConnection;
import org.example.model.Bacheca;

import javax.swing.*;

/**
 * The type Modifica bacheca controller.
 */
public class ModificaBachecaControllerImpl extends ControllerFather implements ModificaBachecaController {

    @Override
    public void inizializzazione(JTextField titoloField, JTextField descrizioneField) {
        Bacheca bacheca = SessionManager.getInstance().getCurrentBacheca();
        if (bacheca != null) {
            titoloField.setText(bacheca.getTitolo());
            descrizioneField.setText(bacheca.getDescrizione());
        }
    }

    @Override
    public void ok(String titoloModificato, String descrizioneModificata) {
        Bacheca bacheca = SessionManager.getInstance().getCurrentBacheca();
        if (bacheca != null) {
            bacheca.setTitolo(titoloModificato);
            bacheca.setDescrizione(descrizioneModificata);
            DatabaseConnection.bachecaDB.update(bacheca);
        }
    }
}