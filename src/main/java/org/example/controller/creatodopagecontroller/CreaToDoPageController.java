package org.example.controller.creatodopagecontroller;


import org.example.model.Bacheca;
import org.example.model.ChecklistItem;

import javax.swing.*;

/**
 * The interface Crea to do page controller.
 */
public interface CreaToDoPageController {
    /**
     * Inizializzazione.
     *
     * @param giorno              the giorno
     * @param mese                the mese
     * @param anno                the anno
     * @param nomeUtenteCondiviso the nome utente condiviso
     * @param condivisoLabel      the condiviso label
     * @param checkList           the check list
     * @param comboBacheca        the combo bacheca
     * @param clabel              the clabel
     */
    void inizializzazione(JSpinner giorno, JSpinner mese, JSpinner anno, JTextField nomeUtenteCondiviso, JLabel condivisoLabel, JList<ChecklistItem> checkList, JComboBox<Bacheca> comboBacheca, JLabel clabel);

    /**
     * Crea to do.
     *
     * @param creaToDoPagePanel   the crea to do page panel
     * @param condivisoCheckBox   the condiviso check box
     * @param titoloField         the titolo field
     * @param nomeUtenteCondiviso the nome utente condiviso
     * @param giorno              the giorno
     * @param mese                the mese
     * @param anno                the anno
     * @param checkList           the check list
     * @param comboBacheca        the combo bacheca
     */
    void creaToDo(JPanel creaToDoPagePanel, JCheckBox condivisoCheckBox, JTextField titoloField, JTextField nomeUtenteCondiviso, JSpinner giorno, JSpinner mese, JSpinner anno, JList<ChecklistItem> checkList, JComboBox<Bacheca> comboBacheca);

    /**
     * Aggiungi checklist item.
     *
     * @param checkList the check list
     */
    void aggiungiChecklistItem(JList<ChecklistItem> checkList);

    /**
     * Return bacheca main page.
     */
    void returnBachecaMainPage();

    /**
     * Update screen.
     *
     * @param condivisoCheckBox   the condiviso check box
     * @param nomeUtenteCondiviso the nome utente condiviso
     * @param condivisoLabel      the condiviso label
     * @param comboBacheca        the combo bacheca
     * @param clabel              the clabel
     */
    void updateScreen(JCheckBox condivisoCheckBox, JTextField nomeUtenteCondiviso, JLabel condivisoLabel,JComboBox<Bacheca> comboBacheca, JLabel clabel);

    /**
     * Update combo bacheca.
     *
     * @param comboBacheca        the combo bacheca
     * @param nomeUtenteCondiviso the nome utente condiviso
     */
    void updateComboBacheca(JComboBox<Bacheca> comboBacheca, JTextField nomeUtenteCondiviso);
}
