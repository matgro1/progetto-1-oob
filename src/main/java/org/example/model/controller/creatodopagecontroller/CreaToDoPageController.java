package org.example.model.controller.creatodopagecontroller;

import org.example.model.Bacheca;
import org.example.model.Utente;

import javax.swing.*;
import java.time.LocalDate;

public interface CreaToDoPageController {
    void inizializzazione(JSpinner giorno, JSpinner mese, JSpinner anno,JTextField nomeUtenteCondiviso, JLabel condivisoLabel);
    void returnBachecaMainPage(JFrame frame, Bacheca bacheca, Utente returnUtente);
    void updateScreen(JCheckBox condivisoCheckBox, JTextField nomeUtenteCondiviso, JLabel condivisoLabel);

}
