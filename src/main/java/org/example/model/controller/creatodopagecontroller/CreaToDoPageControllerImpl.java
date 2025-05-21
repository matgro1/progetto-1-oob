package org.example.model.controller.creatodopagecontroller;

import org.example.model.Bacheca;
import org.example.model.BachecaMainPage;
import org.example.model.Utente;

import javax.swing.*;
import java.time.LocalDate;

public class CreaToDoPageControllerImpl implements CreaToDoPageController{
    @Override
    public void inizializzazione(JSpinner giorno, JSpinner mese, JSpinner anno, JTextField nomeUtenteCondiviso, JLabel condivisoLabel) {

        SpinnerNumberModel giornoModel = new SpinnerNumberModel(1, 1, 31, 1);
        giorno.setModel(giornoModel);
        SpinnerNumberModel meseModel = new SpinnerNumberModel(1, 1, 12, 1);
        mese.setModel(meseModel);
        SpinnerNumberModel annoModel = new SpinnerNumberModel(2025, 2025, 2050, 1);
        anno.setModel(annoModel);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(anno, "#");
        anno.setEditor(editor);
        condivisoLabel.setVisible(false);
        nomeUtenteCondiviso.setVisible(false);
    }
    @Override
    public void returnBachecaMainPage(JFrame frame, Bacheca bacheca, Utente returnUtente) {
        frame.getContentPane().removeAll();
        frame.setContentPane(new BachecaMainPage(frame, bacheca, returnUtente).getBachecaMainPage());
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void updateScreen(JCheckBox condivisoCheckBox, JTextField nomeUtenteCondiviso, JLabel condivisoLabel) {
       if (condivisoCheckBox.isSelected()){
           condivisoLabel.setVisible(true);
           nomeUtenteCondiviso.setVisible(true);
       }
       else{
           condivisoLabel.setVisible(false);
           nomeUtenteCondiviso.setVisible(false);
       }
    }
}
