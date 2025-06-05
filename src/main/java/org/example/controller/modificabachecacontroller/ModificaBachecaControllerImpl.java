package org.example.controller.modificabachecacontroller;

import org.example.controller.ControllerFather;

import javax.swing.*;

public class ModificaBachecaControllerImpl extends ControllerFather implements ModificaBachecaController{
    @Override
    public void inizializzazione(JTextField titoloField, JTextField descrizioneField) {
        titoloField.setText(bacheca.getNome());
        descrizioneField.setText(bacheca.getDescrizione());
    }

    @Override
    public void ok(String titoloModificato, String descrizioneModificata) {
        if (bacheca != null) {
            bacheca.setTitolo(titoloModificato);
            bacheca.setDescrizione(descrizioneModificata);
        }
    }

}
