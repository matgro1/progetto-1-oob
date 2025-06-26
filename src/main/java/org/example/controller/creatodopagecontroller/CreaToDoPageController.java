package org.example.controller.creatodopagecontroller;


import org.example.model.Bacheca;
import org.example.model.ChecklistItem;

import javax.swing.*;

public interface CreaToDoPageController {
    void inizializzazione(JSpinner giorno, JSpinner mese, JSpinner anno, JTextField nomeUtenteCondiviso, JLabel condivisoLabel, JList<ChecklistItem> checkList, JComboBox<Bacheca> comboBacheca);

    void creaToDo(JPanel creaToDoPagePanel, JCheckBox condivisoCheckBox, JTextField titoloField, JTextField nomeUtenteCondiviso, JSpinner giorno, JSpinner mese, JSpinner anno, JList<ChecklistItem> checkList, JComboBox<Bacheca> comboBacheca);
    void aggiungiChecklistItem(JList<ChecklistItem> checkList);
    void returnBachecaMainPage();
    void updateScreen(JCheckBox condivisoCheckBox, JTextField nomeUtenteCondiviso, JLabel condivisoLabel,JComboBox<Bacheca> comboBacheca);
    void updateComboBacheca(JComboBox<Bacheca> comboBacheca, JTextField nomeUtenteCondiviso);
}
