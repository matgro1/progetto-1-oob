package org.example.model.controller.creatodopagecontroller;

import org.example.model.*;
import org.example.model.controller.Controller;

import javax.swing.*;
import java.time.LocalDate;

public class CreaToDoPageControllerImpl extends Controller implements CreaToDoPageController{

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
    public void returnBachecaMainPage() {
        frame.getContentPane().removeAll();
        frame.setContentPane(new BachecaMainPage().getBachecaMainPage());
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
    public void creaToDo(JPanel creaToDoPagePanel, JCheckBox condivisoCheckBox,JTextField titoloField, JTextField nomeUtenteCondiviso , JSpinner giorno, JSpinner mese, JSpinner anno){
        int g = (int) giorno.getValue();
        int m = (int) mese.getValue();
        int a = (int) anno.getValue();
        LocalDate data = LocalDate.of(a, m, g);
        if(!condivisoCheckBox.isSelected()) {
            bacheca.getToDo().add(new ToDo(titoloField.getText(), data));
            frame.getContentPane().removeAll();
            frame.setContentPane(new BachecaMainPage().getBachecaMainPage());
            frame.revalidate();
            frame.repaint();
        }
        else {
            Utente utente = null;
            for (Utente u : utenti) if (u.getLogin().compareTo(nomeUtenteCondiviso.getText()) != 0) utente = u;
            if (utente != null) {
                bacheca.getToDo().add(new ToDoCondiviso(titoloField.getText(), data, utente, data));
                frame.getContentPane().removeAll();
                frame.setContentPane(new BachecaMainPage().getBachecaMainPage());
                frame.revalidate();
                frame.repaint();
            }
            else {
                JOptionPane.showMessageDialog(creaToDoPagePanel, "Nome utente non valido!", "Errore di Condivisione", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
