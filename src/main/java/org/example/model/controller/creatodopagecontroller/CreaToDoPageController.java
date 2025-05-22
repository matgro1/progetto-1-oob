package org.example.model.controller.creatodopagecontroller;


import javax.swing.*;

public interface CreaToDoPageController {
    void inizializzazione(JSpinner giorno, JSpinner mese, JSpinner anno,JTextField nomeUtenteCondiviso, JLabel condivisoLabel);
    void returnBachecaMainPage();
    void updateScreen(JCheckBox condivisoCheckBox, JTextField nomeUtenteCondiviso, JLabel condivisoLabel);
    void creaToDo(JPanel creaToDoPagePanel, JCheckBox condivisoCheckBox,JTextField titoloField, JTextField nomeUtenteCondiviso , JSpinner giorno, JSpinner mese, JSpinner anno);
}
