package org.example.model.controller.creatodopagecontroller;


import org.example.model.ChecklistItem;

import javax.swing.*;

public interface CreaToDoPageController {
    void inizializzazione(JSpinner giorno, JSpinner mese, JSpinner anno,JTextField nomeUtenteCondiviso, JLabel condivisoLabel,JList checkList);

    void creaToDo(JPanel creaToDoPagePanel, JCheckBox condivisoCheckBox, JTextField titoloField, JTextField nomeUtenteCondiviso, JSpinner giorno, JSpinner mese, JSpinner anno, JList<ChecklistItem> checkList);
    void aggiungiChecklistItem(JList checkList);
    void returnBachecaMainPage();
    void updateScreen(JCheckBox condivisoCheckBox, JTextField nomeUtenteCondiviso, JLabel condivisoLabel);
}
